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
public class MateriaPeriodoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "materia_codigo_materia")
    private int materiaCodigoMateria;
    @Basic(optional = false)
    @Column(name = "anio")
    private int anio;
    @Basic(optional = false)
    @Column(name = "semestre_anio")
    private int semestreAnio;

    public MateriaPeriodoPK() {
    }

    public MateriaPeriodoPK(int materiaCodigoMateria, int anio, int semestreAnio) {
        this.materiaCodigoMateria = materiaCodigoMateria;
        this.anio = anio;
        this.semestreAnio = semestreAnio;
    }

    public int getMateriaCodigoMateria() {
        return materiaCodigoMateria;
    }

    public void setMateriaCodigoMateria(int materiaCodigoMateria) {
        this.materiaCodigoMateria = materiaCodigoMateria;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getSemestreAnio() {
        return semestreAnio;
    }

    public void setSemestreAnio(int semestreAnio) {
        this.semestreAnio = semestreAnio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) materiaCodigoMateria;
        hash += (int) anio;
        hash += (int) semestreAnio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MateriaPeriodoPK)) {
            return false;
        }
        MateriaPeriodoPK other = (MateriaPeriodoPK) object;
        if (this.materiaCodigoMateria != other.materiaCodigoMateria) {
            return false;
        }
        if (this.anio != other.anio) {
            return false;
        }
        if (this.semestreAnio != other.semestreAnio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.MateriaPeriodoPK[ materiaCodigoMateria=" + materiaCodigoMateria + ", anio=" + anio + ", semestreAnio=" + semestreAnio + " ]";
    }
    
}
