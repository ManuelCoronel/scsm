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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @NamedQuery(name = "Microcurriculo.findById", query = "SELECT m FROM Microcurriculo m WHERE m.id = :id")})
public class Microcurriculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "microcurriculoId")
    private List<SeccionMicrocurriculo> seccionMicrocurriculoList;
    @JoinColumn(name = "area_de_formacion_id", referencedColumnName = "id")
    @ManyToOne
    private AreaFormacion areaDeFormacionId;
    @JoinColumns({
        @JoinColumn(name = "materia_codigo_materia", referencedColumnName = "codigo_materia"),
        @JoinColumn(name = "materia_pensum_codigo", referencedColumnName = "pensum_codigo")})
    @ManyToOne(optional = false)
    private Materia materia;

    public Microcurriculo() {
    }

    public Microcurriculo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Microcurriculo)) {
            return false;
        }
        Microcurriculo other = (Microcurriculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Microcurriculo[ id=" + id + " ]";
    }
    
}
