package GrupoF.Proyecto3.Controladores;

import GrupoF.Proyecto3.Entidades.Contrato;
import GrupoF.Proyecto3.Entidades.Usuario;
import GrupoF.Proyecto3.Excepciones.MiExcepcion;
import GrupoF.Proyecto3.Servicios.ContratoServicio;
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
@RequestMapping("/contratos")
public class ControladorContrato {

    @Autowired
    private ContratoServicio coS;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @PostMapping("/contratar")
    public String registrarContrato(@RequestParam String idCliente, @RequestParam String idProveedor, ModelMap modelo) {
        try {
            coS.registrarContrato(idCliente, idProveedor);
            modelo.put("exito", "Contrato registrado exitosamente.");
        } catch (MiExcepcion e) {
            modelo.put("error", "Error al registrar el contrato.");
        }
        return ("sesion-cliente.html");
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @PostMapping("/listaContratos")
    public List listarContratos(HttpSession session, @RequestParam String modo) throws MiExcepcion {
        Usuario sesionUsuario = (Usuario) session.getAttribute("usuariosession");
        if (modo.equalsIgnoreCase("cliente")) {
            String idCliente = sesionUsuario.getId();
            List<Contrato> contratos = coS.listarContratosPorCliente(idCliente);
            return contratos;
        } else {
            String idProveedor = sesionUsuario.getId();
            List<Contrato> contratos = coS.listarContratosPorProveedor(idProveedor);
            return contratos;
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @PostMapping("/modificarEstado")
    public String modificarEstado(HttpSession session, @RequestParam String modo, @RequestParam String idContrato, @RequestParam String nuevoEstado, ModelMap modelo) throws MiExcepcion {
        if (modo.equalsIgnoreCase("cliente")) {
            coS.editarEstadoContratoCliente(idContrato, nuevoEstado);
            modelo.put("notificacion", "Se ha cambiado el estado del contrato exitosamente.");
            return "sesion-cliente.html";
        } else if (modo.equalsIgnoreCase("proveedor")) {
            coS.editarEstadoContratoProveedor(idContrato, nuevoEstado);
            modelo.put("notificacion", "Se ha cambiado el estado del contrato exitosamente.");
            return "sesion-proveedor.html";
        } 
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @PostMapping("/calificar")
    public String calificar(HttpSession session, @RequestParam String modo, @RequestParam String idContrato, @RequestParam int calificacion, @RequestParam String comentario, ModelMap modelo) throws MiExcepcion {
        if (modo.equalsIgnoreCase("cliente")) {
            coS.calificarProveedor(idContrato, calificacion, comentario);
            modelo.put("notificacion", "Se ha calificado al Proveedor exitosamente.");
            return "sesion-cliente.html";
        } else if (modo.equalsIgnoreCase("proveedor")) {
            coS.calificarCliente(idContrato, calificacion, comentario);
            modelo.put("notificacion", "Se ha calificado al Cliente exitosamente.");
            return "sesion-proveedor.html";
        } 
        return null;
    }

}
