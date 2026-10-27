package es.safareyes.schoolevents.controladores;

import es.safareyes.schoolevents.dto.request.LugarRequest;
import es.safareyes.schoolevents.dto.response.LugarResponse;
import es.safareyes.schoolevents.servicios.LugarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugares")
@AllArgsConstructor
public class LugarController {

    private LugarService lugarService;

    // 1. OBTERNER TODOS LOS LUGARES
    @GetMapping
    public ResponseEntity<List<LugarResponse>> listarTodos() {
        List<LugarResponse> lugares = lugarService.listarTodos();
        return ResponseEntity.ok(lugares);
    }

    // 2. BUSCAR LUGAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<LugarResponse> buscarPorId(@PathVariable Integer id) {
        LugarResponse lugar = lugarService.buscarPorId(id);
        return ResponseEntity.ok(lugar);
    }

    // 3. CREAR UN NUEVO LUGAR
    @PostMapping
    public ResponseEntity<LugarResponse> crear(@Valid @RequestBody LugarRequest request) {
        LugarResponse nuevoLugar = lugarService.crear(request);
        return new ResponseEntity<>(nuevoLugar, HttpStatus.CREATED);
    }

    // 4. ACTUALIZAR UN LUGAR EXISTENTE
    @PutMapping("/{id}")
    public ResponseEntity<LugarResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody LugarRequest request) {
        LugarResponse actualizado = lugarService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    // 5. ELIMINAR UN LUGAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        lugarService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}