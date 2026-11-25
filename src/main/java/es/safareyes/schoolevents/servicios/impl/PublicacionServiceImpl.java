package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.Errores.RecursoNoEncontradoException;
import es.safareyes.schoolevents.dto.request.PublicacionRequest;
import es.safareyes.schoolevents.dto.response.PublicacionResponse;
import es.safareyes.schoolevents.mapper.PublicacionMapper;
import es.safareyes.schoolevents.modelos.*;
import es.safareyes.schoolevents.repositorios.*;
import es.safareyes.schoolevents.servicios.PublicacionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PublicacionServiceImpl implements PublicacionService {

    private IPublicacionRepository publicacionRepository;
    private IEventoRepository eventoRepository;
    private IUsuarioRepository usuarioRepository;
    private IFotoRepository fotoRepository;
    private IInscripcionRepository inscripcionRepository;

    private PublicacionMapper publicacionMapper;

    // 1. LISTAR TODAS LAS PUBLICACIONES
    @Override
    @Transactional(readOnly = true)
    public List<PublicacionResponse> listarTodas() {

        List<Publicacion> listaEntidades = publicacionRepository.findAll();
        List<PublicacionResponse> listaRespuestas = new ArrayList<>();

        for (Publicacion publicacion : listaEntidades) {
            PublicacionResponse dto = publicacionMapper.aResponse(publicacion);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 2. LISTAR PUBLICACIONES POR EVENTO
    @Override
    @Transactional(readOnly = true)
    public List<PublicacionResponse> listarPorEvento(Integer eventoId) {

        List<Publicacion> listaEntidades = publicacionRepository.findByEventoId(eventoId);
        List<PublicacionResponse> listaRespuestas = new ArrayList<>();

        for (Publicacion publicacion : listaEntidades) {
            PublicacionResponse dto = publicacionMapper.aResponse(publicacion);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 3. LISTAR PUBLICACIONES POR USUARIO
    @Override
    @Transactional(readOnly = true)
    public List<PublicacionResponse> listarPorUsuario(Integer usuarioId) {

        List<Publicacion> listaEntidades = publicacionRepository.findByUsuarioId(usuarioId);
        List<PublicacionResponse> listaRespuestas = new ArrayList<>();

        for (Publicacion publicacion : listaEntidades) {
            PublicacionResponse dto = publicacionMapper.aResponse(publicacion);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 4. BUSCAR PUBLICACIÓN POR ID
    @Override
    @Transactional(readOnly = true)
    public PublicacionResponse buscarPorId(Integer id) {

        Publicacion publicacion = publicacionRepository.findById(id).orElse(null);

        if (publicacion == null) {
            throw new RecursoNoEncontradoException("No existe ninguna publicación con el ID: " + id);
        }

        PublicacionResponse dto = publicacionMapper.aResponse(publicacion);
        return dto;
    }

    // 5. CREAR PUBLICACIÓN
    @Override
    @Transactional
    public PublicacionResponse crear(PublicacionRequest request) {

        // 1. Buscar el evento
        Evento evento = eventoRepository.findById(request.idEvento()).orElse(null);

        if (evento == null) {
            throw new RecursoNoEncontradoException(
                    "No existe el evento con el ID: " + request.idEvento()
            );
        }

        // Comprobar que el evento está finalizado
        if (evento.getEstado() != Evento.estadoevento.FINALIZADO) {
            throw new RuntimeException(
                    "Solo se pueden publicar resultados de eventos finalizados"
            );
        }

        // busca el usuario
        Usuario usuario = usuarioRepository.findById(request.idUsuario()).orElse(null);

        if (usuario == null) {
            throw new RecursoNoEncontradoException(
                    "No existe el usuario con el ID: " + request.idUsuario()
            );
        }

        // comprueba si el usuario es el organizador
        boolean esOrganizador = evento.getOrganizador() != null
                && evento.getOrganizador().getId().equals(usuario.getId());

        //  Si no es el organizador se comprueba su inscripción
        if (!esOrganizador) {

            Inscripcion inscripcion = inscripcionRepository.findByEventoIdAndUsuarioId(evento.getId(), usuario.getId())
                    .orElse(null);

            if (inscripcion == null
                    || inscripcion.getEstado() != Inscripcion.estadoinscripcion.CONFIRMADA) {

                throw new RuntimeException(
                        "Solo el organizador o los estudiantes con inscripción confirmada pueden publicar resultados"
                );
            }
        }

        // 6. Convertir el DTO en entidad
        Publicacion nuevaPublicacion = publicacionMapper.aEntidad(request);

        nuevaPublicacion.setFecha(new Date());
        nuevaPublicacion.setEvento(evento);
        nuevaPublicacion.setUsuario(usuario);

        // 7. Guardar la publicación
        Publicacion guardada = publicacionRepository.save(nuevaPublicacion);

        // 8. Guardar las fotografías
        if (request.fotos() != null) {

            if (request.fotos().size() > 10) {
                throw new RuntimeException("No puedes adjuntar más de 10 fotografías");
            }

            List<Foto> fotos = new ArrayList<>();

            for (String url : request.fotos()) {

                Foto foto = new Foto();
                foto.setUrl(url);
                foto.setPublicacion(guardada);

                fotos.add(foto);
            }

            fotoRepository.saveAll(fotos);

            guardada.setFotos(fotos);
        }

        // 9. Devolver la respuesta
        return publicacionMapper.aResponse(guardada);
    }

    // 6. ACTUALIZAR PUBLICACIÓN
    @Override
    @Transactional
    public PublicacionResponse actualizar(Integer id, Integer usuarioId, PublicacionRequest request) {

        // 1. Comprobar si la publicación existe
        Publicacion publicacion = publicacionRepository.findById(id).orElse(null);

        if (publicacion == null) {
            throw new RecursoNoEncontradoException("No existe una publicación con ese ID");
        }

        // 2. Comprobar si el usuario existe
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null) {
            throw new RecursoNoEncontradoException("No existe un usuario con ese ID");
        }

        // 3. Comprobar que el usuario es el autor de la publicación
        if (!publicacion.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Solo el autor puede modificar esta publicación");
        }

        // 4. Actualizar los datos de la publicación
        publicacion.setTitulo(request.titulo());
        publicacion.setDescripcion(request.descripcion());

        // 5. Actualizar las fotografías
        if (request.fotos() != null) {

            if (request.fotos().size() > 10) {
                throw new RuntimeException(
                        "No puedes adjuntar más de 10 fotografías"
                );
            }

            // Eliminar las fotografías anteriores
            fotoRepository.deleteAll(
                    fotoRepository.findByPublicacionId(id)
            );

            List<Foto> fotosActualizadas = new ArrayList<>();

            // Crear las nuevas fotografías
            for (String url : request.fotos()) {

                Foto foto = new Foto();
                foto.setUrl(url);
                foto.setPublicacion(publicacion);

                fotosActualizadas.add(foto);
            }

            // Guardar las nuevas fotografías
            List<Foto> fotosGuardadas = fotoRepository.saveAll(fotosActualizadas);

            publicacion.setFotos(fotosGuardadas);
        }

        // 6. Guardar los cambios de la publicación
        Publicacion actualizada = publicacionRepository.save(publicacion);

        // 7. Devolver la publicación actualizada
        return publicacionMapper.aResponse(actualizada);
    }

    // 7. ELIMINAR PUBLICACIÓN
    @Override
    @Transactional
    public void eliminar(Integer id, Integer usuarioId) {

        // 1. Buscar la publicación
        Publicacion publicacion = publicacionRepository.findById(id).orElse(null);

        if (publicacion == null) {
            throw new RecursoNoEncontradoException(
                    "No existe la publicación con el ID: " + id
            );
        }

        // 2. Comprobar que el usuario existe
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null) {
            throw new RecursoNoEncontradoException(
                    "No existe el usuario con el ID: " + usuarioId
            );
        }

        // 3. Comprobar que el usuario es el autor
        if (!publicacion.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException(
                    "No puedes eliminar una publicación de otro usuario"
            );
        }

        // 4. Eliminar las fotografías asociadas
        fotoRepository.deleteAll(
                fotoRepository.findByPublicacionId(id)
        );

        // 5. Eliminar la publicación
        publicacionRepository.delete(publicacion);
    }
}