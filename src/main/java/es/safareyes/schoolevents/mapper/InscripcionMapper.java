package es.safareyes.schoolevents.mapper;

import es.safareyes.schoolevents.dto.request.InscripcionRequest;
import es.safareyes.schoolevents.dto.response.InscripcionResponse;
import es.safareyes.schoolevents.modelos.Inscripcion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InscripcionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "evento", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Inscripcion aEntidad(InscripcionRequest request);

    @Mapping(target = "tituloEvento", source = "evento.titulo")
    @Mapping(target = "nombreUsuario", source = "usuario.nombre")
    InscripcionResponse aResponse(Inscripcion entidad);
}