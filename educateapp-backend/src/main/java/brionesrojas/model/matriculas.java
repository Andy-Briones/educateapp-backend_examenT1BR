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

public class matriculas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id_matricula;

    @Column(nullable = false, length = 80)
    private String periodo_academico;

    @Column(nullable = false)
    private Date fecha_matricula;

    @Column(nullable = false, length = 80)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false, foreignKey = @ForeignKey(name = "FK_matriculas_estudiantes"))
    private estudiantes estudiante;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false, foreignKey = @ForeignKey(name = "FK_matriculas_cursos"))
    private cursos curso;
}
