
package GrupoF.Proyecto3.Entidades;

import java.io.Serializable;
import javax.persistence.Column;
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
public class Proveedor extends Usuario implements Serializable {
    @Column(name="num_matricula")
    private Integer numMatricula;
    @Column(name="cat_servicio")
    private String categoriaServicio;
    @Column(name="costo_hora")
    private double costoHora;
   
}
