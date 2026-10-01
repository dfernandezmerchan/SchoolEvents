package es.safareyes.schoolevents.modelos;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "curso")

@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto incrementable
    @Column(name = "id") //relacionar atributo en java con la columna de la base de datos
    private Integer id;

    @Column(name = "nombre", length = 100, unique = true)
    private String nombre;

    @Column(name = "promocion", length = 100)
    private String promocion;

}
