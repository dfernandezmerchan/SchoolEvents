package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.request.InscripcionRequest;
import es.safareyes.schoolevents.dto.response.InscripcionResponse;

import java.util.List;

public interface InscripcionService {
    InscripcionResponse inscribirse(InscripcionRequest request);
    void cancelarInscripcion(Integer eventoId, Integer usuarioId);
    List<InscripcionResponse> listarPorEvento(Integer eventoId);
    List<InscripcionResponse> listarPorUsuario(Integer usuarioId);
    InscripcionResponse buscarPorId(Integer id);
}