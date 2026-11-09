package es.safareyes.schoolevents.controladores;


import es.safareyes.schoolevents.dto.response.NotificacionResponse;
import es.safareyes.schoolevents.servicios.NotificacionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notificacion")
@AllArgsConstructor
public class NotificacionController {

    private NotificacionService notificacionService;

    //listar todas las notificaciones por usuario
    @GetMapping("/{id}")
    public ResponseEntity<List<NotificacionResponse>> buscarPorUsuarioId(@PathVariable Integer id) {
        List<NotificacionResponse> notificacion = notificacionService.listarPorUsuario(id);
        return ResponseEntity.ok(notificacion);
    }

}
