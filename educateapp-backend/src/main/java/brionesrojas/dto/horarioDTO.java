package brionesrojas.dto;

import brionesrojas.model.cursos;
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

public class horarioDTO extends RepresentationModel<horarioDTO> {
    private int id_horario;

    @NotNull
    @Size(min = 5, max = 80)
    @Column(nullable = false, length = 80)
    private String dia_semana;

    @NotNull
    @Column(nullable = false)
    private Date hora_inicio;

    @NotNull
    @Column(nullable = false)
    private Date hora_fin;

    @NotNull
    @Size(min = 3, max = 80)
    @Column(nullable = false, length = 80)
    private String aula;

    @NotNull
    private int curso;
}
