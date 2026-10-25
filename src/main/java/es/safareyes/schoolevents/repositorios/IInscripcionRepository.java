package es.safareyes.schoolevents.repositorios;

import es.safareyes.schoolevents.modelos.Inscripcion;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IInscripcionRepository extends JpaRepository<Inscripcion, Integer> {

    @EntityGraph(attributePaths = {"usuario", "evento"})
    List<Inscripcion> findByEventoId(Integer eventoId);

    @EntityGraph(attributePaths = {"evento", "evento.lugar", "evento.categoria"})
    List<Inscripcion> findByUsuarioId(Integer usuarioId);

    Optional<Inscripcion> findByEventoIdAndUsuarioId(Integer eventoId, Integer usuarioId);

    // Métodos para el control de aforo y promociones de la lista de espera
    long countByEventoIdAndEstado(Integer eventoId, Inscripcion.estadoinscripcion estado);

    Optional<Inscripcion> findFirstByEventoIdAndEstadoOrderByFechaAsc(Integer eventoId, Inscripcion.estadoinscripcion estado);

    @Query(value = """
    SELECT 
        cat.nombre AS categoria,
        cur.nombre AS curso,
        COUNT(i.id) AS totalParticipantes
    FROM inscripcion i
    INNER JOIN evento e ON i.evento_id = e.id
    INNER JOIN categoria cat ON e.id_categoria = cat.id
    INNER JOIN usuario u ON i.usuario_id = u.id
    INNER JOIN curso cur ON u.curso_id = cur.id
    WHERE e.fechaini BETWEEN :desde AND :hasta
    GROUP BY cat.nombre, cur.nombre
    ORDER BY cat.nombre, cur.nombre
    """, nativeQuery = true)
    List<Object[]> getInformeParticipacion(
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta);
}