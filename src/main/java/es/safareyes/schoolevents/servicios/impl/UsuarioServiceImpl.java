package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.dto.request.UsuarioRequest;
import es.safareyes.schoolevents.dto.response.UsuarioResponse;
import es.safareyes.schoolevents.mapper.UsuarioMapper;
import es.safareyes.schoolevents.modelos.Curso;
import es.safareyes.schoolevents.modelos.Usuario;
import es.safareyes.schoolevents.repositorios.ICursoRepository;
import es.safareyes.schoolevents.repositorios.IUsuarioRepository;
import es.safareyes.schoolevents.servicios.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final ICursoRepository cursoRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository,
                              ICursoRepository cursoRepository,
                              UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.usuarioMapper = usuarioMapper;
    }

    // 1. LISTAR TODOS
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarTodos() {

        List<Usuario> listaEntidades = usuarioRepository.findAll();
        List<UsuarioResponse> listaRespuestas = new ArrayList<>();

        for (Usuario usuario : listaEntidades) {
            UsuarioResponse dto = usuarioMapper.aResponse(usuario);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 2. BUSCAR POR ID
    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Integer id) {

        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            throw new RuntimeException("No existe ningún usuario con el ID: " + id);
        }

        UsuarioResponse dto = usuarioMapper.aResponse(usuario);
        return dto;
    }

    // 3. CREAR USUARIO
    @Override
    @Transactional
    public UsuarioResponse crear(UsuarioRequest request) {

        // DTO -> Entidad
        Usuario nuevoUsuario = usuarioMapper.aEntidad(request);

        // Si la petición viene con idCurso, buscamos la entidad Curso en PostgreSQL
        if (request.idCurso() != null) {
            Curso cursoEncontrado = cursoRepository.findById(request.idCurso()).orElse(null);

            if (cursoEncontrado == null) {
                throw new RuntimeException("No existe el curso con el ID: " + request.idCurso());
            }

            nuevoUsuario.setCurso(cursoEncontrado);
        }

        // Guardamos en la base de datos
        Usuario guardado = usuarioRepository.save(nuevoUsuario);

        // Entidad -> DTO de respuesta
        UsuarioResponse dto = usuarioMapper.aResponse(guardado);
        return dto;
    }

    // 4. ACTUALIZAR USUARIO
    @Override
    @Transactional
    public UsuarioResponse actualizar(Integer id, UsuarioRequest request) {

        // Buscamos si el usuario a modificar existe
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);

        if (usuarioExistente == null) {
            throw new RuntimeException("No se puede actualizar. No existe el usuario con ID: " + id);
        }

        // Actualizamos los campos directos coincidiendo con UsuarioRequest
        usuarioExistente.setNombre(request.nombre());
        usuarioExistente.setApellido1(request.apellido1());
        usuarioExistente.setApellido2(request.apellido2());
        usuarioExistente.setEmail(request.email());
        usuarioExistente.setUsername(request.username());
        usuarioExistente.setPassword(request.password());
        usuarioExistente.setFechanac(request.fechanac());

        // Actualizamos la relación con el Curso
        if (request.idCurso() != null) {
            Curso cursoEncontrado = cursoRepository.findById(request.idCurso()).orElse(null);

            if (cursoEncontrado == null) {
                throw new RuntimeException("No existe el curso con el ID: " + request.idCurso());
            }

            usuarioExistente.setCurso(cursoEncontrado);
        } else {
            // Si viene null, desvinculamos el curso del usuario
            usuarioExistente.setCurso(null);
        }

        // Guardamos los cambios
        Usuario actualizado = usuarioRepository.save(usuarioExistente);

        // Entidad -> DTO de respuesta
        UsuarioResponse dto = usuarioMapper.aResponse(actualizado);
        return dto;
    }

    // 5. ELIMINAR USUARIO
    @Override
    @Transactional
    public void eliminar(Integer id) {

        boolean existe = usuarioRepository.existsById(id);

        if (!existe) {
            throw new RuntimeException("No se puede eliminar. No existe el usuario con ID: " + id);
        }

        usuarioRepository.deleteById(id);
    }
}