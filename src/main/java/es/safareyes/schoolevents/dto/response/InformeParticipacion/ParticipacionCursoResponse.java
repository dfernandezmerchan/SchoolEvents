package es.safareyes.schoolevents.dto.response.InformeParticipacion;

public record ParticipacionCursoResponse(
        String curso,
        Long totalParticipantes
) {}