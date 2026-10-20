package es.safareyes.schoolevents.dto.request;

import es.safareyes.schoolevents.modelos.Inscripcion.estadoinscripcion;
import jakarta.validation.constraints.NotNull;

public record InscripcionRequest(
        @NotNull(message = "Debes indicar el ID del evento")
        Integer idEvento,

        @NotNull(message = "Debes indicar el ID del usuario")
        Integer idUsuario,

        // Opcional: útil si un administrador actualiza el estado directamente (ej: CANCELADA)
        estadoinscripcion estado
) {}