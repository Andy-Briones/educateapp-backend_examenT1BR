package brionesrojas.dto;

import brionesrojas.model.docentes;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
public class cursoDTO extends RepresentationModel<cursoDTO> {
    private int id_curso;

    @NotNull
    @Size(min=3, max = 80)
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotNull
    @Size(min=4, max = 8)
    @Column(nullable = false, length = 8)
    private String codigo;

    @NotNull
    @Column(nullable = false)
    private int creditos;

    @NotNull
    @Column(nullable = false)
    private int semestre;

    @NotNull
    private int docente;
}
