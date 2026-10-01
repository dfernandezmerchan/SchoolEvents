package es.safareyes.schoolevents.repositorios;

import es.safareyes.schoolevents.modelos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {

}
