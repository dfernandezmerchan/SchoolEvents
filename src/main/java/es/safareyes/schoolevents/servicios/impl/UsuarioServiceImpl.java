package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.dto.request.UsuarioRequest;
import es.safareyes.schoolevents.dto.response.UsuarioResponse;
import es.safareyes.schoolevents.servicios.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {


    @Override
    public List<UsuarioResponse> listarTodos() {
        return List.of();
    }

    @Override
    public UsuarioResponse buscarPorId(Integer id) {
        return null;
    }

    @Override
    public UsuarioResponse crear(UsuarioRequest request) {
        return null;
    }

    @Override
    public UsuarioResponse actualizar(Integer id, UsuarioRequest request) {
        return null;
    }

    @Override
    public void eliminar(Integer id) {

    }
}
