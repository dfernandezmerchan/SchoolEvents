package es.safareyes.schoolevents.mapper;

import es.safareyes.schoolevents.dto.request.CategoriaRequest;
import es.safareyes.schoolevents.dto.response.CategoriaResponse;
import es.safareyes.schoolevents.modelos.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    // Entrada: Convierte el Request a Entidad para guardar en BD
    @Mapping(target = "id", ignore = true) // El ID se genera en la BD
    Categoria aEntidad(CategoriaRequest request);

    // Salida: Convierte la Entidad al DTO Response
    CategoriaResponse aResponse(Categoria entidad);
}