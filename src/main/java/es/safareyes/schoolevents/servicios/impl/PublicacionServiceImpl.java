package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.dto.request.PublicacionRequest;
import es.safareyes.schoolevents.dto.response.PublicacionResponse;
import es.safareyes.schoolevents.mapper.PublicacionMapper;
import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.modelos.Publicacion;
import es.safareyes.schoolevents.modelos.Usuario;
import es.safareyes.schoolevents.repositorios.IEventoRepository;
import es.safareyes.schoolevents.repositorios.IPublicacionRepository;
import es.safareyes.schoolevents.repositorios.IUsuarioRepository;
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
            throw new RuntimeException("No existe ninguna publicación con el ID: " + id);
        }

        PublicacionResponse dto = publicacionMapper.aResponse(publicacion);
        return dto;
    }

    // 5. CREAR PUBLICACIÓN
    @Override
    @Transactional
    public PublicacionResponse crear(PublicacionRequest request) {

        // DTO -> Entidad
        Publicacion nuevaPublicacion = publicacionMapper.aEntidad(request);

        // Se asigna automáticamente la fecha actual del sistema
        nuevaPublicacion.setFecha(new Date());

        // Validar y asignar Evento
        if (request.idEvento() != null) {
            Evento evento = eventoRepository.findById(request.idEvento()).orElse(null);
            if (evento == null) {
                throw new RuntimeException("No existe el evento con el ID: " + request.idEvento());
            }
            nuevaPublicacion.setEvento(evento);
        }

        // Validar y asignar Usuario
        if (request.idUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(request.idUsuario()).orElse(null);
            if (usuario == null) {
                throw new RuntimeException("No existe el usuario con el ID: " + request.idUsuario());
            }
            nuevaPublicacion.setUsuario(usuario);
        }

        Publicacion guardada = publicacionRepository.save(nuevaPublicacion);

        PublicacionResponse dto = publicacionMapper.aResponse(guardada);
        return dto;
    }

    // 6. ACTUALIZAR PUBLICACIÓN
    @Override
    @Transactional
    public PublicacionResponse actualizar(Integer id, PublicacionRequest request) {

        Publicacion publicacionExistente = publicacionRepository.findById(id).orElse(null);

        if (publicacionExistente == null) {
            throw new RuntimeException("No se puede actualizar. No existe la publicación con ID: " + id);
        }

        // Actualizar datos propios (se mantiene la fecha original de publicación intacta)
        publicacionExistente.setTitulo(request.titulo());
        publicacionExistente.setDescripcion(request.descripcion());

        // Actualizar relación Evento
        if (request.idEvento() != null) {
            Evento evento = eventoRepository.findById(request.idEvento()).orElse(null);
            if (evento == null) {
                throw new RuntimeException("No existe el evento con el ID: " + request.idEvento());
            }
            publicacionExistente.setEvento(evento);
        } else {
            publicacionExistente.setEvento(null);
        }

        // Actualizar relación Usuario
        if (request.idUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(request.idUsuario()).orElse(null);
            if (usuario == null) {
                throw new RuntimeException("No existe el usuario con el ID: " + request.idUsuario());
            }
            publicacionExistente.setUsuario(usuario);
        } else {
            publicacionExistente.setUsuario(null);
        }

        Publicacion actualizada = publicacionRepository.save(publicacionExistente);

        PublicacionResponse dto = publicacionMapper.aResponse(actualizada);
        return dto;
    }

    // 7. ELIMINAR PUBLICACIÓN
    @Override
    @Transactional
    public void eliminar(Integer id) {

        boolean existe = publicacionRepository.existsById(id);

        if (!existe) {
            throw new RuntimeException("No se puede eliminar. No existe la publicación con ID: " + id);
        }

        publicacionRepository.deleteById(id);
    }
}