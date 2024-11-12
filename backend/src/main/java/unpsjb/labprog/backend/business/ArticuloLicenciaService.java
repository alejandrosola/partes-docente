package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.ArticuloLicencia;

@Service
public class ArticuloLicenciaService {
    
    // Conecta al repositorio, hace una instancia
    @Autowired
    ArticuloLicenciaRepository repository;

    public List<ArticuloLicencia> findAll(){
        List<ArticuloLicencia> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public ArticuloLicencia findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public ArticuloLicencia save(ArticuloLicencia licencia) {
        return repository.save(licencia);
    }

    @Transactional
    public ArticuloLicencia delete(int id) {
        ArticuloLicencia anArticuloLicencia = this.findById(id);
        if (anArticuloLicencia != null)
            repository.delete(anArticuloLicencia);
        return anArticuloLicencia;
    }

    public ArticuloLicencia findByNombre(String nombre) {
        return repository.findByNombre(nombre);
    }

    public List<ArticuloLicencia> search(String term) {
        return repository.search("%" + term.toUpperCase() + "%");
    }

}