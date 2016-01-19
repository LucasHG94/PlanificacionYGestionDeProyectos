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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sturm
 */
@Entity
@Table(name = "Informesemanal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Informesemanal.findAll", query = "SELECT i FROM Informesemanal i"),
    @NamedQuery(name = "Informesemanal.findByNicktrabajador", query = "SELECT i FROM Informesemanal i WHERE i.informesemanalPK.nicktrabajador = :nicktrabajador"),
    @NamedQuery(name = "Informesemanal.findByIdproyecto", query = "SELECT i FROM Informesemanal i WHERE i.informesemanalPK.idproyecto = :idproyecto"),
    @NamedQuery(name = "Informesemanal.findByIdetapa", query = "SELECT i FROM Informesemanal i WHERE i.informesemanalPK.idetapa = :idetapa"),
    @NamedQuery(name = "Informesemanal.findByIdactividad", query = "SELECT i FROM Informesemanal i WHERE i.informesemanalPK.idactividad = :idactividad"),
    @NamedQuery(name = "Informesemanal.findByFechasemana", query = "SELECT i FROM Informesemanal i WHERE i.informesemanalPK.fechasemana = :fechasemana"),
    @NamedQuery(name = "Informesemanal.findByHorastarea1", query = "SELECT i FROM Informesemanal i WHERE i.horastarea1 = :horastarea1"),
    @NamedQuery(name = "Informesemanal.findByHorastarea2", query = "SELECT i FROM Informesemanal i WHERE i.horastarea2 = :horastarea2"),
    @NamedQuery(name = "Informesemanal.findByHorastarea3", query = "SELECT i FROM Informesemanal i WHERE i.horastarea3 = :horastarea3"),
    @NamedQuery(name = "Informesemanal.findByHorastarea4", query = "SELECT i FROM Informesemanal i WHERE i.horastarea4 = :horastarea4"),
    @NamedQuery(name = "Informesemanal.findByHorastarea5", query = "SELECT i FROM Informesemanal i WHERE i.horastarea5 = :horastarea5"),
    @NamedQuery(name = "Informesemanal.findByHorastarea6", query = "SELECT i FROM Informesemanal i WHERE i.horastarea6 = :horastarea6"),
    @NamedQuery(name = "Informesemanal.findByEstado", query = "SELECT i FROM Informesemanal i WHERE i.estado = :estado")})
public class Informesemanal implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InformesemanalPK informesemanalPK;
    @Column(name = "horastarea1")
    private Integer horastarea1;
    @Column(name = "horastarea2")
    private Integer horastarea2;
    @Column(name = "horastarea3")
    private Integer horastarea3;
    @Column(name = "horastarea4")
    private Integer horastarea4;
    @Column(name = "horastarea5")
    private Integer horastarea5;
    @Column(name = "horastarea6")
    private Integer horastarea6;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "estado")
    private String estado;
    @JoinColumns({
        @JoinColumn(name = "idproyecto", referencedColumnName = "idproyecto", insertable = false, updatable = false),
        @JoinColumn(name = "idetapa", referencedColumnName = "idetapa", insertable = false, updatable = false),
        @JoinColumn(name = "idactividad", referencedColumnName = "id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Actividad actividad;
    @JoinColumn(name = "nicktrabajador", referencedColumnName = "nick", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Trabajador trabajador;

    public Informesemanal() {
    }

    public Informesemanal(InformesemanalPK informesemanalPK) {
        this.informesemanalPK = informesemanalPK;
    }

    public Informesemanal(InformesemanalPK informesemanalPK, String estado) {
        this.informesemanalPK = informesemanalPK;
        this.estado = estado;
    }

    public Informesemanal(String nicktrabajador, int idproyecto, int idetapa, int idactividad, Date fechasemana) {
        this.informesemanalPK = new InformesemanalPK(nicktrabajador, idproyecto, idetapa, idactividad, fechasemana);
    }

    public InformesemanalPK getInformesemanalPK() {
        return informesemanalPK;
    }

    public void setInformesemanalPK(InformesemanalPK informesemanalPK) {
        this.informesemanalPK = informesemanalPK;
    }

    public Integer getHorastarea1() {
        return horastarea1;
    }

    public void setHorastarea1(Integer horastarea1) {
        this.horastarea1 = horastarea1;
    }

    public Integer getHorastarea2() {
        return horastarea2;
    }

    public void setHorastarea2(Integer horastarea2) {
        this.horastarea2 = horastarea2;
    }

    public Integer getHorastarea3() {
        return horastarea3;
    }

    public void setHorastarea3(Integer horastarea3) {
        this.horastarea3 = horastarea3;
    }

    public Integer getHorastarea4() {
        return horastarea4;
    }

    public void setHorastarea4(Integer horastarea4) {
        this.horastarea4 = horastarea4;
    }

    public Integer getHorastarea5() {
        return horastarea5;
    }

    public void setHorastarea5(Integer horastarea5) {
        this.horastarea5 = horastarea5;
    }

    public Integer getHorastarea6() {
        return horastarea6;
    }

    public void setHorastarea6(Integer horastarea6) {
        this.horastarea6 = horastarea6;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
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
        hash += (informesemanalPK != null ? informesemanalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Informesemanal)) {
            return false;
        }
        Informesemanal other = (Informesemanal) object;
        if ((this.informesemanalPK == null && other.informesemanalPK != null) || (this.informesemanalPK != null && !this.informesemanalPK.equals(other.informesemanalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Informesemanal[ informesemanalPK=" + informesemanalPK + " ]";
    }
    
}
