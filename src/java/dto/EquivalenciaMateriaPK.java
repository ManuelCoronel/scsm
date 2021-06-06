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
public class EquivalenciaMateriaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "materia_codigo_materia")
    private int materiaCodigoMateria;
    @Basic(optional = false)
    @Column(name = "equivalencia_materia")
    private int equivalenciaMateria;

    public EquivalenciaMateriaPK() {
    }

    public EquivalenciaMateriaPK(int materiaCodigoMateria, int equivalenciaMateria) {
        this.materiaCodigoMateria = materiaCodigoMateria;
        this.equivalenciaMateria = equivalenciaMateria;
    }

    public int getMateriaCodigoMateria() {
        return materiaCodigoMateria;
    }

    public void setMateriaCodigoMateria(int materiaCodigoMateria) {
        this.materiaCodigoMateria = materiaCodigoMateria;
    }

    public int getEquivalenciaMateria() {
        return equivalenciaMateria;
    }

    public void setEquivalenciaMateria(int equivalenciaMateria) {
        this.equivalenciaMateria = equivalenciaMateria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) materiaCodigoMateria;
        hash += (int) equivalenciaMateria;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquivalenciaMateriaPK)) {
            return false;
        }
        EquivalenciaMateriaPK other = (EquivalenciaMateriaPK) object;
        if (this.materiaCodigoMateria != other.materiaCodigoMateria) {
            return false;
        }
        if (this.equivalenciaMateria != other.equivalenciaMateria) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.EquivalenciaMateriaPK[ materiaCodigoMateria=" + materiaCodigoMateria + ", equivalenciaMateria=" + equivalenciaMateria + " ]";
    }
    
}
