package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.response.CambioEventoResponse;

import java.util.List;

public interface CambioEventoService {
    List<CambioEventoResponse> listarTodos();
    List<CambioEventoResponse> listarPorEvento(Integer eventoId);
    CambioEventoResponse buscarPorId(Integer id);
    CambioEventoResponse registrarCambio(Integer idEvento, String campoModificado, String valorAnterior, String valorNuevo);
    void eliminar(Integer id);
}