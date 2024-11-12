package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.model.ArticuloLicencia;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Horario;
import unpsjb.labprog.backend.model.Licencia;

@Service
public class Articulo36A implements ArticuloControl {
    @Override
    public void checkValid(Licencia aLicencia, List<Licencia> someLicencias) {
        int countByAnio = 0;
        int countByMonth = 0;

        if (aLicencia.getPedidoDesde().getMonth().equals(aLicencia.getPedidoHasta().getMonth())) {
            for (Licencia licencia : someLicencias) {
                if (licencia.getPedidoDesde().getYear() == aLicencia.getPedidoDesde().getYear())
                    countByAnio += ChronoUnit.DAYS.between(licencia.getPedidoDesde(), licencia.getPedidoHasta()) + 1;

                if ((licencia.getPedidoDesde().getMonth().equals(aLicencia.getPedidoDesde().getMonth())) ||
                        licencia.getPedidoHasta().getMonth().equals(aLicencia.getPedidoDesde().getMonth())) {
                    countByMonth += ChronoUnit.DAYS.between(licencia.getPedidoDesde(), licencia.getPedidoHasta()) + 1;
                }
            }
            countByMonth += ChronoUnit.DAYS.between(aLicencia.getPedidoDesde(), aLicencia.getPedidoHasta()) + 1;
            countByAnio += ChronoUnit.DAYS.between(aLicencia.getPedidoDesde(), aLicencia.getPedidoHasta()) + 1;

            if (countByAnio > 6) {
                throw new DataIntegrityViolationException(String.format(
                        "NO se otorga Licencia artículo 36A a %s %s debido a que supera el tope de 6 días de licencia por año",
                        aLicencia.getPersona().getNombre(), aLicencia.getPersona().getApellido()));
            }

            if (countByMonth > 2) {
                throw new DataIntegrityViolationException(String.format(
                        "NO se otorga Licencia artículo 36A a %s %s debido a que supera el tope de 2 días de licencia por mes",
                        aLicencia.getPersona().getNombre(), aLicencia.getPersona().getApellido()));
            }
        } else {
            // Separo el rango de fechas en dos
            LocalDate tempFecha = aLicencia.getPedidoHasta();
            aLicencia.setPedidoHasta(aLicencia.getPedidoDesde().withDayOfMonth(
                    aLicencia.getPedidoDesde().getMonth().length(aLicencia.getPedidoDesde().isLeapYear())));
            this.checkValid(aLicencia, someLicencias);
            aLicencia.setPedidoHasta(tempFecha);

            tempFecha = aLicencia.getPedidoDesde();
            aLicencia.setPedidoDesde(aLicencia.getPedidoHasta().withDayOfMonth(01));
            this.checkValid(aLicencia, someLicencias);
            aLicencia.setPedidoDesde(tempFecha);
        }

        boolean encontrado = false;
        for (LocalDate date = aLicencia.getPedidoDesde(); date
                .isBefore(aLicencia.getPedidoHasta().plusDays(1)); date = date.plusDays(1)) {
            encontrado = false;
            for (Designacion des : aLicencia.getDesignaciones()) {
                for (Horario horario : des.getCargo().getHorarios()) {
                    if (horario.getDia().getValor() == date.getDayOfWeek().getValue()) {
                        encontrado = true;
                    }
                }
            }
            
            if (!encontrado) {
                throw new DataIntegrityViolationException(String.format(
                        "NO se otorga Licencia artículo %s a %s %s debido a que el agente no tiene designación ese día en la institución",
                        aLicencia.getArticulo().getNombre(), aLicencia.getPersona().getNombre(),
                        aLicencia.getPersona().getApellido()));
            }
        }
    }

    @Override
    public boolean isResponsibleFor(ArticuloLicencia articulo) {
        return "36A".equals(articulo.getNombre());
    }
}
