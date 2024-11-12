package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.Licencia;

@Service
public class ControlService {

    @Autowired
    DesignacionRepository designacionRepository;
    @Autowired
    LicenciaRepository licenciaRepository;
    @Autowired
    CargoRepository cargoRepository;

    public List<Designacion> findDesignacionPeriodoCargo(LocalDate fechaInicio, LocalDate fechaFin, Cargo cargo) {
        return designacionRepository.findDesignacionPeriodoCargo(fechaInicio, fechaFin, cargo);
    }

    public List<Designacion> findDesignacionPeriodo(LocalDate fechaInicio, LocalDate fechaFin, String cuil) {
        return designacionRepository.findDesignacionPeriodoPersona(fechaInicio, fechaFin, cuil);
    }

    public List<Designacion> findDesignacionesPersona(String cuil) {
        return designacionRepository.findDesignacionesPersona(cuil);
    }

    public List<Licencia> findLicenciaPeriodoPersona(LocalDate fechaInicio, LocalDate fechaFin, String cuil) {
        return licenciaRepository.findLicenciaPeriodoPersona(fechaInicio, fechaFin, cuil);
    }

    public List<Licencia> findHuecos(LocalDate fechaInicio, LocalDate fechaFin, String cuil) {
        return licenciaRepository.findHuecos(fechaInicio, fechaFin, cuil);
    }

    public Cargo findCargoById(Integer id) {
        return cargoRepository.findById(id).orElse(null);
    }
}