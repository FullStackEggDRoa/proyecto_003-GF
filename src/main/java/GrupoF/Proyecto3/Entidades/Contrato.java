
package GrupoF.Proyecto3.Entidades;

import GrupoF.Proyecto3.Enumeradores.NombreEstadoContrato;
import java.util.Date;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Contrato {

    private String id;
    private Cliente cliente;
    private Proveedor proveedor;
    private Date fechaInicio;
    private Date fechaFinalizado;
    private NombreEstadoContrato estadoContrato;
    private int califProveedor;
    private int califCliente;
    private String comentarioCliente;
    private String comentarioProveedor;
    
}
