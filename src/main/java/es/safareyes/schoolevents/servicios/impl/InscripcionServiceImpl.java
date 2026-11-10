package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.Errores.RecursoNoEncontradoException;
import es.safareyes.schoolevents.Errores.ReglaNegocioException;
import es.safareyes.schoolevents.dto.request.InscripcionRequest;
import es.safareyes.schoolevents.dto.response.InscripcionResponse;
import es.safareyes.schoolevents.mapper.InscripcionMapper;
import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.modelos.Inscripcion;
import es.safareyes.schoolevents.modelos.Usuario;
import es.safareyes.schoolevents.repositorios.*;
import es.safareyes.schoolevents.servicios.InscripcionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InscripcionServiceImpl implements InscripcionService {

    // Necesitamos 3 repositorios (Inscripcion, Usuario y Evento) y el Mapper
    private IInscripcionRepository inscripcionRepository;
    private IUsuarioRepository usuarioRepository;
    private IEventoRepository eventoRepository;
    private InscripcionMapper inscripcionMapper;

    // 1. INSCRIBIR A UN USUARIO EN UN EVENTO
    @Override
    @Transactional
    public InscripcionResponse inscribirse(InscripcionRequest request) {

        // PASO A: Comprobar si el usuario existe en PostgreSQL
        Usuario usuario = usuarioRepository.findById(request.idUsuario()).orElse(null);

        if (usuario == null) {
            throw new RecursoNoEncontradoException(
                    "No existe ningún usuario con el ID: " + request.idUsuario()
            );
        }

        // PASO B: Comprobar si el evento existe en PostgreSQL
        Evento evento = eventoRepository.findById(request.idEvento()).orElse(null);

        if (evento == null) {
            throw new RecursoNoEncontradoException(
                    "No existe ningún evento con el ID: " + request.idEvento()
            );
        }

        // PASO C: Convertir el DTO Request a la entidad Inscripcion
        Inscripcion nuevaInscripcion = inscripcionMapper.aEntidad(request);

        // PASO D: Asignar usuario y evento
        nuevaInscripcion.setUsuario(usuario);
        nuevaInscripcion.setEvento(evento);

        // PASO E: Comprobar cuántas plazas están ocupadas
        long confirmadas = inscripcionRepository.countByEventoIdAndEstado(
                evento.getId(),
                Inscripcion.estadoinscripcion.CONFIRMADA
        );

        // Si hay plazas disponibles, se confirma.
        // Si el aforo está lleno, queda en espera.
        if (confirmadas < evento.getLugar().getAforomaximo()) {
            nuevaInscripcion.setEstado(
                    Inscripcion.estadoinscripcion.CONFIRMADA
            );
        } else {
            nuevaInscripcion.setEstado(
                    Inscripcion.estadoinscripcion.EN_ESPERA
            );
        }

        // Fecha de la inscripción
        nuevaInscripcion.setFecha(LocalDateTime.now());

        // PASO F: Guardar la inscripción
        Inscripcion guardada = inscripcionRepository.save(nuevaInscripcion);

        // PASO G: Convertir a DTO de respuesta
        InscripcionResponse respuestaDto = inscripcionMapper.aResponse(guardada);

        return respuestaDto;
    }

    // 2. CANCELAR INCRIPCIÓN POR EVENTO Y USUARIO
    @Override
    @Transactional
    public void cancelarInscripcion(Integer eventoId, Integer usuarioId) {

        // Buscar la inscripción del usuario en el evento
        Inscripcion inscripcion = inscripcionRepository
                .findByEventoIdAndUsuarioId(eventoId, usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una inscripción para este usuario en el evento"
                ));

        // Comprobar que no esté ya cancelada
        if (inscripcion.getEstado() == Inscripcion.estadoinscripcion.CANCELADA) {
            throw new ReglaNegocioException(
                    "La inscripción ya está cancelada"
            );
        }

        // Cancelar la inscripción
        inscripcion.setEstado(Inscripcion.estadoinscripcion.CANCELADA);
        inscripcionRepository.save(inscripcion);

        // Buscar a la primera persona de la lista de espera
        Optional<Inscripcion> siguiente = inscripcionRepository
                .findFirstByEventoIdAndEstadoOrderByFechaAsc(
                        eventoId,
                        Inscripcion.estadoinscripcion.EN_ESPERA
                );

        // Si hay alguien esperando, pasa a estar confirmado
        if (siguiente.isPresent()) {

            Inscripcion inscripcionSiguiente = siguiente.get();

            inscripcionSiguiente.setEstado(
                    Inscripcion.estadoinscripcion.CONFIRMADA
            );

            inscripcionRepository.save(inscripcionSiguiente);
        }
    }

    // 3. LISTAR INSCRIPCIONES DE UN EVENTO
    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listarPorEvento(Integer idEvento) {

        List<Inscripcion> listaEntidades = inscripcionRepository.findByEventoId(idEvento);

        List<InscripcionResponse> listaRespuestas = new ArrayList<>();

        for (Inscripcion inscripcion : listaEntidades) {
            InscripcionResponse dto = inscripcionMapper.aResponse(inscripcion);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 4. agenda usuario
    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> agendaUsuario(Integer idUsuario) {

        if(!usuarioRepository.existsById(idUsuario)) {
            throw new RecursoNoEncontradoException("No se ha encontrad ningún usuario con id " + idUsuario);
        }

        List<Inscripcion> listaEntidades = inscripcionRepository.findByUsuarioId(idUsuario);

        List<InscripcionResponse> listaRespuestas = new ArrayList<>();

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
            throw new RecursoNoEncontradoException("No existe ninguna inscripción con el ID: " + id);
        }

        InscripcionResponse dto = inscripcionMapper.aResponse(inscripcion);
        return dto;
    }

    //informe de participación de un evento concreto
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getInformeParticipacion(
            LocalDateTime desde,
            LocalDateTime hasta) {

        return inscripcionRepository.getInformeParticipacion(desde, hasta);
    }

}