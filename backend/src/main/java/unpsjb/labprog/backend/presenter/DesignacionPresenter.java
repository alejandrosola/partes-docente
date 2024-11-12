
package unpsjb.labprog.backend.presenter;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.ControlService;
import unpsjb.labprog.backend.business.DesignacionService;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.EnumTipoDesignacion;
import unpsjb.labprog.backend.model.Persona;
import unpsjb.labprog.backend.util.DesignacionExceptionHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("designaciones") // Es la ruta relativa
public class DesignacionPresenter {

    // Solo conoce al service (arquitectura por capas)
    @Autowired
    DesignacionService service;
    @Autowired
    ControlService controlService;
    @Autowired
    DesignacionExceptionHandler exceptionHandler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll());
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Response.ok(service.findByPage(page, size));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> save(@RequestBody Designacion aDesignacion) {
        try {
            String msg;
            if (aDesignacion.getCargo().getTipoDesignacion().equals(EnumTipoDesignacion.CARGO)) {
                msg = String.format("%s %s ha sido designado/a como %s exitosamente",
                        aDesignacion.getPersona().getNombre(), aDesignacion.getPersona().getApellido(),
                        aDesignacion.getCargo().getNombre());
            } else {
                msg = String.format("%s %s ha sido designado/a a la asignatura %s a la división %d° %d° exitosamente",
                        aDesignacion.getPersona().getNombre(), aDesignacion.getPersona().getApellido(),
                        aDesignacion.getCargo().getNombre(),
                        aDesignacion.getCargo().getDivision().getAnio(),
                        aDesignacion.getCargo().getDivision().getNumero());
            }

            List<Designacion> someDesignaciones = service.findDesignacionPeriodoCargo(aDesignacion.getFechaInicio(),
                    aDesignacion.getFechaFin(),
                    aDesignacion.getCargo());
            if (!someDesignaciones.isEmpty()) {
                msg += String.format(", en reemplazo de %s %s",
                        someDesignaciones.get(someDesignaciones.size() - 1).getPersona().getNombre(),
                        someDesignaciones.get(someDesignaciones.size() - 1).getPersona().getApellido());
            }
            return Response.ok(service.save(aDesignacion), msg);
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, aDesignacion);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Designacion aDesignacion) {
        try {
            return Response.ok(service.save(aDesignacion), "Designación actualizada correctamente");
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, aDesignacion);
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        return service.delete(id) != null ? Response.ok(null, "Designación eliminada correctamente")
                : Response.notFound("No existe la designación de id " + id);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        Designacion aDesignacionOrNull = service.findById(id);
        return aDesignacionOrNull != null ? Response.ok(aDesignacionOrNull)
                : Response.notFound("Designación no encontrada");
    }

    @RequestMapping(value = "/{anio}-{mes}-{dia}-{idCargo}", method = RequestMethod.GET)
    public ResponseEntity<Object> findPersonaFechaCargo(@PathVariable("anio") String anio,
            @PathVariable("mes") String mes, @PathVariable("dia") String dia, @PathVariable("idCargo") Integer idCargo) {

        Persona aPersonaOrNull = service.findPersonaFechaCargo(LocalDate.parse(String.format("%s-%s-%s", anio, mes, dia)), idCargo);
        return aPersonaOrNull != null ? Response.ok(aPersonaOrNull) : 
                Response.notFound("El cargo no tiene persona designada para la fecha " + controlService.findCargoById(idCargo));
    }
}