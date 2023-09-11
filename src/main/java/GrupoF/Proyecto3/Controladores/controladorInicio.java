/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GrupoF.Proyecto3.Controladores;


import GrupoF.Proyecto3.Servicios.ClienteServicio;
import GrupoF.Proyecto3.Servicios.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author droa
 */
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
    public String registro(@RequestParam(required = false) String error, ModelMap modelo ) {

        if (error != null) {
            modelo.put("notificacion", "Usuario o Contraseña invalidos!");
            
        }
        
        return "registro-usuario.html";
    }
    @PostMapping("/registrar_cliente")
    public String registrarCliente(@RequestParam String nombreApellido, @RequestParam String contrasenia,@RequestParam Integer dni,@RequestParam String correo, @RequestParam Integer telefono,
            @RequestParam String contraseniaChk, @RequestParam String direccion, ModelMap modelo) {

        try {
//            cS.registrarCliente(nombreApellido,contrasenia,dni,correo,telefono,direccion);

            modelo.put("notificacion", "Usuario registrado correctamente!");
            modelo.put("correo",correo);
            modelo.put("contrasenia", contrasenia);
            
            return "login.html";
            
        } catch (Exception ex) {

            modelo.put("notificacion", ex.getMessage());
            
            return "registro_usuario.html";
        }

    }
    
}

