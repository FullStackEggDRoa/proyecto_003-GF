
package GrupoF.Proyecto3.Servicios;

import GrupoF.Proyecto3.Entidades.Cliente;
import GrupoF.Proyecto3.Entidades.Proveedor;
import GrupoF.Proyecto3.Repositorios.ClienteRepositorio;
import GrupoF.Proyecto3.Repositorios.ProveedorRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService{
    
    @Autowired
    private ClienteRepositorio cR;
    @Autowired
    private ProveedorRepositorio pR;
    @Autowired
    private ClienteServicio cS; 
    @Autowired
    private ProveedorServicio pS;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Cliente cliente = cR.buscarPorCorreo(username);
        Proveedor proveedor = pR.buscarPorCorreo(username);
        
        if(cliente != null){

            return cS.loadUserByUsername(username);
            
        }else if(proveedor != null){

            return pS.loadUserByUsername(username);
      
        }else{
            
            throw new UsernameNotFoundException("Serivicio No especficado o No Reconocido");
            
        }
    }
}
