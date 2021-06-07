/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dto.Materia;
import dto.Pensum;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.registrarMicrocurriculo;

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
    
    public static void cargarMicrocurriculo(){
    
    
    }
    
public static void listar(){



}
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            String accion = request.getParameter("accion");
            if (accion.equalsIgnoreCase("listar")){
            
            
            
            } else if(accion.equals("registrar")){
                this.registrar(request, response);
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
    
    private void registrar(HttpServletRequest request, HttpServletResponse response) {
        List<Materia> materias = ((Pensum)request.getAttribute("pensum")).getMateriaList();
        for(Materia m: materias){
            
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
