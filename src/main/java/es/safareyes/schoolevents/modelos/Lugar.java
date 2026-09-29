package es.safareyes.schoolevents.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lugar")

@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Lugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto incrementable
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "aforomaximo")
    private Integer aforomaximo;

}
