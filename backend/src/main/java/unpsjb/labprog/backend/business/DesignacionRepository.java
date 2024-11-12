package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.EnumDia;
import unpsjb.labprog.backend.model.Persona;

@Repository
public interface DesignacionRepository extends CrudRepository<Designacion, Integer>, PagingAndSortingRepository<Designacion, Integer> {
    @Query("SELECT e FROM Designacion e WHERE (e.cargo = ?3) " +
    "AND (e.fechaInicio <= COALESCE(?2, e.fechaInicio)) AND (?1 <= COALESCE(e.fechaFin, ?1))")
    List<Designacion> findDesignacionPeriodoCargo(LocalDate fechaInicio, LocalDate fechaFin, Cargo cargo);

    @Query("SELECT e FROM Designacion e WHERE (e.cargo.id = ?2) AND (COALESCE(e.fechaFin, ?1) >= ?1) AND (e.fechaInicio <= ?1)")
    List<Designacion> findPersonaDesignadaCargo(LocalDate fecha, Integer idCargo);

    @Query("SELECT e FROM Designacion e WHERE (e.persona.cuil = ?1)")
    List<Designacion>findDesignacionesPersona(String cuil);

    // Buscar las designaciones vigentes de una persona en el período dado
    @Query("SELECT e FROM Designacion e WHERE (e.persona.cuil = ?3) AND " + 
    "(e.fechaInicio <= ?1) AND (?2 <= COALESCE(e.fechaFin, ?2))")
    List<Designacion> findDesignacionPeriodoPersona(LocalDate fechaInicio, LocalDate fechaFin, String cuil);


    @Query("SELECT e FROM Designacion e JOIN e.cargo.horarios h WHERE h.hora <= ?1 AND h.horaFin >= ?1 AND h.dia = ?2 AND e.persona = ?3 AND e.fechaInicio <= ?4 AND e.fechaFin >= ?4")
    List<Designacion> designacionEnHorarioDiaDocente(LocalTime hora, EnumDia diaSemana, Persona docente, LocalDate fecha);
} 