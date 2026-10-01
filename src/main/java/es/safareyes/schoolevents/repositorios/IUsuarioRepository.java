package es.safareyes.schoolevents.repositorios;

import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

}
