/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GrupoF.Proyecto3.Servicios;

import GrupoF.Proyecto3.Entidades.Cliente;
import GrupoF.Proyecto3.Repositorios.ClienteRepositorio;
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

/**
 *
 * @author droa
 */
@Service
public class UsuarioServicio implements UserDetailsService{
    
    @Autowired
    private ClienteRepositorio cR;
    
    @Autowired
    private ClienteRepositorio pR;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        
        if(pR.buscarPorCorreo(username) != null){
            
            Cliente cliente = cR.buscarPorCorreo(username);
                    
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ cliente.getRol().toString());
            
            permisos.add(p);
   
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", cliente);
            
            return new User(cliente.getCorreo(), cliente.getContrasenia(),permisos);
            
        }else if(pR.buscarPorCorreo(username)!= null){
            
            Cliente cliente = cR.buscarPorCorreo(username);
                    
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ cliente.getRol().toString());
            
            permisos.add(p);
   
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", cliente);
            
            return new User(cliente.getCorreo(), cliente.getContrasenia(),permisos);
        
        }else{
            
            //throw new UsernameNotFoundException("Usuario y contraseña inválidos");
            
            return null;
        }
    }
}
