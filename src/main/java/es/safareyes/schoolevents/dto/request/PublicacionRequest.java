package es.safareyes.schoolevents.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PublicacionRequest(
        @NotBlank(message = "El título de la publicación es obligatorio")
        @Size(max = 100, message = "El título no puede superar los 100 caracteres")
        String titulo,

        @NotBlank(message = "La descripción es obligatoria")
        String descripcion,

        @NotNull(message = "Debes indicar el ID del evento asociado")
        Integer idEvento,

        @NotNull(message = "Debes indicar el ID del usuario autor de la publicación")
        Integer idUsuario
) {}