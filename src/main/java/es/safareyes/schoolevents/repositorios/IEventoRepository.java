package es.safareyes.schoolevents.repositorios;

import es.safareyes.schoolevents.modelos.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//esto se hace por cada tabla

@Repository
public interface IEventoRepository extends JpaRepository<Evento, Integer> {

}
