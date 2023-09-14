

package GrupoF.Proyecto3.Servicios;
import GrupoF.Proyecto3.Enumeradores.NombreRol;
import GrupoF.Proyecto3.Entidades.Dni;
import GrupoF.Proyecto3.Entidades.Proveedor;

import GrupoF.Proyecto3.Repositorios.ProveedorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorServicio {
    
    @Autowired
    private ProveedorRepositorio pr;
    
    @Transactional
    public void registrarProveedor (String nombreApellido, String contrasenia, String dni, String correo, Integer telefono, NombreRol NombreRol, Integer numMatricula, String categoriaServicio, Double costoHora) throws Exception{
        
        validarP(nombreApellido, contrasenia, dni, correo, telefono, numMatricula, categoriaServicio, costoHora);
             
        if (pr.buscarPorCorreo(correo) != null){
            throw new Exception("Ya existe un usuario registrado con este correo electrónico.");
        }
        
        Proveedor proveedor = new Proveedor();
        Dni dni2 = new Dni();
        
        proveedor.setNombreApellido(nombreApellido);
        proveedor.setContrasenia(contrasenia);
        dni2.setNumero(dni); 
        proveedor.setDni(dni2);
        proveedor.setCorreo(correo);
        proveedor.setTelefono(telefono);
        
        proveedor.setNumMatricula(numMatricula);
        proveedor.setCategoriaServicio(categoriaServicio);
        proveedor.setCostoHora(costoHora);
        proveedor.setAlta(true);
        proveedor.setRol(NombreRol.USUARIO);
        
        pr.save(proveedor);
              
    }
    @Transactional
    private void validarP(String nombreApellido, String contrasenia, String dni, String correo, Integer telefono, Integer numMatricula, String categoriaServicio, Double costoHora) throws Exception{

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
        if (telefono == -1 || telefono == null) {
            throw new Exception("El telefono no puede ser nulo o estar vacio");
        }
        if (numMatricula == -1 || numMatricula == null) {
            throw new Exception("El número de matrícula no puede ser nulo o estar vacio");
        }
        if (categoriaServicio.isEmpty() || categoriaServicio == null) {
            throw new Exception("La categoria de servicio no puede ser nula o estar vacia");
        }
        if (costoHora == -1 || costoHora == null) {
            throw new Exception("El costo no puede ser nulo o estar vacio");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Proveedor> listarProveedores() {

        List<Proveedor> proveedores = new ArrayList();
        proveedores = pr.findAll();
        return proveedores;
    }

    @Transactional
    public void actualizarProveedor(String id, String nombreApellido, String contrasenia, String dni, String correo, Integer telefono, Integer numMatricula, String categoriaServicio, Double costoHora) throws Exception{
        validarP(nombreApellido, contrasenia, dni, correo, telefono, numMatricula, categoriaServicio, costoHora);

        Optional<Proveedor> respuestaProveedor = pr.findById(id);
        Dni dni1 = new Dni();
        
        if (respuestaProveedor.isPresent()) {
            Proveedor proveedor = respuestaProveedor.get();
            proveedor.setNombreApellido(nombreApellido);
            proveedor.setContrasenia(contrasenia);
            dni1.setNumero(dni);
            proveedor.setCorreo(correo);
            proveedor.setTelefono(telefono);
            proveedor.setNumMatricula(numMatricula);
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

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Proveedor proveedor = pr.findByCorreo(username);
//        
//        if (proveedor != null) {
//            
//            List<GrantedAuthority> permisos = new ArrayList();
//            
//            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ proveedor.getRol().toString());
//            
//            permisos.add(p);
//   
//            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//            
//            HttpSession session = attr.getRequest().getSession(true);
//            
//            session.setAttribute("usuariosession", proveedor);
//            
//            return new User(proveedor.getCorreo(), proveedor.getContrasenia(),permisos);
//        }else{
//            //throw new UsernameNotFoundException("Usuario y contraseña inválidos");
//            
//            return null;
//        }
//    }
    
}

