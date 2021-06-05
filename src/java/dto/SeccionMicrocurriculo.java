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
@Table(name = "seccion_microcurriculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeccionMicrocurriculo.findAll", query = "SELECT s FROM SeccionMicrocurriculo s"),
    @NamedQuery(name = "SeccionMicrocurriculo.findById", query = "SELECT s FROM SeccionMicrocurriculo s WHERE s.seccionMicrocurriculoPK.id = :id"),
    @NamedQuery(name = "SeccionMicrocurriculo.findByCodigoMateria", query = "SELECT s FROM SeccionMicrocurriculo s WHERE s.seccionMicrocurriculoPK.codigoMateria = :codigoMateria"),
    @NamedQuery(name = "SeccionMicrocurriculo.findByEditable", query = "SELECT s FROM SeccionMicrocurriculo s WHERE s.editable = :editable")})
public class SeccionMicrocurriculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SeccionMicrocurriculoPK seccionMicrocurriculoPK;
    @Basic(optional = false)
    @Column(name = "editable")
    private short editable;
    @JoinColumn(name = "codigo_materia", referencedColumnName = "codigo_materia", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Microcurriculo microcurriculo;
    @JoinColumn(name = "seccion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Seccion seccionId;
    @JoinColumn(name = "tipo_seccion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoSeccion tipoSeccionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seccionMicrocurriculo")
    private List<Contenido> contenidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seccionMicrocurriculo")
    private List<SeccionCambio> seccionCambioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seccionMicrocurriculo1")
    private List<SeccionCambio> seccionCambioList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seccionMicrocurriculo")
    private List<TablaMicrocurriculo> tablaMicrocurriculoList;

    public SeccionMicrocurriculo() {
    }

    public SeccionMicrocurriculo(SeccionMicrocurriculoPK seccionMicrocurriculoPK) {
        this.seccionMicrocurriculoPK = seccionMicrocurriculoPK;
    }

    public SeccionMicrocurriculo(SeccionMicrocurriculoPK seccionMicrocurriculoPK, short editable) {
        this.seccionMicrocurriculoPK = seccionMicrocurriculoPK;
        this.editable = editable;
    }

    public SeccionMicrocurriculo(int id, int codigoMateria) {
        this.seccionMicrocurriculoPK = new SeccionMicrocurriculoPK(id, codigoMateria);
    }

    public SeccionMicrocurriculoPK getSeccionMicrocurriculoPK() {
        return seccionMicrocurriculoPK;
    }

    public void setSeccionMicrocurriculoPK(SeccionMicrocurriculoPK seccionMicrocurriculoPK) {
        this.seccionMicrocurriculoPK = seccionMicrocurriculoPK;
    }

    public short getEditable() {
        return editable;
    }

    public void setEditable(short editable) {
        this.editable = editable;
    }

    public Microcurriculo getMicrocurriculo() {
        return microcurriculo;
    }

    public void setMicrocurriculo(Microcurriculo microcurriculo) {
        this.microcurriculo = microcurriculo;
    }

    public Seccion getSeccionId() {
        return seccionId;
    }

    public void setSeccionId(Seccion seccionId) {
        this.seccionId = seccionId;
    }

    public TipoSeccion getTipoSeccionId() {
        return tipoSeccionId;
    }

    public void setTipoSeccionId(TipoSeccion tipoSeccionId) {
        this.tipoSeccionId = tipoSeccionId;
    }

    @XmlTransient
    public List<Contenido> getContenidoList() {
        return contenidoList;
    }

    public void setContenidoList(List<Contenido> contenidoList) {
        this.contenidoList = contenidoList;
    }

    @XmlTransient
    public List<SeccionCambio> getSeccionCambioList() {
        return seccionCambioList;
    }

    public void setSeccionCambioList(List<SeccionCambio> seccionCambioList) {
        this.seccionCambioList = seccionCambioList;
    }

    @XmlTransient
    public List<SeccionCambio> getSeccionCambioList1() {
        return seccionCambioList1;
    }

    public void setSeccionCambioList1(List<SeccionCambio> seccionCambioList1) {
        this.seccionCambioList1 = seccionCambioList1;
    }

    @XmlTransient
    public List<TablaMicrocurriculo> getTablaMicrocurriculoList() {
        return tablaMicrocurriculoList;
    }

    public void setTablaMicrocurriculoList(List<TablaMicrocurriculo> tablaMicrocurriculoList) {
        this.tablaMicrocurriculoList = tablaMicrocurriculoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seccionMicrocurriculoPK != null ? seccionMicrocurriculoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeccionMicrocurriculo)) {
            return false;
        }
        SeccionMicrocurriculo other = (SeccionMicrocurriculo) object;
        if ((this.seccionMicrocurriculoPK == null && other.seccionMicrocurriculoPK != null) || (this.seccionMicrocurriculoPK != null && !this.seccionMicrocurriculoPK.equals(other.seccionMicrocurriculoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.SeccionMicrocurriculo[ seccionMicrocurriculoPK=" + seccionMicrocurriculoPK + " ]";
    }
    
}
