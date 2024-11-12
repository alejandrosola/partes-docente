package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.CargoService;
import unpsjb.labprog.backend.business.DivisionService;
import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.util.CargoExceptionHandler;

@RestController
@RequestMapping("cargos")
public class CargoPresenter {
    @Autowired
    CargoService service;
    @Autowired
    DivisionService divisionService;
    @Autowired
    CargoExceptionHandler exceptionHandler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll());
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
        return Response.ok(service.search(term));
    }

    @RequestMapping(value = "/calendar-division/{aDiaSemana}-{aDivisionAnio}-{aDivisionNumero}-{anAnio}-{aMes}-{aDia}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCalendarioDia(@PathVariable("aDiaSemana") String aDiaSemana,
            @PathVariable("aDivisionAnio") Integer aDivisionAnio,
            @PathVariable("aDivisionNumero") Integer aDivisionNumero,
            @PathVariable("anAnio") String anAnio,
            @PathVariable("aMes") String aMes,
            @PathVariable("aDia") String aDia) {
        return Response
                .ok(service.getCalendarioDiaDivision(aDiaSemana, aDivisionAnio, aDivisionNumero, anAnio, aMes, aDia));
    }

    @RequestMapping(value = "/calendar-docente/{aDiaSemana}-{aTurno}-{aPersonaCuil}-{anAnio}-{aMes}-{aDia}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCalendarioDia(@PathVariable("aDiaSemana") String aDiaSemana,
            @PathVariable("aTurno") String aTurno,
            @PathVariable("aPersonaCuil") String aPersonaCuil,
            @PathVariable("anAnio") String anAnio,
            @PathVariable("aMes") String aMes,
            @PathVariable("aDia") String aDia) {
        return Response.ok(service.getCalendarioDiaDocente(aDiaSemana, aTurno, aPersonaCuil, anAnio, aMes, aDia));
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Response.ok(service.findByPage(page, size));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> save(@RequestBody Cargo aCargo) {
        try {
            // Si es espacio curricular, le corresponde una división
            if (aCargo.getTipoDesignacion().getValor().equals("ESPACIOCURRICULAR")) {
                // Busco la división y se la asigno al cargo
                aCargo.setDivision(divisionService.findByAnioNumero(aCargo.getDivision().getAnio(),
                        aCargo.getDivision().getNumero()));

                return Response.ok(service.save(aCargo),
                        "Espacio Curricular " + aCargo.getNombre() + " para la división " +
                                aCargo.getDivision().getAnio() + "° " + aCargo.getDivision().getNumero()
                                + "° ingresado correctamente");
            }
            return Response.ok(service.save(aCargo), "Cargo de " + aCargo.getNombre() + " ingresado correctamente");
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, aCargo);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Cargo aCargo) {
        try {
            // Si es espacio curricular, le corresponde una división
            if (aCargo.getTipoDesignacion().getValor().equals("ESPACIOCURRICULAR")) {
                // Busco la división y se la asigno al cargo
                aCargo.setDivision(divisionService.findByAnioNumero(aCargo.getDivision().getAnio(),
                        aCargo.getDivision().getNumero()));

                return Response.ok(service.save(aCargo),
                        "Espacio Curricular " + aCargo.getNombre() + " para la división " +
                                aCargo.getDivision().getAnio() + "° " + aCargo.getDivision().getNumero()
                                + "° actualizado correctamente");
            }

            return Response.ok(service.save(aCargo), "Cargo de " + aCargo.getNombre() + " actualizado correctamente");
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, aCargo);
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        Cargo aCargoOrNull = service.findById(id);
        return aCargoOrNull != null ? Response.ok(aCargoOrNull) : Response.notFound("Cargo no encontrado");
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        try {
            return service.delete(id) != null ? Response.ok(null, "Cargo eliminado correctamente")
                    : Response.notFound("No existe el cargo");
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, service.findById(id));
        }
    }

    @RequestMapping(value = "/cargo/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<Object> findByNombre(@PathVariable("nombre") String nombre) {

        try {
            Cargo aCargoOrNull = service.findByNombre(nombre);
            return aCargoOrNull != null ? Response.ok(aCargoOrNull) : Response.notFound("Cargo no encontrado");
        } catch (Exception e) {
            return exceptionHandler.handle(e, null);
        }
    }

    @RequestMapping(value = "/espaciocurricular/{nombre}-{anio}-{numero}", method = RequestMethod.GET)
    public ResponseEntity<Object> findByNombreDivision(@PathVariable("nombre") String nombre,
            @PathVariable("anio") int anio, @PathVariable("numero") int numero) {
        try {
            Cargo aCargoOrNull = service.findByNombreDivision(nombre, anio, numero);
            return aCargoOrNull != null ? Response.ok(aCargoOrNull) : Response.notFound("Cargo no encontrado" + nombre + anio + numero);
        } catch (Exception e) {
            return exceptionHandler.handle(e, null);
        }
    }

}
