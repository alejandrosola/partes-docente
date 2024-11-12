package unpsjb.labprog.backend.presenter;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.LicenciaService;
import unpsjb.labprog.backend.model.Licencia;
import unpsjb.labprog.backend.util.LicenciaExceptionHandler;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
@RequestMapping("licencias") // Es la ruta relativa
public class LicenciaPresenter {

    // Solo conoce al service (arquitectura por capas)
    @Autowired
    LicenciaService service;
    @Autowired
    LicenciaExceptionHandler exceptionHandler;

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

    @RequestMapping(value = "/partediario/{anio}-{mes}-{dia}", method = RequestMethod.GET)
    public ResponseEntity<Object> getParteDiario(@PathVariable("anio") String anio, @PathVariable("mes") String mes,
            @PathVariable("dia") String dia) {
        try {
            return Response.ok(service.getParteDiario(LocalDate.parse(String.format("%s-%s-%s", anio, mes, dia))));
        } catch (DateTimeParseException dtpe) {
            return Response.error("Ingrese una fecha válida");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Licencia aLicencia) {
        try {
            return Response.ok(service.save(aLicencia),
                    String.format("Se otorga Licencia artículo %s a %s %s", aLicencia.getArticulo().getNombre(),
                            aLicencia.getPersona().getNombre(), aLicencia.getPersona().getApellido()));
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, aLicencia);
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        Licencia aLicenciaOrNull = service.findById(id);
        return aLicenciaOrNull != null ? Response.ok(aLicenciaOrNull) : Response.notFound("Licencia no encontrada");
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        return service.delete(id) != null ? Response.ok(null, "Licencia eliminada correctamente")
                : Response.notFound("No existe la licencia");
    }

}