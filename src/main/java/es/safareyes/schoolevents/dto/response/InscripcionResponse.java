package es.safareyes.schoolevents.dto.response;

import es.safareyes.schoolevents.modelos.Inscripcion.estadoinscripcion;
import java.time.LocalDateTime;

public record InscripcionResponse(
        Integer id,
        estadoinscripcion estado,
        LocalDateTime fecha,

        // Datos aplanados de las relaciones
        String tituloEvento,  // Aplanado: coge evento.getTitulo()
        String nombreUsuario  // Aplanado: coge usuario.getNombre()
) {}