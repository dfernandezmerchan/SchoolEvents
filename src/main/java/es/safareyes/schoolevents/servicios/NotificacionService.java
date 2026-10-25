package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.request.NotificacionRequest;
import es.safareyes.schoolevents.dto.response.NotificacionResponse;

import java.util.List;

public interface NotificacionService {
    List<NotificacionResponse> listarTodas();
    List<NotificacionResponse> listarPorUsuario(Integer usuarioId);
    NotificacionResponse buscarPorId(Integer id);
    NotificacionResponse crear(NotificacionRequest request);
    NotificacionResponse actualizar(Integer id, NotificacionRequest request);
    void eliminar(Integer id);
    void marcarComoLeida(Integer id); // Nuevo método
}