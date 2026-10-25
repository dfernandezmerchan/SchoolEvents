package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.dto.request.InscripcionRequest;
import es.safareyes.schoolevents.dto.response.InscripcionResponse;
import es.safareyes.schoolevents.mapper.InscripcionMapper;
import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.modelos.Inscripcion;
import es.safareyes.schoolevents.modelos.Usuario;
import es.safareyes.schoolevents.repositorios.IEventoRepository;
import es.safareyes.schoolevents.repositorios.IInscripcionRepository;
import es.safareyes.schoolevents.repositorios.IUsuarioRepository;
import es.safareyes.schoolevents.servicios.InscripcionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    private final IInscripcionRepository inscripcionRepository;
    private final IEventoRepository eventoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final InscripcionMapper inscripcionMapper;

    public InscripcionServiceImpl(IInscripcionRepository inscripcionRepository,
                                  IEventoRepository eventoRepository,
                                  IUsuarioRepository usuarioRepository,
                                  InscripcionMapper inscripcionMapper) {
        this.inscripcionRepository = inscripcionRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.inscripcionMapper = inscripcionMapper;
    }

    @Override
    @Transactional
    public InscripcionResponse inscribirse(InscripcionRequest request) {
        Evento evento = eventoRepository.findById(request.idEvento())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + request.idEvento()));

        Usuario usuario = usuarioRepository.findById(request.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.idUsuario()));

        // RN-02 y RN-04: Solo eventos PUBLICADO y antes de la fecha límite
        if (evento.getEstado() != Evento.estadoevento.PUBLICADO) {
            throw new RuntimeException("No es posible inscribirse en un evento que no esté PUBLICADO");
        }
        if (evento.getFechalimite() != null && LocalDateTime.now().isAfter(evento.getFechalimite())) {
            throw new RuntimeException("Ha pasado la fecha límite para inscribirse en este evento");
        }

        // RN-03: Restricción de curso (si el evento está restringido a ciertos cursos)
        if (evento.getCursos() != null && !evento.getCursos().isEmpty()) {
            if (usuario.getCurso() == null || !evento.getCursos().contains(usuario.getCurso())) {
                throw new RuntimeException("Tu curso no está autorizado para asistir a este evento");
            }
        }

        // Buscar si ya existe inscripción (evita duplicados según RN-04)
        Optional<Inscripcion> inscripcionExistente = inscripcionRepository
                .findByEventoIdAndUsuarioId(request.idEvento(), request.idUsuario());

        Inscripcion inscripcion = inscripcionExistente.orElseGet(Inscripcion::new);
        inscripcion.setEvento(evento);
        inscripcion.setUsuario(usuario);

        if (inscripcion.getId() == null) {
            inscripcion.setFecha(LocalDateTime.now());
        }

        // Si la petición especifica un estado, se respeta; en otro caso, se calcula según el aforo (RN-05)
        if (request.estado() != null) {
            inscripcion.setEstado(request.estado());
        } else {
            long plazasConfirmadas = inscripcionRepository.countByEventoIdAndEstado(
                    request.idEvento(), Inscripcion.estadoinscripcion.CONFIRMADA);

            Integer aforoMaximo = (evento.getLugar() != null) ? evento.getLugar().getAforomaximo() : Integer.MAX_VALUE;

            if (plazasConfirmadas < aforoMaximo) {
                inscripcion.setEstado(Inscripcion.estadoinscripcion.CONFIRMADA);
            } else {
                inscripcion.setEstado(Inscripcion.estadoinscripcion.EN_ESPERA);
            }
        }

        Inscripcion guardada = inscripcionRepository.save(inscripcion);
        return inscripcionMapper.aResponse(guardada);
    }

    @Override
    @Transactional
    public void cancelarInscripcion(Integer eventoId, Integer usuarioId) {
        Inscripcion inscripcion = inscripcionRepository.findByEventoIdAndUsuarioId(eventoId, usuarioId)
                .orElseThrow(() -> new RuntimeException("No existe ninguna inscripción activa para este usuario en el evento"));

        boolean teniaPlaza = (inscripcion.getEstado() == Inscripcion.estadoinscripcion.CONFIRMADA);
        inscripcionRepository.delete(inscripcion);

        // RN-05: Promoción automática del primero en lista de espera al liberar plaza
        if (teniaPlaza) {
            Optional<Inscripcion> primeroEnEspera = inscripcionRepository
                    .findFirstByEventoIdAndEstadoOrderByFechaAsc(eventoId, Inscripcion.estadoinscripcion.EN_ESPERA);

            if (primeroEnEspera.isPresent()) {
                Inscripcion promocionado = primeroEnEspera.get();
                promocionado.setEstado(Inscripcion.estadoinscripcion.CONFIRMADA);
                inscripcionRepository.save(promocionado);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listarPorEvento(Integer eventoId) {
        return inscripcionRepository.findByEventoId(eventoId)
                .stream()
                .map(inscripcionMapper::aResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listarPorUsuario(Integer usuarioId) {
        return inscripcionRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(inscripcionMapper::aResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InscripcionResponse buscarPorId(Integer id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada con ID: " + id));
        return inscripcionMapper.aResponse(inscripcion);
    }
}