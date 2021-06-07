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
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;
import util.Conexion;

/**
 *
 * @author Manuel
 */
public class RegistroMicrocurriculoBackground extends Thread {
    private Pensum pensum;

    public RegistroMicrocurriculoBackground(Pensum pensum) {
        this.pensum = pensum;
    }

    @Override
    public void run() {
        try {
            this.registrarMicrocurriculos(pensum);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private void registrarMicrocurriculos(Pensum pensum) throws Exception {
        System.out.println("corriendo en segundo plano perras");
        EntityManagerFactory em = Conexion.getConexion().getBd();
        SeccionJpaController tjpa = new SeccionJpaController(em);
        SeccionMicrocurriculoJpaController sjpa = new SeccionMicrocurriculoJpaController(em);
        ContenidoJpaController cjpa = new ContenidoJpaController(em);
        TablaMicrocurriculoJpaController tmjpa = new TablaMicrocurriculoJpaController(Conexion.getConexion().getBd());
        List<Seccion> secciones = tjpa.findSeccionEntities();
        List<Materia> materias = pensum.getMateriaList();
        MicrocurriculoJpaController mjpa = new MicrocurriculoJpaController(em);
        int id = 1;
        for (Materia m : materias) {
            Microcurriculo micro = new Microcurriculo(id++, m.getMateriaPK().getCodigoMateria(), m.getMateriaPK().getPensumCodigo());
            micro.setAreaDeFormacionId(new AreaFormacion(1));
            micro.setMateria(m);

            mjpa.create(micro);

            getDefaultSecciones(micro, secciones, sjpa, cjpa, tmjpa);
        }
    }

    private void getDefaultSecciones(Microcurriculo micro, List<Seccion> secciones, SeccionMicrocurriculoJpaController sjpa, ContenidoJpaController cjpa, TablaMicrocurriculoJpaController tmjpa) throws NonexistentEntityException, Exception {
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

   public String[] obtenerContenidos (String unidad){
       
       formatearContenidos(unidad);
   
   return null;
   }
   
   public String[] formatearContenidos(String unidad){
      
       System.out.println(unidad);
       String[] text = unidad.split("-");
       
       System.out.println("Formateo :");
      
       for (String string : text) {
           System.out.println(string); 
       }
       return null;
   }
        
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        
        
         String cadena = "";
            while(sc.hasNext()){
            cadena += sc.next();
            
            }
         System.out.println("ORIGINAL");
          System.out.println(cadena);
         RegistroMicrocurriculoBackground rm = new RegistroMicrocurriculoBackground(null);
         rm.obtenerContenidos(cadena);
        
    }
}
