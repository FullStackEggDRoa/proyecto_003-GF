package GrupoF.Proyecto3.Controladores;


import GrupoF.Proyecto3.Entidades.Cliente;
import GrupoF.Proyecto3.Entidades.Proveedor;
import GrupoF.Proyecto3.Entidades.Usuario;
import GrupoF.Proyecto3.Enumeradores.NombreRol;
import GrupoF.Proyecto3.Excepciones.MiExcepcion;

import GrupoF.Proyecto3.Servicios.ClienteServicio;
import GrupoF.Proyecto3.Servicios.ProveedorServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class controladorInicio {
    
    @Autowired
    private ClienteServicio cS;
    @Autowired
    private ProveedorServicio pS;
    
    @GetMapping("/")
    public String Index(){
        return "index.html";
    }
    
     @GetMapping("/ingreso")
    public String login(@RequestParam(required = false) String error, ModelMap modelo ) {

        if (error != null) {
            modelo.put("notificacion", "Usuario o Contraseña invalidos");            
        }
        
        return "login.html";
    }
    
    @GetMapping("/registro")
    public String registro(@RequestParam(required = false) String error, @RequestParam String modo,ModelMap modelo ) {

       if (error != null) {
            modelo.put("notificacion", "Usuario o Contraseña invalidos!");
            
       }
       if(modo.equalsIgnoreCase("cliente")){ 
            return "registro-cliente.html";
       }else {
            return "registro-proveedor.html";
       }
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_ADM')")
    @GetMapping("/sesion")
    public String sesion(HttpSession session,ModelMap modelo) {
        
        Usuario sesionUsuario = (Usuario) session.getAttribute("usuariosession");
        
       if (sesionUsuario.getRol().toString().equals("ADM")) {
            
            List<Cliente> clientes = cS.listarClientes();
            modelo.addAttribute("clientes", clientes);
            return "admin.html";
        }
            
            List<Cliente> clientes = cS.listarClientes();
            modelo.addAttribute("clientes", clientes);
            return "sesion.html";
    }
    
    @PostMapping("/registrar_usuario")

    public String registrar(@RequestParam String nombreApellido, @RequestParam String contrasenia,@RequestParam String dni,@RequestParam String correo, @RequestParam String telefono,
            @RequestParam String contraseniaChk, @RequestParam String direccion, @RequestParam String numeroMatricula,
            @RequestParam String categoriaServicio, @RequestParam Double costoHora,@RequestParam String modo, ModelMap modelo) {
            
        try {
            if(modo.equalsIgnoreCase("cliente")){
                cS.registrarCliente(nombreApellido,contrasenia,dni,correo,telefono,direccion,contraseniaChk);
            }else{
                pS.registrarProveedor(nombreApellido, contrasenia, dni, correo,telefono, Integer.valueOf(numeroMatricula), categoriaServicio, costoHora, contraseniaChk);
            }

            modelo.put("notificacion", "Usuario registrado correctamente!");
            modelo.put("correo", correo);
            modelo.put("contrasenia", contrasenia);

            return "login.html";

        } catch (MiExcepcion ex) {

            modelo.put("notificacion", ex.getMessage());
            
            if(modo.equalsIgnoreCase("cliente")){
                return "registro-cliente.html";
            }else{
                return "registro-proveedor.html";
            }
            
        }

    }
    
    
    
}

