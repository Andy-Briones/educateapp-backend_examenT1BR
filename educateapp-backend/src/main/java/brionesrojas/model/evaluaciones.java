package brionesrojas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class evaluaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id_evaluacion;

    @Column(nullable = false, length = 80)
    private String tipo_evaluacion;

    @Column(nullable = false, length = 80)
    private Date fecha;

    @Column(nullable = false)
    private int nota;

    @ManyToOne
    @JoinColumn(name = "id_matricula", nullable = false, foreignKey = @ForeignKey(name = "FK_evaluaciones_matriculas"))
    private matriculas matricula;
}
