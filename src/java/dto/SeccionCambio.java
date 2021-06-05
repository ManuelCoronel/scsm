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
import javax.persistence.JoinColumns;
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
@Table(name = "seccion_cambio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeccionCambio.findAll", query = "SELECT s FROM SeccionCambio s"),
    @NamedQuery(name = "SeccionCambio.findById", query = "SELECT s FROM SeccionCambio s WHERE s.id = :id")})
public class SeccionCambio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "cambio_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cambio cambioId;
    @JoinColumns({
        @JoinColumn(name = "seccion_id_nueva", referencedColumnName = "id"),
        @JoinColumn(name = "seccion_codigo_materia_nueva", referencedColumnName = "codigo_materia")})
    @ManyToOne(optional = false)
    private SeccionMicrocurriculo seccionMicrocurriculo;
    @JoinColumns({
        @JoinColumn(name = "seccion_id_antigua", referencedColumnName = "id"),
        @JoinColumn(name = "seccion_codigo_materia", referencedColumnName = "codigo_materia")})
    @ManyToOne(optional = false)
    private SeccionMicrocurriculo seccionMicrocurriculo1;

    public SeccionCambio() {
    }

    public SeccionCambio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cambio getCambioId() {
        return cambioId;
    }

    public void setCambioId(Cambio cambioId) {
        this.cambioId = cambioId;
    }

    public SeccionMicrocurriculo getSeccionMicrocurriculo() {
        return seccionMicrocurriculo;
    }

    public void setSeccionMicrocurriculo(SeccionMicrocurriculo seccionMicrocurriculo) {
        this.seccionMicrocurriculo = seccionMicrocurriculo;
    }

    public SeccionMicrocurriculo getSeccionMicrocurriculo1() {
        return seccionMicrocurriculo1;
    }

    public void setSeccionMicrocurriculo1(SeccionMicrocurriculo seccionMicrocurriculo1) {
        this.seccionMicrocurriculo1 = seccionMicrocurriculo1;
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
        if (!(object instanceof SeccionCambio)) {
            return false;
        }
        SeccionCambio other = (SeccionCambio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.SeccionCambio[ id=" + id + " ]";
    }
    
}
