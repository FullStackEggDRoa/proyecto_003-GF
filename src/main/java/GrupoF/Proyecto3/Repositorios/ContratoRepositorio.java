
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
    
    @Query("SELECT c FROM Contrato c WHERE c.cliente.id = :Id")
    public List<Contrato> buscarPorIdCliente(@Param("Id") String idCliente);
    
    @Query("SELECT c FROM Contrato c WHERE c.proveedor.id = :Id")
    public List<Contrato> buscarPorIdProveedor(@Param("Id") String idProveedor);
    
    @Query("SELECT c, p.telefono FROM Contrato c INNER JOIN c.proveedor p WHERE c.cliente.id = :idCliente")
    public List<Contrato> listarContratosClienteConDatosProveedor(@Param("idCliente") String idCliente);
}
