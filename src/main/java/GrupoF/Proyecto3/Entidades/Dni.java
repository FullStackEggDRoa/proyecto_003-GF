
package GrupoF.Proyecto3.Entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString

public class Dni {

    private String id;
    private char serie;
    private String numero;

}
