package es.safareyes.schoolevents.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "usuario")

@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column (name = "nombre")
    private String nombre;

    @Column (name = "apellido1")
    private String apellido1;

    @Column (name = "apellido2")
    private String apellido2;

    @Column (name = "email")
    private String email;

    @Column (name = "username")
    private String username;

    @Column (name = "password")
    private String password;

    @Column (name = "fechanac")
    private LocalDate fechanac;

    @Column (name = "fechains")
    private LocalDateTime fechains;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id") //esta es la columna que une
    private Curso curso; //establecer relacion con otra tabla

}
