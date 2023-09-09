/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GrupoF.Proyecto3.Controladores;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

