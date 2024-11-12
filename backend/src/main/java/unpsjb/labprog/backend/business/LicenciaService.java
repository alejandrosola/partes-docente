package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.ArticuloLicencia;
import unpsjb.labprog.backend.model.Licencia;

@Service
public class LicenciaService {

    // Conecta al repositorio, hace una instancia
    @Autowired
    LicenciaRepository repository;
    @Autowired
    ControlService controlService;
    @Autowired
    List<ArticuloControl> articulosControl;

    public List<Licencia> findAll() {
        List<Licencia> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Licencia findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Licencia save(Licencia aLicencia) {
        aLicencia.setDesignaciones(controlService.findDesignacionPeriodo(aLicencia.getPedidoDesde(),
                aLicencia.getPedidoHasta(), aLicencia.getPersona().getCuil()));
        this.checkSave(aLicencia);
        return repository.save(aLicencia);
    }

    @Transactional
    public Licencia delete(int id) {
        Licencia aLicencia = this.findById(id);
        aLicencia.setDesignaciones(null);
        if (aLicencia != null) {
            repository.delete(aLicencia);
        }
        return aLicencia;
    }

    public Page<Licencia> findByPage(int page, int size) {
        return repository.findAll(
                PageRequest.of(page, size));
    }

    private void checkSave(Licencia aLicencia) {
        if (controlService.findDesignacionPeriodo(aLicencia.getPedidoDesde(), aLicencia.getPedidoHasta(), aLicencia.getPersona().getCuil()).isEmpty())
            throw new DataIntegrityViolationException(String.format(
                    "NO se otorga Licencia artículo %s a %s %s debido a que el agente no posee ningún cargo en la institución",
                    aLicencia.getArticulo().getNombre(), aLicencia.getPersona().getNombre(),
                    aLicencia.getPersona().getApellido()));

        if (!controlService.findLicenciaPeriodoPersona(aLicencia.getPedidoDesde(), aLicencia.getPedidoHasta(),
                aLicencia.getPersona().getCuil()).isEmpty()) {
            throw new DataIntegrityViolationException(String.format(
                    "NO se otorga Licencia artículo %s a %s %s debido a que ya posee una licencia en el mismo período",
                    aLicencia.getArticulo().getNombre(), aLicencia.getPersona().getNombre(),
                    aLicencia.getPersona().getApellido()));
        }

        if (aLicencia.getPedidoDesde().isAfter(aLicencia.getPedidoHasta()))
            throw new DataIntegrityViolationException("La fecha de inicio debe ser menor a la fecha de fin.");

        getArticuloControl(aLicencia.getArticulo()).checkValid(aLicencia,
                findLicenciasPersonaArticulo(aLicencia.getPersona().getCuil(), aLicencia.getArticulo().getNombre()));
    }

    public List<Licencia> findLicenciaPeriodoPersona(LocalDate fechaInicio, LocalDate fechaFin, String cuil) {
        return repository.findLicenciaPeriodoPersona(fechaInicio, fechaFin, cuil);
    }

    public List<Licencia> findLicenciasPersonaArticulo(String cuil, String articulo) {
        return repository.findLicenciasPersonaArticulo(cuil, articulo);
    }

    private ArticuloControl getArticuloControl(ArticuloLicencia articulo) {
        for (ArticuloControl articuloControl : articulosControl) {
            if (articuloControl.isResponsibleFor(articulo)) {
                return articuloControl;
            }
        }
        throw new UnsupportedOperationException("Artículo " + articulo.getNombre() + " no soportado");
    }

    public ParteDiario getParteDiario(LocalDate fecha) {
        ParteDiario aParteDiario = new ParteDiario();
        aParteDiario.setDocentes(repository.findLicenciaVigente(fecha));
        aParteDiario.setFecha(fecha);
        return aParteDiario; 
    }
}