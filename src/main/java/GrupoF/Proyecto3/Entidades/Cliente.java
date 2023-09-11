
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
public class Cliente extends Usuario implements Serializable{
    @Column(name="direccion")
    private String direccion;
    
}
