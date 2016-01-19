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
public class ActividadPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idproyecto")
    private int idproyecto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idetapa")
    private int idetapa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private int id;

    public ActividadPK() {
    }

    public ActividadPK(int idproyecto, int idetapa, int id) {
        this.idproyecto = idproyecto;
        this.idetapa = idetapa;
        this.id = id;
    }

    public int getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(int idproyecto) {
        this.idproyecto = idproyecto;
    }

    public int getIdetapa() {
        return idetapa;
    }

    public void setIdetapa(int idetapa) {
        this.idetapa = idetapa;
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
        hash += (int) idetapa;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadPK)) {
            return false;
        }
        ActividadPK other = (ActividadPK) object;
        if (this.idproyecto != other.idproyecto) {
            return false;
        }
        if (this.idetapa != other.idetapa) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ActividadPK[ idproyecto=" + idproyecto + ", idetapa=" + idetapa + ", id=" + id + " ]";
    }
    
}
