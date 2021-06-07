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
@Table(name = "tabla_microcurriculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TablaMicrocurriculo.findAll", query = "SELECT t FROM TablaMicrocurriculo t"),
    @NamedQuery(name = "TablaMicrocurriculo.findById", query = "SELECT t FROM TablaMicrocurriculo t WHERE t.id = :id"),
    @NamedQuery(name = "TablaMicrocurriculo.findByCantidadFilas", query = "SELECT t FROM TablaMicrocurriculo t WHERE t.cantidadFilas = :cantidadFilas"),
    @NamedQuery(name = "TablaMicrocurriculo.findBySeccionMicrocurriculoCodigoMateria", query = "SELECT t FROM TablaMicrocurriculo t WHERE t.seccionMicrocurriculoCodigoMateria = :seccionMicrocurriculoCodigoMateria")})
public class TablaMicrocurriculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cantidad_filas")
    private int cantidadFilas;
    @Basic(optional = false)
    @Column(name = "seccion_microcurriculo_codigo_materia")
    private int seccionMicrocurriculoCodigoMateria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tablaMicrocurriculo")
    private List<TablaMicrocurriculoInfo> tablaMicrocurriculoInfoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tablaMicrocurriculo")
    private List<EncabezadoTabla> encabezadoTablaList;
    @JoinColumn(name = "seccion_microcurriculo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SeccionMicrocurriculo seccionMicrocurriculoId;

    public TablaMicrocurriculo() {
    }

    public TablaMicrocurriculo(Integer id) {
        this.id = id;
    }

    public TablaMicrocurriculo(Integer id, int cantidadFilas, int seccionMicrocurriculoCodigoMateria) {
        this.id = id;
        this.cantidadFilas = cantidadFilas;
        this.seccionMicrocurriculoCodigoMateria = seccionMicrocurriculoCodigoMateria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantidadFilas() {
        return cantidadFilas;
    }

    public void setCantidadFilas(int cantidadFilas) {
        this.cantidadFilas = cantidadFilas;
    }

    public int getSeccionMicrocurriculoCodigoMateria() {
        return seccionMicrocurriculoCodigoMateria;
    }

    public void setSeccionMicrocurriculoCodigoMateria(int seccionMicrocurriculoCodigoMateria) {
        this.seccionMicrocurriculoCodigoMateria = seccionMicrocurriculoCodigoMateria;
    }

    @XmlTransient
    public List<TablaMicrocurriculoInfo> getTablaMicrocurriculoInfoList() {
        return tablaMicrocurriculoInfoList;
    }

    public void setTablaMicrocurriculoInfoList(List<TablaMicrocurriculoInfo> tablaMicrocurriculoInfoList) {
        this.tablaMicrocurriculoInfoList = tablaMicrocurriculoInfoList;
    }

    @XmlTransient
    public List<EncabezadoTabla> getEncabezadoTablaList() {
        return encabezadoTablaList;
    }

    public void setEncabezadoTablaList(List<EncabezadoTabla> encabezadoTablaList) {
        this.encabezadoTablaList = encabezadoTablaList;
    }

    public SeccionMicrocurriculo getSeccionMicrocurriculoId() {
        return seccionMicrocurriculoId;
    }

    public void setSeccionMicrocurriculoId(SeccionMicrocurriculo seccionMicrocurriculoId) {
        this.seccionMicrocurriculoId = seccionMicrocurriculoId;
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
        if (!(object instanceof TablaMicrocurriculo)) {
            return false;
        }
        TablaMicrocurriculo other = (TablaMicrocurriculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.TablaMicrocurriculo[ id=" + id + " ]";
    }
    
}
