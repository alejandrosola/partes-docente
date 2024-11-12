package unpsjb.labprog.backend.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unpsjb.labprog.backend.util.CargoListener;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"division", "nombre"}, name = "division_nombre_uk")})
@EntityListeners(CargoListener.class)
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, name = "nombre")
    private String nombre;
    @Column(nullable = false, name = "cargaHoraria")
    private Integer cargaHoraria;
    @Column(nullable = false, name = "fechaInicio")
    private LocalDate fechaInicio;
    @Column(nullable = true, name = "fechaFin")
    private LocalDate fechaFin;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = true, name="division")
    private Division division;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name="tipoDesignacion")
    private EnumTipoDesignacion tipoDesignacion;
    
    @OneToMany(cascade= { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private List<Horario> horarios;
}