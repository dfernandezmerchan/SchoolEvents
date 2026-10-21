package es.safareyes.schoolevents.mapper;

import es.safareyes.schoolevents.dto.request.CursoRequest;
import es.safareyes.schoolevents.dto.response.CursoResponse;
import es.safareyes.schoolevents.modelos.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    @Mapping(target = "id", ignore = true)
    Curso aEntidad(CursoRequest request);

    CursoResponse aResponse(Curso entidad);
}