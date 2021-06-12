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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Manuel
 */
@Entity
@Table(name = "encabezado_tabla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncabezadoTabla.findAll", query = "SELECT e FROM EncabezadoTabla e")
    , @NamedQuery(name = "EncabezadoTabla.findByEncabezadoId", query = "SELECT e FROM EncabezadoTabla e WHERE e.encabezadoId = :encabezadoId")
    , @NamedQuery(name = "EncabezadoTabla.findById", query = "SELECT e FROM EncabezadoTabla e WHERE e.id = :id")
    , @NamedQuery(name = "EncabezadoTabla.findByTablaMicrocurriculoId", query = "SELECT e FROM EncabezadoTabla e WHERE e.tablaMicrocurriculoId = :tablaMicrocurriculoId")
    , @NamedQuery(name = "EncabezadoTabla.findByTablaMicrocurriculoSeccionMicrocurriculoId", query = "SELECT e FROM EncabezadoTabla e WHERE e.tablaMicrocurriculoSeccionMicrocurriculoId = :tablaMicrocurriculoSeccionMicrocurriculoId")})
public class EncabezadoTabla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "encabezado_id")
    private int encabezadoId;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "tabla_microcurriculo_id")
    private int tablaMicrocurriculoId;
    @Basic(optional = false)
    @Column(name = "tabla_microcurriculo_seccion_microcurriculo_id")
    private int tablaMicrocurriculoSeccionMicrocurriculoId;

    public EncabezadoTabla() {
    }

    public EncabezadoTabla(Integer id) {
        this.id = id;
    }

    public EncabezadoTabla(Integer id, int encabezadoId, int tablaMicrocurriculoId, int tablaMicrocurriculoSeccionMicrocurriculoId) {
        this.id = id;
        this.encabezadoId = encabezadoId;
        this.tablaMicrocurriculoId = tablaMicrocurriculoId;
        this.tablaMicrocurriculoSeccionMicrocurriculoId = tablaMicrocurriculoSeccionMicrocurriculoId;
    }

    public int getEncabezadoId() {
        return encabezadoId;
    }

    public void setEncabezadoId(int encabezadoId) {
        this.encabezadoId = encabezadoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTablaMicrocurriculoId() {
        return tablaMicrocurriculoId;
    }

    public void setTablaMicrocurriculoId(int tablaMicrocurriculoId) {
        this.tablaMicrocurriculoId = tablaMicrocurriculoId;
    }

    public int getTablaMicrocurriculoSeccionMicrocurriculoId() {
        return tablaMicrocurriculoSeccionMicrocurriculoId;
    }

    public void setTablaMicrocurriculoSeccionMicrocurriculoId(int tablaMicrocurriculoSeccionMicrocurriculoId) {
        this.tablaMicrocurriculoSeccionMicrocurriculoId = tablaMicrocurriculoSeccionMicrocurriculoId;
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
        if (!(object instanceof EncabezadoTabla)) {
            return false;
        }
        EncabezadoTabla other = (EncabezadoTabla) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.EncabezadoTabla[ id=" + id + " ]";
    }
    
}
