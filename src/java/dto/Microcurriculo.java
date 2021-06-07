/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Manuel
 */
@Entity
@Table(name = "microcurriculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Microcurriculo.findAll", query = "SELECT m FROM Microcurriculo m"),
    @NamedQuery(name = "Microcurriculo.findByCodigoMateria", query = "SELECT m FROM Microcurriculo m WHERE m.codigoMateria = :codigoMateria")})
public class Microcurriculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo_materia")
    private Integer codigoMateria;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "microcurriculo")
    private List<SeccionMicrocurriculo> seccionMicrocurriculoList;
    
    @JoinColumn(name = "area_de_formacion_id", referencedColumnName = "id")
    @ManyToOne
    private AreaFormacion areaDeFormacionId;
    
    @JoinColumns({
        @JoinColumn(name = "materia_codigo_materia", referencedColumnName = "codigo_materia"),
        @JoinColumn(name = "materia_pensum_codigo", referencedColumnName = "pensum_codigo")})
    @ManyToOne(optional = false)
    private Materia materia;
    
    @JoinColumn(name = "tipo_asignatura_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoAsignatura tipoAsignaturaId;

    public Microcurriculo() {
    }

    public Microcurriculo(Integer codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public Integer getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(Integer codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    @XmlTransient
    public List<SeccionMicrocurriculo> getSeccionMicrocurriculoList() {
        return seccionMicrocurriculoList;
    }

    public void setSeccionMicrocurriculoList(List<SeccionMicrocurriculo> seccionMicrocurriculoList) {
        this.seccionMicrocurriculoList = seccionMicrocurriculoList;
    }

    public AreaFormacion getAreaDeFormacionId() {
        return areaDeFormacionId;
    }

    public void setAreaDeFormacionId(AreaFormacion areaDeFormacionId) {
        this.areaDeFormacionId = areaDeFormacionId;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public TipoAsignatura getTipoAsignaturaId() {
        return tipoAsignaturaId;
    }

    public void setTipoAsignaturaId(TipoAsignatura tipoAsignaturaId) {
        this.tipoAsignaturaId = tipoAsignaturaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoMateria != null ? codigoMateria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Microcurriculo)) {
            return false;
        }
        Microcurriculo other = (Microcurriculo) object;
        if ((this.codigoMateria == null && other.codigoMateria != null) || (this.codigoMateria != null && !this.codigoMateria.equals(other.codigoMateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Microcurriculo[ codigoMateria=" + codigoMateria + " ]";
    }
    
}
