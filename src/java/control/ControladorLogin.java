/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.Login;

/**
 *
 * @author Manuel
 */
@WebServlet(name = "ControladorLogin", urlPatterns = {"/ControladorLogin"})
public class ControladorLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion.equalsIgnoreCase("iniciarSesion")) {
            verificarUsuario(request, response);
        }

    }

    public static void verificarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codigo = Integer.parseInt(request.getParameter("codigo"));

        String contrasena = request.getParameter("clave");
        int rol = Integer.parseInt(request.getParameter("rol"));
        Login login = new Login();
        boolean valido = login.validarUsuario(codigo, contrasena, rol);
        System.out.println(valido);
        if (valido) {
            cargarInformacion(request, response, codigo, login, rol);
            response.sendRedirect("jspTest/listaMicrocurriculos.jsp");
        } else {
         
        }

    }

    public static void cargarInformacion(HttpServletRequest request, HttpServletResponse response, int codigo, Login login, int rol) {
        dto.Docente docente = login.obtenerDocente(codigo);
        request.setAttribute("sesion", docente);
        request.setAttribute("rol", rol);

        if (rol == 1) {
            System.out.println(docente.getProgramaList().get(0).getNombrePrograma());
            cargarPrograma();
        } else {
            cargarDepartamento();
        }

    }

    public static void cargarDepartamento() {

    }

    public static void cargarPrograma() {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
