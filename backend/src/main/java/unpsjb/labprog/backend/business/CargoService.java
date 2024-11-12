package unpsjb.labprog.backend.business;

import java.util.List;
import java.util.Map;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.Designacion;
import unpsjb.labprog.backend.model.EnumDia;
import unpsjb.labprog.backend.model.EnumTipoDesignacion;
import unpsjb.labprog.backend.model.EnumTurno;
import unpsjb.labprog.backend.model.Horario;

@Service
public class CargoService {
    @Autowired
    CargoRepository repository;
    @Autowired
    DesignacionRepository designacionRepository;
    @Autowired
    LicenciaRepository licenciaRepository;
    @Autowired
    DivisionRepository divisionRepository;
    @Autowired
    PersonaRepository personaRepository;

    // Métodos
    public List<Cargo> findAll() {
        List<Cargo> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Cargo findByNombreDivision(String nombre, int anio, int numero) {
        return repository.findByNombreDivision(nombre, anio, numero).orElse(null);
    }

    public Cargo findByNombre(String nombre) {
        return repository.findByNombre(nombre).orElse(null);
    }

    public Cargo save(Cargo aCargo) {
        this.checkSave(aCargo);

        return repository.save(aCargo);
    }

    private void checkSave(Cargo aCargo) {
        if (aCargo.getFechaFin() != null && aCargo.getFechaInicio().isAfter(aCargo.getFechaFin()))
            throw new DataIntegrityViolationException("La fecha de inicio debe ser menor a la fecha de fin.");
        if (aCargo.getTipoDesignacion().equals(EnumTipoDesignacion.CARGO)) {
            Cargo aCargoOrNull = this.findByNombre(aCargo.getNombre());
            if (aCargoOrNull != null && aCargoOrNull.getId() != (aCargo.getId())) {
                throw new DataIntegrityViolationException("Ya existe el cargo " + aCargo.getNombre());
            }
        }

        for (Horario horario : aCargo.getHorarios()) {
            if (horario.getHora().isAfter(horario.getHoraFin()))
                throw new DataIntegrityViolationException("La hora de inicio debe ser menor a la hora de fin.");
        }
    }

    public Cargo findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Cargo delete(int id) {
        Cargo aCargo = this.findById(id);
        if (aCargo != null)
            repository.deleteById(id);
        return aCargo;
    }

    public Page<Cargo> findByPage(int page, int size) {
        return repository.findAll(
                PageRequest.of(page, size));
    }

    public List<Cargo> search(String term) {
        return repository.search("%" + term.toUpperCase() + "%");
    }

    public Map<LocalTime, List<String>> getCalendarioDiaDivision(String aDiaSemana, Integer aDivisionAnio,
            Integer aDivisionNumero, String anAnio, String aMes, String aDia) {
        String[] horas = HorasTurno.HORAS
                .get(this.divisionRepository.findByAnioNumero(aDivisionAnio, aDivisionNumero).orElse(null).getTurno());
        Map<LocalTime, List<String>> result = new HashMap<>();

        LocalDate fecha = LocalDate.parse(String.format("%s-%s-%s", anAnio, aMes, aDia));
        for (int i = 0; i < horas.length; i++) {
            if (result.get(LocalTime.parse(horas[i])) == null) {
                result.put(LocalTime.parse(horas[i]), new ArrayList<String>());
            }

            for (Cargo cargo : repository.cargosEnHorarioDiaDivision(LocalTime.parse(horas[i]),
                    EnumDia.valueOf(aDiaSemana),
                    this.divisionRepository.findByAnioNumero(aDivisionAnio, aDivisionNumero).orElse(null), fecha)) {
                result.get(LocalTime.parse(horas[i])).add(0,
                        cargo.getNombre() + " ("
                                + this.getDesignadoDia(fecha,
                                        cargo, EnumDia.valueOf(aDiaSemana))
                                + ")");
            }
        }

        return result;
    }

    public Map<LocalTime, List<String>> getCalendarioDiaDocente(String aDiaSemana, String aTurno, String aPersonaCuil,
            String anAnio, String aMes, String aDia) {
        String[] horas = HorasTurno.HORAS.get(EnumTurno.valueOf(aTurno));
        Map<LocalTime, List<String>> result = new HashMap<>();

        LocalDate fecha = LocalDate.parse(String.format("%s-%s-%s", anAnio, aMes, aDia));
        fecha = fecha.with(DayOfWeek.of(EnumDia.valueOf(aDiaSemana).getValor()));

        for (int i = 0; i < horas.length; i++) {
            if (result.get(LocalTime.parse(horas[i])) == null) {
                result.put(LocalTime.parse(horas[i]), new ArrayList<String>());
            }

            String temp = "";
            for (Designacion designacion : designacionRepository.designacionEnHorarioDiaDocente(
                    LocalTime.parse(horas[i]),
                    EnumDia.valueOf(aDiaSemana),
                    this.personaRepository.findByCuil(aPersonaCuil).orElse(null), fecha)) {
                temp = "";
                if (designacion.getCargo().getTipoDesignacion().equals(EnumTipoDesignacion.ESPACIOCURRICULAR)) {
                    temp += designacion.getCargo().getNombre() + " " + designacion.getCargo().getDivision().getAnio()
                            + "° "
                            + designacion.getCargo().getDivision().getNumero() + "°";
                } else {
                    temp += designacion.getCargo().getNombre();
                }

                temp += licenciaRepository.findLicenciaPeriodoPersona(fecha, fecha, aPersonaCuil).isEmpty() ? ""
                        : " (Con licencia)";

                result.get(LocalTime.parse(horas[i])).add(0, temp);
            }
        }

        return result;
    }

    private String getDesignadoDia(LocalDate fecha, Cargo cargo, EnumDia dia) {

        fecha = fecha.with(DayOfWeek.of(dia.getValor()));
        String result = "";

        if (!designacionRepository.findPersonaDesignadaCargo(fecha, cargo.getId()).isEmpty()) {
            for (Designacion d : designacionRepository.findPersonaDesignadaCargo(fecha, cargo.getId())) {
                if (licenciaRepository.findLicenciaPeriodoPersona(fecha, fecha, d.getPersona().getCuil()).isEmpty()) {
                    result = designacionRepository.findPersonaDesignadaCargo(fecha, cargo.getId()).size() == 1
                            ? d.getPersona().getNombre() + " " + d.getPersona().getApellido()
                            : d.getPersona().getNombre() + " " + d.getPersona().getApellido() + " Suplente";
                }
            }
            result = result.equals("") ? "Ausente por licencia" : result;
        }
        result = result.equals("") ? "Sin designar" : result;

        return result;
    }

}
