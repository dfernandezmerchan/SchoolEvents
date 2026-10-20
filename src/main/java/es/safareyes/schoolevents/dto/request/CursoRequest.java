package es.safareyes.schoolevents.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CursoRequest(
        @NotBlank(message = "El nombre del curso es obligatorio")
        @Size(max = 100, message = "El nombre del curso no puede superar los 100 caracteres")
        String nombre,

        @NotBlank(message = "La promoción es obligatoria")
        @Size(max = 100, message = "La promoción no puede superar los 50 caracteres")
        String promocion
) {}