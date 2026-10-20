package es.safareyes.schoolevents.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioResponse(
        Integer id,
        String nombre,
        String apellido1,
        String apellido2,
        String email,
        String username,
        LocalDate fechanac,
        LocalDateTime fechains,

        // Dato aplanado de la relación con Curso
        String curso // Muestra el nombre del curso (ej: "2º DAW")
) {}