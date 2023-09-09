
package GrupoF.Proyecto3.Entidad;

import GrupoF.Proyecto3.Enum.EstadoContrato;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author cre_c
 */
@Entity
public class Contrato {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @OneToOne
    private Cliente cliente;
    @OneToOne
    private Proveedor proveedor;
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(TemporalType.DATE)
    private Date fechaFinalizado;
    private EstadoContrato estadoContrato;
    private int califProveedor;
    private int califCliente;
    private String comentarioCliente;
    private String comentarioProveedor;
    
    //CONSTRUCTOR
    public Contrato() {
    }
    
    //GETTERS
    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFinalizado() {
        return fechaFinalizado;
    }

    public EstadoContrato getEstadoContrato() {
        return estadoContrato;
    }

    public int getCalifProveedor() {
        return califProveedor;
    }

    public int getCalifCliente() {
        return califCliente;
    }

    public String getComentarioCliente() {
        return comentarioCliente;
    }

    public String getComentarioProveedor() {
        return comentarioProveedor;
    }
    
    //SETTERS
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFinalizado(Date fechaFinalizado) {
        this.fechaFinalizado = fechaFinalizado;
    }

    public void setEstadoContrato(EstadoContrato estadoContrato) {
        this.estadoContrato = estadoContrato;
    }

    public void setCalifProveedor(int califProveedor) {
        this.califProveedor = califProveedor;
    }

    public void setCalifCliente(int califCliente) {
        this.califCliente = califCliente;
    }

    public void setComentarioCliente(String comentarioCliente) {
        this.comentarioCliente = comentarioCliente;
    }

    public void setComentarioProveedor(String comentarioProveedor) {
        this.comentarioProveedor = comentarioProveedor;
    }
    
    //TOSTRING
    @Override
    public String toString() {
        return "Contrato{" + "id=" + id + ", cliente=" + cliente + ", proveedor=" + proveedor + ", fechaInicio=" + fechaInicio + ", fechaFinalizado=" + fechaFinalizado + ", estadoContrato=" + estadoContrato + ", califProveedor=" + califProveedor + ", califCliente=" + califCliente + ", comentarioCliente=" + comentarioCliente + ", comentarioProveedor=" + comentarioProveedor + '}';
    }
    
    
}
