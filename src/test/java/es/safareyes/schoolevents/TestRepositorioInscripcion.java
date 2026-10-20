package es.safareyes.schoolevents;


import es.safareyes.schoolevents.modelos.Inscripcion;
import es.safareyes.schoolevents.repositorios.IInscripcionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepositorioInscripcion {

    @Autowired
    private IInscripcionRepository inscripcionesRepo;

    //get 12
    @Test
    void consultarInscripcionesPorEvento() {
        int idEvento = 2;
        List<Inscripcion> inscripciones = inscripcionesRepo.findByEventoId(idEvento);

        if (inscripciones.isEmpty()) {
            System.out.println("No hay inscritos en el evento " + idEvento);
            return;
        }

        System.out.println("=== INSCRITOS EN EL EVENTO " + idEvento + " ===");

        for (Inscripcion ins : inscripciones) {
            System.out.println("ID Inscripción : " + ins.getId());
            System.out.println("Usuario: " + ins.getUsuario().getNombre() + " " +ins.getUsuario().getApellido1());
        }
    }

    //get 16 REVISAR
    @Test
    void consultarInformeParticipacion() {
        LocalDateTime desde = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime hasta = LocalDateTime.of(2026, 12, 31, 23, 59);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


        List<Object[]> informe = inscripcionesRepo.getInformeParticipacion(desde, hasta);

        if (informe.isEmpty()) {
            System.out.println("No hay participaciones registradas en ese rango de fechas.");
            return;
        }

        System.out.println("=== INFORME DE PARTICIPACIÓN ===");
        System.out.println("Desde: " + desde.format(formato) + " Hasta: " + hasta.format(formato));
        for (Object[] fila : informe) {
            System.out.println("Categoría   : " + fila[0]);
            System.out.println("Curso       : " + fila[1]);
            System.out.println("Inscritos   : " + fila[2]);
            System.out.println("-------------------------");
        }
    }

}
