package unpsjb.labprog.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import unpsjb.labprog.backend.business.CargoService;
import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.EnumTipoDesignacion;

@EntityListeners(CargoListener.class)
public class CargoListener {
    @Autowired
    CargoService service;

    @PrePersist
    @PreUpdate
    public void validarTipo(Cargo cargo) {
        if ((EnumTipoDesignacion.ESPACIOCURRICULAR.equals(cargo.getTipoDesignacion())) && cargo.getDivision() == null) {
            throw new DataIntegrityViolationException("La división no puede ser nula para un espacio curricular.");
        }

        if ((EnumTipoDesignacion.CARGO.equals(cargo.getTipoDesignacion()))) {
            if (cargo.getDivision() != null) {
                throw new DataIntegrityViolationException("La división debe ser nula para un cargo.");
            }
        }
    }
}
