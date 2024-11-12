package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.EnumTipoDesignacion;

@Service
public class DesignacionControl {
    @Autowired
    ControlService controlService;
    
    public void checkSave(Designacion aDesignacion) {
        if (aDesignacion.getFechaFin() != null && aDesignacion.getFechaInicio().isAfter(aDesignacion.getFechaFin()))
            throw new DataIntegrityViolationException("La fecha de inicio debe ser menor a la fecha de fin.");
        List<Designacion> someDesignaciones = controlService.findDesignacionPeriodoCargo(aDesignacion.getFechaInicio(),
                aDesignacion.getFechaFin(),
                aDesignacion.getCargo());

        if (!this.containsDate(aDesignacion.getCargo().getFechaInicio(), aDesignacion.getCargo().getFechaFin(),
                aDesignacion.getFechaInicio(), aDesignacion.getFechaFin())) {
            if (aDesignacion.getCargo().getTipoDesignacion().equals(EnumTipoDesignacion.CARGO)) {
                throw new DataIntegrityViolationException(
                        String.format("El cargo %s no está disponible para el período",
                                aDesignacion.getCargo().getNombre()));
            } else {
                throw new DataIntegrityViolationException(
                        String.format("La asginatura %s de la división %d° %d° no está disponible para el período",
                                aDesignacion.getCargo().getNombre(), aDesignacion.getCargo().getDivision().getAnio(),
                                aDesignacion.getCargo().getDivision().getNumero()));
            }
        }

        boolean licencias = true;
        if (!someDesignaciones.isEmpty()) {
            for (Designacion des : someDesignaciones) {
                if (controlService
                        .findHuecos(aDesignacion.getFechaInicio(), aDesignacion.getFechaFin(), des.getPersona().getCuil())
                        .isEmpty()) {
                    licencias = false;
                }
            }

            if (licencias) return;

            String message = String.format("%s %s NO ha sido designado/a",
                    aDesignacion.getPersona().getNombre(), aDesignacion.getPersona().getApellido());
            if (aDesignacion.getCargo().getTipoDesignacion().equals(EnumTipoDesignacion.CARGO)) {
                message += String.format(" como %s, pues el cargo solicitado lo ocupa %s %s para el período",
                        aDesignacion.getCargo().getNombre(),
                        someDesignaciones.get(someDesignaciones.size() - 1).getPersona().getNombre(),
                        someDesignaciones.get(someDesignaciones.size() - 1).getPersona().getApellido());
            } else {
                message += String.format(
                        " debido a que la asignatura %s de la división %d° %d° turno %s lo ocupa %s %s para el período",
                        aDesignacion.getCargo().getNombre(), aDesignacion.getCargo().getDivision().getAnio(),
                        aDesignacion.getCargo().getDivision().getNumero(),
                        aDesignacion.getCargo().getDivision().getTurno(),
                        someDesignaciones.get(someDesignaciones.size() - 1).getPersona().getNombre(),
                        someDesignaciones.get(someDesignaciones.size() - 1).getPersona().getApellido());
            }
            throw new DataIntegrityViolationException(message);
        }
    }

    private boolean containsDate(LocalDate fechaInicio1, LocalDate fechaFin1, LocalDate fechaInicio2,
            LocalDate fechaFin2) {

        if (fechaInicio2.compareTo(fechaInicio1) >= 0) {
            if (fechaFin1 == null)
                return true;
            if (fechaFin2 == null)
                return false;
            if (fechaFin2.compareTo(fechaFin1) <= 0)
                return true;
        }
        return false;
    }
}
