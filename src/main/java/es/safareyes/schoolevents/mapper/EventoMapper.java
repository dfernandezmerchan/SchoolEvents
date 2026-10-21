package es.safareyes.schoolevents.mapper;

import es.safareyes.schoolevents.dto.request.EventoRequest;
import es.safareyes.schoolevents.dto.response.EventoResponse;
import es.safareyes.schoolevents.modelos.Curso;
import es.safareyes.schoolevents.modelos.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lugar", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "organizador", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    Evento aEntidad(EventoRequest request);

    @Mapping(target = "lugar", source = "lugar.nombre")
    @Mapping(target = "categoria", source = "categoria.nombre")
    @Mapping(target = "organizador", source = "organizador.nombre")
    @Mapping(target = "cursos", source = "cursos", qualifiedByName = "mapearNombresCursos")
    EventoResponse aResponse(Evento entidad);

    @Named("mapearNombresCursos")
    default Set<String> mapearNombresCursos(Set<Curso> cursos) {
        if (cursos == null) return null;
        return cursos.stream()
                .map(Curso::getNombre)
                .collect(Collectors.toSet());
    }
}