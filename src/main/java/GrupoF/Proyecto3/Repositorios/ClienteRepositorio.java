package GrupoF.Proyecto3.Repositorios;

import GrupoF.Proyecto3.enridad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
    
}
