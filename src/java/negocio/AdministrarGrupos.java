/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import dto.MateriaPeriodo;
import dto.MateriaPeriodoPK;
import java.util.List;
import util.Conexion;

/**
 *
 * @author Manuel
 */
public class AdministrarGrupos {

    public AdministrarGrupos() {
    }

    public List<dto.Docente> obtenerDocentes(dto.Programa programa) {
        return programa.getDepartamentoId().getDocenteList();
    }

    public boolean verificarMateria(int anio, int semestre, int codigoMateria, int codigoPensum) {
        Conexion con = Conexion.getConexion();
        dao.MateriaPeriodoJpaController materiaPeriodoDao =  new dao.MateriaPeriodoJpaController(con.getBd());
      //  dto.MateriaPeriodoPK materiaPeriodo = new MateriaPeriodoPK(anio, semestre, codigoMateria, codigoPensum)

   return true;
    }

}
