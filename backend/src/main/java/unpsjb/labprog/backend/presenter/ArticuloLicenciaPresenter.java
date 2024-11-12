package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.ArticuloLicenciaService;
import unpsjb.labprog.backend.model.ArticuloLicencia;
import unpsjb.labprog.backend.util.ArticuloLicenciaExceptionHandler;

@RestController
@RequestMapping("articulos")
public class ArticuloLicenciaPresenter {
    @Autowired
    ArticuloLicenciaService service;
    @Autowired
    ArticuloLicenciaExceptionHandler exceptionHandler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll());
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
        return Response.ok(service.search(term));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> save(@RequestBody ArticuloLicencia anArticulo) {
        try {
            return Response.ok(service.save(anArticulo), String.format("Artículo %s ingresado correctamente", anArticulo.getNombre()));
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, anArticulo);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody ArticuloLicencia anArticulo) {
        try {
            return Response.ok(service.save(anArticulo), String.format("Artículo %s actualizado correctamente", anArticulo.getNombre()));
        } catch (DataIntegrityViolationException e) {
            return exceptionHandler.handle(e, anArticulo);
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        ArticuloLicencia anArticuloOrNull = service.findById(id);
        return anArticuloOrNull != null ? Response.ok(anArticuloOrNull) : Response.notFound("Artículo no encontrado");
    }

    @RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("nombre") String nombre) {
        ArticuloLicencia anArticuloOrNull = service.findByNombre(nombre);
        return anArticuloOrNull != null ? Response.ok(anArticuloOrNull) : Response.notFound("Artículo no encontrado");
    }

    @RequestMapping(value="/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        return service.delete(id) != null ? Response.ok(null, "Artículo eliminado correctamente"):
            Response.notFound("No existe el artículo");
    }
}