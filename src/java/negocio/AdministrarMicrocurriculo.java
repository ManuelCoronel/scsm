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
import java.util.ArrayList;
import java.util.List;
import util.Conexion;

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

    public ArrayList<dto.Microcurriculo> obtenerTodosMicrocurriculos(int idPrograma) {

        Conexion con = Conexion.getConexion();
        dao.ProgramaJpaController daoPrograma = new dao.ProgramaJpaController(con.getBd());
        dto.Programa programa = daoPrograma.findPrograma(idPrograma);
        List<dto.Pensum> pensums = programa.getPensumList();
        ArrayList<dto.Microcurriculo> microcurriculos = new ArrayList<>();
        for (Pensum pensum : pensums) {
            List<dto.Materia> materias = pensum.getMateriaList();
            for (Materia materia : materias) {
                microcurriculos.add(materia.getMicrocurriculoList().get(0));
            }
        }

        return microcurriculos;
    }

    public void registrarMicrocurriculos(Pensum pensum) throws Exception {
        System.out.println("Se fue");
        List<Materia> materias = pensum.getMateriaList();
        MicrocurriculoJpaController mjpa = new MicrocurriculoJpaController(Conexion.getConexion().getBd());
        TipoAsignaturaJpaController tjpa = new TipoAsignaturaJpaController(Conexion.getConexion().getBd());
        for (Materia m : materias) {
            Microcurriculo micro = new Microcurriculo();
            micro.setAreaDeFormacionId(new AreaFormacion(1));
            micro.setMateria(m);
            micro.setTipoAsignaturaId(tjpa.findTipoAsignatura(m.getTyper() ? 2 : 1));
            micro.setSeccionMicrocurriculoList(null);

            mjpa.create(micro);
            micro = mjpa.findMicrocurriculo(mjpa.getMicrocurriculoCount());

            getDefaultSecciones(micro);
            mjpa.edit(micro);
        }
    }

    private void getDefaultSecciones(Microcurriculo micro) throws NonexistentEntityException, Exception {
        System.out.println("llego a secciones");
        List<SeccionMicrocurriculo> seccionesDefault = new ArrayList<>();
        SeccionJpaController tjpa = new SeccionJpaController(Conexion.getConexion().getBd());
        SeccionMicrocurriculoJpaController sjpa = new SeccionMicrocurriculoJpaController(Conexion.getConexion().getBd());
        ContenidoJpaController cjpa = new ContenidoJpaController(Conexion.getConexion().getBd());
        TablaMicrocurriculoJpaController tmjpa = new TablaMicrocurriculoJpaController(Conexion.getConexion().getBd());
        List<Seccion> secciones = tjpa.findSeccionEntities();
        for (Seccion t : secciones) {
            SeccionMicrocurriculo s = new SeccionMicrocurriculo();
            s.setMicrocurriculoId(micro);
            short a = 0;
            s.setEditable(a);
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
                    getEncabezados(tm, encabezados, 0);
                } else {
                    getEncabezados(tm, encabezados, 1);
                }
                tm.setEncabezadoTablaList(encabezados);
                tmjpa.edit(tm);
            }
            seccionesDefault.add(s);
        }
        micro.setSeccionMicrocurriculoList(seccionesDefault);
    }

    private void getEncabezados(TablaMicrocurriculo tm, List<EncabezadoTabla> encabezados, int columnas) {
        int i = 0;
        for (String enca : COLUMNAS[0]) {
            encabezados.add(new EncabezadoTabla(new EncabezadoTablaPK(i++, tm.getId()), enca));
        }
    }
}
