package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.dto.request.EventoRequest;
import es.safareyes.schoolevents.dto.response.EventoResponse;
import es.safareyes.schoolevents.mapper.EventoMapper;
import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.repositorios.IEventoRepository;
import es.safareyes.schoolevents.servicios.EventoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {

    // 1. Inyectamos tu repositorio y tu mapper
    private final IEventoRepository eventoRepository;
    private final EventoMapper eventoMapper;

    public EventoServiceImpl(IEventoRepository eventoRepository, EventoMapper eventoMapper) {
        this.eventoRepository = eventoRepository;
        this.eventoMapper = eventoMapper;
    }

    // Usamos la consulta findByCategoria_Nombre... que hiciste en IEventoRepository
    @Override
    @Transactional(readOnly = true)
    public List<EventoResponse> listarPorCategoria(String categoria) {
        return eventoRepository.findByCategoria_NombreOrderByFechainiAsc(categoria)
                .stream()
                .map(eventoMapper::aResponse)
                .toList();
    }

    // Usamos la consulta findByTituloContainingIgnoreCase... de tu IEventoRepository
    @Override
    @Transactional(readOnly = true)
    public List<EventoResponse> buscarPorTitulo(String titulo) {
        return eventoRepository.findByTituloContainingIgnoreCaseOrderByFechainiAsc(titulo)
                .stream()
                .map(eventoMapper::aResponse)
                .toList();
    }

    // Usamos la consulta findByFechainiBetween... de tu IEventoRepository
    @Override
    @Transactional(readOnly = true)
    public List<EventoResponse> buscarEntreFechas(LocalDateTime desde, LocalDateTime hasta) {
        return eventoRepository.findByFechainiBetweenOrderByFechainiAsc(desde, hasta)
                .stream()
                .map(eventoMapper::aResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EventoResponse buscarPorId(Integer id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + id));
        return eventoMapper.aResponse(evento);
    }

    @Override
    @Transactional
    public EventoResponse crear(EventoRequest request) {
        // Convertimos el DTO que viene del usuario a la Entidad JPA
        Evento evento = eventoMapper.aEntidad(request);

        // Guardamos en la base de datos
        Evento guardado = eventoRepository.save(evento);

        // Devolvemos la respuesta formateada en DTO
        return eventoMapper.aResponse(guardado);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        if (!eventoRepository.existsById(id)) {
            throw new RuntimeException("Evento no encontrado con ID: " + id);
        }
        eventoRepository.deleteById(id);
    }
}