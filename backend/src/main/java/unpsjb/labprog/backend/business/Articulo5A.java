package unpsjb.labprog.backend.business;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.model.ArticuloLicencia;
import unpsjb.labprog.backend.model.Licencia;

@Service
public class Articulo5A implements ArticuloControl {

    @Override
    public void checkValid(Licencia aLicencia, List<Licencia> someLicencias) {
        int count = 0;

        for (Licencia licencia : someLicencias) {
            if (licencia.getPedidoDesde().getYear() == aLicencia.getPedidoDesde().getYear())
                count += ChronoUnit.DAYS.between(licencia.getPedidoDesde(), licencia.getPedidoHasta()) + 1;
        }
        count += ChronoUnit.DAYS.between(aLicencia.getPedidoDesde(), aLicencia.getPedidoHasta()) + 1;

        if (count > 30) {
            throw new DataIntegrityViolationException(String.format(
                    "NO se otorga Licencia artículo 5A a %s %s debido a que supera el tope de 30 días de licencia",
                    aLicencia.getPersona().getNombre(), aLicencia.getPersona().getApellido()));
        }
    }

    @Override
    public boolean isResponsibleFor(ArticuloLicencia articulo) {
        return "5A".equals(articulo.getNombre());
    }

}