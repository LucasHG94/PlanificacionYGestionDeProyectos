/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "DATOSCONFIGURABLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Datosconfigurables.findAll", query = "SELECT d FROM Datosconfigurables d"),
    @NamedQuery(name = "Datosconfigurables.findByNombrevariable", query = "SELECT d FROM Datosconfigurables d WHERE d.nombrevariable = :nombrevariable"),
    @NamedQuery(name = "Datosconfigurables.findByValor", query = "SELECT d FROM Datosconfigurables d WHERE d.valor = :valor")})
public class Datosconfigurables implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "NOMBREVARIABLE")
    private String nombrevariable;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private int valor;

    public Datosconfigurables() {
    }

    public Datosconfigurables(String nombrevariable) {
        this.nombrevariable = nombrevariable;
    }

    public Datosconfigurables(String nombrevariable, int valor) {
        this.nombrevariable = nombrevariable;
        this.valor = valor;
    }

    public String getNombrevariable() {
        return nombrevariable;
    }

    public void setNombrevariable(String nombrevariable) {
        this.nombrevariable = nombrevariable;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombrevariable != null ? nombrevariable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Datosconfigurables)) {
            return false;
        }
        Datosconfigurables other = (Datosconfigurables) object;
        if ((this.nombrevariable == null && other.nombrevariable != null) || (this.nombrevariable != null && !this.nombrevariable.equals(other.nombrevariable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Datosconfigurables[ nombrevariable=" + nombrevariable + " ]";
    }
    
}
