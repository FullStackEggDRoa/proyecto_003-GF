
package GrupoF.Proyecto3.Servicios;

import GrupoF.Proyecto3.Entidades.Cliente;
import GrupoF.Proyecto3.Entidades.Dni;
import GrupoF.Proyecto3.Enumeradores.NombreRol;
import GrupoF.Proyecto3.Repositorios.ClienteRepositorio;
import GrupoF.Proyecto3.Repositorios.DniRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ClienteServicio implements UserDetailsService {

    @Autowired
    private ClienteRepositorio cr;
    @Autowired
    private DniRepositorio dR;

    @Transactional
    public void registrarCliente(String nombreApellido, String contrasenia, String dni, String correo, String telefono, String direccion) throws Exception  {

        validarC(nombreApellido, contrasenia, dni, correo, direccion);

        if (cr.buscarPorCorreo(correo) != null) {
            throw new Exception("Ya existe un usuario registrado con este correo electrónico.");
        }

        Cliente cliente = new Cliente();
        Dni dni1 = new Dni();
                
        cliente.setNombreApellido(nombreApellido);
        cliente.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
        dni1.setNumero(dni);
        dR.save(dni1);
        cliente.setDni(dni1);        
        cliente.setCorreo(correo);
        cliente.setTelefono(Integer.valueOf(telefono));
        cliente.setDireccion(direccion);
        cliente.setAlta(true);
        cliente.setRol(NombreRol.USUARIO);

        cr.save(cliente);
    }
    
    
    private void validarC(String nombreApellido, String contrasenia, String dni, String correo, String direccion) throws Exception {

        if (nombreApellido.isEmpty() || nombreApellido == null) {
            throw new Exception("El nombre y apellido no pueden ser nulos o estar vacíos");
        }
        if (contrasenia.isEmpty() || contrasenia == null || contrasenia.length() <= 8) {
            throw new Exception("La contraseña no puede estar vacía, y tener más de 8 caracteres");
        }
        if (dni.isEmpty() || dni == null) {
            throw new Exception("El DNI no puede ser nulo o estar vacio");
        }
        if (correo.isEmpty() || correo == null) {
            throw new Exception("El correo no puede ser nulo o estar vacio");
        }
        if (direccion.isEmpty() || direccion == null) {
            throw new Exception("La direccion no puede ser nula o estar vacia");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {

        List<Cliente> clientes = new ArrayList();
        clientes = cr.findAll();
        return clientes;
    }

    @Transactional
    public void actualizarCliente(String id, String nombreApellido, String contrasenia, String dni, String correo, Integer telefono, String direccion) throws Exception {
        
        validarC(nombreApellido, contrasenia, dni, correo, direccion);

        Optional<Cliente> respuestaCliente = cr.findById(id);
        Dni dni1 = new Dni();
        if (respuestaCliente.isPresent()) {

            Cliente cliente = respuestaCliente.get();
            cliente.setNombreApellido(nombreApellido);
            cliente.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
            dni1.setNumero(dni);
            cliente.setCorreo(correo);
            cliente.setTelefono(telefono);
            cliente.setDireccion(direccion);

            cr.save(cliente);
        }
    }
    
    @Transactional
    public void bajaCliente(String id){
        Optional<Cliente> cliente = cr.findById(id);
        if (cliente.isPresent()){
            Cliente clienteAux = cliente.get();
            clienteAux.setAlta(false);
            cr.save(clienteAux);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = cr.buscarPorCorreo(username);
        
        if (cliente != null) {
            
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ cliente.getRol().toString());
            
            permisos.add(p);
   
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", cliente);
            
            return new User(cliente.getCorreo(), cliente.getContrasenia(),permisos);
        }else{
            return null;
        }
    }
}
