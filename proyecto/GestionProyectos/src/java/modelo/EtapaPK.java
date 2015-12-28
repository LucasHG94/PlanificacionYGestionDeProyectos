/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sturm
 */
@Embeddable
public class EtapaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPROYECTO")
    private int idproyecto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private int id;

    public EtapaPK() {
    }

    public EtapaPK(int idproyecto, int id) {
        this.idproyecto = idproyecto;
        this.id = id;
    }

    public int getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(int idproyecto) {
        this.idproyecto = idproyecto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idproyecto;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EtapaPK)) {
            return false;
        }
        EtapaPK other = (EtapaPK) object;
        if (this.idproyecto != other.idproyecto) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EtapaPK[ idproyecto=" + idproyecto + ", id=" + id + " ]";
    }
    
}
