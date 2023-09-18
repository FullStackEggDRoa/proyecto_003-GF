

package GrupoF.Proyecto3.Servicios;

import GrupoF.Proyecto3.Entidades.Dni;
import GrupoF.Proyecto3.Entidades.Proveedor;
import GrupoF.Proyecto3.Enumeradores.NombreRol;
import GrupoF.Proyecto3.Excepciones.MiExcepcion;
import GrupoF.Proyecto3.Repositorios.DniRepositorio;
import GrupoF.Proyecto3.Repositorios.ProveedorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ProveedorServicio implements UserDetailsService {
    
    @Autowired
    private ProveedorRepositorio pr;
    private DniRepositorio dr;

    
    @Transactional
    public void registrarProveedor (String nombreApellido, String contrasenia, String dni, String correo, String telefono, Integer numeroMatricula, String categoriaServicio, Double costoHora, String contraseniaChk) throws MiExcepcion{
        
        validarDatosProveedor(nombreApellido, contrasenia, dni, correo, telefono, numeroMatricula, categoriaServicio, costoHora, contraseniaChk);

             
        if (pr.buscarPorCorreo(correo) != null){
            throw new MiExcepcion("Ya existe un usuario registrado con este correo electrónico.");
        }
        
        Proveedor proveedor = new Proveedor();
        Dni dni2 = new Dni();
        
        proveedor.setNombreApellido(nombreApellido);
        proveedor.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
        dni2.setNumero(dni);
        dr.save(dni2);
        proveedor.setDni(dni2);
        proveedor.setCorreo(correo);
        proveedor.setTelefono(Integer.valueOf(telefono));
        proveedor.setNumMatricula(numeroMatricula);
        proveedor.setCategoriaServicio(categoriaServicio);
        proveedor.setCostoHora(costoHora);
        proveedor.setAlta(true);
        proveedor.setRol(NombreRol.USUARIO);
        
        pr.save(proveedor);
    }
    
    
    private void validarDatosProveedor(String nombreApellido, String contrasenia, String dni, String correo, String telefono, Integer numeroMatricula, String categoriaServicio, Double costoHora, String contraseniaChk) throws MiExcepcion{

        if (nombreApellido.isEmpty() || nombreApellido == null) {
            throw new MiExcepcion("El nombre y apellido no pueden ser nulos o estar vacíos");
        }
        if (contrasenia.isEmpty() || contrasenia == null || contrasenia.length() <= 8) {
            throw new MiExcepcion("La contraseña no puede estar vacía y debe tener al menos 8 caracteres");
        }
        if (!contrasenia.equals(contraseniaChk)) {
            throw new MiExcepcion("Las contraseñas ingresadas no coinciden");
        }
           if (dni.isEmpty() || dni == null) {
            throw new MiExcepcion("El DNI no puede ser nulo o estar vacio");
        }
         if (correo.isEmpty() || correo == null) {
            throw new MiExcepcion("El correo no puede ser nulo o estar vacio");
        }
        if (telefono.isEmpty() || telefono == null) {
            throw new MiExcepcion("El telefono no puede ser nulo o estar vacio");
        }
        if (numeroMatricula == -1 || numeroMatricula == null) {
            throw new MiExcepcion("El número de matrícula no puede ser nulo o estar vacio");
        }
        if (categoriaServicio.isEmpty() || categoriaServicio == null) {
            throw new MiExcepcion("La categoria de servicio no puede ser nula o estar vacia");
        }
        if (costoHora == -1 || costoHora == null) {
            throw new MiExcepcion("El costo no puede ser nulo o estar vacio");
        }
    }

    @Transactional(readOnly = true)
    public List<Proveedor> listarProveedores() {

        List<Proveedor> proveedores = new ArrayList();
        proveedores = pr.findAll();
        return proveedores;
    }

    @Transactional

    public void actualizarProveedor(String id, String nombreApellido, String contrasenia, String dni, String correo, String telefono, Integer numeroMatricula, String categoriaServicio, Double costoHora, String contraseniaChk) throws MiExcepcion {
        
        validarDatosProveedor(nombreApellido, contrasenia, dni, correo, telefono, numeroMatricula, categoriaServicio, costoHora, contraseniaChk);

        Optional<Proveedor> respuestaProveedor = pr.findById(id);
        Dni dni2 = new Dni();
        if (respuestaProveedor.isPresent()) {

            Proveedor proveedor = respuestaProveedor.get();
            proveedor.setNombreApellido(nombreApellido);
            proveedor.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));

            dni2.setNumero(dni);
            dr.save(dni2);
            proveedor.setDni(dni2);
            proveedor.setCorreo(correo);
            proveedor.setTelefono(Integer.valueOf(telefono));
            proveedor.setNumMatricula(numeroMatricula);
            proveedor.setCategoriaServicio(categoriaServicio);
            proveedor.setCostoHora(costoHora);
            
            pr.save(proveedor);
        }
    }
    
    @Transactional

    public void bajaProveedor(String id){
        Optional<Proveedor> proveedor = pr.findById(id);
        if (proveedor.isPresent()){
            Proveedor proveedorAux = proveedor.get();
            proveedorAux.setAlta(false);
            pr.save(proveedorAux);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Proveedor proveedor = pr.buscarPorCorreo(username);
        
        if (proveedor != null) {
            
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ proveedor.getRol().toString());
            
            permisos.add(p);
   
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", proveedor);
            
            return new User(proveedor.getCorreo(), proveedor.getContrasenia(),permisos);
        
        }else{

            return null;
        }
    }
    
}

