/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author sturm
 */
@Embeddable
public class VacacionesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechasemana")
    @Temporal(TemporalType.DATE)
    private Date fechasemana;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nicktrabajador")
    private String nicktrabajador;

    public VacacionesPK() {
    }

    public VacacionesPK(Date fechasemana, String nicktrabajador) {
        this.fechasemana = fechasemana;
        this.nicktrabajador = nicktrabajador;
    }

    public Date getFechasemana() {
        return fechasemana;
    }

    public void setFechasemana(Date fechasemana) {
        this.fechasemana = fechasemana;
    }

    public String getNicktrabajador() {
        return nicktrabajador;
    }

    public void setNicktrabajador(String nicktrabajador) {
        this.nicktrabajador = nicktrabajador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fechasemana != null ? fechasemana.hashCode() : 0);
        hash += (nicktrabajador != null ? nicktrabajador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VacacionesPK)) {
            return false;
        }
        VacacionesPK other = (VacacionesPK) object;
        if ((this.fechasemana == null && other.fechasemana != null) || (this.fechasemana != null && !this.fechasemana.equals(other.fechasemana))) {
            return false;
        }
        if ((this.nicktrabajador == null && other.nicktrabajador != null) || (this.nicktrabajador != null && !this.nicktrabajador.equals(other.nicktrabajador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.VacacionesPK[ fechasemana=" + fechasemana + ", nicktrabajador=" + nicktrabajador + " ]";
    }
    
}
