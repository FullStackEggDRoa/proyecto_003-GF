package GrupoF.Proyecto3.Controladores;

import GrupoF.Proyecto3.Entidades.Contrato;
import GrupoF.Proyecto3.Entidades.Proveedor;
import GrupoF.Proyecto3.Entidades.Usuario;
import GrupoF.Proyecto3.Excepciones.MiExcepcion;
import GrupoF.Proyecto3.Servicios.ContratoServicio;
import GrupoF.Proyecto3.Servicios.ProveedorServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@RequestMapping("/contratos")
public class ControladorContrato {

    @Autowired

    private ContratoServicio coS;  
    @Autowired
    private ProveedorServicio pS;
    

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @GetMapping("/contratar")
    public String registrarContrato(HttpSession session, @RequestParam (name = "categoriaServicio", defaultValue = "Gas") String categoriaServicio, @RequestParam String idProveedor, @RequestParam (name = "contenido", defaultValue = "1") String contenido, ModelMap modelo) {
       
        Usuario sesionUsuario = (Usuario) session.getAttribute("usuariosession");
        
        String idCliente = sesionUsuario.getId();
        List<Proveedor> proveedores = new ArrayList <>();

        try {
            
            coS.registrarContrato(idCliente, idProveedor);
            
            String nombrePerfil = sesionUsuario.getNombreApellido();
            proveedores = pS.buscarProveedoresPorCategoria(categoriaServicio);
            
            modelo.put("notificacion", "Contrato registrado exitosamente.");
            modelo.addAttribute("idCliente", idCliente);
            modelo.addAttribute("nombrePerfil", nombrePerfil);
            modelo.addAttribute("proveedores", proveedores);
            modelo.put("modo", "cliente");
            modelo.put("contenido", contenido);
            
            return ("sesion-cliente.html");
            
        } catch (MiExcepcion e) {
            
            modelo.put("error", "Error al registrar el contrato.");
            
            String nombrePerfil = sesionUsuario.getNombreApellido();
            
            modelo.put("notificacion", "Contrato registrado exitosamente.");
            modelo.addAttribute("idCliente", idCliente);
            modelo.addAttribute("nombrePerfil", nombrePerfil);
            modelo.addAttribute("proveedores", proveedores);
            modelo.put("modo", "cliente");
            modelo.put("contenido", contenido);
            
            return ("sesion-cliente.html");
        }
        
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @PostMapping("/listaContratos")
    public String listarContratos(HttpSession session, @RequestParam String modo, ModelMap modelo) throws MiExcepcion {
        Usuario sesionUsuario = (Usuario) session.getAttribute("usuariosession");
        if (modo.equalsIgnoreCase("cliente")) {
            String idCliente = sesionUsuario.getId();
            List<Contrato> contratos = coS.listarContratosPorCliente(idCliente);
            modelo.addAttribute("contratos", contratos);
            return "sesion-cliente.html";
        } else {
            String idProveedor = sesionUsuario.getId();
            List<Contrato> contratos = coS.listarContratosPorProveedor(idProveedor);
            modelo.addAttribute("contratos", contratos);
            return "sesion-proveedor.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @PostMapping("/modificarEstado")
    public String modificarEstado(HttpSession session, @RequestParam String modo, @RequestParam String idContrato, @RequestParam String nuevoEstado, ModelMap modelo) throws MiExcepcion {
        if (modo.equalsIgnoreCase("cliente")) {
            coS.editarEstadoContratoCliente(idContrato, nuevoEstado);
            modelo.put("notificacion", "Se ha cambiado el estado del contrato exitosamente.");
            return "sesion-cliente.html";
        } else {
            coS.editarEstadoContratoProveedor(idContrato, nuevoEstado);
            modelo.put("notificacion", "Se ha cambiado el estado del contrato exitosamente.");
            return "sesion-proveedor.html";
        } 
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @PostMapping("/calificar")
    public String calificar(HttpSession session, @RequestParam String modo, @RequestParam String idContrato, @RequestParam int calificacion, @RequestParam String comentario, ModelMap modelo) throws MiExcepcion {
        if (modo.equalsIgnoreCase("cliente")) {
            coS.calificarProveedor(idContrato, calificacion, comentario);
            modelo.put("notificacion", "Se ha calificado al Proveedor exitosamente.");
            return "sesion-cliente.html";
        } else {
            coS.calificarCliente(idContrato, calificacion, comentario);
            modelo.put("notificacion", "Se ha calificado al Cliente exitosamente.");
            return "sesion-proveedor.html";
        } 
    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @PostMapping("/eliminarComentarioCliente")
    public String eliminarComentarioCliente(@RequestParam String idContrato, ModelMap modelo) throws MiExcepcion {
        coS.eliminarComentarioCliente(idContrato);
        modelo.put("notificacion", "El mensaje ha sido eliminado exitosamente.");
        return "admin.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @PostMapping("/eliminarComentarioProveedor")
    public String eliminarComentarioProveedor(@RequestParam String idContrato, ModelMap modelo) throws MiExcepcion {
        coS.eliminarComentarioProveedor(idContrato);
        modelo.put("notificacion", "El mensaje ha sido eliminado exitosamente.");
        return "admin.html";
    }
    
}
