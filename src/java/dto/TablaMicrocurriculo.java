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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    @NamedQuery(name = "TablaMicrocurriculo.findAll", query = "SELECT t FROM TablaMicrocurriculo t")
    , @NamedQuery(name = "TablaMicrocurriculo.findById", query = "SELECT t FROM TablaMicrocurriculo t WHERE t.tablaMicrocurriculoPK.id = :id")
    , @NamedQuery(name = "TablaMicrocurriculo.findByCantidadFilas", query = "SELECT t FROM TablaMicrocurriculo t WHERE t.cantidadFilas = :cantidadFilas")
    , @NamedQuery(name = "TablaMicrocurriculo.findBySeccionMicrocurriculoId", query = "SELECT t FROM TablaMicrocurriculo t WHERE t.tablaMicrocurriculoPK.seccionMicrocurriculoId = :seccionMicrocurriculoId")
    , @NamedQuery(name = "TablaMicrocurriculo.findByCantColumnas", query = "SELECT t FROM TablaMicrocurriculo t WHERE t.cantColumnas = :cantColumnas")})
public class TablaMicrocurriculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TablaMicrocurriculoPK tablaMicrocurriculoPK;
    @Basic(optional = false)
    @Column(name = "cantidad_filas")
    private int cantidadFilas;
    @Basic(optional = false)
    @Column(name = "cant_columnas")
    private int cantColumnas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tablaMicrocurriculo")
    private List<TablaMicrocurriculoInfo> tablaMicrocurriculoInfoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTabla")
    private List<EncabezadoTabla> encabezadoTablaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeccion")
    private List<EncabezadoTabla> encabezadoTablaList1;
    @JoinColumn(name = "seccion_microcurriculo_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SeccionMicrocurriculo seccionMicrocurriculo;

    public TablaMicrocurriculo() {
    }

    public TablaMicrocurriculo(TablaMicrocurriculoPK tablaMicrocurriculoPK) {
        this.tablaMicrocurriculoPK = tablaMicrocurriculoPK;
    }

    public TablaMicrocurriculo(TablaMicrocurriculoPK tablaMicrocurriculoPK, int cantidadFilas, int cantColumnas) {
        this.tablaMicrocurriculoPK = tablaMicrocurriculoPK;
        this.cantidadFilas = cantidadFilas;
        this.cantColumnas = cantColumnas;
    }

    public TablaMicrocurriculo(int id, int seccionMicrocurriculoId) {
        this.tablaMicrocurriculoPK = new TablaMicrocurriculoPK(id, seccionMicrocurriculoId);
    }

    public TablaMicrocurriculoPK getTablaMicrocurriculoPK() {
        return tablaMicrocurriculoPK;
    }

    public void setTablaMicrocurriculoPK(TablaMicrocurriculoPK tablaMicrocurriculoPK) {
        this.tablaMicrocurriculoPK = tablaMicrocurriculoPK;
    }

    public int getCantidadFilas() {
        return cantidadFilas;
    }

    public void setCantidadFilas(int cantidadFilas) {
        this.cantidadFilas = cantidadFilas;
    }

    public int getCantColumnas() {
        return cantColumnas;
    }

    public void setCantColumnas(int cantColumnas) {
        this.cantColumnas = cantColumnas;
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

    @XmlTransient
    public List<EncabezadoTabla> getEncabezadoTablaList1() {
        return encabezadoTablaList1;
    }

    public void setEncabezadoTablaList1(List<EncabezadoTabla> encabezadoTablaList1) {
        this.encabezadoTablaList1 = encabezadoTablaList1;
    }

    public SeccionMicrocurriculo getSeccionMicrocurriculo() {
        return seccionMicrocurriculo;
    }

    public void setSeccionMicrocurriculo(SeccionMicrocurriculo seccionMicrocurriculo) {
        this.seccionMicrocurriculo = seccionMicrocurriculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tablaMicrocurriculoPK != null ? tablaMicrocurriculoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TablaMicrocurriculo)) {
            return false;
        }
        TablaMicrocurriculo other = (TablaMicrocurriculo) object;
        if ((this.tablaMicrocurriculoPK == null && other.tablaMicrocurriculoPK != null) || (this.tablaMicrocurriculoPK != null && !this.tablaMicrocurriculoPK.equals(other.tablaMicrocurriculoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.TablaMicrocurriculo[ tablaMicrocurriculoPK=" + tablaMicrocurriculoPK + " ]";
    }
    
}
