package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.request.EventoRequest;
import es.safareyes.schoolevents.dto.response.EventoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoService {
    List<EventoResponse> listarPorCategoria(String categoria);
    List<EventoResponse> buscarPorTitulo(String titulo);
    List<EventoResponse> buscarEntreFechas(LocalDateTime desde, LocalDateTime hasta);
    EventoResponse buscarPorId(Integer id);
    EventoResponse crear(EventoRequest request);
    void eliminar(Integer id);
}