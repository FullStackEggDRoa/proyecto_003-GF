
package GrupoF.Proyecto3.enridad;

import javax.persistence.Entity;

/**
 *
 * @author cre_c
 */
@Entity
public class Cliente extends Usuario{

    private String direccion;
    
    //CONSTRUCTOR
    public Cliente() {
    }
    
    //GETTER
    public String getDireccion() {
        return direccion;
    }
    
    //SETTER
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    //TOSTRING

    @Override
    public String toString() {
        return "usuario" + Usuario.class.toString() +  "Cliente{" + "direccion=" + direccion + '}';
    }
    
    
    
}
