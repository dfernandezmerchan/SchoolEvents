package es.safareyes.schoolevents.mapper;

import es.safareyes.schoolevents.dto.request.LugarRequest;
import es.safareyes.schoolevents.dto.response.LugarResponse;
import es.safareyes.schoolevents.modelos.Lugar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LugarMapper {

    @Mapping(target = "id", ignore = true)
    Lugar aEntidad(LugarRequest request);

    LugarResponse aResponse(Lugar entidad);
}