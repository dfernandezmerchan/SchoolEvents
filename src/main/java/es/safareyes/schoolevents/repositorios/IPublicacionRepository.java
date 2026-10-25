package es.safareyes.schoolevents.repositorios;

import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.modelos.Publicacion;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPublicacionRepository extends JpaRepository<Publicacion, Integer> {
    @Override
    @EntityGraph(attributePaths = {"fotos"})
    List<Publicacion> findAll();

    List<Publicacion> findByUsuarioId(Integer usuarioId);

    List<Publicacion> findByEventoId(Integer eventoId);
}
