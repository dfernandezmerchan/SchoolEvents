package es.safareyes.schoolevents.controladores;

import es.safareyes.schoolevents.dto.request.CursoRequest;
import es.safareyes.schoolevents.dto.response.CursoResponse;
import es.safareyes.schoolevents.servicios.CursoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@AllArgsConstructor
public class CursoController {

    private CursoService cursoService;

    // GET: /api/cursos
    @GetMapping
    public ResponseEntity<List<CursoResponse>> listarTodos() {
        List<CursoResponse> cursos = cursoService.listarTodos();
        return ResponseEntity.ok(cursos);
    }

    // GET: /api/cursos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> buscarPorId(@PathVariable Integer id) {
        CursoResponse curso = cursoService.buscarPorId(id);
        return ResponseEntity.ok(curso);
    }

    // POST: /api/cursos
    @PostMapping
    public ResponseEntity<CursoResponse> crear(@Valid @RequestBody CursoRequest request) {
        CursoResponse nuevoCurso = cursoService.crear(request);
        return new ResponseEntity<>(nuevoCurso, HttpStatus.CREATED);
    }

    // PUT: /api/cursos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody CursoRequest request) {
        CursoResponse actualizado = cursoService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    // DELETE: /api/cursos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        cursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}