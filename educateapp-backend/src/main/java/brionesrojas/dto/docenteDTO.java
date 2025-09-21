package brionesrojas.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class docenteDTO extends RepresentationModel<docenteDTO> {
    private int id_docente;

    @NotNull
    @Size(min = 3, max = 70)
    @Column(nullable = false, length = 70)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 70)
    @Column(nullable = false, length = 70)
    private String apellido;

    @NotNull
    @Column(nullable = false, length = 8)
    private String dni;

    @Email
    @Column(nullable = false, length = 70)
    private String correo;

    @NotNull
    @Column(nullable = false, length = 9)
    private String telefono;

    @NotNull
    @Column(nullable = false, length = 80)
    private String especialidad;

    @NotNull
    @Column(nullable = false, length = 50)
    private String estado;
}
