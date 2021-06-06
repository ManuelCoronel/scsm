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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Manuel
 */
@Entity
@Table(name = "materia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materia.findAll", query = "SELECT m FROM Materia m"),
    @NamedQuery(name = "Materia.findByCodigoMateria", query = "SELECT m FROM Materia m WHERE m.codigoMateria = :codigoMateria"),
    @NamedQuery(name = "Materia.findByNombre", query = "SELECT m FROM Materia m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Materia.findByCreditos", query = "SELECT m FROM Materia m WHERE m.creditos = :creditos"),
    @NamedQuery(name = "Materia.findBySemestre", query = "SELECT m FROM Materia m WHERE m.semestre = :semestre"),
    @NamedQuery(name = "Materia.findByHt", query = "SELECT m FROM Materia m WHERE m.ht = :ht"),
    @NamedQuery(name = "Materia.findByHp", query = "SELECT m FROM Materia m WHERE m.hp = :hp"),
    @NamedQuery(name = "Materia.findByHti", query = "SELECT m FROM Materia m WHERE m.hti = :hti"),
    @NamedQuery(name = "Materia.findByCr", query = "SELECT m FROM Materia m WHERE m.cr = :cr")})
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo_materia")
    private Integer codigoMateria;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "creditos")
    private int creditos;
    @Basic(optional = false)
    @Column(name = "semestre")
    private int semestre;
    @Basic(optional = false)
    @Column(name = "ht")
    private int ht;
    @Basic(optional = false)
    @Column(name = "hp")
    private int hp;
    @Basic(optional = false)
    @Column(name = "hti")
    private int hti;
    @Column(name = "cr")
    private Integer cr;
    @JoinTable(name = "prerrequisito_materia", joinColumns = {
        @JoinColumn(name = "materia_codigo_materia", referencedColumnName = "codigo_materia")}, inverseJoinColumns = {
        @JoinColumn(name = "materia_codigo_prerrequisito", referencedColumnName = "codigo_materia")})
    @ManyToMany
    private List<Materia> materiaList;
    @ManyToMany(mappedBy = "materiaList")
    private List<Materia> materiaList1;
    @JoinColumns({
        @JoinColumn(name = "pensum_codigo", referencedColumnName = "codigo"),
        @JoinColumn(name = "pensum_programa_codigo", referencedColumnName = "programa_codigo")})
    @ManyToOne(optional = false)
    private Pensum pensum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materia")
    private List<EquivalenciaMateria> equivalenciaMateriaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "materia")
    private Microcurriculo microcurriculo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materia")
    private List<MateriaPeriodo> materiaPeriodoList;

    public Materia() {
    }

    public Materia(Integer codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public Materia(Integer codigoMateria, String nombre, int creditos, int semestre, int ht, int hp, int hti) {
        this.codigoMateria = codigoMateria;
        this.nombre = nombre;
        this.creditos = creditos;
        this.semestre = semestre;
        this.ht = ht;
        this.hp = hp;
        this.hti = hti;
    }

    public Integer getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(Integer codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getHt() {
        return ht;
    }

    public void setHt(int ht) {
        this.ht = ht;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHti() {
        return hti;
    }

    public void setHti(int hti) {
        this.hti = hti;
    }

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
    }

    @XmlTransient
    public List<Materia> getMateriaList() {
        return materiaList;
    }

    public void setMateriaList(List<Materia> materiaList) {
        this.materiaList = materiaList;
    }

    @XmlTransient
    public List<Materia> getMateriaList1() {
        return materiaList1;
    }

    public void setMateriaList1(List<Materia> materiaList1) {
        this.materiaList1 = materiaList1;
    }

    public Pensum getPensum() {
        return pensum;
    }

    public void setPensum(Pensum pensum) {
        this.pensum = pensum;
    }

    @XmlTransient
    public List<EquivalenciaMateria> getEquivalenciaMateriaList() {
        return equivalenciaMateriaList;
    }

    public void setEquivalenciaMateriaList(List<EquivalenciaMateria> equivalenciaMateriaList) {
        this.equivalenciaMateriaList = equivalenciaMateriaList;
    }

    public Microcurriculo getMicrocurriculo() {
        return microcurriculo;
    }

    public void setMicrocurriculo(Microcurriculo microcurriculo) {
        this.microcurriculo = microcurriculo;
    }

    @XmlTransient
    public List<MateriaPeriodo> getMateriaPeriodoList() {
        return materiaPeriodoList;
    }

    public void setMateriaPeriodoList(List<MateriaPeriodo> materiaPeriodoList) {
        this.materiaPeriodoList = materiaPeriodoList;
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
        if (!(object instanceof Materia)) {
            return false;
        }
        Materia other = (Materia) object;
        if ((this.codigoMateria == null && other.codigoMateria != null) || (this.codigoMateria != null && !this.codigoMateria.equals(other.codigoMateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Materia[ codigoMateria=" + codigoMateria + " ]";
    }
    
}
