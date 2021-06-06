/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.ArrayList;
import util.Conexion;

/**
 *
 * @author Manuel
 */
public class Microcurriculo {

    public Microcurriculo() {
    }
    
    public ArrayList<dto.Microcurriculo> obtenerTodosMicrocurriculos(int idPrograma){
        
        Conexion con = Conexion.getConexion();
        dao.ProgramaJpaController daoPrograma = new dao.ProgramaJpaController(con.getBd());
        dto.Programa programa = daoPrograma.findPrograma(idPrograma);
       
        
    return null;
    }
    
  
    
}
