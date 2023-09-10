package GrupoF.Proyecto3.Repositorios;

import GrupoF.Proyecto3.Entidades.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepositorio extends JpaRepository<Contrato, String> {
    
}
