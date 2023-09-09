package GrupoF.Proyecto3.repositorios;

import GrupoF.Proyecto3.enridad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>  {
    
}
