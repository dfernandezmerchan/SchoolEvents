package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.request.LugarRequest;
import es.safareyes.schoolevents.dto.response.LugarResponse;

import java.util.List;

public interface LugarService {
    List<LugarResponse> listarTodos();
    LugarResponse buscarPorId(Integer id);
    LugarResponse crear(LugarRequest request);
    LugarResponse actualizar(Integer id, LugarRequest request);
    void eliminar(Integer id);
}