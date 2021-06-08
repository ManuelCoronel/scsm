/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;
import dto.Materia;
import dto.Pensum;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;

/**
 *
 * @author Manuel
 */
public class AdministrarMicrocurriculo {
    public AdministrarMicrocurriculo() {
    }

    public ArrayList<dto.Microcurriculo> obtenerMicrocurriculosPensum(int codigo, int programaCodigo) {
        AdministrarPensum administrarPensum = new AdministrarPensum();
        dto.Pensum pensum = administrarPensum.obtenerPensum(codigo, programaCodigo);

        ArrayList<dto.Microcurriculo> microcurriculos = new ArrayList<>();
        for (Materia materia : pensum.getMateriaList()) {
            microcurriculos.add(materia.getMicrocurriculoList().get(0));
        }

        return microcurriculos; 
    }

    public dto.Microcurriculo obtenerMicrocurriculo(int idMicrocurriculo, int codigoMateria, int codigoPensum) {
        Conexion con = Conexion.getConexion();
        dao.MicrocurriculoJpaController daoMicrocurriculo = new dao.MicrocurriculoJpaController(con.getBd());
       dto.MicrocurriculoPK microcurriculoPK = new dto.MicrocurriculoPK(idMicrocurriculo, codigoMateria, codigoPensum);
        return daoMicrocurriculo.findMicrocurriculo(microcurriculoPK);
    }

    public ArrayList<dto.Microcurriculo> obtenerTodosMicrocurriculos(int idPrograma) {

        Conexion con = Conexion.getConexion();
        dao.ProgramaJpaController daoPrograma = new dao.ProgramaJpaController(con.getBd());
        dto.Programa programa = daoPrograma.findPrograma(idPrograma);
        List<dto.Pensum> pensums = programa.getPensumList();
        ArrayList<dto.Microcurriculo> microcurriculos = new ArrayList<>();
        for (Pensum pensum : pensums) {
            List<dto.Materia> materias = pensum.getMateriaList();
            for (Materia materia : materias) {

                try {
                    microcurriculos.add(materia.getMicrocurriculoList().get(0));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }

        return microcurriculos;
    }

    public void registrarMicrocurriculos(Pensum pensum){
        new RegistroMicrocurriculoBackground(pensum).start();
    }
    
    public List<dto.AreaFormacion> obtenerAreasFormacion(){
        Conexion con = Conexion.getConexion();
        dao.AreaFormacionJpaController daoAreasFormacion = new dao.AreaFormacionJpaController(con.getBd());
      
        return daoAreasFormacion.findAreaFormacionEntities();
    }
    
    public List<dto.TipoAsignatura> obtenerTiposAisgnatura(){
        Conexion con = Conexion.getConexion();
        dao.TipoAsignaturaJpaController daoTipoAsignatura = new dao.TipoAsignaturaJpaController(con.getBd());
        return daoTipoAsignatura.findTipoAsignaturaEntities();
    }
    
}
