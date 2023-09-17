package GrupoF.Proyecto3.Controladores;

import GrupoF.Proyecto3.Entidades.Cliente;
import GrupoF.Proyecto3.Entidades.Proveedor;
import GrupoF.Proyecto3.Entidades.Usuario;
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
@RequestMapping("/usuario")
public class ControladorUsuario {

    @Autowired
    private ClienteServicio cS;
    @Autowired
    private ProveedorServicio pS;
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_ADM')")
    @GetMapping("/sesion")
    public String sesion(HttpSession session,@RequestParam String modo, ModelMap modelo) {
        
        Usuario sesionUsuario = (Usuario) session.getAttribute("usuariosession");
        
        if (sesionUsuario.getRol().toString().equals("ADM")) {
            
            List<Cliente> clientes = cS.listarClientes();
            List<Proveedor> proveedores = pS.listarProveedores();
            modelo.addAttribute("clientes", clientes);
            modelo.addAttribute("proveedores", proveedores);
            return "sesion-admin.html";
            
        }else if(modo.equalsIgnoreCase("cliente")){
            
            //Cliente cliente = cS.;
            //modelo.addAttribute("clientes", clientes);
            return "sesion-cliente.html";
        
        }else{
            
            List<Proveedor> proveedores = pS.listarProveedores();
            modelo.addAttribute("proveedores", proveedores);
            return "sesion-proveedor.html";
        }
    }
    
    
    @GetMapping("/registro")
    public String registro(){
        return "registro-usuario.html";
    }
    
    @PostMapping("/registrar")
    public String registrar(@RequestParam String nombreApellido, @RequestParam String contraseña, @RequestParam String dni,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String direccion, ModelMap modelo){
        
        try {
            cS.registrarCliente(nombreApellido, contraseña, dni, correo, telefono, direccion);
            modelo.put("exito","El usuario se grabo correctamente");
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "registro-usuario.html";
        }
        
        
        return ("index.html");
    }
    
    //metodo para listar
    
    //metodos GET y POST para modificar
    
}
