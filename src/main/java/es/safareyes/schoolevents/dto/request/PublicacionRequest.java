package es.safareyes.schoolevents.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PublicacionRequest(
        @NotBlank(message = "El título de la publicación es obligatorio")
        @Size(max = 100, message = "El título no puede superar los 100 caracteres")
        String titulo,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 2000, message = "La descripción no puede superar los 2000 caracteres")
        String descripcion,

        @NotNull(message = "Debes indicar el ID del evento asociado")
        Integer idEvento,

        @NotNull(message = "Debes indicar el ID del usuario autor de la publicación")
        Integer idUsuario,

        @Size(max = 10, message = "No puedes adjuntar más de 10 fotografías")
        List<
                @NotBlank(message = "La URL de la fotografía no puede estar vacía")
                @Size(max = 500, message = "La URL no puede superar los 500 caracteres")
                        String
                > fotos
) {}