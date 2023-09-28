package GrupoF.Proyecto3.Controladores;

import GrupoF.Proyecto3.Excepciones.MiExcepcion;
import GrupoF.Proyecto3.Servicios.ContratoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public  String registrarContrato(@RequestParam String idCliente, @RequestParam String idProveedor, ModelMap modelo) {
        try {
            coS.registrarContrato(idCliente, idProveedor);
            modelo.put("exito","Contrato registrado exitosamente.");
        } catch (MiExcepcion e) {
            modelo.put("error", "Error al registrar el contrato.");
        }
        return ("sesion-cliente.html");
    }
    
}
