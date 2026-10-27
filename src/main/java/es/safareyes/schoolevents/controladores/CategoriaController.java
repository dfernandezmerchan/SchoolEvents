package es.safareyes.schoolevents.controladores;

import es.safareyes.schoolevents.dto.request.CategoriaRequest;
import es.safareyes.schoolevents.dto.response.CategoriaResponse;
import es.safareyes.schoolevents.servicios.CategoriaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@AllArgsConstructor
public class CategoriaController {

    private CategoriaService categoriaService;

    // GET: /api/categoria
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listarTodos() {
        List<CategoriaResponse> categorias = categoriaService.listarTodas();
        return ResponseEntity.ok(categorias);
    }

    // GET: /api/categoria/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Integer id) {
        CategoriaResponse categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoria);
    }

    // POST: /api/categoria
    @PostMapping
    public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse nuevaCategoria = categoriaService.crear(request);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

}
