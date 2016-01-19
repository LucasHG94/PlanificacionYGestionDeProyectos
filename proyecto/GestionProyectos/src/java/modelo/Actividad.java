/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sturm
 */
@Entity
@Table(name = "ACTIVIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actividad.findAll", query = "SELECT a FROM Actividad a"),
    @NamedQuery(name = "Actividad.findByIdproyecto", query = "SELECT a FROM Actividad a WHERE a.actividadPK.idproyecto = :idproyecto"),
    @NamedQuery(name = "Actividad.findByIdetapa", query = "SELECT a FROM Actividad a WHERE a.actividadPK.idetapa = :idetapa"),
    @NamedQuery(name = "Actividad.findById", query = "SELECT a FROM Actividad a WHERE a.actividadPK.id = :id"),
    @NamedQuery(name = "Actividad.findByNombre", query = "SELECT a FROM Actividad a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Actividad.findByFechainicio", query = "SELECT a FROM Actividad a WHERE a.fechainicio = :fechainicio"),
    @NamedQuery(name = "Actividad.findByFechafin", query = "SELECT a FROM Actividad a WHERE a.fechafin = :fechafin"),
    @NamedQuery(name = "Actividad.findByEsfuerzoestimado", query = "SELECT a FROM Actividad a WHERE a.esfuerzoestimado = :esfuerzoestimado"),
    @NamedQuery(name = "Actividad.findByRol", query = "SELECT a FROM Actividad a WHERE a.rol = :rol")})
public class Actividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ActividadPK actividadPK;
    @Size(max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHAINICIO")
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Column(name = "FECHAFIN")
    @Temporal(TemporalType.DATE)
    private Date fechafin;
    @Column(name = "ESFUERZOESTIMADO")
    private Integer esfuerzoestimado;
    @Size(max = 20)
    @Column(name = "ROL")
    private String rol;
    @JoinTable(name = "PRECEDENCIA", joinColumns = {
        @JoinColumn(name = "IDPROYECTOPRECEDENTE", referencedColumnName = "IDPROYECTO"),
        @JoinColumn(name = "IDETAPAPRECEDENTE", referencedColumnName = "IDETAPA"),
        @JoinColumn(name = "IDACTIVIDADPRECEDENTE", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "IDPROYECTOPRECEDIDA", referencedColumnName = "IDPROYECTO"),
        @JoinColumn(name = "IDETAPAPRECEDIDA", referencedColumnName = "IDETAPA"),
        @JoinColumn(name = "IDACTIVIDADPRECEDIDA", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Actividad> actividadCollection;
    @ManyToMany(mappedBy = "actividadCollection")
    private Collection<Actividad> actividadCollection1;
    @ManyToMany(mappedBy = "actividadCollection")
    private Collection<Trabajador> trabajadorCollection;
    @JoinColumns({
        @JoinColumn(name = "IDETAPA", referencedColumnName = "ID", insertable = false, updatable = false),
        @JoinColumn(name = "IDPROYECTO", referencedColumnName = "IDPROYECTO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Etapa etapa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad")
    private Collection<Informesemanal> informesemanalCollection;

    public Actividad() {
    }

    public Actividad(ActividadPK actividadPK) {
        this.actividadPK = actividadPK;
    }

    public Actividad(int idproyecto, int idetapa, int id) {
        this.actividadPK = new ActividadPK(idproyecto, idetapa, id);
    }

    public ActividadPK getActividadPK() {
        return actividadPK;
    }

    public void setActividadPK(ActividadPK actividadPK) {
        this.actividadPK = actividadPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Integer getEsfuerzoestimado() {
        return esfuerzoestimado;
    }

    public void setEsfuerzoestimado(Integer esfuerzoestimado) {
        this.esfuerzoestimado = esfuerzoestimado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @XmlTransient
    public Collection<Actividad> getActividadCollection() {
        return actividadCollection;
    }

    public void setActividadCollection(Collection<Actividad> actividadCollection) {
        this.actividadCollection = actividadCollection;
    }

    @XmlTransient
    public Collection<Actividad> getActividadCollection1() {
        return actividadCollection1;
    }

    public void setActividadCollection1(Collection<Actividad> actividadCollection1) {
        this.actividadCollection1 = actividadCollection1;
    }

    @XmlTransient
    public Collection<Trabajador> getTrabajadorCollection() {
        return trabajadorCollection;
    }

    public void setTrabajadorCollection(Collection<Trabajador> trabajadorCollection) {
        this.trabajadorCollection = trabajadorCollection;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    @XmlTransient
    public Collection<Informesemanal> getInformesemanalCollection() {
        return informesemanalCollection;
    }

    public void setInformesemanalCollection(Collection<Informesemanal> informesemanalCollection) {
        this.informesemanalCollection = informesemanalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actividadPK != null ? actividadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.actividadPK == null && other.actividadPK != null) || (this.actividadPK != null && !this.actividadPK.equals(other.actividadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Actividad[ actividadPK=" + actividadPK + " ]";
    }
    
}
