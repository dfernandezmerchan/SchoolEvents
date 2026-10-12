package es.safareyes.schoolevents.repositorios;

import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.modelos.Inscripcion;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    @EntityGraph(attributePaths = {"usuario"})
    List<Inscripcion> findByEventoId(Integer eventoId);
}
