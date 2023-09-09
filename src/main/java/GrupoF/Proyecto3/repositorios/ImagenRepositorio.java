package GrupoF.Proyecto3.repositorios;

import GrupoF.Proyecto3.enridad.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
    
}
