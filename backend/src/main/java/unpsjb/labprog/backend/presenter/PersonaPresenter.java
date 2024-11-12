package unpsjb.labprog.backend.presenter;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.PersonaService;
import unpsjb.labprog.backend.model.Persona;
import unpsjb.labprog.backend.util.PersonaExceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.model.processor.PhaseIndicator;

@RestController
@RequestMapping("personas") // Es la ruta relativa
public class PersonaPresenter {

    // Solo conoce al service (arquitectura por capas)
    @Autowired
    PersonaService service;
    @Autowired
    PersonaExceptionHandler exceptionHandler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll());
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
return Response.ok(service.search(term));
    }

    @RequestMapping(value = "/reporte/{anio}-{mes}", method = RequestMethod.GET)
    public ResponseEntity<Object> reporte(@PathVariable("anio") Integer anio, @PathVariable("mes") Integer mes) {
        return Response.ok(service.reporte(anio, mes));
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Response.ok(service.findByPage(page, size));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Persona aPersona) {
        try {
            return Response.ok(service.save(aPersona),
                    aPersona.getNombre() + " " + aPersona.getApellido() + " con CUIL " +
                            aPersona.getCuil() + " ingresado/a correctamente");
        } catch (DataIntegrityViolationException e) {

            return exceptionHandler.handle(e, aPersona);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Persona aPersona) {
        try {
            return Response.ok(service.save(aPersona),
                    aPersona.getNombre() + " " + aPersona.getApellido() + " con CUIL " +
                            aPersona.getCuil() + " actualizado/a correctamente");
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, aPersona);
        }
    }

    @RequestMapping(value = "/{cuil}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("cuil") String cuil) {
        try {
            return service.delete(cuil) != null ? Response.ok(null, "Persona eliminada correctamente")
                    : Response.notFound("La persona con CUIL " + cuil + " no existe");
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, service.findByCuil(cuil));
        }
    }

    @RequestMapping(value = "/{cuil}", method = RequestMethod.GET)
    public ResponseEntity<Object> findByCuil(@PathVariable("cuil") String cuil) {
        Persona aPersonaOrNull = service.findByCuil(cuil);
        return aPersonaOrNull != null ? Response.ok(aPersonaOrNull) : Response.notFound("Persona no encontrada");
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        Persona aPersonaOrNull = service.findById(id);
        return aPersonaOrNull != null ? Response.ok(aPersonaOrNull) : Response.notFound("Persona no encontrada");
    }

}