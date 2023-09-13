package GrupoF.Proyecto3.Servicios;

import GrupoF.Proyecto3.Entidades.Cliente;
import GrupoF.Proyecto3.Entidades.Contrato;
import GrupoF.Proyecto3.Entidades.Proveedor;
import GrupoF.Proyecto3.Repositorios.ClienteRepositorio;
import GrupoF.Proyecto3.Repositorios.ContratoRepositorio;
import GrupoF.Proyecto3.Repositorios.ProveedorRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContratoServicio {
    
    @Autowired
    private ContratoRepositorio contratoRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    
    public void registrarContrato(String idCliente, String idProveedor){
        
        Cliente cliente = clienteRepositorio.findById(idCliente).get();
        Proveedor proveedor = proveedorRepositorio.findById(idProveedor).get();
                
        Contrato contrato = new Contrato();
        contrato.setCliente(cliente);
        contrato.setProveedor(proveedor);
        contrato.setFechaInicio(new Date());
        
        contratoRepositorio.save(contrato);
    }
    
    public List<Contrato> listarContrato(){
        
        List<Contrato> contratos = new ArrayList();
        contratos = contratoRepositorio.findAll();
        return contratos;
        
    }
}
