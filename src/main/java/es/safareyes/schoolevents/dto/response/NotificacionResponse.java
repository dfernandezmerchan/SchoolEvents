package es.safareyes.schoolevents.dto.response;

import es.safareyes.schoolevents.modelos.Notificacion.tiponotificacion;

public record NotificacionResponse(
        Integer id,
        String mensaje,
        Boolean leida,
        tiponotificacion tipo,

        // Datos aplanados de las relaciones
        Integer idEvento,
        String tituloEvento,

        Integer idUsuario,
        String nombreUsuario
) {}