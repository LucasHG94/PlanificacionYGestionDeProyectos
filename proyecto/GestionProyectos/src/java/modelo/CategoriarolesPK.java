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
public class CategoriarolesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "CATEGORIA")
    private int categoria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ROL")
    private String rol;

    public CategoriarolesPK() {
    }

    public CategoriarolesPK(int categoria, String rol) {
        this.categoria = categoria;
        this.rol = rol;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) categoria;
        hash += (rol != null ? rol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriarolesPK)) {
            return false;
        }
        CategoriarolesPK other = (CategoriarolesPK) object;
        if (this.categoria != other.categoria) {
            return false;
        }
        if ((this.rol == null && other.rol != null) || (this.rol != null && !this.rol.equals(other.rol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.CategoriarolesPK[ categoria=" + categoria + ", rol=" + rol + " ]";
    }
    
}
