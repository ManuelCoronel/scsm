/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import dao.ContenidoJpaController;
import dao.EncabezadoTablaJpaController;
import dao.MicrocurriculoJpaController;
import dao.SeccionJpaController;
import dao.SeccionMicrocurriculoJpaController;
import dao.TablaMicrocurriculoJpaController;
import dao.TipoAsignaturaJpaController;
import dao.TipoSeccionJpaController;
import dao.exceptions.NonexistentEntityException;
import dto.AreaFormacion;
import dto.Contenido;
import dto.EncabezadoTabla;
import dto.EncabezadoTablaPK;
import dto.Materia;
import dto.Microcurriculo;
import dto.Pensum;
import dto.Seccion;
import dto.SeccionMicrocurriculo;
import dto.TablaMicrocurriculo;
import dto.TipoSeccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import util.MyConnection;

/**
 *
 * @author Manuel
 */
public class AdministrarMicrocurriculo {

    private static final String COLUMNAS[][] = {
        {"Unidad No", "Nombre de las Unidades", "Trabajo Presencial", "Trabajo Independiente", "Horas Totales"},
        {"Contenidos por unidades", "Actividades presenciales", "Trabajo Independiente"}
    };

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
    
       public dto.Microcurriculo obtenerMicrocurriculo(){
           return null;
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
                
                try{
                  microcurriculos.add(materia.getMicrocurriculoList().get(0));
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
                
            
            }
        }

        return microcurriculos;
    }

    public void registrarMicrocurriculos(Pensum pensum) throws Exception {
        System.out.println("Se fue");
        List<Materia> materias = pensum.getMateriaList();
        MicrocurriculoJpaController mjpa = new MicrocurriculoJpaController(Conexion.getConexion().getBd());
        for (Materia m : materias) {
            Microcurriculo micro = new Microcurriculo();
            micro.setAreaDeFormacionId(new AreaFormacion(1));
            micro.setMateria(m);
            micro.setSeccionMicrocurriculoList(null);

            mjpa.create(micro);
            Connection con = MyConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT id FROM microcurriculo WHERE materia_codigo_materia=? AND materia_pensum_codigo=?");
            ps.setInt(1, m.getMateriaPK().getCodigoMateria());
            ps.setInt(2, m.getMateriaPK().getPensumCodigo());
            ResultSet rs = ps.executeQuery();
            rs.next();//Siempre lo encontrara
            micro.setId(rs.getInt("id"));
            con.close();
            
            getDefaultSecciones(micro);
        }
        System.out.println("fin vida hpta");
    }

    private void getDefaultSecciones(Microcurriculo micro) throws NonexistentEntityException, Exception {
        System.out.println("llego a secciones");
        List<SeccionMicrocurriculo> seccionesDefault = new ArrayList<>();
        SeccionJpaController tjpa = new SeccionJpaController(Conexion.getConexion().getBd());
        SeccionMicrocurriculoJpaController sjpa = new SeccionMicrocurriculoJpaController(Conexion.getConexion().getBd());
        ContenidoJpaController cjpa = new ContenidoJpaController(Conexion.getConexion().getBd());
        TablaMicrocurriculoJpaController tmjpa = new TablaMicrocurriculoJpaController(Conexion.getConexion().getBd());
        EncabezadoTablaJpaController etjpa = new EncabezadoTablaJpaController(Conexion.getConexion().getBd());
        List<Seccion> secciones = tjpa.findSeccionEntities();
        for (Seccion t : secciones) {
            SeccionMicrocurriculo s = new SeccionMicrocurriculo();
            s.setMicrocurriculoId(micro);
            short a = 0;
            s.setEditable(a);
            s.setMicrocurriculoId(micro);
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
                List<EncabezadoTabla> encabezados = new ArrayList<>();
                if (t.getId() == 1) {
                    getEncabezados(etjpa, tm, encabezados, 0);
                } else {
                    getEncabezados(etjpa, tm, encabezados, 1);
                }
                tm.setEncabezadoTablaList(encabezados);
                tmjpa.edit(tm);
            }
            seccionesDefault.add(s);
        }
        micro.setSeccionMicrocurriculoList(seccionesDefault);
    }

    private void getEncabezados(EncabezadoTablaJpaController etjpa, TablaMicrocurriculo tm, List<EncabezadoTabla> encabezados, int columnas) throws Exception {
        int i = 0;
        for (String enca : COLUMNAS[columnas]) {
            EncabezadoTabla et = new EncabezadoTabla(new EncabezadoTablaPK(i++, tm.getId()), enca);
            et.setTablaMicrocurriculo(tm);
            etjpa.create(et);
            encabezados.add(et);
        }
    }
}
