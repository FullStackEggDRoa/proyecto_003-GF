
package GrupoF.Proyecto3.Servicios;

import GrupoF.Proyecto3.entidad.Cliente;
import GrupoF.Proyecto3.Repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServicio {
    
    @Autowired
    private ClienteRepositorio cr;
    
    @Transactional
    public void registrarCliente (String nombreApellido, String contrasenia, Integer dni, String correo, Integer telefono, String direccion){
        
        validarC(nombreApellido, contrasenia, correo, direccion);
             
        if (cr.findByCorreo(correo) != null){
            throw new Exception("Ya existe un usuario registrado con este correo electrónico.");
        }
        
        Cliente cliente = new Cliente();
                
        cliente.setNombreApellido(nombreApellido);
        cliente.setContrasenia(contrasenia);
        cliente.setDni(new Dni('x',dni.toString()));     
        cliente.setCorreo(correo);
        cliente.setTelefono(telefono);
        cliente.setDireccion(direccion);
        cliente.serAlta(true);
        cliente.setRol(Rol.USUARIO);
        
        cr.save(cliente);
              
    }
    
    private void validarC(String nombreApellido, String contrasenia, String correo, String direccion) throws Exception{

        if (nombreApellido.isEmpty() || nombreApellido == null) {
            throw new Exception("El nombre y apellido no pueden ser nulos o estar vacíos");
        }
        if (contrasenia.isEmpty() || contrasenia == null || contrasenia.length() <= 8) {
            throw new Exception("La contraseña no puede estar vacía, y tener más de 8 caracteres");
        }
        if (correo.isEmpty() || correo == null) {
            throw new Exception("El correo no puede ser nulo o estar vacio");
        }
        if (direccion.isEmpty() || direccion == null) {
            throw new Exception("La direccion no puede ser nula o estar vacia");
        }
     
    }
    
}
