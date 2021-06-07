/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.MicrocurriculoJpaController;
import dao.TipoAsignaturaJpaController;
import dto.AreaFormacion;
import dto.Materia;
import dto.Microcurriculo;
import dto.Pensum;
import dto.TipoAsignatura;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.AdministrarMicrocurriculo;
import negocio.registrarMicrocurriculo;
import util.Conexion;

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
        negocio.AdministrarMicrocurriculo adminMicrocurriculo = new negocio.AdministrarMicrocurriculo();
        dto.Usuario usuario = (dto.Usuario) request.getSession().getAttribute("usuario");
        ArrayList<dto.Microcurriculo> microcurriculos = adminMicrocurriculo.obtenerTodosMicrocurriculos(usuario.getDocente().getProgramaList().get(0).getCodigo());
        request.getSession().setAttribute("microcurriculos", microcurriculos);
        response.sendRedirect("jspTest/listarMcricurriculos.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion.equalsIgnoreCase("listarTodos")) {
            listar(request, response);

        } else if (accion.equals("registrar")) {
            try{
                System.out.println("registrando");
                this.registrar(request, response);
            }catch(Exception e){
                e.printStackTrace();
            }
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
