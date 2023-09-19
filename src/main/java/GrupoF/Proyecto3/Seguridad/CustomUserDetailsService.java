/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GrupoF.Proyecto3.Seguridad;

import GrupoF.Proyecto3.Servicios.ClienteServicio;
import GrupoF.Proyecto3.Servicios.ProveedorServicio;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private ClienteServicio cS; // Replace with your UserService implementations
    @Autowired
    private ProveedorServicio pS;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // Check some condition to determine which service is calling
        if (request.getParameter("service").equals("ClienteServicio")) {
            return cS.loadUserByUsername(username); // Delegate to UserService1
        } else if (request.getParameter("service").equals("ProveedorServicio")) {
            return pS.loadUserByUsername(username); // Delegate to UserService2
        }

        throw new UsernameNotFoundException("Service not specified or not recognized");
    }
}
