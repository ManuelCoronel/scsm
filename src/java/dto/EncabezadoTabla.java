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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
    @NamedQuery(name = "EncabezadoTabla.findAll", query = "SELECT e FROM EncabezadoTabla e")
    , @NamedQuery(name = "EncabezadoTabla.findById", query = "SELECT e FROM EncabezadoTabla e WHERE e.id = :id")})
public class EncabezadoTabla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_encabezado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Encabezado idEncabezado;
    @JoinColumn(name = "id_tabla", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TablaMicrocurriculo idTabla;

    public EncabezadoTabla() {
    }

    public EncabezadoTabla(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Encabezado getIdEncabezado() {
        return idEncabezado;
    }

    public void setIdEncabezado(Encabezado idEncabezado) {
        this.idEncabezado = idEncabezado;
    }

    public TablaMicrocurriculo getIdTabla() {
        return idTabla;
    }

    public void setIdTabla(TablaMicrocurriculo idTabla) {
        this.idTabla = idTabla;
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
