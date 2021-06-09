/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dto.Pensum;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.AdministrarMicrocurriculo;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion.equalsIgnoreCase("listarTodos")) {
            listar(request, response);
        }
        if (accion.equals("registrar")) {
            try {
                this.registrar(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (accion.equalsIgnoreCase("editar")) {
            editar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        System.out.println(accion);
        if (accion.equalsIgnoreCase("Registrar")) {
            String[] contenidos = request.getParameterValues("contenido");
            System.out.println(Arrays.toString(contenidos));
        }
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        new AdministrarMicrocurriculo().registrarMicrocurriculos(((Pensum) request.getSession().getAttribute("pensum")));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
