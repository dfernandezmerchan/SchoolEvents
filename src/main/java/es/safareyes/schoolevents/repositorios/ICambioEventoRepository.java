package es.safareyes.schoolevents.repositorios;

import es.safareyes.schoolevents.modelos.CambioEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICambioEventoRepository extends JpaRepository<CambioEvento, Integer> {
    List<CambioEvento> findByEventoId(Integer eventoId);
}