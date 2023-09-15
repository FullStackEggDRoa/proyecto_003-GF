package GrupoF.Proyecto3.Controladores;

import GrupoF.Proyecto3.Servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ClienteServicio us;
    
    @GetMapping("/registro")
    public String registro(){
        return "registro-usuario.html";
    }
    
    @PostMapping("/registrar")
    public String registrar(@RequestParam String nombreApellido, @RequestParam String contraseña, @RequestParam String dni,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String direccion, ModelMap modelo){
        
        try {
            us.registrarCliente(nombreApellido, contraseña, dni, correo, telefono, direccion);
            modelo.put("exito","El usuario se grabo correctamente");
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "registro-usuario.html";
        }
        
        
        return ("index.html");
    }
    
}
