package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.request.CategoriaRequest;
import es.safareyes.schoolevents.dto.response.CategoriaResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoriaService {
    List<CategoriaResponse> listarTodas();
    CategoriaResponse buscarPorId(Integer id);
    CategoriaResponse crear(CategoriaRequest request);
    CategoriaResponse actualizar(Integer id, CategoriaRequest request);
}