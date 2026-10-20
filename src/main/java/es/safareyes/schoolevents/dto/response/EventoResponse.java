package es.safareyes.schoolevents.dto.response;

import es.safareyes.schoolevents.modelos.Evento.estadoevento;

import java.time.LocalDateTime;
import java.util.Set;

public record EventoResponse(
        Integer id,
        String titulo,
        String descripcion,
        LocalDateTime fechaini,
        LocalDateTime fechafin,
        LocalDateTime fechalimite,
        estadoevento estado,

        // Datos aplanados de las relaciones, de esto se encarga el mapstruct
        String lugar,        // Ejemplo: "Salón de Actos"
        String categoria,    // Ejemplo: "Deportes"
        String organizador,  // Ejemplo: "Juan Pérez"
        Set<String> cursos   // Ejemplo: ["1º DAM", "2º DAW"]
) {}