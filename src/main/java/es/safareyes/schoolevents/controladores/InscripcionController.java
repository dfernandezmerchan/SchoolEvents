package es.safareyes.schoolevents.controladores;

import es.safareyes.schoolevents.dto.request.InscripcionRequest;
import es.safareyes.schoolevents.dto.response.InformeParticipacion.InformeParticipacionResponse;
import es.safareyes.schoolevents.dto.response.InscripcionResponse;
import es.safareyes.schoolevents.dto.response.ParticipacionCursoResponse;
import es.safareyes.schoolevents.dto.response.ParticipacionInformeResponse;
import es.safareyes.schoolevents.servicios.InscripcionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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


    // Informe de participación de un evento agrupado por categorías
    @GetMapping("/informe")
    public ResponseEntity<List<ParticipacionInformeResponse>> getInformeParticipacion(
            @RequestParam LocalDateTime desde,
            @RequestParam LocalDateTime hasta) {

        // Obtenemos los datos del informe
        List<InformeParticipacionResponse> informe =
                inscripcionService.getInformeParticipacion(desde, hasta);

        List<ParticipacionInformeResponse> respuesta = new ArrayList<>();

        for (InformeParticipacionResponse fila : informe) {

            // Comprobamos si la categoría ya está creada
            ParticipacionInformeResponse categoria = null;

            for (ParticipacionInformeResponse elemento : respuesta) {
                if (elemento.categoria().equals(fila.getCategoria())) {
                    categoria = elemento;
                    break;
                }
            }

            // Si no existe, creamos la categoría
            if (categoria == null) {
                categoria = new ParticipacionInformeResponse(
                        fila.getCategoria(),
                        new ArrayList<>()
                );

                respuesta.add(categoria);
            }

            // Añadimos el curso y su número de participantes a la categoría
            categoria.cursos().add(
                    new ParticipacionCursoResponse(
                            fila.getCurso(),
                            fila.getTotalParticipantes()
                    )
            );
        }

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/evento/{eventoId}/confirmados")
    public ResponseEntity<List<InscripcionResponse>> listarConfirmados(
            @PathVariable Integer eventoId) {

        return ResponseEntity.ok(
                inscripcionService.listarConfirmados(eventoId)
        );
    }

    @GetMapping("/evento/{eventoId}/espera")
    public ResponseEntity<List<InscripcionResponse>> listarEnEspera(
            @PathVariable Integer eventoId) {

        return ResponseEntity.ok(
                inscripcionService.listarEnEspera(eventoId)
        );
    }
}