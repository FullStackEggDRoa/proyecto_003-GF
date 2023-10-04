package GrupoF.Proyecto3.Controladores;

import GrupoF.Proyecto3.Entidades.Cliente;
import GrupoF.Proyecto3.Entidades.Proveedor;
import GrupoF.Proyecto3.Entidades.Usuario;
import GrupoF.Proyecto3.Excepciones.MiExcepcion;
import GrupoF.Proyecto3.Servicios.ClienteServicio;
import GrupoF.Proyecto3.Servicios.ProveedorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class ControladorUsuario {

    @Autowired
    private ClienteServicio cS;
    @Autowired
    private ProveedorServicio pS;
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_ADM')")
    @GetMapping("/sesion")
public String sesion(HttpSession session, @RequestParam (name = "categoriaServicio", defaultValue = "-") String categoriaServicio,@RequestParam (name = "contenido", defaultValue = "1") String contenido, ModelMap modelo) throws MiExcepcion {        
        Usuario sesionUsuario = (Usuario) session.getAttribute("usuariosession");
        
        if (sesionUsuario.getRol().toString().equals("ADM")) {
            String nombrePerfil = null;
            List<Cliente> clientes = cS.listarClientes();
            List<Proveedor> proveedores = pS.listarProveedores();
            if(sesionUsuario.getClass().getName().contains("Cliente")){
                nombrePerfil = sesionUsuario.getNombreApellido();
            }else if(sesionUsuario.getClass().getName().contains("Proveedor")){
                nombrePerfil = sesionUsuario.getNombreApellido();
            }
            modelo.addAttribute("clientes", clientes);
            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("nombrePerfil",  nombrePerfil);
            return "sesion-admin.html";
            
        }else if(sesionUsuario.getClass().getName().contains("Cliente")){
            
            String idCliente = sesionUsuario.getId();
            String nombrePerfil = sesionUsuario.getNombreApellido();
            List<Proveedor> proveedores = pS.buscarProveedoresPorCategoria(categoriaServicio);
            modelo.addAttribute("idCliente", idCliente);
            modelo.addAttribute("nombrePerfil", nombrePerfil);
            modelo.addAttribute("proveedores", proveedores);
            modelo.put("modo", "cliente");
            modelo.put("contenido", contenido);
            return "sesion-cliente.html";
        
        }else{
            String idProveedor = sesionUsuario.getId();
            String nombrePerfil = sesionUsuario.getNombreApellido();
            Proveedor proveedor = pS.proveedorById(idProveedor);
            List<Proveedor> proveedores = pS.listarProveedores();
            modelo.addAttribute("Proveedor", proveedor);
            modelo.addAttribute("idProveedor", idProveedor);
            modelo.addAttribute("nombrePerfil",  nombrePerfil);
            modelo.put("modo", "proveedor");
            return "sesion-proveedor.html";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_ADM')")
    @GetMapping("/modificacion")
    public String modificacion(HttpSession session,@RequestParam String modo, ModelMap modelo){
        Usuario sesionUsuario = (Usuario) session.getAttribute("usuariosession");
        if (modo.equalsIgnoreCase("cliente")) {
            String idCliente = sesionUsuario.getId();
            String nombrePerfil = sesionUsuario.getNombreApellido();
            Cliente cliente = cS.clienteById(idCliente);
            modelo.put("modo", "cliente");
            modelo.addAttribute("Cliente", cliente);
            modelo.addAttribute("idCliente", idCliente);
            modelo.addAttribute("nombrePerfil", nombrePerfil);
            return "modificar-cliente.html";
            
        } else {
            String idProveedor = sesionUsuario.getId();
            String nombrePerfil = sesionUsuario.getNombreApellido();
            Proveedor proveedor = pS.proveedorById(idProveedor);
            modelo.put("modo", "proveedor");
            modelo.addAttribute("Proveedor", proveedor);
            modelo.addAttribute("idProveedor", idProveedor);
            modelo.addAttribute("nombrePerfil", nombrePerfil);
            return "modificar-proveedor.html";
            
        }        
    }
    
    @PostMapping("/modificar")
    public String modificar(MultipartFile archivo, @RequestParam String Id, @RequestParam String nombreApellido, @RequestParam String contrasenia,@RequestParam String dni,@RequestParam String correo, @RequestParam String telefono,
            @RequestParam String contraseniaChk, @RequestParam String direccion, @RequestParam String numeroMatricula,
            @RequestParam String categoriaServicio, @RequestParam Double costoHora,@RequestParam String modo, ModelMap modelo)
    {
        try {
            if (modo.equalsIgnoreCase("cliente")) {
                cS.actualizarCliente(archivo, Id, nombreApellido, contrasenia, dni, correo, telefono, direccion, contraseniaChk);
                modelo.put("notificacion", "Datos de usuario actualizados correctamente!");
                modelo.put("modo", "cliente");
                return "redirect:/usuario/sesion";
            }
            else if (modo.equalsIgnoreCase("proveedor")){
                pS.actualizarProveedor(archivo, Id, nombreApellido, contrasenia, dni, correo, telefono, Integer.valueOf(numeroMatricula), categoriaServicio, costoHora, contraseniaChk);
                modelo.put("notificacion", "Datos de usuario actualizados correctamente!");
                modelo.put("modo", "proveedor");
                return "redirect:/usuario/sesion";
            }else {
                
                return "sesion-admin.html";                
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
