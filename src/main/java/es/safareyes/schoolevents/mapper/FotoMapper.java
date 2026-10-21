package es.safareyes.schoolevents.mapper;

import es.safareyes.schoolevents.dto.request.FotoRequest;
import es.safareyes.schoolevents.dto.response.FotoResponse;
import es.safareyes.schoolevents.modelos.Foto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FotoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicacion", ignore = true)
    Foto aEntidad(FotoRequest request);

    @Mapping(target = "idPublicacion", source = "publicacion.id")
    @Mapping(target = "tituloPublicacion", source = "publicacion.titulo")
    FotoResponse aResponse(Foto entidad);
}