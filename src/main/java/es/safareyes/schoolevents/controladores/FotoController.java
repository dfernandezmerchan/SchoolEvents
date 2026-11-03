package es.safareyes.schoolevents.controladores;

import es.safareyes.schoolevents.dto.request.CategoriaRequest;
import es.safareyes.schoolevents.dto.request.FotoRequest;
import es.safareyes.schoolevents.dto.request.LugarRequest;
import es.safareyes.schoolevents.dto.response.CategoriaResponse;
import es.safareyes.schoolevents.dto.response.FotoResponse;
import es.safareyes.schoolevents.dto.response.LugarResponse;
import es.safareyes.schoolevents.dto.response.PublicacionResponse;
import es.safareyes.schoolevents.modelos.Foto;
import es.safareyes.schoolevents.servicios.CategoriaService;
import es.safareyes.schoolevents.servicios.FotoService;
import es.safareyes.schoolevents.servicios.PublicacionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fotos")
@AllArgsConstructor
public class FotoController {

    private final FotoService fotoService;
    private final PublicacionService publicacionService;
    private CategoriaService categoriaService;

    //buscar todas las fotos
    // GET: /api/fotos
    @GetMapping
    public ResponseEntity<List<FotoResponse>> listarTodas() {
        List<FotoResponse> fotos = fotoService.listarTodas();
        return ResponseEntity.ok(fotos);
    }

    //buscar foto por id
    // GET: /api/fotos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<FotoResponse> buscarPorId(@PathVariable Integer id) {
        FotoResponse fotos = fotoService.buscarPorId(id);
        return ResponseEntity.ok(fotos);
    }

    //buscar fotos por id de publicacion
    // get: /api/fotos/publicacion/{id}
    @GetMapping("publicacion/{id}")
    public ResponseEntity<List<FotoResponse>> buscarPorIdPublicacion(@PathVariable Integer id) {
        List<FotoResponse> fotos = fotoService.listarPorPublicacion(id);
        return ResponseEntity.ok(fotos);
    }

    //crear foto
    //post: /api/fotos/publicacionid/fotos
    @PostMapping("/{publicacionId}/fotos")
    public ResponseEntity<FotoResponse> crearFoto(@PathVariable Integer publicacionId, @RequestBody @Valid FotoRequest request) {
        FotoResponse nuevaFoto = fotoService.crearFoto(publicacionId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFoto);
    }

    //eliminar foto
    //delete: /api/fotos/{id}
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarFoto(@PathVariable Integer id) {

        fotoService.eliminar(id);
        return ResponseEntity.noContent().build();

    }

    //actualizar foto
    //put. /api/fotos/{id}
    @PutMapping("{id}")
    public ResponseEntity<FotoResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody FotoRequest request) {
        FotoResponse actualizado = fotoService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);

    }


}
