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

    @Test
    void filtrarEventosCategoria() {
        //Categorias posibles: Tecnología, Deportes, Cultura, Ocio, Formación
        List<Evento> successFiltradoCategorias = eventos.findByCategoria_NombreOrderByFechainiAsc("Deportes");

        System.out.println("Eventos encontrados: " + successFiltradoCategorias.size());
        for (Evento evento: successFiltradoCategorias) {
            System.out.println("Titulo del evento: " + evento.getTitulo());
            System.out.println("Descripción: " + evento.getDescripcion());
            System.out.println("Fecha de inicio " + evento.getFechaini());
            System.out.println("Fecha de fin: " + evento.getFechafin() );
            System.out.println("Cursos asignados:");
            evento.getCursos().forEach(curso -> System.out.println(curso.getNombre()));
            System.out.println("categoría: " + evento.getCategoria().getNombre());
            System.out.println("Lugar: " + evento.getLugar().getNombre());
            System.out.println("Organizador: " + evento.getOrganizador().getNombre());
        }
    }

    @Test
    void filtrarEventosCursos() {
        List<Evento> successFiltradoCursos = eventos.findByCursos_IdOrderByFechainiAsc(1);
        System.out.println("Eventos encontrados: " + successFiltradoCursos.size());
        for (Evento evento: successFiltradoCursos) {
            System.out.println("Titulo del evento: " + evento.getTitulo());
            System.out.println("Descripción: " + evento.getDescripcion());
            System.out.println("Fecha de inicio " + evento.getFechaini());
            System.out.println("Fecha de fin: " + evento.getFechafin() );
            System.out.println("Cursos asignados:");
            evento.getCursos().forEach(curso -> System.out.println(curso.getNombre()));
            System.out.println("categoría: " + evento.getCategoria().getNombre());
            System.out.println("Lugar: " + evento.getLugar().getNombre());
            System.out.println("Organizador: " + evento.getOrganizador().getNombre());
        }

    }

    @Test
    void filtrarEventosTitulo() {
        List<Evento> successFiltradoTitulos = eventos.findByTituloContainingIgnoreCaseOrderByFechainiAsc("Fútbol");
        System.out.println("Eventos encontrados: " + successFiltradoTitulos.size());
        for (Evento evento: successFiltradoTitulos) {
            System.out.println("Titulo del evento: " + evento.getTitulo());
            System.out.println("Descripción: " + evento.getDescripcion());
            System.out.println("Fecha de inicio " + evento.getFechaini());
            System.out.println("Fecha de fin: " + evento.getFechafin() );
            System.out.println("Cursos asignados:");
            evento.getCursos().forEach(curso -> System.out.println(curso.getNombre()));
            System.out.println("categoría: " + evento.getCategoria().getNombre());
            System.out.println("Lugar: " + evento.getLugar().getNombre());
            System.out.println("Organizador: " + evento.getOrganizador().getNombre());
        }

    }

    @Test
    void filtrarEventosDesde() {
        List<Evento> successFiltradoFechaD = eventos.findByFechainiGreaterThanEqualOrderByFechainiAsc(LocalDateTime.now());
        System.out.println("Eventos encontrados: " + successFiltradoFechaD.size());
        for (Evento evento: successFiltradoFechaD) {
            System.out.println("Titulo del evento: " + evento.getTitulo());
            System.out.println("Descripción: " + evento.getDescripcion());
            System.out.println("Fecha de inicio " + evento.getFechaini());
            System.out.println("Fecha de fin: " + evento.getFechafin() );
            System.out.println("Cursos asignados:");
            evento.getCursos().forEach(curso -> System.out.println(curso.getNombre()));
            System.out.println("categoría: " + evento.getCategoria().getNombre());
            System.out.println("Lugar: " + evento.getLugar().getNombre());
            System.out.println("Organizador: " + evento.getOrganizador().getNombre());
        }

    }

    @Test
    void filtrarEventosEntre() {
        List<Evento> successFiltradoFechaE = eventos.findByFechainiBetweenOrderByFechainiAsc(LocalDateTime.of(2026, 10, 1, 0, 0),
                LocalDateTime.of(2026, 10, 31, 23, 59));
        System.out.println("Eventos encontrados: " + successFiltradoFechaE.size());
        for (Evento evento: successFiltradoFechaE) {
            System.out.println("Titulo del evento: " + evento.getTitulo());
            System.out.println("Descripción: " + evento.getDescripcion());
            System.out.println("Fecha de inicio " + evento.getFechaini());
            System.out.println("Fecha de fin: " + evento.getFechafin() );
            System.out.println("Cursos asignados:");
            evento.getCursos().forEach(curso -> System.out.println(curso.getNombre()));
            System.out.println("categoría: " + evento.getCategoria().getNombre());
            System.out.println("Lugar: " + evento.getLugar().getNombre());
            System.out.println("Organizador: " + evento.getOrganizador().getNombre());
        }

    }

}
