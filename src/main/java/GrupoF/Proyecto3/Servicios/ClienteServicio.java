
package GrupoF.Proyecto3.Servicios;

import GrupoF.Proyecto3.Entidades.Cliente;
import GrupoF.Proyecto3.Entidades.Dni;
import GrupoF.Proyecto3.Entidades.Imagen;
import GrupoF.Proyecto3.Entidades.Usuario;
import GrupoF.Proyecto3.Enumeradores.NombreRol;
import GrupoF.Proyecto3.Excepciones.MiExcepcion;
import GrupoF.Proyecto3.Repositorios.ClienteRepositorio;
import GrupoF.Proyecto3.Repositorios.DniRepositorio;
import GrupoF.Proyecto3.Repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteServicio implements UserDetailsService {

    @Autowired
    private ClienteRepositorio cR;

    @Autowired
    private DniRepositorio dR;
    
    @Autowired
    private UsuarioRepositorio uR;
    
    @Autowired
    private ImagenServicio iS;
  

    @Transactional
    public void registrarCliente(String nombreApellido, String contrasenia, String dni, String correo, String telefono, String direccion, String contraseniaChk) throws MiExcepcion {
        
        validarDatosCliente(nombreApellido, dni, correo, direccion);
        validarContraseniaCliente(contrasenia, contraseniaChk);

        if (cR.buscarPorCorreo(correo) != null) {
            throw new MiExcepcion("Ya existe un usuario registrado con este correo electrónico.");
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

        cR.save(cliente);
    }
    
    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {

        List<Cliente> clientes = new ArrayList();
        clientes = cR.findAll();
        return clientes;
    }

    @Transactional
    public void actualizarCliente(MultipartFile archivo,  String id, String nombreApellido, String contrasenia, String dni, String correo, String telefono, String direccion, String contraseniaChk) throws MiExcepcion {
        
        validarDatosCliente(nombreApellido, dni, correo, direccion);
        
        Optional<Cliente> respuestaCliente = cR.findById(id);
        Dni dni1 = new Dni();
        if (respuestaCliente.isPresent()) {
            
            String idImagen = null;

            Cliente cliente = respuestaCliente.get();
            cliente.setNombreApellido(nombreApellido);
            dni1.setNumero(dni);
            dR.save(dni1);
            cliente.setDni(dni1);
            cliente.setCorreo(correo);
            cliente.setTelefono(Integer.valueOf(telefono));
            cliente.setDireccion(direccion);
            
            if(!(contrasenia.equals(cliente.getContrasenia()))){
                cambiarContraseniaCliente(id, contrasenia, contraseniaChk);
            }
            
            Imagen imagen = new Imagen();
            
            if(cliente.getImagen()!=null){
                idImagen = cliente.getImagen().getId();
                try {
                    iS.actualizar(archivo, idImagen);
                } catch (Exception ex) {
                    throw new MiExcepcion("No se pudo Actualizar el Avatar");
                }
            }else{
                try {
                    imagen = iS.guardar(archivo);
                } catch (Exception ex) {
                    throw new MiExcepcion("No se pudo Cargar el Avatar");
                }
            }
            cliente.setImagen(imagen);
            
            cR.save(cliente);
        }
    }
    
    public void cambiarContraseniaCliente (String id, String nuevaContrasenia, String contraseniaChk) throws MiExcepcion{
         
        Optional<Cliente> cliente = cR.findById(id);
        if (cliente.isPresent()){
            validarContraseniaCliente(nuevaContrasenia, contraseniaChk);
        
            Cliente clienteAux = cliente.get();
            clienteAux.setContrasenia(new BCryptPasswordEncoder().encode(nuevaContrasenia));
            cR.save(clienteAux);

        }
    }
    
    @Transactional
    public void bajaCliente(String id){
        Optional<Cliente> cliente = cR.findById(id);
        if (cliente.isPresent()){
            Cliente clienteAux = cliente.get();
            clienteAux.setAlta(false);
            cR.save(clienteAux);
        }
    }

    @Transactional
    public Cliente clienteById (String id){
        Optional<Cliente> cliente = cR.findById(id);
        Cliente clienteAux = new Cliente();
        if (cliente.isPresent()){
            clienteAux = cliente.get();    
        }
        return clienteAux;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 
        Cliente cliente = (Cliente) uR.buscarPorCorreo(username);
        
        if (cliente != null) {
            
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
    
     private void validarDatosCliente(String nombreApellido, String dni, String correo, String direccion) throws MiExcepcion {
        if (nombreApellido.isEmpty() || nombreApellido == null) {
            throw new MiExcepcion("El nombre y apellido no pueden ser nulos o estar vacíos.");
        }
        if (dni.isEmpty() || dni == null) {
            throw new MiExcepcion("El DNI no puede ser nulo o estar vacio");
        }
        if (correo.isEmpty() || correo == null) {
            throw new MiExcepcion("El correo no puede ser nulo o estar vacio");
        }
        if (direccion.isEmpty() || direccion == null) {
            throw new MiExcepcion("La direccion no puede ser nula o estar vacia");
        }
    }
    
    private void validarContraseniaCliente (String contrasenia, String contraseniaChk) throws MiExcepcion{
        
      if (contrasenia.isEmpty() || contrasenia == null || contrasenia.length() <= 8) {
            throw new MiExcepcion("La contraseña no puede estar vacía y debe tener al menos 8 caracteres");
        }
        if (!contrasenia.equals(contraseniaChk)) {
            throw new MiExcepcion("Las contraseñas ingresadas no coinciden.");
        }  
    }
    
}
