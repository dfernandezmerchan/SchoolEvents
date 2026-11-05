package es.safareyes.schoolevents.controladores;

import es.safareyes.schoolevents.dto.request.PublicacionRequest;
import es.safareyes.schoolevents.dto.request.UsuarioRequest;
import es.safareyes.schoolevents.dto.response.PublicacionResponse;
import es.safareyes.schoolevents.dto.response.UsuarioResponse;
import es.safareyes.schoolevents.servicios.PublicacionService;
import es.safareyes.schoolevents.servicios.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicacion")
@AllArgsConstructor
public class PublicacionController {

    private PublicacionService publicacionService;

    //listar todas las publicaciones
    @GetMapping
    public ResponseEntity<List<PublicacionResponse>> listarTodas() {
        List<PublicacionResponse> publicaciones = publicacionService.listarTodas();
        return ResponseEntity.ok(publicaciones);
    }

    // GET /api/publicacion/eventos/{eventoId}
    @GetMapping("/eventos/{eventoId}")
    public ResponseEntity<List<PublicacionResponse>> listarPorEvento(@PathVariable Integer eventoId) {
        List<PublicacionResponse> publicaciones = publicacionService.listarPorEvento(eventoId);
        return ResponseEntity.ok(publicaciones);
    }

    // GET /api/publicacion/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PublicacionResponse>> listarPorUsuario(@PathVariable Integer usuarioId) {
        List<PublicacionResponse> publicaciones = publicacionService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(publicaciones);
    }

    // GET /api/publicacion/{usuarioId}
    // 2. BUSCAR publicacion POR ID
    @GetMapping("/{id}")
    public ResponseEntity<PublicacionResponse> buscarPorId(@PathVariable Integer id) {
        PublicacionResponse publicacion = publicacionService.buscarPorId(id);
        return ResponseEntity.ok(publicacion);
    }

    // 3. CREAR UN NUEVO publicacion
    @PostMapping
    public ResponseEntity<PublicacionResponse> crear(@Valid @RequestBody PublicacionRequest request) {
        PublicacionResponse nuevaPublicacion = publicacionService.crear(request);
        return new ResponseEntity<>(nuevaPublicacion, HttpStatus.CREATED);
    }

    // 4. ACTUALIZAR UNa PUBLICACION EXISTENTE
    @PutMapping("/{id}")
    public ResponseEntity<PublicacionResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody PublicacionRequest request) {
        PublicacionResponse actualizado = publicacionService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    // 5. ELIMINAR UNa publicacion
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        publicacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }








}
