package es.safareyes.schoolevents.mapper;

import es.safareyes.schoolevents.dto.request.UsuarioRequest;
import es.safareyes.schoolevents.dto.response.UsuarioResponse;
import es.safareyes.schoolevents.modelos.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechains", ignore = true)
    @Mapping(target = "curso", ignore = true) // Se asigna en el Servicio tras buscar el Curso por ID
    Usuario aEntidad(UsuarioRequest request);

    @Mapping(target = "curso", source = "curso.nombre")
    UsuarioResponse aResponse(Usuario entidad);
}