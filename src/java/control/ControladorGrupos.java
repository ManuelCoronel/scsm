/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.DocenteJpaController;
import dao.MateriaJpaController;
import dao.MateriaPeriodoGrupoJpaController;
import dto.Docente;
import dto.Materia;
import dto.MateriaPK;
import dto.MateriaPeriodo;
import dto.MateriaPeriodoGrupo;
import dto.MateriaPeriodoGrupoPK;
import dto.MateriaPeriodoPK;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Conexion;

/**
 *
 * @author jhoser
 */
@WebServlet(name = "ControladorGrupos", urlPatterns = {"/ControladorGrupos"})
public class ControladorGrupos extends HttpServlet {

    EntityManagerFactory em = Conexion.getConexion().getBd();
    DocenteJpaController docenteDao = new DocenteJpaController(em);
    MateriaJpaController materiaDao = new MateriaJpaController(em);
    MateriaPeriodoGrupoJpaController materiaGrupoDao = new MateriaPeriodoGrupoJpaController(em);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorGrupos</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorGrupos at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            switch (request.getParameter("action")) {
                case "optionDocente":
                    this.optionDocente(request, response);
                    break;
                case "optionMateria":
                    this.optionMateria(request, response);
                    break;
                case "registrarGrupo":
                    this.registrarGrupo(request, response);
                    break;

            }
            pw.println("<h1>Hizo algo</h1>");
            /*
            grupos jsp
            <%
                    List<MateriaPeriodoGrupo> mgp = (List<MateriaPeriodoGrupo>) request.getSession().getAttribute("listaGrupos");
                    System.out.println(mgp.size());
                    for (MateriaPeriodoGrupo grupos : mgp) {
                %>
                <tbody>
                    <tr>
                        <td><%= grupos.getMateriaPeriodo().getMateria().getMateriaPK().getCodigoMateria()%></td>
                        <td><%= grupos.getMateriaPeriodo().getMateria().getNombre()%></td>
                        <td><%= grupos.getDocente().getCodigoDocente()%></td>
                        <td><%= grupos.getMateriaPeriodoGrupoPK().getGrupo()%></td>
                        <td> X - Y - Z</td>
                        <%}%>  
                    </tr>
                </tbody>
             */

        } catch (Exception e) {
            System.out.println("estoy editando");
            pw.println("<h1>Error</h1>");
            e.printStackTrace();
            System.err.println(e);
        }
        pw.flush();
    }

    public void optionDocente(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        PrintWriter pw = response.getWriter();
        List<Docente> docentes = docenteDao.findDocenteEntities();
        docentes.forEach((teacher) -> {
            pw.println("<option value=" + teacher.getCodigoDocente() + ">" + teacher.getNombre() + " " + teacher.getApellido() + "</option>");
        });
        pw.flush();
    }

    public void optionMateria(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        PrintWriter pw = response.getWriter();
        List<Materia> materias = materiaDao.findMateriaEntities();
        materias.forEach((mat) -> {
            pw.println("<option value=" + mat.getMateriaPK().getCodigoMateria() + ">" + mat.getNombre() + "</option>");
        });
        pw.flush();
    }

    public void listarGrupo(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        System.out.println("Listando grupos");
        List<MateriaPeriodoGrupo> mpg = (List<MateriaPeriodoGrupo>) materiaGrupoDao.findMateriaPeriodoGrupoEntities();
        request.getSession().setAttribute("listaGrupos", mpg);
        response.sendRedirect("jspTest/grupos.jsp");
    }

    public void registrarGrupo(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {

        Integer idMateria = Integer.parseInt(request.getParameter("optionMateria"));
        Integer idDocente = Integer.parseInt(request.getParameter("optionDocente"));
        String txtGrupo = request.getParameter("txtGrupo");
        MateriaPK mpk = new MateriaPK();
        mpk.setCodigoMateria(idMateria);
        Materia m = materiaDao.findMateria(mpk);
        Docente d = docenteDao.findDocente(idDocente);
        MateriaPeriodoGrupo grupo = new MateriaPeriodoGrupo(txtGrupo, idDocente);
        MateriaPeriodoGrupoPK grupopk = new MateriaPeriodoGrupoPK(txtGrupo, idDocente);
        MateriaPeriodoPK mpgk = new MateriaPeriodoPK(idMateria, 2021, 1);
        MateriaPeriodo mp = new MateriaPeriodo(idMateria, 2021, 1);
        mp.setMateriaPeriodoPK(mpgk);
        mp.setMateria(m);
        grupopk.setDocenteCodigo(d.getCodigoDocente());
        grupopk.setGrupo(txtGrupo);
        grupo.setMateriaPeriodoGrupoPK(grupopk);
        grupo.setDocente(d);
        mpgk.setMateriaCodigoMateria(idMateria);
        grupo.getMateriaPeriodoGrupoPK().setDocenteCodigo(idDocente);
        grupo.getMateriaPeriodoGrupoPK().setGrupo(txtGrupo);
        grupo.setMateriaPeriodo(mp);
        System.out.println("materia " + idMateria + " docente " + idDocente + " grupo " + txtGrupo);

        System.out.println("materiaPK " + mpk.toString());
        System.out.println("materiaPeriodo " + mp.toString());
        System.out.println("grupo " + grupo.toString());
        materiaGrupoDao.create(grupo);
        response.sendRedirect("jspTest/grupos.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
