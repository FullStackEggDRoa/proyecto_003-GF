package GrupoF.Proyecto3.Controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorErrores implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView PaginaError(HttpServletRequest httpRequest) {

        ModelAndView paginaError = new ModelAndView("ERORR");
        String mensajeError = "";
        int codigoError = codiError(httpRequest);
        switch (codigoError) {
            case 400: {
                mensajeError = "RECURSO NO ENCONTRADO.";
                break;
            }
            case 401: {
                mensajeError = "NO SE ENCUENTRA AUTORIZADO.";
                break;
            }
            case 403: {
                mensajeError = "NO TIENE PERMISO, PARA ACCEDER AL RECURSO.";
                break;
            }
            case 404: {
                mensajeError = "EL RECURSO SOLICITADO NO FUE ENCONTRADO.";
                break;
            }
            case 500: {
                mensajeError = "OCURRIO UN ERROR INTERNO.";
                break;
            }
        }
        //DESCOMENTAR AL CREAR LA VISTA DE ERROR, EN EL FRONT.
        //errorPage.addObject("codigo", httpErrorCode);
        //errorPage.addObject("mensaje", mensajeError);
        return paginaError;
        
    }

    private int codiError(HttpServletRequest httpRequest) {

        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

    public String getErrorPath() {

        return "/error.htp";

    }

}
