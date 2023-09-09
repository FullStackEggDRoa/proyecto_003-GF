package GrupoF.Proyecto3.repositorios;

import GrupoF.Proyecto3.enridad.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepositorio extends JpaRepository<Contrato, String> {
    
}