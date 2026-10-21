package es.safareyes.schoolevents.mapper;

import es.safareyes.schoolevents.dto.request.NotificacionRequest;
import es.safareyes.schoolevents.dto.response.NotificacionResponse;
import es.safareyes.schoolevents.modelos.Notificacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "leida", ignore = true)
    @Mapping(target = "evento", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Notificacion aEntidad(NotificacionRequest request);

    @Mapping(target = "tituloEvento", source = "evento.titulo")
    @Mapping(target = "nombreUsuario", source = "usuario.nombre")
    NotificacionResponse aResponse(Notificacion entidad);
}