
package GrupoF.Proyecto3.enridad;

import GrupoF.Proyecto3.Enum.Rol;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author cre_c
 */

@Entity
public class Usuario {
    
    //ATRIBUTOS
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombreApellido;
    private String contraseña;
    private Boolean alta;
    private Dni dni;
    private String correo;
    private Integer telefono;
    private Imagen imagen;
    private Rol rol;
    
    //CONSTRUCTORES

    public Usuario() {
    }
    
    //GETTERS
    public String getId() {
        return id;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public Boolean getAlta() {
        return alta;
    }

    public Dni getDni() {
        return dni;
    }

    public String getCorreo() {
        return correo;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public Imagen getImagen() {
        return imagen;
    }
    
    //SETTERS
    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public void setDni(Dni dni) {
        this.dni = dni;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }
    
    //TOSTRING
    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombreApellido=" + nombreApellido + ", contrase\u00f1a=" + contraseña + ", alta=" + alta + ", dni=" + dni + ", correo=" + correo + ", telefono=" + telefono + ", imagen=" + imagen + '}';
    }
    
    
}
