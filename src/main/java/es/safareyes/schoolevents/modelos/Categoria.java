package es.safareyes.schoolevents.modelos;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categoria")

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto incrementable
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", unique = true, length = 100)
    private String nombre;

    @Column(name = "color", length = 100)
    private String color;

    @Column(name = "icono", length = 100)
    private String icono;

}
