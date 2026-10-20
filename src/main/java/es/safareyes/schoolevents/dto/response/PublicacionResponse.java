package es.safareyes.schoolevents.dto.response;

import java.util.Date;
import java.util.List;

public record PublicacionResponse(
        Integer id,
        String titulo,
        String descripcion,
        Date fecha,

        // Datos aplanados de las relaciones
        String tituloEvento,  // Aplanado: coge evento.getTitulo()
        String nombreUsuario, // Aplanado: coge usuario.getNombre()
        List<String> fotos    // Aplanado: lista con las URLs o rutas de las fotos asociadas
) {}