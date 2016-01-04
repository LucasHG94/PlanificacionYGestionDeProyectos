/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sturm
 */
@Entity
@Table(name = "ETAPA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etapa.findAll", query = "SELECT e FROM Etapa e"),
    @NamedQuery(name = "Etapa.findByIdproyecto", query = "SELECT e FROM Etapa e WHERE e.etapaPK.idproyecto = :idproyecto"),
    @NamedQuery(name = "Etapa.findById", query = "SELECT e FROM Etapa e WHERE e.etapaPK.id = :id"),
    @NamedQuery(name = "Etapa.findByNombre", query = "SELECT e FROM Etapa e WHERE e.nombre = :nombre")})
public class Etapa implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EtapaPK etapaPK;
    @Size(max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "IDPROYECTO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "etapa")
    private Collection<Actividad> actividadCollection;

    public Etapa() {
    }

    public Etapa(EtapaPK etapaPK) {
        this.etapaPK = etapaPK;
    }

    public Etapa(int idproyecto, int id) {
        this.etapaPK = new EtapaPK(idproyecto, id);
    }

    public EtapaPK getEtapaPK() {
        return etapaPK;
    }

    public void setEtapaPK(EtapaPK etapaPK) {
        this.etapaPK = etapaPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @XmlTransient
    public Collection<Actividad> getActividadCollection() {
        return actividadCollection;
    }

    public void setActividadCollection(Collection<Actividad> actividadCollection) {
        this.actividadCollection = actividadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (etapaPK != null ? etapaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etapa)) {
            return false;
        }
        Etapa other = (Etapa) object;
        if ((this.etapaPK == null && other.etapaPK != null) || (this.etapaPK != null && !this.etapaPK.equals(other.etapaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Etapa[ etapaPK=" + etapaPK + " ]";
    }
    
}
