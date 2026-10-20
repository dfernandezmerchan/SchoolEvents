package es.safareyes.schoolevents.dto.response;

public record FotoResponse(
        Integer id,
        String url,

        // Dato aplanado de la relación con Publicacion
        Integer idPublicacion,
        String tituloPublicacion
) {}