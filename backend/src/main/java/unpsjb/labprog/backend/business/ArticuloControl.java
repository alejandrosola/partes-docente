package unpsjb.labprog.backend.business;

import java.util.List;

import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.model.ArticuloLicencia;
import unpsjb.labprog.backend.model.Licencia;

@Service
public interface ArticuloControl {
    public void checkValid(Licencia aLicencia, List<Licencia> someLicencias);
    public boolean isResponsibleFor(ArticuloLicencia anArticulo);
}