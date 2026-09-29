package es.safareyes.schoolevents.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "estado")
    private Integer estado;

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


}
