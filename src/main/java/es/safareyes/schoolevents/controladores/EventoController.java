package es.safareyes.schoolevents.controladores;

import es.safareyes.schoolevents.dto.request.EventoRequest;
import es.safareyes.schoolevents.dto.response.EventoResponse;
import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.servicios.EventoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/eventos")
@AllArgsConstructor
public class EventoController {

    private EventoService eventoService;

    //GET  - consultar todos los eventos paginados
    // /api/v1/eventos?page=1&size=10 pagina
    // GET /api/v1/eventos?categoriaId=2 por categoria
    // GET /api/v1/eventos?cursoId=3 por curso
    // GET /api/v1/eventos?texto=futbol por texto en un buscador
    // GET /api/v1/eventos?desde=2026-12-01T00:00:00&hasta=2026-12-31T23:59:59 por fechas
    // EJEMPLO COMBINANDO TODO
    // GET /api/v1/eventos?categoriaId=2&cursoId=3&texto=futbol&desde=2026-12-01T00:00:00&hasta=2026-12-31T23:59:59&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<EventoResponse>> listarEventos(
            @RequestParam(required = false) Integer categoriaId,
            @RequestParam(required = false) Integer cursoId,
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) LocalDateTime desde,
            @RequestParam(required = false) LocalDateTime hasta,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<EventoResponse> eventos = eventoService.listarConFiltros(
                categoriaId,
                cursoId,
                texto,
                desde,
                hasta,
                pageable
        );

        return ResponseEntity.ok(eventos);
    }

    //listar con filtros
    @GetMapping("/filtros")
    public ResponseEntity<List<EventoResponse>> listarEventosFiltros(
            @RequestParam(required = false) Integer categoriaId,
            @RequestParam(required = false) Evento.estadoevento estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<EventoResponse> eventos = eventoService.listarConFiltros(categoriaId,estado);
        return ResponseEntity.ok(eventos);
    }


    // 2. GET /api/v1/eventos/calendario?mes=&anio= - Eventos del mes (Público)
    @GetMapping("/calendario")
    public ResponseEntity<List<EventoResponse>> obtenerCalendario(
            @RequestParam int mes,
            @RequestParam int anio) {

        List<EventoResponse> eventosMes = eventoService.listarPorMesYAnio(mes, anio);
        return ResponseEntity.ok(eventosMes);
    }

    // 3. GET /api/v1/eventos/{id} - Detalle con lugar, requisitos, plazas y cursos (Público)
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> buscarPorId(@PathVariable Integer id) {
        EventoResponse evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }

    // 4. POST /api/v1/eventos - Crear evento en BORRADOR (ORGANIZADOR, ADMIN)
    @PostMapping
    public ResponseEntity<EventoResponse> crear(@Valid @RequestBody EventoRequest request) {
        EventoResponse nuevoEvento = eventoService.crear(request);
        return new ResponseEntity<>(nuevoEvento, HttpStatus.CREATED);
    }

    // 5. PUT /api/v1/eventos/{id} - Editar evento (ORGANIZADOR propio, ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<EventoResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EventoRequest request) {

        EventoResponse actualizado = eventoService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    // 6. PATCH /api/v1/eventos/{id}/estado - Publicar o cancelar (ORGANIZADOR propio, ADMIN)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<EventoResponse> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam String nuevoEstado) {

        EventoResponse actualizado = eventoService.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.ok(actualizado);
    }
}