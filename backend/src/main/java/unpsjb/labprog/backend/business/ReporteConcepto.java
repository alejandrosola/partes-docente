package unpsjb.labprog.backend.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unpsjb.labprog.backend.model.Persona;

@Getter
@Setter
@NoArgsConstructor
public class ReporteConcepto {
    private Persona persona;
    private Long cantLicencias;
}
