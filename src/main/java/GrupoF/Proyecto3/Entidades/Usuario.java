
package GrupoF.Proyecto3.Entidades;

import GrupoF.Proyecto3.Enumeradores.NombreRol;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
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
@Inheritance(strategy = InheritanceType.JOINED)

public class Usuario implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "idusuario",unique = true)
    private String id;
    @Column(name="nombre_apellido")
    private String nombreApellido;
    @Column(name="contrasenia")
    private String contrasenia;
    @Column(name="alta")
    private Boolean alta;
    
    @OneToOne
    private Dni dni;
    @Column(name="correo")
    private String correo;
    @Column(name="telefono")
    private String telefono;
    
    @OneToOne
    private Imagen imagen;
    @Enumerated(EnumType.STRING)
    @Column(name="Rol")
    private NombreRol rol;
    
}
