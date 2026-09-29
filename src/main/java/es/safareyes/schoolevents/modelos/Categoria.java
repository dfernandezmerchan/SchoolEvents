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

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "color")
    private String color;

    @Column(name = "icono")
    private String icono;

}
