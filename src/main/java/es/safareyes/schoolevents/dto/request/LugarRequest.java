package es.safareyes.schoolevents.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LugarRequest(
        @NotBlank(message = "El nombre del lugar es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @NotNull(message = "El aforo máximo es obligatorio")
        @Min(value = 1, message = "El aforo máximo debe ser como mínimo de 1 persona")
        Integer aforoMaximo
) {}