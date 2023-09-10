package GrupoF.Proyecto3.Repositorios;

import GrupoF.Proyecto3.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>  {
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    public Usuario buscarPorCorreo(@Param("correo") String correo);    
}

