package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.Errores.RecursoNoEncontradoException;
import es.safareyes.schoolevents.dto.request.EventoRequest;
import es.safareyes.schoolevents.dto.response.EventoResponse;
import es.safareyes.schoolevents.mapper.EventoMapper;
import es.safareyes.schoolevents.modelos.Categoria;
import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.repositorios.ICategoriaRepository;
import es.safareyes.schoolevents.repositorios.IEventoRepository;
import es.safareyes.schoolevents.servicios.CambioEventoService;
import es.safareyes.schoolevents.servicios.EventoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class EventoServiceImpl implements EventoService {

    private IEventoRepository eventoRepository;
    private ICategoriaRepository categoriaRepository;
    private EventoMapper eventoMapper;
    private CambioEventoService cambioEventoService;

    // 1. LISTAR TODAS LAS PUBLICACIONES / EVENTOS
    @Override
    @Transactional(readOnly = true)
    public List<EventoResponse> listarTodas() {

        List<Evento> listaEntidades = eventoRepository.findAll();
        List<EventoResponse> listaRespuestas = new ArrayList<>();

        for (Evento evento : listaEntidades) {
            EventoResponse dto = eventoMapper.aResponse(evento);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 2. LISTAR EVENTOS CON FILTRO D CATEGORIA Y/O ESTADO
    @Override
    @Transactional(readOnly = true)
    public List<EventoResponse> listarConFiltros(
            Integer idcategoria,
            Evento.estadoevento estado) {

        List<Evento> listaEntidades;

        if (idcategoria != null && estado != null) {

            listaEntidades = eventoRepository
                    .findByCategoria_IdAndEstadoOrderByFechainiAsc(
                            idcategoria, estado);

        } else if (idcategoria != null) {

            listaEntidades = eventoRepository
                    .findByCategoria_IdOrderByFechainiAsc(idcategoria);

        } else if (estado != null) {

            listaEntidades = eventoRepository
                    .findByEstadoOrderByFechainiAsc(estado);

        } else {

            listaEntidades = eventoRepository
                    .findAllByOrderByFechainiAsc();
        }

        List<EventoResponse> listaRespuestas = new ArrayList<>();

        for (Evento evento : listaEntidades) {
            EventoResponse dto = eventoMapper.aResponse(evento);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 3. BUSCAR EVENTOS POR TÍTULO
    @Override
    @Transactional(readOnly = true)
    public List<EventoResponse> buscarPorTitulo(String titulo) {

        List<Evento> listaEntidades = eventoRepository.findByTituloContainingIgnoreCaseOrderByFechainiAsc(titulo);
        List<EventoResponse> listaRespuestas = new ArrayList<>();

        for (Evento evento : listaEntidades) {
            EventoResponse dto = eventoMapper.aResponse(evento);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 4. BUSCAR EVENTOS ENTRE FECHAS
    @Override
    @Transactional(readOnly = true)
    public List<EventoResponse> buscarEntreFechas(LocalDateTime desde, LocalDateTime hasta) {

        List<Evento> listaEntidades = eventoRepository.findByFechainiBetweenOrderByFechainiAsc(desde, hasta);
        List<EventoResponse> listaRespuestas = new ArrayList<>();

        for (Evento evento : listaEntidades) {
            EventoResponse dto = eventoMapper.aResponse(evento);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 5. OBTENER EVENTOS DEL MES Y AÑO (CALENDARIO)
    @Override
    @Transactional(readOnly = true)
    public List<EventoResponse> listarPorMesYAnio(int mes, int anio) {

        LocalDateTime inicioMes = LocalDateTime.of(anio, mes, 1, 0, 0, 0);
        LocalDateTime finMes = inicioMes.plusMonths(1).minusNanos(1);

        List<Evento> listaEntidades = eventoRepository.findByFechainiBetweenOrderByFechainiAsc(inicioMes, finMes);
        List<EventoResponse> listaRespuestas = new ArrayList<>();

        for (Evento evento : listaEntidades) {
            EventoResponse dto = eventoMapper.aResponse(evento);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 6. BUSCAR UN EVENTO POR SU ID
    @Override
    @Transactional(readOnly = true)
    public EventoResponse buscarPorId(Integer id) {

        Evento evento = eventoRepository.findById(id).orElse(null);

        if (evento == null) {
            throw new RecursoNoEncontradoException("No existe ningún evento con el ID: " + id);
        }

        EventoResponse dto = eventoMapper.aResponse(evento);
        return dto;
    }

    // 7. CREAR UN NUEVO EVENTO
    @Override
    @Transactional
    public EventoResponse crear(EventoRequest request) {

        Evento nuevoEvento = eventoMapper.aEntidad(request);

        if (request.idCategoria() != null) {
            Categoria categoriaEncontrada = categoriaRepository.findById(request.idCategoria()).orElse(null);

            if (categoriaEncontrada == null) {
                throw new RecursoNoEncontradoException("No existe la categoría con el ID: " + request.idCategoria());
            }

            nuevoEvento.setCategoria(categoriaEncontrada);
        }

        Evento guardado = eventoRepository.save(nuevoEvento);

        EventoResponse dto = eventoMapper.aResponse(guardado);
        return dto;
    }

    // 8. ACTUALIZAR UN EVENTO EXISTENTE
    @Override
    @Transactional
    public EventoResponse actualizar(Integer id, EventoRequest request) {

        Evento eventoExistente = eventoRepository.findById(id).orElse(null);

        if (eventoExistente == null) {
            throw new RecursoNoEncontradoException(
                    "No se puede actualizar. No existe el evento con ID: " + id
            );
        }

        // Guardamos los valores anteriores
        String tituloAnterior = eventoExistente.getTitulo();
        String descripcionAnterior = eventoExistente.getDescripcion();
        LocalDateTime fechainiAnterior = eventoExistente.getFechaini();
        LocalDateTime fechafinAnterior = eventoExistente.getFechafin();
        Evento.estadoevento estadoAnterior = eventoExistente.getEstado();
        Integer categoriaAnterior = eventoExistente.getCategoria() != null
                ? eventoExistente.getCategoria().getId()
                : null;

        // Actualizamos los valores
        eventoExistente.setTitulo(request.titulo());
        eventoExistente.setDescripcion(request.descripcion());
        eventoExistente.setFechaini(request.fechaini());
        eventoExistente.setFechafin(request.fechafin());
        eventoExistente.setEstado(request.estado());

        if (request.idCategoria() != null) {

            Categoria categoriaEncontrada =
                    categoriaRepository.findById(request.idCategoria()).orElse(null);

            if (categoriaEncontrada == null) {
                throw new RecursoNoEncontradoException(
                        "No existe la categoría con el ID: " + request.idCategoria()
                );
            }

            eventoExistente.setCategoria(categoriaEncontrada);
        }

        Evento guardado = eventoRepository.save(eventoExistente);

        // Registrar automáticamente los cambios

        if (!Objects.equals(tituloAnterior, request.titulo())) {
            cambioEventoService.registrarCambio(
                    id,
                    "titulo",
                    tituloAnterior,
                    request.titulo()
            );
        }

        if (!Objects.equals(descripcionAnterior, request.descripcion())) {
            cambioEventoService.registrarCambio(
                    id,
                    "descripcion",
                    descripcionAnterior,
                    request.descripcion()
            );
        }

        if (!Objects.equals(fechainiAnterior, request.fechaini())) {
            cambioEventoService.registrarCambio(
                    id,
                    "fechaini",
                    String.valueOf(fechainiAnterior),
                    String.valueOf(request.fechaini())
            );
        }

        if (!Objects.equals(fechafinAnterior, request.fechafin())) {
            cambioEventoService.registrarCambio(
                    id,
                    "fechafin",
                    String.valueOf(fechafinAnterior),
                    String.valueOf(request.fechafin())
            );
        }

        if (!Objects.equals(estadoAnterior, request.estado())) {
            cambioEventoService.registrarCambio(
                    id,
                    "estado",
                    estadoAnterior != null ? estadoAnterior.name() : null,
                    request.estado() != null ? request.estado().name() : null
            );
        }

        if (!Objects.equals(categoriaAnterior, request.idCategoria())) {
            cambioEventoService.registrarCambio(
                    id,
                    "categoria",
                    String.valueOf(categoriaAnterior),
                    String.valueOf(request.idCategoria())
            );
        }

        return eventoMapper.aResponse(guardado);
    }

    // 9. CAMBIAR ESTADO DE UN EVENTO (BORRADOR, PUBLICADO, CANCELADO)
    @Override
    @Transactional
    public EventoResponse cambiarEstado(Integer id, String nuevoEstado) {

        Evento evento = eventoRepository.findById(id).orElse(null);

        if (evento == null) {
            throw new RecursoNoEncontradoException("No existe el evento con ID: " + id);
        }

        String estadoAnterior = (evento.getEstado() != null) ? evento.getEstado().name() : null;
        evento.setEstado(Evento.estadoevento.valueOf(nuevoEstado.toUpperCase()));

        Evento guardado = eventoRepository.save(evento);

        // Registro automático en el historial de auditoría CambioEvento
        cambioEventoService.registrarCambio(id, "estado", estadoAnterior, nuevoEstado);

        EventoResponse dto = eventoMapper.aResponse(guardado);
        return dto;
    }

    // 11. ELIMINAR EVENTO POR ID
    @Override
    @Transactional
    public void eliminar(Integer id) {

        boolean existe = eventoRepository.existsById(id);

        if (!existe) {
            throw new RuntimeException("No se puede eliminar. No existe el evento con ID: " + id);
        }

        eventoRepository.deleteById(id);
    }
}