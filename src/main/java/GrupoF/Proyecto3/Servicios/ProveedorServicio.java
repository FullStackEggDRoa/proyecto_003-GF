
package GrupoF.Proyecto3.Servicios;

import GrupoF.Proyecto3.Entidad.proveedor;
import GrupoF.Proyecto3.Repositorios.ProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorServicio {
    
    @Autowired
    private ProveedorRepositorio pr;
    
    @Transactional
    public void registrarProveedor (String nombreApellido, String contrasenia, Integer dni, String correo, Integer telefono, Integer numeroMatricula, String categoriaServicio, Double costoHora){
        
        validarP(nombreApellido, contrasenia, dni, correo, telefono, numeroMatricula, categoriaServicio, costoHora);
             
        if (pr.findByCorreo(correo) != null){
            throw new Exception("Ya existe un usuario registrado con este correo electrónico.");
        }
        
        Proveedor proveedor = new Proveedor();
        
        proveedor.setNombreApellido(nombreApellido);
        proveedor.setContrasenia(contrasenia);
        proveedor.setDni(new Dni('x',dni.toString())); 
        proveedor.setCorreo(correo);
        proveedor.setTelefono(telefono);
        proveedor.setnumeroMatricula(numeroMatricula);
        proveedor.setCategoriaMatricula(categoriaMatricula);
        proveedor.setCostoHora(costaHora);
        proveedor.serAlta(true);
        proveedor.setRol(Rol.USUARIO);
        
        pr.save(proveedor);
              
    }
    @Transactional
    private void validarP(String nombreApellido, String contrasenia, Integer dni, String correo, Integer telefono, Integer numeroMatricula, String categoriaServicio, Double costoHora) throws Exception{

        if (nombreApellido.isEmpty() || nombreApellido == null) {
            throw new Exception("El nombre y apellido no pueden ser nulos o estar vacíos");
        }
        if (contrasenia.isEmpty() || contrasenia == null || contrasenia.length() <= 8) {
            throw new Exception("La contraseña no puede estar vacía, y tener más de 8 caracteres");
        }
           if (dni == -1 || dni == null) {
            throw new Exception("El DNI no puede ser nulo o estar vacio");
        }
         if (correo.isEmpty() || correo == null) {
            throw new Exception("El correo no puede ser nulo o estar vacio");
        }
        if (telefono == -1 || telefono == null) {
            throw new Exception("El telefono no puede ser nulo o estar vacio");
        }
        if (numeroMatricula == -1 || numeroMatricula == null) {
            throw new Exception("El número de matrícula no puede ser nulo o estar vacio");
        }
        if (categoriaServicio.isEmpty() || categoriaServicio == null) {
            throw new Exception("La categoria de servicio no puede ser nula o estar vacia");
        }
        if (costoHora == -1 || costoHora == null) {
            throw new Exception("El costo no puede ser nulo o estar vacio");
        }
     
    }
    
}
}
