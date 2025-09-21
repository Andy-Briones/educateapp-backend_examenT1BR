package brionesrojas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class cursos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id_curso;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, length = 80)
    private String codigo;

    @Column(nullable = false, length = 80)
    private int creditos;

    @Column(nullable = false, length = 80)
    private int semestre;

    @ManyToOne
    @JoinColumn(name = "id_docente", nullable = false, foreignKey = @ForeignKey(name = "FK_cursos_docentes"))
    private docentes docente;
}
