package GrupoF.Proyecto3.Controladores;

import GrupoF.Proyecto3.Entidades.Cliente;
import GrupoF.Proyecto3.Entidades.Proveedor;
import GrupoF.Proyecto3.Entidades.Usuario;
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
@RequestMapping("/usuario")
public class ControladorUsuario {

    @Autowired
    private ClienteServicio cS;
    @Autowired
    private ProveedorServicio pS;
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_ADM')")
    @GetMapping("/sesion")
    public String sesion(HttpSession session, ModelMap modelo) {
        
        Usuario sesionUsuario = (Usuario) session.getAttribute("usuariosession");
        
        if (sesionUsuario.getRol().toString().equals("ADM")) {
            
            List<Cliente> clientes = cS.listarClientes();
            List<Proveedor> proveedores = pS.listarProveedores();
            modelo.addAttribute("clientes", clientes);
            modelo.addAttribute("proveedores", proveedores);
            return "sesion-admin.html";
            
        }else if(sesionUsuario.getClass().getName().contains("Cliente")){
            
            String idCliente = sesionUsuario.getId();
            modelo.addAttribute("idCliente", idCliente);
            return "sesion-cliente.html";
        
        }else{
            String idProveedor = sesionUsuario.getId();
            List<Proveedor> proveedores = pS.listarProveedores();
            modelo.addAttribute("idProveedor", idProveedor);
            return "sesion-proveedor.html";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_ADM')")
    @GetMapping("/modificacion")
    public String registro(HttpSession session, @RequestParam String modo, ModelMap modelo){
        Usuario sesionUsuario = (Usuario) session.getAttribute("usuariosession");
        if (modo.equalsIgnoreCase("cliente")) {
            String idCliente = sesionUsuario.getId();
            Cliente cliente = cS.clienteById(idCliente);
            modelo.addAttribute("Cliente", cliente);
            return "modificar-cliente.html";
        } else {
            String idProveedor = sesionUsuario.getId();
            Proveedor proveedor = pS.proveedorById(idProveedor);
            modelo.addAttribute("Proveedor", proveedor);
            return "modificar-proveedor.html";
        }        
    }
    
    @PostMapping("/modificar")
    public String modificar(@RequestParam String Id, @RequestParam String nombreApellido, @RequestParam String contrasenia,@RequestParam String dni,@RequestParam String correo, @RequestParam String telefono,
            @RequestParam String contraseniaChk, @RequestParam String direccion, @RequestParam String numeroMatricula,
            @RequestParam String categoriaServicio, @RequestParam Double costoHora,@RequestParam String modo, ModelMap modelo)
    {
        try {
            if (modo.equalsIgnoreCase("cliente")) {
                cS.actualizarCliente(Id, nombreApellido, contrasenia, dni, correo, telefono, direccion);
                modelo.put("notificacion", "Datos de usuario actualizados correctamente!");
                return "sesion-cliente.html";
            }
            else{
                pS.actualizarProveedor(Id, nombreApellido, contrasenia, dni, correo, telefono, Integer.valueOf(numeroMatricula), categoriaServicio, costoHora);
                modelo.put("notificacion", "Datos de usuario actualizados correctamente!");
                return "sesion-proveedor.html";
            }
           
        } catch (MiExcepcion ex) {
            if (modo.equalsIgnoreCase("cliente")) {
                return "modificar-cliente.html";
            } else {
                return "modificar-proveedor.html";
            }
        }
        
        
    }

    
}
