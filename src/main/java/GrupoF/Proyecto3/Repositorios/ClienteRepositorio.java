package GrupoF.Proyecto3.Repositorios;


import GrupoF.Proyecto3.Entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
    
    @Query("SELECT c FROM Cliente c WHERE c.correo = :correo")
    public Cliente buscarPorCorreo(@Param("correo") String correo);
}

