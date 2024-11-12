package unpsjb.labprog.backend.model;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Designacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, name = "situacionRevista")
    private String situacionRevista;

    @Column(nullable = false, name = "fechaInicio")
    private LocalDate fechaInicio;

    @Column(nullable = true, name = "fechaFin")
    private LocalDate fechaFin;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false, name="cargo")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cargo cargo;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false, name="persona")
    private Persona persona;
}