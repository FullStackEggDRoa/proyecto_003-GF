package GrupoF.Proyecto3.repositorios;

import GrupoF.Proyecto3.enridad.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {
    
}