package es.safareyes.schoolevents.controladores;

import es.safareyes.schoolevents.dto.request.LugarRequest;
import es.safareyes.schoolevents.dto.request.UsuarioRequest;
import es.safareyes.schoolevents.dto.response.LugarResponse;
import es.safareyes.schoolevents.dto.response.UsuarioResponse;
import es.safareyes.schoolevents.servicios.LugarService;
import es.safareyes.schoolevents.servicios.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService usuarioService;

    // 1. OBTERNER TODOS LOS USUARIOS
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        List<UsuarioResponse> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // 2. BUSCAR usuario POR ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Integer id) {
        UsuarioResponse usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // 3. CREAR UN NUEVO USUARIO
    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse nuevoUsuario = usuarioService.crear(request);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // 4. ACTUALIZAR UN USUARIO EXISTENTE
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse actualizado = usuarioService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    // 5. ELIMINAR UN usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
