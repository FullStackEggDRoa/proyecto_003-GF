package GrupoF.Proyecto3.Controladores;


import GrupoF.Proyecto3.Servicios.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/proveedor")
public class ControladorProveedor {

    @Autowired
    private ProveedorServicio ps;
    
    @GetMapping("/registro")
    public String registro(){
        return "registro-proveedor.html";
    }
    
    @PostMapping("/registrar")
    public String registrar(@RequestParam String nombreApellido, @RequestParam String contraseña, @RequestParam(required=false) String dni,
            @RequestParam String correo, @RequestParam(required=false) String telefono, @RequestParam(required=false) String numeroMatricula,
            @RequestParam String categoriaServicio, @RequestParam(required=false) String costoHora, ModelMap modelo){
        
        try {
           //ps.registrarProveedor(nombreApellido, contraseña, dni, correo, telefono, numeroMatricula, categoriaServicio, costoHora);
           modelo.put("exito","el proveedor fue registrado correctamente");
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "registro-proveedor.html";
        }
        
        return ("index.html");
    }
    
    //metodo para listar
    
    //metodo  GET y POST para modificar
    
}
