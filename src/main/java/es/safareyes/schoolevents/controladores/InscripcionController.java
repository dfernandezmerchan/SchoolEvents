package es.safareyes.schoolevents.controladores;

import es.safareyes.schoolevents.dto.request.InscripcionRequest;
import es.safareyes.schoolevents.dto.response.InscripcionResponse;
import es.safareyes.schoolevents.servicios.InscripcionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inscripciones")
@AllArgsConstructor
public class InscripcionController {

    private InscripcionService inscripcionService;

    // INSCRIBIRSE EN UN EVENTO
    @PostMapping
    public ResponseEntity<InscripcionResponse> inscribirse(
            @Valid @RequestBody InscripcionRequest request) {

        InscripcionResponse nuevaInscripcion =
                inscripcionService.inscribirse(request);

        return new ResponseEntity<>(nuevaInscripcion, HttpStatus.CREATED);
    }

    // CANCELAR UNA INSCRIPCIÓN
    @DeleteMapping("/{eventoId}/{usuarioId}")
    public ResponseEntity<Void> cancelarInscripcion(
            @PathVariable Integer eventoId,
            @PathVariable Integer usuarioId) {

        inscripcionService.cancelarInscripcion(eventoId, usuarioId);

        return ResponseEntity.noContent().build();
    }

    // LISTAR INSCRIPCIONES DE UN EVENTO
    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<InscripcionResponse>> listarPorEvento(
            @PathVariable Integer eventoId) {

        List<InscripcionResponse> inscripciones =
                inscripcionService.listarPorEvento(eventoId);

        return ResponseEntity.ok(inscripciones);
    }

    // LISTAR INSCRIPCIONES DE UN USUARIO
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<InscripcionResponse>> agendaUsuario(
            @PathVariable Integer usuarioId) {

        List<InscripcionResponse> inscripciones =
                inscripcionService.agendaUsuario(usuarioId);

        return ResponseEntity.ok(inscripciones);
    }

    // BUSCAR UNA INSCRIPCIÓN POR ID
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionResponse> buscarPorId(
            @PathVariable Integer id) {

        InscripcionResponse inscripcion =
                inscripcionService.buscarPorId(id);

        return ResponseEntity.ok(inscripcion);
    }

    //informe de un evento
    @GetMapping("/informe")
    public ResponseEntity<List<Object[]>> getInformeParticipacion(
            @RequestParam LocalDateTime desde,
            @RequestParam LocalDateTime hasta) {

        List<Object[]> informe =
                inscripcionService.getInformeParticipacion(desde, hasta);

        return ResponseEntity.ok(informe);
    }
}