package GrupoF.Proyecto3.Repositorios;

import GrupoF.Proyecto3.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    public Usuario findByCorreo(@Param("correo") String correo);
}
