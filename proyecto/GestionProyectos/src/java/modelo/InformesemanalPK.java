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
public class InformesemanalPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NICKTRABAJADOR")
    private String nicktrabajador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPROYECTO")
    private int idproyecto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDETAPA")
    private int idetapa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDACTIVIDAD")
    private int idactividad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHASEMANA")
    @Temporal(TemporalType.DATE)
    private Date fechasemana;

    public InformesemanalPK() {
    }

    public InformesemanalPK(String nicktrabajador, int idproyecto, int idetapa, int idactividad, Date fechasemana) {
        this.nicktrabajador = nicktrabajador;
        this.idproyecto = idproyecto;
        this.idetapa = idetapa;
        this.idactividad = idactividad;
        this.fechasemana = fechasemana;
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

    public int getIdetapa() {
        return idetapa;
    }

    public void setIdetapa(int idetapa) {
        this.idetapa = idetapa;
    }

    public int getIdactividad() {
        return idactividad;
    }

    public void setIdactividad(int idactividad) {
        this.idactividad = idactividad;
    }

    public Date getFechasemana() {
        return fechasemana;
    }

    public void setFechasemana(Date fechasemana) {
        this.fechasemana = fechasemana;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nicktrabajador != null ? nicktrabajador.hashCode() : 0);
        hash += (int) idproyecto;
        hash += (int) idetapa;
        hash += (int) idactividad;
        hash += (fechasemana != null ? fechasemana.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformesemanalPK)) {
            return false;
        }
        InformesemanalPK other = (InformesemanalPK) object;
        if ((this.nicktrabajador == null && other.nicktrabajador != null) || (this.nicktrabajador != null && !this.nicktrabajador.equals(other.nicktrabajador))) {
            return false;
        }
        if (this.idproyecto != other.idproyecto) {
            return false;
        }
        if (this.idetapa != other.idetapa) {
            return false;
        }
        if (this.idactividad != other.idactividad) {
            return false;
        }
        if ((this.fechasemana == null && other.fechasemana != null) || (this.fechasemana != null && !this.fechasemana.equals(other.fechasemana))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.InformesemanalPK[ nicktrabajador=" + nicktrabajador + ", idproyecto=" + idproyecto + ", idetapa=" + idetapa + ", idactividad=" + idactividad + ", fechasemana=" + fechasemana + " ]";
    }
    
}
