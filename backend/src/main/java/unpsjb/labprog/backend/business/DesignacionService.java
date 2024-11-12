package unpsjb.labprog.backend.business;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.EnumTipoDesignacion;
import unpsjb.labprog.backend.model.Persona;

@Service
public class DesignacionService {
    @Autowired
    DesignacionRepository repository;
    @Autowired
    ControlService controlService;
    @Autowired
    DesignacionControl designacionControl;

    public List<Designacion> findAll() {
        List<Designacion> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Designacion findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Designacion> findDesignacionPeriodoCargo(LocalDate fechaInicio, LocalDate fechaFin, Cargo cargo) {
        return repository.findDesignacionPeriodoCargo(fechaInicio, fechaFin, cargo);
    }

    @Transactional
    public Designacion save(Designacion aDesignacion) {
        designacionControl.checkSave(aDesignacion);
        return repository.save(aDesignacion);
    }

    @Transactional
    public Designacion delete(int id) {
        Designacion aDesignacion = this.findById(id);
        if (aDesignacion != null)
            repository.deleteById(id);
        return aDesignacion;
    }

    public Page<Designacion> findByPage(int page, int size) {
        return repository.findAll(
                PageRequest.of(page, size));
    }

    public List<Designacion> findDesignacionPeriodoPersona(LocalDate fechaInicio, LocalDate fechaFin, String cuil) {
        return repository.findDesignacionPeriodoPersona(fechaInicio, fechaFin, cuil);
    }

    public Persona findPersonaFechaCargo(LocalDate fecha, Integer idCargo) {
        return repository.findPersonaDesignadaCargo(fecha, idCargo).isEmpty() ? 
            null : repository.findPersonaDesignadaCargo(fecha, idCargo).get(0).getPersona();
    }

}
