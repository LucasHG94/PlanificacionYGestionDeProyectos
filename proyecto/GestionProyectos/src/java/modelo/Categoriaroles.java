/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sturm
 */
@Entity
@Table(name = "Categoriaroles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoriaroles.findAll", query = "SELECT c FROM Categoriaroles c"),
    @NamedQuery(name = "Categoriaroles.findByCategoria", query = "SELECT c FROM Categoriaroles c WHERE c.categoriarolesPK.categoria = :categoria"),
    @NamedQuery(name = "Categoriaroles.findByRol", query = "SELECT c FROM Categoriaroles c WHERE c.categoriarolesPK.rol = :rol")})
public class Categoriaroles implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CategoriarolesPK categoriarolesPK;

    public Categoriaroles() {
    }

    public Categoriaroles(CategoriarolesPK categoriarolesPK) {
        this.categoriarolesPK = categoriarolesPK;
    }

    public Categoriaroles(int categoria, String rol) {
        this.categoriarolesPK = new CategoriarolesPK(categoria, rol);
    }

    public CategoriarolesPK getCategoriarolesPK() {
        return categoriarolesPK;
    }

    public void setCategoriarolesPK(CategoriarolesPK categoriarolesPK) {
        this.categoriarolesPK = categoriarolesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoriarolesPK != null ? categoriarolesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriaroles)) {
            return false;
        }
        Categoriaroles other = (Categoriaroles) object;
        if ((this.categoriarolesPK == null && other.categoriarolesPK != null) || (this.categoriarolesPK != null && !this.categoriarolesPK.equals(other.categoriarolesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Categoriaroles[ categoriarolesPK=" + categoriarolesPK + " ]";
    }
    
}
