package GrupoF.Proyecto3.Servicios;

import GrupoF.Proyecto3.Entidades.Dni;
import GrupoF.Proyecto3.Entidades.Imagen;
import GrupoF.Proyecto3.Entidades.Proveedor;
import GrupoF.Proyecto3.Enumeradores.NombreRol;
import GrupoF.Proyecto3.Excepciones.MiExcepcion;
import GrupoF.Proyecto3.Repositorios.DniRepositorio;
import GrupoF.Proyecto3.Repositorios.ProveedorRepositorio;
import GrupoF.Proyecto3.Repositorios.UsuarioRepositorio;
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
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProveedorServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio uR;
    
    @Autowired
    private ProveedorRepositorio pR;
    
    @Autowired
    private DniRepositorio dR;
    
    @Autowired
    private ImagenServicio iS;
    
    @Transactional
    public void registrarProveedor (String nombreApellido, String contrasenia, String dni, String correo, String telefono, Integer numeroMatricula, String categoriaServicio, Double costoHora, String contraseniaChk) throws MiExcepcion{
       
        validarDatosProveedor(nombreApellido, dni, correo, telefono, numeroMatricula, categoriaServicio, costoHora);
        validarContraseniaProveedor(contrasenia, contraseniaChk);

        if (pR.buscarPorCorreo(correo) != null) {
            throw new MiExcepcion("Ya existe un usuario registrado con este correo electrónico.");
        }

        Proveedor proveedor = new Proveedor();
        Dni dni2 = new Dni();

        proveedor.setNombreApellido(nombreApellido);
        proveedor.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
        dni2.setNumero(dni);
        dR.save(dni2);
        proveedor.setDni(dni2);
        proveedor.setCorreo(correo);
        proveedor.setTelefono(Integer.valueOf(telefono));
        proveedor.setNumMatricula(numeroMatricula);
        proveedor.setCategoriaServicio(categoriaServicio);
        proveedor.setCostoHora(costoHora);
        proveedor.setAlta(true);
        proveedor.setRol(NombreRol.USUARIO);

        pR.save(proveedor);
    }

    @Transactional
    public void actualizarProveedor(MultipartFile archivo, String id, String nombreApellido, String contrasenia, String dni, String correo, String telefono, Integer numeroMatricula, String categoriaServicio, Double costoHora, String contraseniaChk) throws MiExcepcion {
    
        validarDatosProveedor(nombreApellido, dni, correo, telefono, numeroMatricula, categoriaServicio, costoHora);

        Optional<Proveedor> respuestaProveedor = pR.findById(id);
        Dni dni2 = new Dni();
        if (respuestaProveedor.isPresent()) {
            
            String idImagen = null;

            Proveedor proveedor = respuestaProveedor.get();
            proveedor.setNombreApellido(nombreApellido);
            dni2.setNumero(dni);
            dR.save(dni2);
            proveedor.setDni(dni2);
            proveedor.setCorreo(correo);
            proveedor.setTelefono(Integer.valueOf(telefono));
            proveedor.setNumMatricula(numeroMatricula);
            proveedor.setCategoriaServicio(categoriaServicio);
            proveedor.setCostoHora(costoHora);

            if(!(contrasenia.equals(proveedor.getContrasenia()))){
                cambiarContraseniaProveedor(id, contrasenia, contraseniaChk);
            }
            
            Imagen imagen = new Imagen();
            
            if(proveedor.getImagen()!=null){
                idImagen = proveedor.getImagen().getId();
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
            
            proveedor.setImagen(imagen);
            
            pR.save(proveedor);
        }
    }
    
    public void cambiarContraseniaProveedor(String id, String nuevaContrasenia, String contraseniaChk) throws MiExcepcion {
        
        Optional<Proveedor> proveedor = pR.findById(id);
        if (proveedor.isPresent()) {
            validarContraseniaProveedor(nuevaContrasenia, contraseniaChk);

            Proveedor proveedorAux = proveedor.get();
            proveedorAux.setContrasenia(new BCryptPasswordEncoder().encode(nuevaContrasenia));
            pR.save(proveedorAux);
        }
    }

    @Transactional
    public void bajaProveedor(String id) {
        Optional<Proveedor> proveedor = pR.findById(id);
        if (proveedor.isPresent()) {
            Proveedor proveedorAux = proveedor.get();
            proveedorAux.setAlta(false);
            pR.save(proveedorAux);
        }
    }

    @Transactional
    public Proveedor proveedorById(String id) {
        Optional<Proveedor> proveedor = pR.findById(id);
        Proveedor proveedorAux = new Proveedor();
        if (proveedor.isPresent()) {
            proveedorAux = proveedor.get();
        }
        return proveedorAux;
    }
    
    @Transactional(readOnly = true)
    public List<Proveedor> listarProveedores() {

        List<Proveedor> proveedores = new ArrayList();
        proveedores = pR.findAll();
        return proveedores;
    }

    private void validarDatosProveedor(String nombreApellido, String dni, String correo, String telefono, Integer numeroMatricula, String categoriaServicio, Double costoHora) throws MiExcepcion {

        if (nombreApellido.isEmpty() || nombreApellido == null) {
            throw new MiExcepcion("El nombre y apellido no pueden ser nulos o estar vacíos");
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

    private void validarContraseniaProveedor(String contrasenia, String contraseniaChk) throws MiExcepcion {

        if (contrasenia.isEmpty() || contrasenia == null || contrasenia.length() <= 8) {
            throw new MiExcepcion("La contraseña no puede estar vacía y debe tener al menos 8 caracteres");

        }
        if (!contrasenia.equals(contraseniaChk)) {
            throw new MiExcepcion("Las contraseñas ingresadas no coinciden");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Proveedor proveedor = (Proveedor) uR.buscarPorCorreo(username);
        
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

    public List<Proveedor> listaProveedoresOrdenados() {
        return pR.listarProveedoresPorCategoriaYNombre();
    }
    
    public List<Proveedor> buscarProveedoresPorCategoria(String categoriaServicio) throws MiExcepcion {
        if (categoriaServicio == null || categoriaServicio.isEmpty()) {
            throw new MiExcepcion("La categoría de servicio no puede estar vacía o ser nula");
        }
        return (List<Proveedor>) pR.buscarPorCategoria(categoriaServicio);
    }
    
}