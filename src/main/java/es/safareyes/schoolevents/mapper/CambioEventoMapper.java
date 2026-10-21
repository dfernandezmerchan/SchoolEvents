package es.safareyes.schoolevents.mapper;

import es.safareyes.schoolevents.dto.response.CambioEventoResponse;
import es.safareyes.schoolevents.modelos.CambioEvento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CambioEventoMapper {

    @Mapping(target = "idEvento", source = "evento.id")
    @Mapping(target = "tituloEvento", source = "evento.titulo")
    CambioEventoResponse aResponse(CambioEvento entidad);
}