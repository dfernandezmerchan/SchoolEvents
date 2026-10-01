package es.safareyes.schoolevents;

import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.repositorios.IEventoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepositorioEvento {

    @Autowired
    private IEventoRepository repository;

    @Test
    void consultarEvento() {

        List<Evento> todos = repository.findAll();

        System.out.println("Número de eventos: " + todos.size());

        for (Evento evento : todos) {
            System.out.println("ID: " + evento.getId());
            System.out.println("Título: " + evento.getTitulo());
            System.out.println("Descripción: " + evento.getDescripcion());
            System.out.println("Estado: " + evento.getEstado());
            System.out.println("-----------------------------");
        }

    }

}
