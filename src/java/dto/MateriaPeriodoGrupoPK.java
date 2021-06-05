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
public class MateriaPeriodoGrupoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "grupo")
    private String grupo;
    @Basic(optional = false)
    @Column(name = "materia_periodo_materia_codigo_materia")
    private int materiaPeriodoMateriaCodigoMateria;
    @Basic(optional = false)
    @Column(name = "materia_periodo_anio")
    private int materiaPeriodoAnio;
    @Basic(optional = false)
    @Column(name = "materia_periodo_semestre_anio")
    private int materiaPeriodoSemestreAnio;
    @Basic(optional = false)
    @Column(name = "docente_codigo")
    private int docenteCodigo;

    public MateriaPeriodoGrupoPK() {
    }

    public MateriaPeriodoGrupoPK(String grupo, int materiaPeriodoMateriaCodigoMateria, int materiaPeriodoAnio, int materiaPeriodoSemestreAnio, int docenteCodigo) {
        this.grupo = grupo;
        this.materiaPeriodoMateriaCodigoMateria = materiaPeriodoMateriaCodigoMateria;
        this.materiaPeriodoAnio = materiaPeriodoAnio;
        this.materiaPeriodoSemestreAnio = materiaPeriodoSemestreAnio;
        this.docenteCodigo = docenteCodigo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public int getMateriaPeriodoMateriaCodigoMateria() {
        return materiaPeriodoMateriaCodigoMateria;
    }

    public void setMateriaPeriodoMateriaCodigoMateria(int materiaPeriodoMateriaCodigoMateria) {
        this.materiaPeriodoMateriaCodigoMateria = materiaPeriodoMateriaCodigoMateria;
    }

    public int getMateriaPeriodoAnio() {
        return materiaPeriodoAnio;
    }

    public void setMateriaPeriodoAnio(int materiaPeriodoAnio) {
        this.materiaPeriodoAnio = materiaPeriodoAnio;
    }

    public int getMateriaPeriodoSemestreAnio() {
        return materiaPeriodoSemestreAnio;
    }

    public void setMateriaPeriodoSemestreAnio(int materiaPeriodoSemestreAnio) {
        this.materiaPeriodoSemestreAnio = materiaPeriodoSemestreAnio;
    }

    public int getDocenteCodigo() {
        return docenteCodigo;
    }

    public void setDocenteCodigo(int docenteCodigo) {
        this.docenteCodigo = docenteCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupo != null ? grupo.hashCode() : 0);
        hash += (int) materiaPeriodoMateriaCodigoMateria;
        hash += (int) materiaPeriodoAnio;
        hash += (int) materiaPeriodoSemestreAnio;
        hash += (int) docenteCodigo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MateriaPeriodoGrupoPK)) {
            return false;
        }
        MateriaPeriodoGrupoPK other = (MateriaPeriodoGrupoPK) object;
        if ((this.grupo == null && other.grupo != null) || (this.grupo != null && !this.grupo.equals(other.grupo))) {
            return false;
        }
        if (this.materiaPeriodoMateriaCodigoMateria != other.materiaPeriodoMateriaCodigoMateria) {
            return false;
        }
        if (this.materiaPeriodoAnio != other.materiaPeriodoAnio) {
            return false;
        }
        if (this.materiaPeriodoSemestreAnio != other.materiaPeriodoSemestreAnio) {
            return false;
        }
        if (this.docenteCodigo != other.docenteCodigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.MateriaPeriodoGrupoPK[ grupo=" + grupo + ", materiaPeriodoMateriaCodigoMateria=" + materiaPeriodoMateriaCodigoMateria + ", materiaPeriodoAnio=" + materiaPeriodoAnio + ", materiaPeriodoSemestreAnio=" + materiaPeriodoSemestreAnio + ", docenteCodigo=" + docenteCodigo + " ]";
    }
    
}