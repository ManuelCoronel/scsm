/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import dao.ContenidoJpaController;

import dao.MicrocurriculoJpaController;
import dao.SeccionJpaController;
import dao.SeccionMicrocurriculoJpaController;
import dao.TablaMicrocurriculoJpaController;
import dao.exceptions.NonexistentEntityException;
import dto.AreaFormacion;
import dto.Contenido;
import dto.Materia;
import dto.Microcurriculo;
import dto.Pensum;
import dto.Seccion;
import dto.SeccionMicrocurriculo;
import dto.TablaMicrocurriculo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import util.Conexion;
import util.MyConnection;

/**
 *
 * @author Manuel
 */
public class AdministrarMicrocurriculo {
    public AdministrarMicrocurriculo() {
    }

    public ArrayList<dto.Microcurriculo> obtenerMicrocurriculosPensum(int codigo, int programaCodigo) {

        Conexion con = Conexion.getConexion();
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

    public void registrarMicrocurriculos(Pensum pensum) throws Exception {
        EntityManagerFactory em = Conexion.getConexion().getBd();
        SeccionJpaController tjpa = new SeccionJpaController(em);
        SeccionMicrocurriculoJpaController sjpa = new SeccionMicrocurriculoJpaController(em);
        ContenidoJpaController cjpa = new ContenidoJpaController(em);
        TablaMicrocurriculoJpaController tmjpa = new TablaMicrocurriculoJpaController(Conexion.getConexion().getBd());
        List<Seccion> secciones = tjpa.findSeccionEntities();
        List<Materia> materias = pensum.getMateriaList();
        MicrocurriculoJpaController mjpa = new MicrocurriculoJpaController(em);
        int id=1;
        for (Materia m : materias) {
            Microcurriculo micro = new Microcurriculo(id++, m.getMateriaPK().getCodigoMateria(), m.getMateriaPK().getPensumCodigo());
            micro.setAreaDeFormacionId(new AreaFormacion(1));
            micro.setMateria(m);
            micro.setSeccionMicrocurriculoList(null);

            mjpa.create(micro);
            
            getDefaultSecciones(micro, secciones, tjpa, sjpa, cjpa, tmjpa);
        }
    }

    private void getDefaultSecciones(Microcurriculo micro, List<Seccion> secciones,SeccionJpaController tjpa, SeccionMicrocurriculoJpaController sjpa, ContenidoJpaController cjpa, TablaMicrocurriculoJpaController tmjpa) throws NonexistentEntityException, Exception {
        List<SeccionMicrocurriculo> seccionesDefault = new ArrayList<>();
        
        for (Seccion t : secciones) {
            SeccionMicrocurriculo s = new SeccionMicrocurriculo();
            short a = 0;
            s.setEditable(a);
            s.setMicrocurriculo(micro);
            if (t.getTipoSeccionId().getId() == 1) {
                s.setSeccionId(t);
                sjpa.create(s);
                Contenido c = new Contenido();
                c.setCantidadItemsLista(0);
                c.setSeccionMicrocurriculoId(s);
                c.setTexto("Empty text");
                cjpa.create(c);
            } else {
                s.setSeccionId(t);
                sjpa.create(s);
                TablaMicrocurriculo tm = new TablaMicrocurriculo();
                tm.setCantidadFilas(a);
                tm.setSeccionMicrocurriculoCodigoMateria(micro.getMateria().getMateriaPK().getCodigoMateria());
                tm.setSeccionMicrocurriculoId(s);
                tmjpa.create(tm);
            }
            seccionesDefault.add(s);
        }
        micro.setSeccionMicrocurriculoList(seccionesDefault);
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
