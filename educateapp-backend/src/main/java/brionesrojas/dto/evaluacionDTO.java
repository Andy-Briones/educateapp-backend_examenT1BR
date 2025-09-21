package brionesrojas.dto;

import brionesrojas.model.matriculas;
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

public class evaluacionDTO extends RepresentationModel<evaluacionDTO> {
    private int id_evaluacion;

    @NotNull
    @Size(min = 3, max = 80)
    @Column(nullable = false, length = 80)
    private String tipo_evaluacion;

    @NotNull
    @Column(nullable = false, length = 80)
    private Date fecha;

    @NotNull
    @Column(nullable = false)
    private int nota;

    @NotNull
    private int matricula;
}
