package es.safareyes.schoolevents.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record UsuarioRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @NotBlank(message = "El primer apellido es obligatorio")
        @Size(max = 100, message = "El primer apellido no puede superar los 100 caracteres")
        String apellido1,

        @Size(max = 100, message = "El segundo apellido no puede superar los 100 caracteres")
        String apellido2,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email,

        @NotBlank(message = "El nombre de usuario (username) es obligatorio")
        @Size(min = 4, max = 50, message = "El username debe tener entre 4 y 50 caracteres")
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        @Past(message = "La fecha de nacimiento debe ser una fecha del pasado")
        LocalDate fechanac,

        // Relación con el curso (recibimos solo su ID)
        Integer idCurso
) {}