
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

public class Dni {

    private String id;
    private char serie;
    private String numero;

}