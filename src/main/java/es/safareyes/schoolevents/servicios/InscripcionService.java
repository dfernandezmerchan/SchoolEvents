package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.request.InscripcionRequest;
import es.safareyes.schoolevents.dto.response.InformeParticipacionResponse;
import es.safareyes.schoolevents.dto.response.InscripcionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface InscripcionService {
    InscripcionResponse inscribirse(InscripcionRequest request);
    void cancelarInscripcion(Integer eventoId, Integer usuarioId);
    List<InscripcionResponse> listarPorEvento(Integer eventoId);
    List<InscripcionResponse> agendaUsuario(Integer usuarioId);
    InscripcionResponse buscarPorId(Integer id);
    List<InformeParticipacionResponse> getInformeParticipacion(
            LocalDateTime desde,
            LocalDateTime hasta
    );

    // para pasar lista
    List<InscripcionResponse> listarConfirmados(Integer eventoId);
    // para ver lista de espera
    List<InscripcionResponse> listarEnEspera(Integer eventoId);

}