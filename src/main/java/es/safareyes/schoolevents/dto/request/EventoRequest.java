package es.safareyes.schoolevents.dto.request;

import es.safareyes.schoolevents.modelos.Evento.estadoevento;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;

public record EventoRequest(
        @NotBlank(message = "El título del evento es obligatorio")
        @Size(max = 100, message = "El título no puede superar los 100 caracteres")
        String titulo,

        String descripcion,

        @NotNull(message = "La fecha de inicio es obligatoria")
        @FutureOrPresent(message = "La fecha de inicio no puede ser una fecha pasada")
        LocalDateTime fechaini,

        @NotNull(message = "La fecha de fin es obligatoria")
        @FutureOrPresent(message = "La fecha de fin no puede ser una fecha pasada")
        LocalDateTime fechafin,

        @FutureOrPresent(message = "La fecha límite de inscripción no puede ser una fecha pasada")
        LocalDateTime fechalimite,

        @NotNull(message = "Debes indicar el estado del evento")
        estadoevento estado,

        @NotNull(message = "Debes indicar el ID del lugar donde se celebrará")
        Integer idLugar,

        @NotNull(message = "Debes indicar el ID de la categoría asociada")
        Integer idCategoria,

        @NotNull(message = "Debes indicar el ID del usuario organizador")
        Integer idOrganizador,

        // Colección de IDs de los cursos asignados a este evento
        Set<Integer> idsCursos
) {}