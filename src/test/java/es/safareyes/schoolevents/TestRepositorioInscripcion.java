package es.safareyes.schoolevents;


import es.safareyes.schoolevents.modelos.Inscripcion;
import es.safareyes.schoolevents.repositorios.IInscripcionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepositorioInscripcion {

    @Autowired
    private IInscripcionRepository inscripcionesRepo;

    //get 12
    @Test
    void consultarInscripcionesPorEvento() {
        int idEvento = 1;
        List<Inscripcion> inscripciones = inscripcionesRepo.findByEventoId(idEvento);

        if (inscripciones.isEmpty()) {
            System.out.println("No hay inscritos en el evento " + idEvento);
            return;
        }

        System.out.println("=== INSCRITOS EN EL EVENTO " + idEvento + " ===");

        for (Inscripcion ins : inscripciones) {
            System.out.println("ID Inscripción : " + ins.getId());
            System.out.println("Usuario        : " + ins.getUsuario().getNombre() + " " +ins.getUsuario().getApellido1());
        }
    }

}
