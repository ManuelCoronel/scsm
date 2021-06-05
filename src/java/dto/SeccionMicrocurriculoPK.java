/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Manuel
 */
@Embeddable
public class SeccionMicrocurriculoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @Column(name = "codigo_materia")
    private int codigoMateria;

    public SeccionMicrocurriculoPK() {
    }

    public SeccionMicrocurriculoPK(int id, int codigoMateria) {
        this.id = id;
        this.codigoMateria = codigoMateria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(int codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) codigoMateria;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeccionMicrocurriculoPK)) {
            return false;
        }
        SeccionMicrocurriculoPK other = (SeccionMicrocurriculoPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.codigoMateria != other.codigoMateria) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.SeccionMicrocurriculoPK[ id=" + id + ", codigoMateria=" + codigoMateria + " ]";
    }
    
}
