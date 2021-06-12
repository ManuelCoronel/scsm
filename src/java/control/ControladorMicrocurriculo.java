/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dto.Pensum;
import dto.Seccion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.AdministrarMicrocurriculo;
import negocio.RegistroMicrocurriculoBackground;

/**
 *
 * @author Manuel
 */
@WebServlet(name = "ControladorMicrocurriculo", urlPatterns = {"/ControladorMicrocurriculo"})
public class ControladorMicrocurriculo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    public static void cargarMicrocurriculo() {

    }

    public static void listar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("materias");

        AdministrarMicrocurriculo adminMicrocurriculo = new AdministrarMicrocurriculo();
        dto.Usuario usuario = (dto.Usuario) request.getSession().getAttribute("usuario");
        List<dto.Materia> materias = adminMicrocurriculo.obtenerTodasMateria(usuario.getDocente().getProgramaList().get(0).getCodigo());
        request.getSession().setAttribute("areasFormacion", adminMicrocurriculo.obtenerAreasFormacion());
        request.getSession().setAttribute("tipoAsignatura", adminMicrocurriculo.obtenerTiposAisgnatura());
        request.getSession().setAttribute("materias", materias);

        response.sendRedirect("jspTest/listaMicrocurriculos.jsp");
    }

    public void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int id = Integer.parseInt(request.getParameter("idMicrocurriculo"));
        int codigoPensum = Integer.parseInt(request.getParameter("codigoPensum"));
        int codigoMateria = Integer.parseInt(request.getParameter("codigoMateria"));
        negocio.AdministrarMicrocurriculo adminMicrocurriculo = new negocio.AdministrarMicrocurriculo();
        dto.Microcurriculo microcurriculo = adminMicrocurriculo.obtenerMicrocurriculo(id, codigoMateria, codigoPensum);
        request.getSession().setAttribute("microcurriculo", microcurriculo);
        response.sendRedirect("jspTest/registrarMicrocurriculo.jsp");

    }

    public void registrarContenidoTabla(HttpServletRequest request, HttpServletResponse response) {

        AdministrarMicrocurriculo admin = new AdministrarMicrocurriculo();
        List<dto.Seccion> secciones = admin.obtenerSecciones();

        for (Seccion seccion : secciones) {
            if (seccion.getId() == 2) {

            }
        }
    }

    public void consultar(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int id = Integer.parseInt(request.getParameter("idMicrocurriculo"));
        int codigoPensum = Integer.parseInt(request.getParameter("codigoPensum"));
        int codigoMateria = Integer.parseInt(request.getParameter("codigoMateria"));
        negocio.AdministrarMicrocurriculo adminMicrocurriculo = new negocio.AdministrarMicrocurriculo();
        dto.Microcurriculo microcurriculo = adminMicrocurriculo.obtenerMicrocurriculo(id, codigoMateria, codigoPensum);
        request.getSession().setAttribute("microcurriculo", microcurriculo);
        response.sendRedirect("jspTest/consultarMicrocurriculo.jsp");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion.equalsIgnoreCase("listarTodos")) {
            listar(request, response);
        }

        if (accion.equalsIgnoreCase("editar")) {
            editar(request, response);
        }

        if (accion.equalsIgnoreCase("Consultar")) {
            consultar(request, response);
        }

        if (accion.equalsIgnoreCase("registrar")) {
            try {
                crearMicrocurriculo(request, response);
            } catch (Exception err) {
                System.out.println(err.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        System.out.println(accion);
        if (accion.equalsIgnoreCase("Registrar")) {
            try {
                registrar(request, response);
            } catch (Exception ex) {
                Logger.getLogger(ControladorMicrocurriculo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void obtenerUnidades(HttpServletRequest request, HttpServletResponse response) {
        int cantidadFilas = Integer.parseInt(request.getParameter("nfilas-1"));
        String contenido[][] = new String[cantidadFilas][5];
        for (int i = 0; i < cantidadFilas; i++) {
            for (int j = 0; j < 5; j++) {
                contenido[i][j] = request.getParameter("contenido-1" + "-" + (i + 1) + "-" + (j + 1));
            }
        }

    }

    public void obtenerContenidos(HttpServletRequest request, HttpServletResponse response) {
        int cantidadFilas = Integer.parseInt(request.getParameter("nfilas-2"));
        String contenido[][] = new String[cantidadFilas][3];
        for (int i = 0; i < cantidadFilas; i++) {
            for (int j = 0; j < 3; j++) {
                contenido[i][j] = request.getParameter("contenido-2" + "-" + (i + 1) + "-" + (j + 1));
            }
        }

    }

    public void registrarInformacionTablas(HttpServletRequest request, HttpServletResponse response, AdministrarMicrocurriculo adminM) throws Exception {

        dto.Microcurriculo microcurriculo = (dto.Microcurriculo) request.getSession().getAttribute("microcurriculo");
        microcurriculo = adminM.obtenerMicrocurriculoId(microcurriculo.getMicrocurriculoPK().getId());
        System.out.println("Microcurricul contenido" + microcurriculo.toString());
        int contador = 1;

        for (dto.SeccionMicrocurriculo secciones : microcurriculo.getSeccionMicrocurriculoList()) {
            if (secciones.getSeccionId().getTipoSeccionId().getId() == 2) {

                int cantidadFilas = Integer.parseInt(request.getParameter("nfilas-" + secciones.getId()));
                System.out.println("ID = " + secciones.getId());
                System.out.println("Cantidad Filas" + cantidadFilas);

                System.out.println("Tabla contenido" + secciones.getTablaMicrocurriculoList());
                 secciones.getTablaMicrocurriculoList().get(0).setCantidadFilas(cantidadFilas);
                 adminM.actualizarFilasTabla(secciones.getTablaMicrocurriculoList().get(0));
                String contenido[][] = new String[cantidadFilas][secciones.getTablaMicrocurriculoList().get(0).getCantidadColumnas()];
                for (int i = 0; i < contenido.length; i++) {
                    for (int j = 0; j < contenido[i].length; j++) {
                        contenido[i][j] = (String) request.getParameter("contenido-" + contador + "-" + (i + 1) + "-" + j);
                        System.out.println("contenido-" + contador + "-" + (i + 1) + "-" + j);
                        System.out.println(contenido[i][j]);
                    }
                
                }
                adminM.registrarContenidoTablas(contenido, secciones, secciones.getTablaMicrocurriculoList().get(0));
                contador++;
            }

        }

    }

    public void registrarSecciones(HttpServletRequest request, HttpServletResponse response, AdministrarMicrocurriculo adminM) throws Exception {
        List<dto.Seccion> secciones = adminM.obtenerSecciones();
        response.setContentType("text/html");
        for (Seccion seccione : secciones) {
            if (seccione.getTipoSeccionId().getId() != 2) {

                String informacion = request.getParameter("seccion-" + seccione.getId());
                System.out.println("SECCION : " + "seccion-" + seccione.getId());
                int idSeccionMicrocurriculo = Integer.parseInt(request.getParameter("seccionId-" + seccione.getId()));
                System.out.println("informacion :" + informacion);
                adminM.ingresarContenidoSecciones(informacion.toString(), idSeccionMicrocurriculo);
            }
        }

    }

    private void registrar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AdministrarMicrocurriculo adminMicrocurriculo = new AdministrarMicrocurriculo();
        int areaFormacion = Integer.parseInt(request.getParameter("areasFormacion"));
        dto.Microcurriculo microcurriculo = (dto.Microcurriculo) request.getSession().getAttribute("microcurriculo");
        adminMicrocurriculo.actualizarAreaFormacionMicrocurriculo(microcurriculo, areaFormacion);
        registrarInformacionTablas(request, response, adminMicrocurriculo);
        registrarSecciones(request, response, adminMicrocurriculo);
        response.sendRedirect("jspTest/listaMicrocurriculos.jsp");
    }

    private void crearMicrocurriculo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        new RegistroMicrocurriculoBackground(((Pensum) request.getSession().getAttribute("pensum"))).start();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
