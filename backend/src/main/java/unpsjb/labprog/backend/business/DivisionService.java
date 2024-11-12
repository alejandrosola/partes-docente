package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Division;
@Service
public class DivisionService {
    
    // Conecta al repositorio, hace una instancia
    @Autowired
    DivisionRepository repository;

    public List<Division> findAll(){
        List<Division> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Division findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Division save(Division division) {
        return repository.save(division);
    }

    @Transactional
    public Division delete(int anio, int numero) {
        Division aDivision = this.findByAnioNumero(anio, numero);
        if (aDivision != null)
            repository.delete(aDivision);
        return aDivision;
    }
    
    public Division findByAnioNumero(int anio, int numero) {
        return repository.findByAnioNumero(anio, numero).orElse(null);
    }

    public Page<Division> findByPage(int page, int size) {
        return repository.findAll(
                PageRequest.of(page, size));
    }

    public List<Division> search(String term) {
        return repository.search("%" + term.toUpperCase() + "%");
    }
}