package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.request.UsuarioRequest;
import es.safareyes.schoolevents.dto.response.UsuarioResponse;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponse> listarTodos();
    UsuarioResponse buscarPorId(Integer id);
    UsuarioResponse crear(UsuarioRequest request);
    UsuarioResponse actualizar(Integer id, UsuarioRequest request);
    void eliminar(Integer id);

}
