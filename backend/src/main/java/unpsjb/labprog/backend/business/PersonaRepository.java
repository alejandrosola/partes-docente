package unpsjb.labprog.backend.business;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Persona;

@Repository
// CrudRepository tiene todos los crud implementados Integer es el id
// Se comunica con el model con los getter y setter
public interface PersonaRepository
        extends CrudRepository<Persona, Integer>, PagingAndSortingRepository<Persona, Integer> {
    // Solo los metodos por defecto.
    @Query("SELECT e FROM Persona e WHERE e.cuil = ?1")
    Optional<Persona> findByCuil(String cuil);

    @Query("SELECT e FROM Persona e WHERE UPPER(e.cuil) like ?1 OR UPPER(e.nombre) like ?1 OR UPPER(e.apellido) like ?1 OR UPPER(e.nombre) || ' ' || UPPER(e.apellido) like ?1"
            +
            " OR e.cuil || ' ' || UPPER(e.nombre) || ' ' || UPPER(e.apellido) like ?1")
    List<Persona> search(String term);

    @Query("SELECT p, COUNT(l) FROM Persona p JOIN Licencia l WHERE l.articulo.nombre = '36A' AND l.persona = p AND (EXTRACT(YEAR FROM l.pedidoDesde) "
            + "<= ?2 AND EXTRACT(YEAR FROM l.pedidoHasta) >= ?2) AND (EXTRACT(MONTH FROM l.pedidoDesde) <= ?1 AND EXTRACT(MONTH FROM l.pedidoHasta) >= ?1)"
            + " GROUP BY p ORDER BY COUNT(l) DESC")
    List<Object[]> reporte(Integer aMes, Integer anAnio);

}