package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.request.PublicacionRequest;
import es.safareyes.schoolevents.dto.response.PublicacionResponse;

import java.util.List;

public interface PublicacionService {
    List<PublicacionResponse> listarTodas();
    List<PublicacionResponse> listarPorEvento(Integer eventoId);
    List<PublicacionResponse> listarPorUsuario(Integer usuarioId);
    PublicacionResponse buscarPorId(Integer id);
    PublicacionResponse crear(PublicacionRequest request);
    PublicacionResponse actualizar(Integer id, PublicacionRequest request);
    void eliminar(Integer id);
}