package GrupoF.Proyecto3.Repositorios;

import GrupoF.Proyecto3.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
    @Query("SELECT c FROM Cliente c WHERE c.correo = :correo")
    public Cliente findByCorreo(@Param("correo") String correo);
}
