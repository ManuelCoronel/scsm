/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "encabezado_tabla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncabezadoTabla.findAll", query = "SELECT e FROM EncabezadoTabla e"),
    @NamedQuery(name = "EncabezadoTabla.findByNombre", query = "SELECT e FROM EncabezadoTabla e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "EncabezadoTabla.findByColumna", query = "SELECT e FROM EncabezadoTabla e WHERE e.encabezadoTablaPK.columna = :columna"),
    @NamedQuery(name = "EncabezadoTabla.findByTablaMicrocurriculoId", query = "SELECT e FROM EncabezadoTabla e WHERE e.encabezadoTablaPK.tablaMicrocurriculoId = :tablaMicrocurriculoId")})
public class EncabezadoTabla implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EncabezadoTablaPK encabezadoTablaPK;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "tabla_microcurriculo_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TablaMicrocurriculo tablaMicrocurriculo;

    public EncabezadoTabla() {
    }

    public EncabezadoTabla(EncabezadoTablaPK encabezadoTablaPK) {
        this.encabezadoTablaPK = encabezadoTablaPK;
    }

    public EncabezadoTabla(EncabezadoTablaPK encabezadoTablaPK, String nombre) {
        this.encabezadoTablaPK = encabezadoTablaPK;
        this.nombre = nombre;
    }

    public EncabezadoTabla(int columna, int tablaMicrocurriculoId) {
        this.encabezadoTablaPK = new EncabezadoTablaPK(columna, tablaMicrocurriculoId);
    }

    public EncabezadoTablaPK getEncabezadoTablaPK() {
        return encabezadoTablaPK;
    }

    public void setEncabezadoTablaPK(EncabezadoTablaPK encabezadoTablaPK) {
        this.encabezadoTablaPK = encabezadoTablaPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TablaMicrocurriculo getTablaMicrocurriculo() {
        return tablaMicrocurriculo;
    }

    public void setTablaMicrocurriculo(TablaMicrocurriculo tablaMicrocurriculo) {
        this.tablaMicrocurriculo = tablaMicrocurriculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (encabezadoTablaPK != null ? encabezadoTablaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncabezadoTabla)) {
            return false;
        }
        EncabezadoTabla other = (EncabezadoTabla) object;
        if ((this.encabezadoTablaPK == null && other.encabezadoTablaPK != null) || (this.encabezadoTablaPK != null && !this.encabezadoTablaPK.equals(other.encabezadoTablaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.EncabezadoTabla[ encabezadoTablaPK=" + encabezadoTablaPK + " ]";
    }
    
}
