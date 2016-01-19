/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sturm
 */
@Entity
@Table(name = "Dedicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dedicacion.findAll", query = "SELECT d FROM Dedicacion d"),
    @NamedQuery(name = "Dedicacion.findByNicktrabajador", query = "SELECT d FROM Dedicacion d WHERE d.dedicacionPK.nicktrabajador = :nicktrabajador"),
    @NamedQuery(name = "Dedicacion.findByIdproyecto", query = "SELECT d FROM Dedicacion d WHERE d.dedicacionPK.idproyecto = :idproyecto"),
    @NamedQuery(name = "Dedicacion.findByPorcentaje", query = "SELECT d FROM Dedicacion d WHERE d.porcentaje = :porcentaje")})
public class Dedicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DedicacionPK dedicacionPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "porcentaje")
    private int porcentaje;
    @JoinColumn(name = "idproyecto", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "nicktrabajador", referencedColumnName = "nick", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Trabajador trabajador;

    public Dedicacion() {
    }

    public Dedicacion(DedicacionPK dedicacionPK) {
        this.dedicacionPK = dedicacionPK;
    }

    public Dedicacion(DedicacionPK dedicacionPK, int porcentaje) {
        this.dedicacionPK = dedicacionPK;
        this.porcentaje = porcentaje;
    }

    public Dedicacion(String nicktrabajador, int idproyecto) {
        this.dedicacionPK = new DedicacionPK(nicktrabajador, idproyecto);
    }

    public DedicacionPK getDedicacionPK() {
        return dedicacionPK;
    }

    public void setDedicacionPK(DedicacionPK dedicacionPK) {
        this.dedicacionPK = dedicacionPK;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dedicacionPK != null ? dedicacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dedicacion)) {
            return false;
        }
        Dedicacion other = (Dedicacion) object;
        if ((this.dedicacionPK == null && other.dedicacionPK != null) || (this.dedicacionPK != null && !this.dedicacionPK.equals(other.dedicacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Dedicacion[ dedicacionPK=" + dedicacionPK + " ]";
    }
    
}
