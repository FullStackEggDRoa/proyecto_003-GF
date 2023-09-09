/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GrupoF.Proyecto3.Controladores;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author droa
 */
@Controller
@RequestMapping("/")
public class controladorInicio {
   
    
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
    
}

