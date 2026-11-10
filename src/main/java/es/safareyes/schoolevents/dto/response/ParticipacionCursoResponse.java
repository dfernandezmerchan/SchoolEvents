package es.safareyes.schoolevents.dto.response;


//DTO PARA EL INFORME DE PARTICIPACION
public record ParticipacionCursoResponse(
        String curso,
        Long participantes
) {
}
