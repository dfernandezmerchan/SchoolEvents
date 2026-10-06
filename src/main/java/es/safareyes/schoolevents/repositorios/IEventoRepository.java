package es.safareyes.schoolevents.repositorios;

import es.safareyes.schoolevents.modelos.Evento;
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

    //FILTRAR POR CATEGORIA
    @EntityGraph(attributePaths = {"categoria", "cursos", "lugar", "organizador"})
    List<Evento> findByCategoria_NombreOrderByFechainiAsc(String categoria);

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

}
