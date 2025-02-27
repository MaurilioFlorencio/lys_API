package br.com.lys.services;
import br.com.lys.models.diario.Diario;
import br.com.lys.repositories.DiarioRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class DiarioService {

    private final DiarioRepository diarioRepository;

    public DiarioService(DiarioRepository diarioRepository) {
        this.diarioRepository = diarioRepository;
    }

    @Cacheable(value = "diario", key = "#diario.id")
    public Diario create (Diario diario){
        return diarioRepository.save(diario);
    }

    @Cacheable(value = "diario", key = "#id")
    public Diario read (Long id){
        return diarioRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "diario", key = "#id")
    public void delete (Long id){
        diarioRepository.deleteById(id);
    }

    @Cacheable(value = "diario", key = "#page.pageNumber")
    public Page<Diario> findAll (Pageable page){
        return diarioRepository.findAll(page);
    }

    @CachePut(value = "diario", key = "#id")
    public Diario update (Long id, Diario diario){
        var diarioAux = diarioRepository.findById(id).orElse(null);
        if(diarioAux == null){
            return null;
        }
        if ( diario.getTexto() != null){
            diarioAux.setTexto(diario.getTexto());
        }
        return diarioRepository.save(diarioAux);
    }
}
