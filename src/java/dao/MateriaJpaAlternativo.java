/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Materia;
import dto.Pensum;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author dunke
 */
public class MateriaJpaAlternativo {
    private Connection connection;

    public MateriaJpaAlternativo(Connection connection) {
        this.connection = connection;
    }
    
    public void create(Pensum pensum) throws SQLException{
        PreparedStatement ps = this.connection.prepareStatement
        ("INSERT INTO materia(codigo_materia, nombre, creditos, semestre, pensum_codigo, "
                           + "pensum_programa_codigo, ht, hp, hti, cr) VALUES (?,?,?,?,?,?,?,?,?,?)");
        for(Materia m: pensum.getMateriaList()){
            ps.setInt(1, m.getCodigoMateria());
            ps.setString(2, m.getNombre());
            ps.setInt(3, m.getCreditos());
            ps.setInt(4, m.getSemestre());
            ps.setInt(5, pensum.getPensumPK().getCodigo());
            ps.setInt(6, pensum.getPensumPK().getProgramaCodigo());
            ps.setInt(7, m.getHt());
            ps.setInt(8, m.getHp());
            ps.setInt(9, m.getHti());
            ps.setInt(10, m.getCr());
            ps.execute();
        }
        
        ps = this.connection.prepareStatement("INSERT INTO prerrequisito_materia(materia_codigo_materia, materia_codigo_prerrequisito) VALUES (?,?)");
        for(Materia m: pensum.getMateriaList()){
            for(Materia m_r: m.getMateriaList()){
                ps.setInt(1, m.getCodigoMateria());
                ps.setInt(2, m_r.getCodigoMateria());
                ps.execute();
            }
        }
        
        ps = this.connection.prepareStatement("INSERT INTO equivalencia_materia(materia_codigo_materia, equivalencia_materia, nombre) VALUES (?,?,?)");
        for(Materia m: pensum.getMateriaList()){
            for(Materia m_p: m.getMateriaList1()){
                ps.setInt(1, m.getCodigoMateria());
                ps.setInt(2, m_p.getCodigoMateria());
                ps.setString(3, m_p.getNombre());
                ps.execute();
            }
        }
        
        this.connection.close();
    }
}
