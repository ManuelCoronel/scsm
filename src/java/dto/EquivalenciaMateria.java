/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "equivalencia_materia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EquivalenciaMateria.findAll", query = "SELECT e FROM EquivalenciaMateria e"),
    @NamedQuery(name = "EquivalenciaMateria.findByMateriaCodigoMateria", query = "SELECT e FROM EquivalenciaMateria e WHERE e.equivalenciaMateriaPK.materiaCodigoMateria = :materiaCodigoMateria"),
    @NamedQuery(name = "EquivalenciaMateria.findByEquivalenciaMateria", query = "SELECT e FROM EquivalenciaMateria e WHERE e.equivalenciaMateriaPK.equivalenciaMateria = :equivalenciaMateria"),
    @NamedQuery(name = "EquivalenciaMateria.findByNombre", query = "SELECT e FROM EquivalenciaMateria e WHERE e.nombre = :nombre")})
public class EquivalenciaMateria implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EquivalenciaMateriaPK equivalenciaMateriaPK;
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "materia_codigo_materia", referencedColumnName = "codigo_materia", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materia materia;

    public EquivalenciaMateria() {
    }

    public EquivalenciaMateria(EquivalenciaMateriaPK equivalenciaMateriaPK) {
        this.equivalenciaMateriaPK = equivalenciaMateriaPK;
    }

    public EquivalenciaMateria(int materiaCodigoMateria, int equivalenciaMateria) {
        this.equivalenciaMateriaPK = new EquivalenciaMateriaPK(materiaCodigoMateria, equivalenciaMateria);
    }

    public EquivalenciaMateriaPK getEquivalenciaMateriaPK() {
        return equivalenciaMateriaPK;
    }

    public void setEquivalenciaMateriaPK(EquivalenciaMateriaPK equivalenciaMateriaPK) {
        this.equivalenciaMateriaPK = equivalenciaMateriaPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (equivalenciaMateriaPK != null ? equivalenciaMateriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquivalenciaMateria)) {
            return false;
        }
        EquivalenciaMateria other = (EquivalenciaMateria) object;
        if ((this.equivalenciaMateriaPK == null && other.equivalenciaMateriaPK != null) || (this.equivalenciaMateriaPK != null && !this.equivalenciaMateriaPK.equals(other.equivalenciaMateriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.EquivalenciaMateria[ equivalenciaMateriaPK=" + equivalenciaMateriaPK + " ]";
    }
    
}
