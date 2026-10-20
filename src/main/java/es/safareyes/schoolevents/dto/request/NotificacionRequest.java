package es.safareyes.schoolevents.dto.request;

import es.safareyes.schoolevents.modelos.Notificacion.tiponotificacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificacionRequest(
        @NotBlank(message = "El mensaje de la notificación es obligatorio")
        @Size(max = 100, message = "El mensaje no puede superar los 100 caracteres")
        String mensaje,

        @NotNull(message = "Debes indicar el tipo de notificación")
        tiponotificacion tipo,

        @NotNull(message = "Debes indicar el ID del evento relacionado")
        Integer idEvento,

        @NotNull(message = "Debes indicar el ID del usuario destinatario")
        Integer idUsuario
) {}