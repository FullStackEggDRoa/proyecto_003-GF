
package GrupoF.Proyecto3.Repositorios;

import GrupoF.Proyecto3.Entidades.Contrato;
import GrupoF.Proyecto3.Entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepositorio extends JpaRepository<Contrato, String> {
    
    @Query("SELECT c FROM Contrato c WHERE c.idCliente = :idCliente")
    public Proveedor buscarPorIdCliente(@Param("idCliente") String idCliente);
    
    @Query("SELECT c FROM Contrato c WHERE c.idProveedor = :idProveedor")
    public Proveedor buscarPorIdProveedor(@Param("idProveedor") String idProveedor);
}
