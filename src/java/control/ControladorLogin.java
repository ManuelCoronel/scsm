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
        dto.Usuario usuario = login.obtenerUsuario(codigo, rol);
        request.getSession().setAttribute("usuario", usuario);
       
        
        if (rol == 1) {
            System.out.println(usuario.getDocente().getProgramaList().get(0).getNombrePrograma());
            cargarPrograma(request, response, usuario);
           
        } else {
            cargarDepartamento(request, response, usuario);
        }

    }
    
   

    public static void cargarDepartamento(HttpServletRequest request, HttpServletResponse response,dto.Usuario usuario) {
            request.getSession().setAttribute("departamentoSesion",usuario.getDocente().getDepartamentoId().getNombreDepartamento());
    }

    public static void cargarPrograma(HttpServletRequest request, HttpServletResponse response,dto.Usuario usuario) {
           request.getSession().setAttribute("programaSesion",usuario.getDocente().getProgramaList().get(0));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
