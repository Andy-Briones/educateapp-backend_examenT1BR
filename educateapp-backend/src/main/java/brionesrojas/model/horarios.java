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

public class horarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id_horario;

    @Column(nullable = false, length = 80)
    private String dia_semana;

    @Column(nullable = false)
    private Date hora_inicio;

    @Column(nullable = false)
    private Date hora_fin;

    @Column(nullable = false, length = 80)
    private String aula;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false, foreignKey = @ForeignKey(name = "FK_horarios_cursos"))
    private cursos curso;
}
