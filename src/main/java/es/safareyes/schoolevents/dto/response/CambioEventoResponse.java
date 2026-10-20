package es.safareyes.schoolevents.dto.response;

import java.time.LocalDateTime;

public record CambioEventoResponse(
        Integer id,
        String campoModificado,
        String valorAnterior,
        String valorNuevo,
        LocalDateTime fecha,

        // Dato aplanado de la relación con Evento
        Integer idEvento,
        String tituloEvento
) {}