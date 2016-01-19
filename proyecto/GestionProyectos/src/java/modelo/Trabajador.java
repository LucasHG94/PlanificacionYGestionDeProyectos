/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sturm
 */
@Entity
@Table(name = "Trabajador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trabajador.findAll", query = "SELECT t FROM Trabajador t"),
    @NamedQuery(name = "Trabajador.findByNick", query = "SELECT t FROM Trabajador t WHERE t.nick = :nick"),
    @NamedQuery(name = "Trabajador.findByPassword", query = "SELECT t FROM Trabajador t WHERE t.password = :password"),
    @NamedQuery(name = "Trabajador.findByCategoria", query = "SELECT t FROM Trabajador t WHERE t.categoria = :categoria")})
public class Trabajador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nick")
    private String nick;
    @Size(max = 20)
    @Column(name = "password")
    private String password;
    @Column(name = "categoria")
    private Integer categoria;
    @JoinTable(name = "Asignacion", joinColumns = {
        @JoinColumn(name = "nicktrabajador", referencedColumnName = "nick")}, inverseJoinColumns = {
        @JoinColumn(name = "idproyecto", referencedColumnName = "idproyecto"),
        @JoinColumn(name = "idetapa", referencedColumnName = "idetapa"),
        @JoinColumn(name = "idactividad", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Actividad> actividadCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trabajador")
    private Collection<Vacaciones> vacacionesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trabajador")
    private Collection<Dedicacion> dedicacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trabajador")
    private Collection<Informesemanal> informesemanalCollection;

    public Trabajador() {
    }

    public Trabajador(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    @XmlTransient
    public Collection<Actividad> getActividadCollection() {
        return actividadCollection;
    }

    public void setActividadCollection(Collection<Actividad> actividadCollection) {
        this.actividadCollection = actividadCollection;
    }

    @XmlTransient
    public Collection<Vacaciones> getVacacionesCollection() {
        return vacacionesCollection;
    }

    public void setVacacionesCollection(Collection<Vacaciones> vacacionesCollection) {
        this.vacacionesCollection = vacacionesCollection;
    }

    @XmlTransient
    public Collection<Dedicacion> getDedicacionCollection() {
        return dedicacionCollection;
    }

    public void setDedicacionCollection(Collection<Dedicacion> dedicacionCollection) {
        this.dedicacionCollection = dedicacionCollection;
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
        hash += (nick != null ? nick.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trabajador)) {
            return false;
        }
        Trabajador other = (Trabajador) object;
        if ((this.nick == null && other.nick != null) || (this.nick != null && !this.nick.equals(other.nick))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Trabajador[ nick=" + nick + " ]";
    }
    
}
