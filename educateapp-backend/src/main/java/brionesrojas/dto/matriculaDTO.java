package brionesrojas.dto;

import brionesrojas.model.cursos;
import brionesrojas.model.estudiantes;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class matriculaDTO extends RepresentationModel<matriculaDTO> {
    private int id_matricula;

    @NotNull
    @Size(min=5, max = 80)
    @Column(nullable = false, length = 80)
    private String periodo_academico;

    @NotNull
    @Column(nullable = false)
    private Date fecha_matricula;

    @NotNull
    @Size(min=5, max = 80)
    @Column(nullable = false, length = 80)
    private String estado;

    @NotNull
    private int estudiante;

    @NotNull
    private int curso;
}
