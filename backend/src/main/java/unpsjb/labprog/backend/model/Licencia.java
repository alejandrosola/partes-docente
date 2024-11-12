package unpsjb.labprog.backend.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable=false)
    private LocalDate pedidoDesde;
    @Column(nullable=false)
    private LocalDate pedidoHasta;
    @Column(nullable=false)
    private boolean certificadoMedico;
    @ManyToOne(optional=false)
    private ArticuloLicencia articulo;
    @ManyToMany(cascade= CascadeType.ALL)
    private List<Designacion> designaciones;
    @ManyToOne(optional=false)
    private Persona persona;
}