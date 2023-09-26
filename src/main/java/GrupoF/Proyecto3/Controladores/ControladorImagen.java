package GrupoF.Proyecto3.Controladores;

import GrupoF.Proyecto3.Entidades.Cliente;
import GrupoF.Proyecto3.Entidades.Proveedor;
import GrupoF.Proyecto3.Servicios.ClienteServicio;
import GrupoF.Proyecto3.Servicios.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/imagenes")
public class ControladorImagen {

    @Autowired
    ClienteServicio cS;

    @Autowired
    ProveedorServicio pS;

    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id, @RequestParam String modo) {
//        
//        byte[] imagen = null;
//
//        if (modo.equalsIgnoreCase("cliente")) {
//            Cliente cliente = cS.clienteById(id);
//            
//            imagen = cliente.getImagen().getContenido();
//        }
//
//        if (modo.equalsIgnoreCase("proveedor")) {
//            Proveedor proveedor = pS.proveedorById(id);
//            
//            imagen = proveedor.getImagen().getContenido();
//            
//        }
//        
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG);
//        
//        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
//        
//    }
//}

    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {

        Cliente cliente = cS.clienteById(id);
        Proveedor proveedor = pS.proveedorById(id);
        byte[] imagen = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        if (cliente != null) {
            imagen = cliente.getImagen().getContenido();
        } else if (proveedor != null) {
            imagen = proveedor.getImagen().getContenido();
        }
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
}
