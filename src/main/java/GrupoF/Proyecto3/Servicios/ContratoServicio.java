package GrupoF.Proyecto3.Servicios;

import GrupoF.Proyecto3.Entidades.Cliente;
import GrupoF.Proyecto3.Entidades.Contrato;
import GrupoF.Proyecto3.Entidades.Proveedor;
import GrupoF.Proyecto3.Enumeradores.NombreEstadoContrato;
import GrupoF.Proyecto3.Excepciones.MiExcepcion;
import GrupoF.Proyecto3.Repositorios.ClienteRepositorio;
import GrupoF.Proyecto3.Repositorios.ContratoRepositorio;
import GrupoF.Proyecto3.Repositorios.ProveedorRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContratoServicio {
    
    @Autowired
    private ContratoRepositorio coR;
    @Autowired
    private ClienteRepositorio cR;
    @Autowired
    private ProveedorRepositorio pR;
    
    @Autowired
    private ProveedorServicio pS;
    @Autowired
    private ClienteServicio cS;
    
    @Transactional
    public void registrarContrato(String idCliente, String idProveedor){
        
        Cliente cliente = cR.findById(idCliente).get();
        Proveedor proveedor = pR.findById(idProveedor).get();
                
        Contrato contrato = new Contrato();
        contrato.setCliente(cliente);
        contrato.setProveedor(proveedor);
        contrato.setFechaInicio(new Date());
        contrato.setEstadoContrato(NombreEstadoContrato.ESPERA);
        coR.save(contrato);
    }
    
    @Transactional
    public Contrato contratoById (String id){
        
        Optional<Contrato> contrato = coR.findById(id);
        Contrato contratoAux = new Contrato();
        
        if (contrato.isPresent()){
            contratoAux = contrato.get();    
        }
        return contratoAux;
        
    }
    
    public List<Contrato> listarContratos(){
        
        List<Contrato> contratos = new ArrayList();
        contratos = coR.findAll();
        return contratos;
        
    }
    
    public List<Contrato> listarContratosPorCliente (String idCliente){
        
        Cliente cliente = cS.clienteById(idCliente);
        List<Contrato> contratos = new ArrayList();
        contratos = coR.buscarPorIdCliente(cliente.getId());
        return contratos;
        
    }
    
    public List<Contrato> listarContratosPorProveedor (String idProveedor){
        
        Proveedor proveedor = pS.proveedorById(idProveedor);
        List<Contrato> contratos = new ArrayList();
        contratos = coR.buscarPorIdProveedor(proveedor.getId());
        return contratos;
        
    }
    
    @Transactional
    public void calificarProveedor (String idContrato, int calificacionProveedor, String comentarioProveedor) throws MiExcepcion{
        
        Optional<Contrato> respuesta = coR.findById(idContrato);
        
        validarDatos(calificacionProveedor, comentarioProveedor);
        
        if (respuesta.isPresent()){
            
            Contrato contrato = respuesta.get();
            
            contrato.setCalifProveedor(calificacionProveedor);
            contrato.setComentarioProveedor(comentarioProveedor);
            
            coR.save(contrato);
        }
                
    }
    
    private void validarDatos (int calificacionProveedor, String comentarioProveedor) throws MiExcepcion {
        if (calificacionProveedor < 1 || calificacionProveedor > 5) {
            throw new MiExcepcion("La calificadión debe ser mayor a 0 y menor a 5, o no puede estar vacio");
        }
        if (comentarioProveedor.isEmpty() || comentarioProveedor == null) {
            throw new MiExcepcion("El comentario no puede estar vacio");
        }
    }  
    
    public int calificacionPorProveedor (String idProveedor){
        Proveedor proveedor = pS.proveedorById(idProveedor);
        
        List<Contrato> contratos = new ArrayList();
        contratos = listarContratosPorProveedor(proveedor.getId());
        int totalCalificaciones = 0;
                
        for (Contrato contrato : contratos) {
            totalCalificaciones += contrato.getCalifProveedor();
        }
        
        int respuesta = Math.round(totalCalificaciones / contratos.size()); 
                      
        return (respuesta) ;
    }
    
    public int calificacionPorCliente (String idCliente){
        Cliente cliente = cS.clienteById(idCliente);
        
        List<Contrato> contratos = new ArrayList();
        contratos = listarContratosPorCliente(cliente.getId());
        int totalCalificaciones = 0;
                
        for (Contrato contrato : contratos) {
            totalCalificaciones += contrato.getCalifCliente();
        }
        
        int respuesta = Math.round(totalCalificaciones / contratos.size()); 
                      
        return (respuesta) ;
    }
    
    @Transactional
    public void aceptarContratoProveedor(String idContrato){
        
        Contrato contrato = coR.findById(idContrato).get();
        
        contrato.setEstadoContrato(NombreEstadoContrato.PROCESO);
        coR.save(contrato);
    } 
     
    @Transactional
    public void rechazarContratoProveedor(String idContrato){
        
        Contrato contrato = coR.findById(idContrato).get();
        
        contrato.setEstadoContrato(NombreEstadoContrato.RECHAZADO);
        coR.save(contrato);
    } 
    
    @Transactional
    public void finalizarContratoProveedor(String idContrato){
        
        Contrato contrato = coR.findById(idContrato).get();
        
        contrato.setEstadoContrato(NombreEstadoContrato.FINALIZADO);
        coR.save(contrato);
    } 
    
    @Transactional
    public void cancelarContratoCliente(String idContrato){
        
        Contrato contrato = coR.findById(idContrato).get();
        
        contrato.setEstadoContrato(NombreEstadoContrato.CANCELADO);
        coR.save(contrato);
    } 
    
    @Transactional
    public void finalizarContratoCliente(String idContrato){
        
        Contrato contrato = coR.findById(idContrato).get();
        
        contrato.setEstadoContrato(NombreEstadoContrato.FINALIZADO);
        coR.save(contrato);
    } 
}
