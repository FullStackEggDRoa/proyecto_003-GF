
package GrupoF.Proyecto3.Repositorios;

import GrupoF.Proyecto3.Entidades.Contrato;
import GrupoF.Proyecto3.Entidades.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepositorio extends JpaRepository<Contrato, String> {
    
    @Query("SELECT c FROM Contrato c WHERE c.cliente = :Id")
    public List<Contrato> buscarPorIdCliente(@Param("Id") String idCliente);
    
    @Query("SELECT p FROM Contrato p WHERE p.proveedor = :Id")
    public List<Contrato> buscarPorIdProveedor(@Param("Id") String idProveedor);
}
