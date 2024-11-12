package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Division;
import unpsjb.labprog.backend.model.EnumDia;

public interface CargoRepository extends CrudRepository<Cargo, Integer>,  PagingAndSortingRepository<Cargo, Integer> {
    
    @Query("SELECT e FROM Cargo e JOIN e.division d WHERE e.nombre = ?1 AND d.anio = ?2 AND d.numero = ?3")
    Optional<Cargo> findByNombreDivision(String nombre, int anio, int numero);

    @Query("SELECT e FROM Cargo e WHERE e.nombre = ?1 AND e.tipoDesignacion = 'CARGO'")
    Optional<Cargo> findByNombre(String nombre);

    @Query("SELECT e FROM Cargo e JOIN e.horarios h WHERE h.hora <= ?1 AND h.horaFin >= ?1 AND h.dia = ?2 AND e.tipoDesignacion = 'ESPACIOCURRICULAR' AND e.division = ?3 " +
    "AND e.fechaInicio <= ?4 AND e.fechaFin >= ?4")
    List<Cargo> cargosEnHorarioDiaDivision(LocalTime horario, EnumDia dia, Division division, LocalDate fecha);

    @Query("SELECT e FROM Cargo e WHERE (e.tipoDesignacion = 'CARGO' AND UPPER(e.nombre) like ?1) OR " +
    "(e.tipoDesignacion = 'ESPACIOCURRICULAR' AND CONCAT(UPPER(e.nombre), ' ', CAST(e.division.anio as text), '° ', CAST(e.division.numero as text), '° ' " +
    ", UPPER(e.division.turno)) like ?1) OR UPPER(e.tipoDesignacion) like ?1")
    List<Cargo> search(String term);
}