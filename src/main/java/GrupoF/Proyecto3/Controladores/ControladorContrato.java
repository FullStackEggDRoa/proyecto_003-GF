package GrupoF.Proyecto3.Controladores;

import GrupoF.Proyecto3.Servicios.ClienteServicio;
import GrupoF.Proyecto3.Servicios.ContratoServicio;
import GrupoF.Proyecto3.Servicios.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contratos")
public class ControladorContrato {
    
    /*@Autowired
    private ProveedorServicio pS;
    @Autowired
    private ClienteServicio cS;*/
    @Autowired
    private ContratoServicio coS;
        
    @PostMapping("/contratar")
    public  String registrarContrato(@RequestParam String idCliente, @RequestParam String idProveedor, ModelMap modelo) {
        try {
            coS.registrarContrato(idCliente, idProveedor);
            modelo.put("exito","Contrato registrado exitosamente.");
        } catch (Exception e) {
            modelo.put("error", "Error al registrar el contrato.");
        }
        return ("sesion-cliente.html");
    }
    
}
