
package GrupoF.Proyecto3.enridad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author cre_c
 */
@Entity
public class Dni {

    //ATRIBUTOS
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private char serie;
    private String numero;

    //CONSTRUCTORES
    public Dni() {
    }

    public Dni(char serie, String numero) {
        this.serie = serie;
        this.numero = numero;
    }
    
    //METODOS

    //GETTERS
    public String getId() {
        return id;
    }

    public char getSerie() {
        return serie;
    }

    public String getNumero() {
        return numero;
    }
    
    //SETTERS 
    public void setSerie(char serie) {
        this.serie = serie;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    //toSring
    //A MODIFICAR
    @Override
    public String toString() {
        return "Dni{" + "id=" + id + ", serie=" + serie + ", numero=" + numero + '}';
    }
    
    
}
