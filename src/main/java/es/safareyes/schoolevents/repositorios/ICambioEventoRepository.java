package es.safareyes.schoolevents.repositorios;

import es.safareyes.schoolevents.modelos.CambioEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICambioEventoRepository extends JpaRepository<CambioEvento, Integer> {

}
