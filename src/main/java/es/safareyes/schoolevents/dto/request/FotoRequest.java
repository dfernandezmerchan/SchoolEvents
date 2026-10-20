package es.safareyes.schoolevents.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FotoRequest(
        @NotBlank(message = "La URL de la foto es obligatoria")
        @Size(max = 500, message = "La URL no puede superar los 500 caracteres")
        String url,

        @NotNull(message = "Debes indicar el ID de la publicación asociada")
        Integer idPublicacion
) {}