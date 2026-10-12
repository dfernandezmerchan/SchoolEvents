package es.safareyes.schoolevents;

import es.safareyes.schoolevents.modelos.Foto;
import es.safareyes.schoolevents.modelos.Publicacion;
import es.safareyes.schoolevents.repositorios.IPublicacionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

//get 15
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepositorioPublicacion {

    @Autowired
    private IPublicacionRepository publicacionesRepo;

    @Test
    void consultarPublicacionesConFotos() {
        List<Publicacion> publicaciones = publicacionesRepo.findAll();

        for (Publicacion publicacion : publicaciones) {
            System.out.println("Título: " + publicacion.getTitulo());
            System.out.println("Texto: " + publicacion.getDescripcion());

            System.out.println("Fotos:");
            for (Foto foto : publicacion.getFotos()) {
                System.out.println(" - " + foto.getUrl());
            }
            System.out.println("-------------------------");
        }
    }

}
