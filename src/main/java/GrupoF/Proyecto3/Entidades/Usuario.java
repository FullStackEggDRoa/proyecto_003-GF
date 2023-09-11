
package GrupoF.Proyecto3.Entidades;

import GrupoF.Proyecto3.Enumeradores.NombreRol;
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
public class Usuario {
    
    private String id;
    private String nombreApellido;
    private String contrasenia;
    private Boolean alta;
    private Dni dni;
    private String correo;
    private Integer telefono;
    private Imagen imagen;
    private NombreRol rol;
    
}
