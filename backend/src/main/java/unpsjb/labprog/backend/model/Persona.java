package unpsjb.labprog.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable=false, length=30)
    private String dni;
    @Column(unique = true, length=30, nullable=false)
    private String cuil;
    @Column(length=90, nullable=false)
    private String nombre;
    @Column(length=90, nullable=false)
    private String apellido;
    @Column(length=90, nullable=true)
    private String titulo;
    @Column(nullable = false)
    private char sexo;
    @Column(length=90, nullable=false)
    private String domicilio;
    @Column(length=30, nullable=false)
    private String telefono;
}