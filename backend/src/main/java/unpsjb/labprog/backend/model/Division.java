package unpsjb.labprog.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"anio" , "numero"})})
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable=false)
    private Integer anio;
    @Column(nullable=false)
    private Integer numero;
    @Column(length=90, nullable=true)
    private String orientacion;
    @Enumerated(EnumType.STRING)
    @Column(nullable=true)
    private EnumTurno turno;
}

