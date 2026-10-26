package es.safareyes.schoolevents.controladores;

import es.safareyes.schoolevents.dto.request.EventoRequest;
import es.safareyes.schoolevents.dto.response.EventoResponse;
import es.safareyes.schoolevents.servicios.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // 1. GET /api/v1/eventos - Listado paginado con filtros (Público)
    @GetMapping
    public ResponseEntity<List<EventoResponse>> listarEventos(
            @RequestParam(required = false) Integer categoriaId,
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<EventoResponse> eventos = eventoService.listarTodas();
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