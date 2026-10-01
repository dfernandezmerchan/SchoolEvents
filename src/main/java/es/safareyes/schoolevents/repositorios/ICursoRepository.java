package es.safareyes.schoolevents.repositorios;

import es.safareyes.schoolevents.modelos.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICursoRepository extends JpaRepository<Curso, Integer> {

}
