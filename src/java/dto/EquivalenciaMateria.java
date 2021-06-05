/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "EquivalenciaMateria.findByMateriaCodigoMateria", query = "SELECT e FROM EquivalenciaMateria e WHERE e.materiaCodigoMateria = :materiaCodigoMateria"),
    @NamedQuery(name = "EquivalenciaMateria.findByEquivalenciaMateria", query = "SELECT e FROM EquivalenciaMateria e WHERE e.equivalenciaMateria = :equivalenciaMateria"),
    @NamedQuery(name = "EquivalenciaMateria.findByNombre", query = "SELECT e FROM EquivalenciaMateria e WHERE e.nombre = :nombre")})
public class EquivalenciaMateria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "materia_codigo_materia")
    private Integer materiaCodigoMateria;
    @Column(name = "equivalencia_materia")
    private Integer equivalenciaMateria;
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "materia_codigo_materia", referencedColumnName = "codigo_materia", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Materia materia;

    public EquivalenciaMateria() {
    }

    public EquivalenciaMateria(Integer materiaCodigoMateria) {
        this.materiaCodigoMateria = materiaCodigoMateria;
    }

    public Integer getMateriaCodigoMateria() {
        return materiaCodigoMateria;
    }

    public void setMateriaCodigoMateria(Integer materiaCodigoMateria) {
        this.materiaCodigoMateria = materiaCodigoMateria;
    }

    public Integer getEquivalenciaMateria() {
        return equivalenciaMateria;
    }

    public void setEquivalenciaMateria(Integer equivalenciaMateria) {
        this.equivalenciaMateria = equivalenciaMateria;
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
        hash += (materiaCodigoMateria != null ? materiaCodigoMateria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquivalenciaMateria)) {
            return false;
        }
        EquivalenciaMateria other = (EquivalenciaMateria) object;
        if ((this.materiaCodigoMateria == null && other.materiaCodigoMateria != null) || (this.materiaCodigoMateria != null && !this.materiaCodigoMateria.equals(other.materiaCodigoMateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.EquivalenciaMateria[ materiaCodigoMateria=" + materiaCodigoMateria + " ]";
    }
    
}
