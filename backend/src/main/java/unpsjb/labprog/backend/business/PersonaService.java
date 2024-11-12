package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Persona;

@Service
public class PersonaService {
    
    // Conecta al repositorio, hace una instancia
    @Autowired
    PersonaRepository repository;

    public List<ReporteConcepto> reporte(Integer aMes, Integer anAnio) {
        List<ReporteConcepto> result = new ArrayList<>();
        for (Object[] o : repository.reporte(aMes, anAnio)) {
            ReporteConcepto temp = new ReporteConcepto();
            temp.setCantLicencias((Long) o[1]);
            temp.setPersona((Persona) o[0]);
            result.add(temp);
        }
        return result;
    }

    public List<Persona> findAll(){
        List<Persona> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Persona findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Persona findByCuil(String cuil) {
        return repository.findByCuil(cuil).orElse(null);
    }

    @Transactional
    public Persona save(Persona persona) {
        return repository.save(persona);
    }

    @Transactional
    public Persona delete(String cuil) {
        Persona aPersona = this.findByCuil(cuil);
        if (aPersona != null)
            repository.delete(aPersona);
        return aPersona;
    }
    
    public List<Persona> search(String term) {
        return repository.search("%" + term.toUpperCase() + "%");
    }

    public Page<Persona> findByPage(int page, int size) {
        return repository.findAll(
                PageRequest.of(page, size));
    }

}