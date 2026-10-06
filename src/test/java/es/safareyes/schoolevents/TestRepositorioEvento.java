package es.safareyes.schoolevents;

import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.repositorios.IEventoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepositorioEvento {

    @Autowired
    private IEventoRepository eventos;

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
        imprimirEventos(eventos.findByTituloContainingIgnoreCaseOrderByFechainiAsc("Fútbol"));
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

}
