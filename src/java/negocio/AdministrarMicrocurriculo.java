/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import dto.Materia;
import dto.Pensum;
import java.util.ArrayList;
import util.Conexion;

/**
 *
 * @author Manuel
 */
public class AdministrarMicrocurriculo {
    
    public AdministrarMicrocurriculo() {
    }
    
//       public ArrayList<dto.Microcurriculo> obtenerMicrocurriculosPensum(int idPrograma,int codigo, int programaCodigo) {
//        
//        Conexion con = Conexion.getConexion();
//        dao.ProgramaJpaController daoPrograma = new dao.ProgramaJpaController(con.getBd());
//        dto.Programa programa = daoPrograma.findPrograma(idPrograma);
//        ArrayList<dto.Pensum> pensum 
//        ArrayList<dto.Microcurriculo> microcurriculos = new ArrayList<>();
//        for (Pensum pensum : pensums) {
//            ArrayList<dto.Materia> materias = (ArrayList<dto.Materia>) pensum.getMateriaList();
//            for (Materia materia : materias) {
//                microcurriculos.add(materia.getMicrocurriculoList().get(0));
//            }
//        }
//        
//        return microcurriculos;
//    }
    
    public ArrayList<dto.Microcurriculo> obtenerTodosMicrocurriculos(int idPrograma) {
        
        Conexion con = Conexion.getConexion();
        dao.ProgramaJpaController daoPrograma = new dao.ProgramaJpaController(con.getBd());
        dto.Programa programa = daoPrograma.findPrograma(idPrograma);
        ArrayList<dto.Pensum> pensums = (ArrayList<dto.Pensum>) programa.getPensumList();
        ArrayList<dto.Microcurriculo> microcurriculos = new ArrayList<>();
        for (Pensum pensum : pensums) {
            ArrayList<dto.Materia> materias = (ArrayList<dto.Materia>) pensum.getMateriaList();
            for (Materia materia : materias) {
                microcurriculos.add(materia.getMicrocurriculoList().get(0));
            }
        }
        
        return microcurriculos;
    }
    
}
