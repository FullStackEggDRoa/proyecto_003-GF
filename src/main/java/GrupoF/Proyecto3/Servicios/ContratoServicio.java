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
    public void registrarContrato(String idCliente, String idProveedor) throws MiExcepcion {

        Cliente cliente = cR.findById(idCliente).get();
        Proveedor proveedor = pR.findById(idProveedor).get();

        Contrato contrato = new Contrato();
        contrato.setCliente(cliente);
        contrato.setProveedor(proveedor);
        contrato.setFechaInicio(new Date());
        contrato.setEstadoContrato(NombreEstadoContrato.ESPERA);
        coR.save(contrato);
    }

    public Contrato contratoById(String id) throws MiExcepcion {

        Optional<Contrato> contrato = coR.findById(id);
        Contrato contratoAux = new Contrato();

        if (contrato.isPresent()) {
            contratoAux = contrato.get();
        }
        return contratoAux;

    }

    public List<Contrato> listarContratos() throws MiExcepcion { 

        List<Contrato> contratos = new ArrayList();
        contratos = coR.findAll();
        return contratos;

    }

    public List<Contrato> listarContratosPorCliente(String idCliente) throws MiExcepcion { 

        Cliente cliente = cS.clienteById(idCliente);
        List<Contrato> contratos = new ArrayList();
        contratos = coR.buscarPorIdCliente(cliente.getId());
        return contratos;

    }

    public List<Contrato> listarContratosPorProveedor(String idProveedor)throws MiExcepcion { 

        Proveedor proveedor = pS.proveedorById(idProveedor);
        List<Contrato> contratos = new ArrayList();
        contratos = coR.buscarPorIdProveedor(proveedor.getId());
        return contratos;

    }

    @Transactional
    public void calificarProveedor(String idContrato, int calificacionProveedor, String comentarioProveedor) throws MiExcepcion {

        validarDatos(calificacionProveedor, comentarioProveedor);

        Contrato contrato = contratoById(idContrato);

        contrato.setCalifProveedor(calificacionProveedor);
        contrato.setComentarioProveedor(comentarioProveedor);

        coR.save(contrato);
    }

    @Transactional
    public void calificarCliente(String idContrato, int calificacionCliente, String comentarioCliente) throws MiExcepcion {

        validarDatos(calificacionCliente, comentarioCliente);
        
        Contrato contrato = contratoById(idContrato);

        contrato.setCalifCliente(calificacionCliente);
        contrato.setComentarioCliente(comentarioCliente);

        coR.save(contrato);

    }
    
    @Transactional
    public void eliminarComentarioProveedor(String idContrato) throws MiExcepcion {

        Contrato contrato = contratoById(idContrato);

        contrato.setComentarioProveedor("Eliminado por el Administrador, comentario ofensivo.");

        coR.save(contrato);

    }
    
    @Transactional
    public void eliminarComentarioCliente(String idContrato) throws MiExcepcion {

        Contrato contrato = contratoById(idContrato);

        contrato.setComentarioCliente("Eliminado por el Administrador, comentario ofensivo.");

        coR.save(contrato);

    }

    private void validarDatos(int calificacion, String comentario) throws MiExcepcion {
        if (calificacion < 1 || calificacion > 5) {
            throw new MiExcepcion("La calificadión debe ser mayor a 0 y menor a 5, o no puede estar vacio");
        }
        if (comentario.isEmpty()) {
            throw new MiExcepcion("El comentario no puede estar vacio");
        }
    }

    public int calificacionPorProveedor(String idProveedor) throws MiExcepcion {
        Proveedor proveedor = pS.proveedorById(idProveedor);

        List<Contrato> contratos = new ArrayList();
        contratos = listarContratosPorProveedor(proveedor.getId());
        int totalCalificaciones = 0;

        for (Contrato contrato : contratos) {
            totalCalificaciones += contrato.getCalifProveedor();
        }

        int respuesta = Math.round(totalCalificaciones / contratos.size());

        return (respuesta);
    }

    public int calificacionPorCliente(String idCliente) throws MiExcepcion {
        Cliente cliente = cS.clienteById(idCliente);

        List<Contrato> contratos = new ArrayList();
        contratos = listarContratosPorCliente(cliente.getId());
        int totalCalificaciones = 0;

        for (Contrato contrato : contratos) {
            totalCalificaciones += contrato.getCalifCliente();
        }

        int respuesta = Math.round(totalCalificaciones / contratos.size());

        return (respuesta);
    }

    @Transactional
    public void aceptarContratoProveedor(String idContrato) throws MiExcepcion {

        Contrato contrato = contratoById(idContrato);
        
        if (contrato.getEstadoContrato() == NombreEstadoContrato.ESPERA){
            contrato.setEstadoContrato(NombreEstadoContrato.PROCESO);
            coR.save(contrato);
        } else {
            throw new MiExcepcion("Para poder aceptar un contrato, éste debería ser solicitado");
        }
        
    }

    @Transactional
    public void rechazarContratoProveedor(String idContrato) throws MiExcepcion {

        Contrato contrato = contratoById(idContrato);
        
        if (contrato.getEstadoContrato() == NombreEstadoContrato.ESPERA){
            contrato.setEstadoContrato(NombreEstadoContrato.RECHAZADO);
            coR.save(contrato);
        } else {
            throw new MiExcepcion("Para poder rechazar un contrato, éste debería ser solicitado");
        }
        
    }

    @Transactional
    public void finalizarContratoProveedor(String idContrato) throws MiExcepcion {

        Contrato contrato = contratoById(idContrato);
        
        if (contrato.getEstadoContrato() == NombreEstadoContrato.PROCESO){
            contrato.setEstadoContrato(NombreEstadoContrato.FINALIZADO);
            coR.save(contrato);
        } else {
            throw new MiExcepcion("Para poder finalizar un contrato, éste debería haber sido aceptado y estar en proceso");
        }
        
    }

    @Transactional
    public void cancelarContratoCliente(String idContrato) throws MiExcepcion {

        Contrato contrato = contratoById(idContrato);
        
        if (contrato.getEstadoContrato() == NombreEstadoContrato.ESPERA || contrato.getEstadoContrato() == NombreEstadoContrato.PROCESO){
            contrato.setEstadoContrato(NombreEstadoContrato.CANCELADO);
            coR.save(contrato);
        } else {
            throw new MiExcepcion("Para poder cancelar un contrato, éste debería ser solicitado");

        }
        
    }

    @Transactional
    public void finalizarContratoCliente(String idContrato) throws MiExcepcion {

        Contrato contrato = contratoById(idContrato);
        
        if (contrato.getEstadoContrato() == NombreEstadoContrato.PROCESO){
             contrato.setEstadoContrato(NombreEstadoContrato.FINALIZADO);
        coR.save(contrato);
        } else {
            throw new MiExcepcion("Para poder finalizar un contrato, éste debería haber sido aceptado y estar en proceso");
        }

    }
    
}
