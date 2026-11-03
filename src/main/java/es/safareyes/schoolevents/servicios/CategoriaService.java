package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.request.CategoriaRequest;
import es.safareyes.schoolevents.dto.request.EventoRequest;
import es.safareyes.schoolevents.dto.response.CategoriaResponse;
import es.safareyes.schoolevents.dto.response.EventoResponse;

import java.util.List;

public interface CategoriaService {
    List<CategoriaResponse> listarTodas();
    CategoriaResponse buscarPorId(Integer id);
    CategoriaResponse crear(CategoriaRequest request);
    void eliminar(Integer id);
    CategoriaResponse actualizar(Integer id, CategoriaRequest request);

}