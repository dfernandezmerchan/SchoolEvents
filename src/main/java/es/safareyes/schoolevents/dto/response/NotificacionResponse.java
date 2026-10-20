package es.safareyes.schoolevents.dto.response;

import es.safareyes.schoolevents.modelos.Notificacion.tiponotificacion;

public record NotificacionResponse(
        Integer id,
        String mensaje,
        Boolean leida,
        tiponotificacion tipo,

        // Datos aplanados de las relaciones
        String tituloEvento,  // Aplanado: coge evento.getTitulo()
        String nombreUsuario  // Aplanado: coge usuario.getNombre()
) {}