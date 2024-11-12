
package unpsjb.labprog.backend.presenter;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.DivisionService;
import unpsjb.labprog.backend.model.Division;
import unpsjb.labprog.backend.util.DivisionExceptionHandler;

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
@RequestMapping("divisiones") // Es la ruta relativa
public class DivisionPresenter {

    // Solo conoce al service (arquitectura por capas)
    @Autowired
    DivisionService service;
    @Autowired
    DivisionExceptionHandler exceptionHandler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll());
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
        return Response.ok(service.search(term));
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Response.ok(service.findByPage(page, size));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> save(@RequestBody Division aDivision) {
        try {
            return Response.ok(service.save(aDivision), "División " + aDivision.getAnio() + "° " + aDivision.getNumero() +
             "° ingresada correctamente");
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, aDivision);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Division aDivision) {
        try {
            return Response.ok(service.save(aDivision), "División " + aDivision.getAnio() + "° " + aDivision.getNumero() +
                "° " + "actualizada correctamente");
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, aDivision);
        }
    }
    
    @RequestMapping(value="/{anio}-{numero}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("anio") int anio, @PathVariable("numero") int numero) {
        try {
            return service.delete(anio, numero) != null? Response.ok(null, "División eliminada correctamente"):
                Response.notFound(String.format("No existe la división %d° %d°", anio, numero));
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, service.findByAnioNumero(anio, numero));
        }
    }
    
    @RequestMapping(value="/{anio}-{numero}", method = RequestMethod.GET)
    public ResponseEntity<Object> findByAnioNumeroTurno(@PathVariable("anio") int anio, @PathVariable("numero") int numero) {
        Division aDivisionOrNull = service.findByAnioNumero(anio, numero);
        return aDivisionOrNull != null ? Response.ok(aDivisionOrNull): Response.notFound("División no encontrada");
    }

    @RequestMapping(value="/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        Division aDivisionOrNull = service.findById(id);
        return aDivisionOrNull != null ? Response.ok(aDivisionOrNull): Response.notFound("División no encontrada");
    }




}