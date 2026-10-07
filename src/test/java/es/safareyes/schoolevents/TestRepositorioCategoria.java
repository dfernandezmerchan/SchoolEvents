package es.safareyes.schoolevents;

import es.safareyes.schoolevents.modelos.Categoria;
import es.safareyes.schoolevents.repositorios.ICategoriaRepository;
import es.safareyes.schoolevents.repositorios.IEventoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepositorioCategoria {

    @Autowired
    private ICategoriaRepository categorias;

    @Test
    void traerCategorias() {
        List<Categoria> todascategorias = categorias.findAll();
        for (Categoria categoria: todascategorias) {
            System.out.println(categoria.getNombre() + " " + categoria.getIcono() + " " + categoria.getColor());
        }
    }



}
