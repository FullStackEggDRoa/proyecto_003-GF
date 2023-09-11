
package GrupoF.Proyecto3.Entidades;

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
public class Imagen {
    
    private String id;
    private String mime;
    private String nombre;
    private Byte[] contenido;
    
}
