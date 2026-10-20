package es.safareyes.schoolevents.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
        @NotBlank(message = "El nombre de la categoría es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @Size(max = 100, message = "El color no puede superar los 100 caracteres")
        String color,

        @Size(max = 100, message = "El icono no puede superar los 100 caracteres")
        String icono
) {}