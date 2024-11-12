package unpsjb.labprog.backend.business;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Division;

@Repository
// CrudRepository tiene todos los crud implementados Integer es el id
// Se comunica con el model con los getter y setter
public interface DivisionRepository extends CrudRepository<Division, Integer>, PagingAndSortingRepository<Division, Integer> {
    
    @Query("SELECT e FROM Division e WHERE e.anio = ?1 and e.numero = ?2")
    Optional<Division> findByAnioNumero(int anio, int numero);

    @Query("SELECT e FROM Division e WHERE CONCAT(CAST(e.anio as text), '° ', CAST(e.numero as text), '° ', UPPER(e.turno)) like ?1 " + 
    "OR CONCAT(CAST(e.anio as text), ' ', CAST(e.numero as text), ' ', UPPER(e.turno)) like ?1")
    List<Division> search(String term);
}