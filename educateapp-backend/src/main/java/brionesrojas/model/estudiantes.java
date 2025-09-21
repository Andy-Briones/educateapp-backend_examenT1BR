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

public class estudiantes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id_estudiante;

    @Column(nullable = false, length = 70)
    private String nombre;

    @Column(nullable = false, length = 70)
    private String apellido;

    @Column(nullable = false, length = 8)
    private String dni;

    @Column(nullable = false, length = 70)
    private String correo;

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
