package es.safareyes.schoolevents.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "evento")

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fechaini")
    private LocalDateTime fechaini;

    @Column(name = "fechafin")
    private LocalDateTime fechafin;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "titulo", length = 100)
    private String titulo;

    public enum estadoevento{BORRADOR, PUBLICADO, FINALIZADO, CANCELADO };
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "estado")
    private estadoevento estado;

    @Column(name = "fechalimite")
    private LocalDateTime fechalimite;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lugar")
    private Lugar lugar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador_id")
    private Usuario organizador;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "evento_curso",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private Set<Curso> cursos = new HashSet<>();


}
