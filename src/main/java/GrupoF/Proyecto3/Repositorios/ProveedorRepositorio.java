package GrupoF.Proyecto3.Repositorios;

import GrupoF.Proyecto3.Entidades.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {
    @Query("SELECT p FROM Proveedor p WHERE p.correo = :correo")
    public Proveedor buscarPorCorreo(@Param("correo") String correo);
    
    @Query("SELECT p FROM Proveedor p WHERE p.categoriaServicio = :categoriaServicio")
    public Proveedor buscarPorCategoria(@Param("categoriaServicio") String categoriaServicio);
    
    @Query("SELECT p FROM Proveedor p ORDER BY p.categoriaServicio, p.nombreApellido")
    public List<Proveedor> listarProveedoresPorCategoriaYNombre();
 
}

