package es.safareyes.schoolevents;

import es.safareyes.schoolevents.modelos.Lugar;
import es.safareyes.schoolevents.repositorios.ICategoriaRepository;
import es.safareyes.schoolevents.repositorios.ILugarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepositorioLugates {

    @Autowired
    private ILugarRepository lugares;

    @Test
    void traerLugares() {
        List<Lugar> todosLugares = lugares.findAll();
        for(Lugar lugar: todosLugares){
            System.out.println(lugar.getNombre());
            System.out.println("Aforo maximo: " + lugar.getAforomaximo() + "\n");

        }


    }

}
