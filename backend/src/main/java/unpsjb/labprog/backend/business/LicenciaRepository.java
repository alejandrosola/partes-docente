package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Licencia;

@Repository
// CrudRepository tiene todos los crud implementados Integer es el id
// Se comunica con el model con los getter y setter
public interface LicenciaRepository extends CrudRepository<Licencia, Integer>, PagingAndSortingRepository<Licencia, Integer> {
    // Buscar las licencias para una persona en el período dado
    @Query("SELECT e FROM Licencia e WHERE (e.persona.cuil = ?3) AND " +
    " (e.pedidoDesde <= ?2) AND (?1 <= e.pedidoHasta)")
    List<Licencia> findLicenciaPeriodoPersona(LocalDate fechaInicio, LocalDate fechaFin, String cuil);

    @Query("SELECT e FROM Licencia e WHERE (e.persona.cuil = ?3) AND " +
    "(e.pedidoDesde <= ?2) AND (e.pedidoDesde <= ?1) AND (e.pedidoHasta >= ?1) AND (e.pedidoHasta >= ?2)")
    List<Licencia> findHuecos(LocalDate fechaInicio, LocalDate fechaFin, String cuil);
    
    // Buscar las licencias de un artículo para una persona
    @Query("SELECT e FROM Licencia e WHERE (e.persona.cuil = ?1) AND (e.articulo.nombre = ?2)")
    List<Licencia> findLicenciasPersonaArticulo(String cuil, String articulo);

    @Query("SELECT e FROM Licencia e WHERE (e.pedidoDesde <= COALESCE(?2, e.pedidoDesde)) AND (e.pedidoDesde <= ?1) AND (?1 <= e.pedidoHasta)")
    List<Licencia> findLicenciaPeriodo(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT e FROM Licencia e WHERE (e.pedidoDesde <= ?1) AND (e.pedidoHasta >= ?1)")
    List<Licencia> findLicenciaVigente(LocalDate fecha);
}