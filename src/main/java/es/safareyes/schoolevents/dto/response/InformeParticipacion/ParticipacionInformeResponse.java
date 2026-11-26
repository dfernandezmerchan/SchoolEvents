package es.safareyes.schoolevents.dto.response.InformeParticipacion;

import java.util.List;

public record ParticipacionInformeResponse(
        String categoria,
        List<ParticipacionCursoResponse> cursos
) {}