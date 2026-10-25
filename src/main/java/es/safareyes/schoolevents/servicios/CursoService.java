package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.request.CursoRequest;
import es.safareyes.schoolevents.dto.response.CursoResponse;

import java.util.List;

public interface CursoService {
    List<CursoResponse> listarTodos();
    CursoResponse buscarPorId(Integer id);
    CursoResponse crear(CursoRequest request);
    CursoResponse actualizar(Integer id, CursoRequest request);
    void eliminar(Integer id);
}