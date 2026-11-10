package es.safareyes.schoolevents.controladores;

import es.safareyes.schoolevents.dto.response.CambioEventoResponse;
import es.safareyes.schoolevents.servicios.CambioEventoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cambiosevento")
@AllArgsConstructor
public class CambioEventoController {

    private CambioEventoService cambioEventoService;

    // LISTAR TODOS LOS CAMBIOS
    @GetMapping
    public ResponseEntity<List<CambioEventoResponse>> listarTodos() {

        List<CambioEventoResponse> cambios =
                cambioEventoService.listarTodos();

        return ResponseEntity.ok(cambios);
    }

    // LISTAR CAMBIOS DE UN EVENTO
    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<CambioEventoResponse>> listarPorEvento(
            @PathVariable Integer eventoId) {

        List<CambioEventoResponse> cambios =
                cambioEventoService.listarPorEvento(eventoId);

        return ResponseEntity.ok(cambios);
    }

    // BUSCAR UN CAMBIO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<CambioEventoResponse> buscarPorId(
            @PathVariable Integer id) {

        CambioEventoResponse cambio =
                cambioEventoService.buscarPorId(id);

        return ResponseEntity.ok(cambio);
    }

    // REGISTRAR UN CAMBIO
    @PostMapping
    public ResponseEntity<CambioEventoResponse> registrarCambio(
            @RequestParam Integer idEvento,
            @RequestParam String campoModificado,
            @RequestParam String valorAnterior,
            @RequestParam String valorNuevo) {

        CambioEventoResponse cambio =
                cambioEventoService.registrarCambio(
                        idEvento,
                        campoModificado,
                        valorAnterior,
                        valorNuevo
                );

        return new ResponseEntity<>(cambio, HttpStatus.CREATED);
    }

    // ELIMINAR UN CAMBIO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {

        cambioEventoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}