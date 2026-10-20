package es.safareyes.schoolevents.dto.response;

public record CategoriaResponse(
        Integer id,
        String nombre,
        String color,
        String icono
) {}