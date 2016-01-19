/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sturm
 */
@Entity
@Table(name = "VACACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vacaciones.findAll", query = "SELECT v FROM Vacaciones v"),
    @NamedQuery(name = "Vacaciones.findByFechasemana", query = "SELECT v FROM Vacaciones v WHERE v.vacacionesPK.fechasemana = :fechasemana"),
    @NamedQuery(name = "Vacaciones.findByNicktrabajador", query = "SELECT v FROM Vacaciones v WHERE v.vacacionesPK.nicktrabajador = :nicktrabajador")})
public class Vacaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VacacionesPK vacacionesPK;
    @JoinColumn(name = "NICKTRABAJADOR", referencedColumnName = "NICK", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Trabajador trabajador;

    public Vacaciones() {
    }

    public Vacaciones(VacacionesPK vacacionesPK) {
        this.vacacionesPK = vacacionesPK;
    }

    public Vacaciones(Date fechasemana, String nicktrabajador) {
        this.vacacionesPK = new VacacionesPK(fechasemana, nicktrabajador);
    }

    public VacacionesPK getVacacionesPK() {
        return vacacionesPK;
    }

    public void setVacacionesPK(VacacionesPK vacacionesPK) {
        this.vacacionesPK = vacacionesPK;
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
        hash += (vacacionesPK != null ? vacacionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vacaciones)) {
            return false;
        }
        Vacaciones other = (Vacaciones) object;
        if ((this.vacacionesPK == null && other.vacacionesPK != null) || (this.vacacionesPK != null && !this.vacacionesPK.equals(other.vacacionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Vacaciones[ vacacionesPK=" + vacacionesPK + " ]";
    }
    
}
