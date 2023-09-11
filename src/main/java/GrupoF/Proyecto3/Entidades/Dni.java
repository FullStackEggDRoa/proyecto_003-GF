package GrupoF.Proyecto3.Entidades;

<<<<<<< HEAD
=======
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
>>>>>>> developer
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

<<<<<<< HEAD
public class Dni{

=======
public class Dni implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "iddni",unique = true)
>>>>>>> developer
    private String id;
    @Column(name="serie")
    private char serie;
    @Column(name="numero")
    private String numero;
   
}
