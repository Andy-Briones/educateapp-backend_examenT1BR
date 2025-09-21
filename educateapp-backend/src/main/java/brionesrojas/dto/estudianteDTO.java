package brionesrojas.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
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

public class estudianteDTO extends RepresentationModel<estudianteDTO> {
    private int id_estudiante;

    @NotNull
    @Size(min=3, max = 70)
    private String nombre;

    @NotNull
    @Size(min=3, max = 70)
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

    @Column(nullable = false, length = 70)
    private String direccion;

    @Column(nullable = false)
    private Date fecha_nacimiento;

    @Column(nullable = false)
    private Date fecha_ingreso;

    @Column(nullable = false, length = 50)
    private String estado;
}
