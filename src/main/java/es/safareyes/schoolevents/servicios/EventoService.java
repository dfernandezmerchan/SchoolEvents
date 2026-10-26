package es.safareyes.schoolevents.servicios;

import es.safareyes.schoolevents.dto.request.EventoRequest;
import es.safareyes.schoolevents.dto.response.EventoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoService {

    // 1. Consultas y lecturas
    List<EventoResponse> listarTodas();
    EventoResponse buscarPorId(Integer id);
    List<EventoResponse> listarPorCategoria(String categoria);
    List<EventoResponse> buscarPorTitulo(String titulo);
    List<EventoResponse> buscarEntreFechas(LocalDateTime desde, LocalDateTime hasta);

    // 2. Métodos de la especificación oficial (Rutas 1 a 6 y 11)
    List<EventoResponse> listarPorMesYAnio(int mes, int anio);
    EventoResponse crear(EventoRequest request);
    EventoResponse actualizar(Integer id, EventoRequest request);
    EventoResponse cambiarEstado(Integer id, String nuevoEstado);
    List<EventoResponse> obtenerAgendaUsuarioActual(String tipo);
    void eliminar(Integer id);
}