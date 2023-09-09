
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
public class Imagen {

    //ATRIBUTOS
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String mime;
    private String nombre;
    private Byte[] contenido;
    
    //CONSTRUCTORES
    public Imagen() {
    }
    
    //GETTERS
    public String getId() {
        return id;
    }

    public String getMime() {
        return mime;
    }

    public String getNombre() {
        return nombre;
    }

    public Byte[] getContenido() {
        return contenido;
    }
    
    //SETTERS
    public void setMime(String mime) {
        this.mime = mime;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContenido(Byte[] contenido) {
        this.contenido = contenido;
    }
    
}
