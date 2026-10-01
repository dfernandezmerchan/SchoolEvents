package es.safareyes.schoolevents.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notificacion")

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "mensaje", length = 100)
    private String mensaje;

    @Column(name = "leida", nullable = false)
    private Boolean leida = false;

    public enum tiponotificacion{CAMBIO_HORARIO, NUEVO_EVENTO, CANCELACION};
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo")
    private Notificacion.tiponotificacion tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
