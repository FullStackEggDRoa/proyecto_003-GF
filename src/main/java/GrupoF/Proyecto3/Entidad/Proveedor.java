
package GrupoF.Proyecto3.Entidad;

import javax.persistence.Entity;

/**
 *
 * @author cre_c
 */
@Entity
public class Proveedor extends Usuario {

    private Integer numMatricula;
    private String categoriaServicio;
    private double costoHora;
    
    //CONSTRUCTOR
    public Proveedor() {
    }
    
    //GETTERS
    public Integer getNumMatricula() {
        return numMatricula;
    }

    public String getCategoriaServicio() {
        return categoriaServicio;
    }

    public double getCostoHora() {
        return costoHora;
    }
    
    //SETTERS
    public void setNumMatricula(Integer numMatricula) {
        this.numMatricula = numMatricula;
    }

    public void setCategoriaServicio(String categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    public void setCostoHora(double costoHora) {
        this.costoHora = costoHora;
    }
    
    //TOSTRING

    @Override
    public String toString() {
        return Usuario.class.toString() + " Proveedor{" + "numMatricula=" + numMatricula + ", categoriaServicio=" + categoriaServicio + ", costoHora=" + costoHora + '}';
    }
    
    
}
