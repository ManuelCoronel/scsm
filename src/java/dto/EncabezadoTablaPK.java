/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Manuel
 */
@Embeddable
public class EncabezadoTablaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "columna")
    private int columna;
    @Basic(optional = false)
    @Column(name = "tabla_microcurriculo_id")
    private int tablaMicrocurriculoId;

    public EncabezadoTablaPK() {
    }

    public EncabezadoTablaPK(int columna, int tablaMicrocurriculoId) {
        this.columna = columna;
        this.tablaMicrocurriculoId = tablaMicrocurriculoId;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getTablaMicrocurriculoId() {
        return tablaMicrocurriculoId;
    }

    public void setTablaMicrocurriculoId(int tablaMicrocurriculoId) {
        this.tablaMicrocurriculoId = tablaMicrocurriculoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) columna;
        hash += (int) tablaMicrocurriculoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncabezadoTablaPK)) {
            return false;
        }
        EncabezadoTablaPK other = (EncabezadoTablaPK) object;
        if (this.columna != other.columna) {
            return false;
        }
        if (this.tablaMicrocurriculoId != other.tablaMicrocurriculoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.EncabezadoTablaPK[ columna=" + columna + ", tablaMicrocurriculoId=" + tablaMicrocurriculoId + " ]";
    }
    
}
