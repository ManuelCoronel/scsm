/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Manuel
 */
@Entity
@Table(name = "materia_periodo_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MateriaPeriodoGrupo.findAll", query = "SELECT m FROM MateriaPeriodoGrupo m"),
    @NamedQuery(name = "MateriaPeriodoGrupo.findByGrupo", query = "SELECT m FROM MateriaPeriodoGrupo m WHERE m.materiaPeriodoGrupoPK.grupo = :grupo"),
    @NamedQuery(name = "MateriaPeriodoGrupo.findByDocenteCodigo", query = "SELECT m FROM MateriaPeriodoGrupo m WHERE m.materiaPeriodoGrupoPK.docenteCodigo = :docenteCodigo")})
public class MateriaPeriodoGrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MateriaPeriodoGrupoPK materiaPeriodoGrupoPK;
    @JoinColumn(name = "docente_codigo", referencedColumnName = "codigo_docente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Docente docente;
    @JoinColumns({
        @JoinColumn(name = "materia_periodo_materia_codigo_materia", referencedColumnName = "materia_codigo_materia"),
        @JoinColumn(name = "materia_periodo_anio", referencedColumnName = "anio"),
        @JoinColumn(name = "materia_periodo_semestre_anio", referencedColumnName = "semestre_anio")})
    @ManyToOne(optional = false)
    private MateriaPeriodo materiaPeriodo;

    public MateriaPeriodoGrupo() {
    }

    public MateriaPeriodoGrupo(MateriaPeriodoGrupoPK materiaPeriodoGrupoPK) {
        this.materiaPeriodoGrupoPK = materiaPeriodoGrupoPK;
    }

    public MateriaPeriodoGrupo(String grupo, int docenteCodigo) {
        this.materiaPeriodoGrupoPK = new MateriaPeriodoGrupoPK(grupo, docenteCodigo);
    }

    public MateriaPeriodoGrupoPK getMateriaPeriodoGrupoPK() {
        return materiaPeriodoGrupoPK;
    }

    public void setMateriaPeriodoGrupoPK(MateriaPeriodoGrupoPK materiaPeriodoGrupoPK) {
        this.materiaPeriodoGrupoPK = materiaPeriodoGrupoPK;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public MateriaPeriodo getMateriaPeriodo() {
        return materiaPeriodo;
    }

    public void setMateriaPeriodo(MateriaPeriodo materiaPeriodo) {
        this.materiaPeriodo = materiaPeriodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materiaPeriodoGrupoPK != null ? materiaPeriodoGrupoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MateriaPeriodoGrupo)) {
            return false;
        }
        MateriaPeriodoGrupo other = (MateriaPeriodoGrupo) object;
        if ((this.materiaPeriodoGrupoPK == null && other.materiaPeriodoGrupoPK != null) || (this.materiaPeriodoGrupoPK != null && !this.materiaPeriodoGrupoPK.equals(other.materiaPeriodoGrupoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.MateriaPeriodoGrupo[ materiaPeriodoGrupoPK=" + materiaPeriodoGrupoPK + " ]";
    }
    
}
