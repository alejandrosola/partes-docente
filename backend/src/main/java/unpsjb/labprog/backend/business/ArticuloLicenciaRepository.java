package unpsjb.labprog.backend.business;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.ArticuloLicencia;

@Repository
public interface ArticuloLicenciaRepository extends CrudRepository<ArticuloLicencia, Integer> {

    @Query("SELECT e FROM ArticuloLicencia e WHERE e.nombre = ?1")
    ArticuloLicencia findByNombre(String nombre);


    @Query("SELECT e FROM ArticuloLicencia e WHERE UPPER(e.nombre) like ?1 OR UPPER(e.descripcion) like ?1")
    List<ArticuloLicencia> search(String term);
}