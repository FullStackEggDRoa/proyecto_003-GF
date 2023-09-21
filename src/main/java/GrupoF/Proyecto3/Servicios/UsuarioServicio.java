package GrupoF.Proyecto3.Servicios;

import GrupoF.Proyecto3.Entidades.Cliente;
import GrupoF.Proyecto3.Entidades.Proveedor;
import GrupoF.Proyecto3.Repositorios.ClienteRepositorio;
import GrupoF.Proyecto3.Repositorios.DniRepositorio;
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
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private ClienteRepositorio cr;
    
    @Autowired
    private ProveedorRepositorio pr;
    
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        
        Cliente cliente = cr.buscarPorCorreo(correo);
        Proveedor proveedor = pr.buscarPorCorreo(correo);
        
        if(cr.buscarPorCorreo(correo) != null){
            
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ cliente.getRol().toString());
            permisos.add(p);   
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);            
            session.setAttribute("usuariosession", cliente);
            
            return new User(cliente.getCorreo(), cliente.getContrasenia(),permisos);
        }
        else if(pr.buscarPorCorreo(correo) != null){
            
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ proveedor.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", proveedor);
            
            return new User(proveedor.getCorreo(), proveedor.getContrasenia(),permisos);
        }
        return null;
    }
    
}
