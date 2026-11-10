package es.safareyes.schoolevents.dto.response;

import java.util.List;
//DTO PARA EL INFORME DE PARTICIPACION

public record ParticipacionInformeResponse(
        String categoria,
        List<ParticipacionCursoResponse> cursos
) {
}