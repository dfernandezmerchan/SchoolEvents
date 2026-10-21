package es.safareyes.schoolevents.mapper;

import es.safareyes.schoolevents.dto.request.PublicacionRequest;
import es.safareyes.schoolevents.dto.response.PublicacionResponse;
import es.safareyes.schoolevents.modelos.Foto;
import es.safareyes.schoolevents.modelos.Publicacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublicacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "evento", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "fotos", ignore = true)
    Publicacion aEntidad(PublicacionRequest request);

    @Mapping(target = "tituloEvento", source = "evento.titulo")
    @Mapping(target = "nombreUsuario", source = "usuario.nombre")
    @Mapping(target = "fotos", source = "fotos", qualifiedByName = "mapearUrlsFotos")
    PublicacionResponse aResponse(Publicacion entidad);

    @Named("mapearUrlsFotos")
    default List<String> mapearUrlsFotos(List<Foto> fotos) {
        if (fotos == null) return null;
        return fotos.stream()
                .map(Foto::getUrl)
                .toList();
    }
}