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
import javax.validation.constraints.Size;

/**
 *
 * @author sturm
 */
@Embeddable
public class DedicacionPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nicktrabajador")
    private String nicktrabajador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idproyecto")
    private int idproyecto;

    public DedicacionPK() {
    }

    public DedicacionPK(String nicktrabajador, int idproyecto) {
        this.nicktrabajador = nicktrabajador;
        this.idproyecto = idproyecto;
    }

    public String getNicktrabajador() {
        return nicktrabajador;
    }

    public void setNicktrabajador(String nicktrabajador) {
        this.nicktrabajador = nicktrabajador;
    }

    public int getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(int idproyecto) {
        this.idproyecto = idproyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nicktrabajador != null ? nicktrabajador.hashCode() : 0);
        hash += (int) idproyecto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DedicacionPK)) {
            return false;
        }
        DedicacionPK other = (DedicacionPK) object;
        if ((this.nicktrabajador == null && other.nicktrabajador != null) || (this.nicktrabajador != null && !this.nicktrabajador.equals(other.nicktrabajador))) {
            return false;
        }
        if (this.idproyecto != other.idproyecto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DedicacionPK[ nicktrabajador=" + nicktrabajador + ", idproyecto=" + idproyecto + " ]";
    }
    
}
