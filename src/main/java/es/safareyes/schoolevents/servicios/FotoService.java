package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.request.FotoRequest;
import es.safareyes.schoolevents.dto.response.FotoResponse;

import java.util.List;

public interface FotoService {
    List<FotoResponse> listarTodas();
    List<FotoResponse> listarPorPublicacion(Integer publicacionId);
    FotoResponse buscarPorId(Integer id);
    FotoResponse crear(FotoRequest request);
    FotoResponse actualizar(Integer id, FotoRequest request);
    void eliminar(Integer id);
}