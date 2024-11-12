package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unpsjb.labprog.backend.model.Licencia;

@Getter
@Setter
@NoArgsConstructor
public class ParteDiario {
    private LocalDate fecha;
    private Collection<Licencia> docentes;
}