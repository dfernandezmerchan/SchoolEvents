package es.safareyes.schoolevents.controladores;

import es.safareyes.schoolevents.dto.request.NotificacionRequest;
import es.safareyes.schoolevents.dto.response.NotificacionResponse;
import es.safareyes.schoolevents.servicios.NotificacionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
@AllArgsConstructor
public class NotificacionController {

    private NotificacionService notificacionService;

    // LISTAR TODAS LAS NOTIFICACIONES
    @GetMapping
    public ResponseEntity<List<NotificacionResponse>> listarTodas() {

        List<NotificacionResponse> notificaciones =
                notificacionService.listarTodas();

        return ResponseEntity.ok(notificaciones);
    }

    // LISTAR NOTIFICACIONES DE UN USUARIO
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionResponse>> listarPorUsuario(
            @PathVariable Integer usuarioId) {

        List<NotificacionResponse> notificaciones =
                notificacionService.listarPorUsuario(usuarioId);

        return ResponseEntity.ok(notificaciones);
    }

    // BUSCAR UNA NOTIFICACIÓN POR ID
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponse> buscarPorId(
            @PathVariable Integer id) {

        NotificacionResponse notificacion =
                notificacionService.buscarPorId(id);

        return ResponseEntity.ok(notificacion);
    }

    // CREAR UNA NOTIFICACIÓN
    @PostMapping
    public ResponseEntity<NotificacionResponse> crear(
            @Valid @RequestBody NotificacionRequest request) {

        NotificacionResponse nuevaNotificacion =
                notificacionService.crear(request);

        return new ResponseEntity<>(
                nuevaNotificacion,
                HttpStatus.CREATED
        );
    }

    // ACTUALIZAR UNA NOTIFICACIÓN
    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody NotificacionRequest request) {

        NotificacionResponse actualizada =
                notificacionService.actualizar(id, request);

        return ResponseEntity.ok(actualizada);
    }

    // ELIMINAR UNA NOTIFICACIÓN
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {

        notificacionService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    // MARCAR UNA NOTIFICACIÓN COMO LEÍDA
    @PatchMapping("/{id}/leida")
    public ResponseEntity<Void> marcarComoLeida(
            @PathVariable Integer id) {

        notificacionService.marcarComoLeida(id);

        return ResponseEntity.noContent().build();
    }
}