package GrupoF.Proyecto3.repositorios;

import GrupoF.Proyecto3.enridad.Dni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DniRepositorio extends JpaRepository<Dni, String> {
    
}
