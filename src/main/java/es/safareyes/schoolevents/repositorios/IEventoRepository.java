package es.safareyes.schoolevents.repositorios;

import es.safareyes.schoolevents.modelos.Evento;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//esto se hace por cada tabla

@Repository
public interface IEventoRepository extends JpaRepository<Evento, Integer> {

    //FILTRAR POR CATEGORIA y estado

    @EntityGraph(attributePaths = {"categoria", "cursos", "lugar", "organizador"})
    List<Evento> findByCategoria_IdAndEstadoOrderByFechainiAsc(Integer categoria_id, Evento.estadoevento estado);

    //FILTRAR POR Curso

    @EntityGraph(attributePaths = {"categoria", "cursos", "lugar", "organizador"})
    List<Evento> findByCursos_IdOrderByFechainiAsc(Integer idCurso);

    //filtrar por titulo
    @EntityGraph(attributePaths = {"categoria", "cursos", "lugar", "organizador"})
    List<Evento> findByTituloContainingIgnoreCaseOrderByFechainiAsc(String titulo);

    //filtrar desde una fecha concreta
    @EntityGraph(attributePaths = {"categoria", "cursos", "lugar", "organizador"})
    List<Evento> findByFechainiGreaterThanEqualOrderByFechainiAsc(LocalDateTime desde);

    //entre dos fechas
    @EntityGraph(attributePaths = {"categoria", "cursos", "lugar", "organizador"})
    List<Evento> findByFechainiBetweenOrderByFechainiAsc(LocalDateTime desde, LocalDateTime hasta);

    //listado de eventos del mes (get 2) preguntar si es importante los estados borrador para mostrar o no
    @EntityGraph(attributePaths = {"categoria"})
    List<Evento> findByEstadoInAndFechainiBetweenOrderByFechainiAsc(
            List<Evento.estadoevento> estados, LocalDateTime desde, LocalDateTime hasta);


    //filtrar eventos por la id y que deuvelva lugar requisitos plazas libres y cursos
    @Query(value = """
        SELECT 
            e.titulo AS titulo,
            (l.aforomaximo - COUNT(DISTINCT i.id)) AS plazasLibres,
            STRING_AGG(DISTINCT c.nombre, ', ') AS cursos
        FROM evento e
        LEFT JOIN lugar l ON e.id_lugar = l.id
        LEFT JOIN inscripcion i ON e.id = i.evento_id
        LEFT JOIN evento_curso ec ON e.id = ec.evento_id
        LEFT JOIN curso c ON ec.curso_id = c.id
        WHERE e.id = :id
        GROUP BY e.id, l.id
        """, nativeQuery = true)
    Tuple eventosLugar(@Param("id") Integer id);

    //get 11
    @Query(value = """
        SELECT 
            e.titulo,
            e.fechaini,
            l.nombre AS lugarNombre,
            u.nombre AS nombreUsuario 
        FROM evento e
        INNER JOIN inscripcion i ON e.id = i.evento_id
        LEFT JOIN lugar l ON e.id_lugar = l.id
        INNER JOIN usuario u ON i.usuario_id = u.id 
        WHERE i.usuario_id = :usuarioId
        ORDER BY e.fechaini ASC
        """, nativeQuery = true)
    List<Object[]> AgendaUsuario(@Param("usuarioId") Integer usuarioId);


}
