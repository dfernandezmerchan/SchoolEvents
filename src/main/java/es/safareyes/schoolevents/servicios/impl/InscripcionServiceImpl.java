package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.dto.request.InscripcionRequest;
import es.safareyes.schoolevents.dto.response.InscripcionResponse;
import es.safareyes.schoolevents.mapper.InscripcionMapper;
import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.modelos.Inscripcion;
import es.safareyes.schoolevents.modelos.Usuario;
import es.safareyes.schoolevents.repositorios.*;
import es.safareyes.schoolevents.servicios.InscripcionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    // Necesitamos 3 repositorios (Inscripcion, Usuario y Evento) y el Mapper
    private final IInscripcionRepository inscripcionRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IEventoRepository eventoRepository;
    private final InscripcionMapper inscripcionMapper;

    public InscripcionServiceImpl(IInscripcionRepository inscripcionRepository,
                                  IUsuarioRepository usuarioRepository,
                                  IEventoRepository eventoRepository,
                                  InscripcionMapper inscripcionMapper) {
        this.inscripcionRepository = inscripcionRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.inscripcionMapper = inscripcionMapper;
    }

    // 1. INSCRIBIR A UN USUARIO EN UN EVENTO
    @Override
    @Transactional
    public InscripcionResponse inscribirse(InscripcionRequest request) {

        // PASO A: Comprobar si el usuario existe en PostgreSQL
        Usuario usuario = usuarioRepository.findById(request.idUsuario()).orElse(null);
        if (usuario == null) {
            throw new RuntimeException("No existe ningún usuario con el ID: " + request.idUsuario());
        }

        // PASO B: Comprobar si el evento existe en PostgreSQL
        Evento evento = eventoRepository.findById(request.idEvento()).orElse(null);
        if (evento == null) {
            throw new RuntimeException("No existe ningún evento con el ID: " + request.idEvento());
        }

        // PASO C: Convertir el DTO Request a la entidad Inscripcion
        Inscripcion nuevaInscripcion = inscripcionMapper.aEntidad(request);

        // PASO D: Asignar las dos entidades relacionadas
        nuevaInscripcion.setUsuario(usuario);
        nuevaInscripcion.setEvento(evento);

        // PASO E: Guardar la inscripción en PostgreSQL
        Inscripcion guardada = inscripcionRepository.save(nuevaInscripcion);

        // PASO F: Convertir a DTO de respuesta y devolver
        InscripcionResponse respuestaDto = inscripcionMapper.aResponse(guardada);
        return respuestaDto;
    }

    // 2. CANCELAR INCRIPCIÓN POR EVENTO Y USUARIO
    @Override
    @Transactional
    public void cancelarInscripcion(Integer idEvento, Integer idUsuario) {

        // PASO A: Buscar la inscripción correspondiente a ese evento y usuario
        Inscripcion inscripcion = inscripcionRepository
                .findByEventoIdAndUsuarioId(idEvento, idUsuario)
                .orElse(null);

        // PASO B: Comprobar si existía la inscripción
        if (inscripcion == null) {
            throw new RuntimeException("No se encontró la inscripción para el usuario "
                    + idUsuario + " en el evento " + idEvento);
        }

        // PASO C: Eliminar de la base de datos
        inscripcionRepository.delete(inscripcion);
    }

    // 3. LISTAR INSCRIPCIONES DE UN EVENTO
    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listarPorEvento(Integer idEvento) {

        // PASO A: Traer de PostgreSQL la lista de inscripciones para este evento
        List<Inscripcion> listaEntidades = inscripcionRepository.findByEventoId(idEvento);

        // PASO B: Lista vacía para guardar los DTOs
        List<InscripcionResponse> listaRespuestas = new ArrayList<>();

        // PASO C: Bucle for tradicional para mapear cada inscripción
        for (Inscripcion inscripcion : listaEntidades) {
            InscripcionResponse dto = inscripcionMapper.aResponse(inscripcion);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 4. LISTAR INSCRIPCIONES DE UN USUARIO
    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listarPorUsuario(Integer idUsuario) {

        // PASO A: Traer de PostgreSQL la lista de inscripciones de este usuario
        List<Inscripcion> listaEntidades = inscripcionRepository.findByUsuarioId(idUsuario);

        // PASO B: Lista vacía para guardar los DTOs
        List<InscripcionResponse> listaRespuestas = new ArrayList<>();

        // PASO C: Bucle for tradicional para mapear cada inscripción
        for (Inscripcion inscripcion : listaEntidades) {
            InscripcionResponse dto = inscripcionMapper.aResponse(inscripcion);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 5. BUSCAR UNA INSCRIPCIÓN POR SU ID
    @Override
    @Transactional(readOnly = true)
    public InscripcionResponse buscarPorId(Integer id) {

        Inscripcion inscripcion = inscripcionRepository.findById(id).orElse(null);

        if (inscripcion == null) {
            throw new RuntimeException("No existe ninguna inscripción con el ID: " + id);
        }

        InscripcionResponse dto = inscripcionMapper.aResponse(inscripcion);
        return dto;
    }
}