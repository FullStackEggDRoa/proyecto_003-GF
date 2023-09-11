
package GrupoF.Proyecto3.Entidades;

import GrupoF.Proyecto3.Enumeradores.NombreEstadoContrato;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Contrato {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "idcontrato",unique = true)
    private String id;
    @ManyToOne
    private Cliente cliente;   
    @ManyToOne
    private Proveedor proveedor;
    @Column(name="fecha_inicio")
    private Date fechaInicio;
    @Column(name="fecha_fin")
    private Date fechaFinalizado;
    @Enumerated(EnumType.STRING)
    @Column(name="estado")
    private NombreEstadoContrato estadoContrato;
    @Column(name="calificacion_prov")
    private int califProveedor;
    @Column(name="calificacion_clt")
    private int califCliente;
    @Column(name="comentario_prov")
    private String comentarioCliente;
    @Column(name="comentario_clt")
    private String comentarioProveedor;
    
}
