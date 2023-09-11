//package GrupoF.Proyecto3.Repositorios;

=======
import GrupoF.Proyecto3.Entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {
    @Query("SELECT p FROM Proveedor p WHERE p.correo = :correo")
    public Proveedor findByCorreo(@Param("correo") String correo);
}

