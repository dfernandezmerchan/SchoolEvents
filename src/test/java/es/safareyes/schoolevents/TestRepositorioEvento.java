package es.safareyes.schoolevents;

import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.modelos.Lugar;
import es.safareyes.schoolevents.repositorios.IEventoRepository;
import es.safareyes.schoolevents.repositorios.IInscripcionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepositorioEvento {

    @Autowired
    private IEventoRepository eventos;
    List<Evento.estadoevento> visibles = List.of(Evento.estadoevento.PUBLICADO, Evento.estadoevento.FINALIZADO);

    @Autowired
    private IInscripcionRepository inscripciones;


    private void imprimirEventos(List<Evento> eventos) {
        System.out.println("Eventos encontrados: " + eventos.size());
        for (Evento evento : eventos) {
            System.out.println("Titulo del evento: " + evento.getTitulo());
            System.out.println("Descripción: " + evento.getDescripcion());
            System.out.println("Fecha de inicio: " + evento.getFechaini());
            System.out.println("Fecha de fin: " + evento.getFechafin());
            System.out.println("Cursos asignados:");
            evento.getCursos().forEach(curso -> System.out.println("  - " + curso.getNombre()));
            System.out.println("Categoría: " + evento.getCategoria().getNombre());
            System.out.println("Lugar: " + evento.getLugar().getNombre());
            System.out.println("Organizador: " + evento.getOrganizador().getNombre());
            System.out.println("---");
        }
    }

    @Test
    void filtrarEventosCategoria() {
        //Categorias posibles: Tecnología, Deportes, Cultura, Ocio, Formación
        imprimirEventos(eventos.findByCategoria_NombreOrderByFechainiAsc("Deportes"));
    }

    @Test
    void filtrarEventosCursos() {
        imprimirEventos(eventos.findByCursos_IdOrderByFechainiAsc(1));
    }

    @Test
    void filtrarEventosTitulo() {
        imprimirEventos(eventos.findByTituloContainingIgnoreCaseOrderByFechainiAsc("proyectos"));
    }

    @Test
    void filtrarEventosDesde() {
        imprimirEventos(eventos.findByFechainiGreaterThanEqualOrderByFechainiAsc(LocalDateTime.now()));
    }

    @Test
    void filtrarEventosEntre() {
        imprimirEventos(eventos.findByFechainiBetweenOrderByFechainiAsc(
                LocalDateTime.of(2026, 10, 1, 0, 0),
                LocalDateTime.of(2026, 10, 31, 23, 59)));
    }

    @Test
    void eventosDelMes() {
        int mes = 10;
        int anio = 2026;

        LocalDateTime desde = LocalDateTime.of(anio, mes, 1, 0, 0);
        LocalDateTime hasta = desde.plusMonths(1).minusNanos(1); //+1 mes -1 dia osea 31 del mes no 1 del siguiente

        List<Evento> resultado = eventos.findByEstadoInAndFechainiBetweenOrderByFechainiAsc(visibles, desde, hasta);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("Eventos de " + Month.of(mes) + " - " + anio + ": " + resultado.size());
        for (Evento e : resultado) {
            System.out.println(e.getCategoria().getIcono() + " " + e.getFechaini().format(formato) + " - " + e.getTitulo());
        }
    }

    @Test
    void eventosIdDetalle() {
        List<Evento> eventosId = eventos.findByIdOrderByFechainiAsc(1);


        for(Evento evento: eventosId) {
            int aforo = evento.getLugar().getAforomaximo();
            int ocupadas = inscripciones.countByEvento_Id(evento.getId());
            int libres = aforo - ocupadas;


            System.out.println("nombre: " + evento.getTitulo());
            System.out.println("plazas libres: " + libres );
            System.out.println("cursos implicados: ");
            evento.getCursos().forEach(curso -> System.out.println(curso.getNombre()));
        }
    }

}
