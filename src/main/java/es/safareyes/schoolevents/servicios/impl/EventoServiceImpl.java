package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.dto.request.EventoRequest;
import es.safareyes.schoolevents.dto.response.EventoResponse;
import es.safareyes.schoolevents.mapper.EventoMapper;
import es.safareyes.schoolevents.modelos.Categoria;
import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.repositorios.ICategoriaRepository;
import es.safareyes.schoolevents.repositorios.IEventoRepository;
import es.safareyes.schoolevents.servicios.EventoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {

    private final IEventoRepository eventoRepository;
    private final ICategoriaRepository categoriaRepository;
    private final EventoMapper eventoMapper;

    public EventoServiceImpl(IEventoRepository eventoRepository,
                             ICategoriaRepository categoriaRepository,
                             EventoMapper eventoMapper) {
        this.eventoRepository = eventoRepository;
        this.categoriaRepository = categoriaRepository;
        this.eventoMapper = eventoMapper;
    }

    // 1. LISTAR EVENTOS POR CATEGORÍA
    @Override
    @Transactional(readOnly = true)
    public List<EventoResponse> listarPorCategoria(String categoria) {

        // PASO A: Consultar los eventos por el nombre de la categoría en PostgreSQL
        List<Evento> listaEntidades = eventoRepository.findByCategoria_NombreOrderByFechainiAsc(categoria);

        // PASO B: Preparar lista vacía para las respuestas
        List<EventoResponse> listaRespuestas = new ArrayList<>();

        // PASO C: Bucle for tradicional para recorrer y mapear
        for (Evento evento : listaEntidades) {
            EventoResponse dto = eventoMapper.aResponse(evento);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 2. BUSCAR EVENTOS POR TÍTULO
    @Override
    @Transactional(readOnly = true)
    public List<EventoResponse> buscarPorTitulo(String titulo) {

        // PASO A: Buscar eventos que contengan el texto en el título
        List<Evento> listaEntidades = eventoRepository.findByTituloContainingIgnoreCaseOrderByFechainiAsc(titulo);

        // PASO B: Preparar lista de respuesta
        List<EventoResponse> listaRespuestas = new ArrayList<>();

        // PASO C: Bucle for tradicional
        for (Evento evento : listaEntidades) {
            EventoResponse dto = eventoMapper.aResponse(evento);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 3. BUSCAR EVENTOS ENTRE FECHAS
    @Override
    @Transactional(readOnly = true)
    public List<EventoResponse> buscarEntreFechas(LocalDateTime desde, LocalDateTime hasta) {

        // PASO A: Consultar en la base de datos los eventos en el rango de fechas
        List<Evento> listaEntidades = eventoRepository.findByFechainiBetweenOrderByFechainiAsc(desde, hasta);

        // PASO B: Preparar lista vacía
        List<EventoResponse> listaRespuestas = new ArrayList<>();

        // PASO C: Bucle for tradicional
        for (Evento evento : listaEntidades) {
            EventoResponse dto = eventoMapper.aResponse(evento);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 4. BUSCAR UN EVENTO POR SU ID
    @Override
    @Transactional(readOnly = true)
    public EventoResponse buscarPorId(Integer id) {

        // PASO A: Traer el evento de la BD o null si no existe
        Evento evento = eventoRepository.findById(id).orElse(null);

        // PASO B: Validación con 'if' tradicional
        if (evento == null) {
            throw new RuntimeException("No existe ningún evento con el ID: " + id);
        }

        // PASO C: Convertir a DTO y devolver
        EventoResponse dto = eventoMapper.aResponse(evento);
        return dto;
    }

    // 5. CREAR UN NUEVO EVENTO
    @Override
    @Transactional
    public EventoResponse crear(EventoRequest request) {

        // PASO A: Convertir DTO de petición a Entidad Evento
        Evento nuevoEvento = eventoMapper.aEntidad(request);

        // PASO B: Asignar la Categoría si viene su ID en el request
        // (Ajusta 'idCategoria()' si en tu EventoRequest la propiedad tiene otro nombre como 'categoriaId()')
        if (request.idCategoria() != null) {
            Categoria categoriaEncontrada = categoriaRepository.findById(request.idCategoria()).orElse(null);

            if (categoriaEncontrada == null) {
                throw new RuntimeException("No existe la categoría con el ID: " + request.idCategoria());
            }

            nuevoEvento.setCategoria(categoriaEncontrada);
        }

        // PASO C: Guardar en PostgreSQL
        Evento guardado = eventoRepository.save(nuevoEvento);

        // PASO D: Convertir la entidad guardada a DTO de respuesta
        EventoResponse dto = eventoMapper.aResponse(guardado);
        return dto;
    }

    // 6. ELIMINAR EVENTO POR ID
    @Override
    @Transactional
    public void eliminar(Integer id) {

        // PASO A: Comprobar si existe el evento en la BD
        boolean existe = eventoRepository.existsById(id);

        if (!existe) {
            throw new RuntimeException("No se puede eliminar. No existe el evento con ID: " + id);
        }

        // PASO B: Eliminar de PostgreSQL
        eventoRepository.deleteById(id);
    }
}