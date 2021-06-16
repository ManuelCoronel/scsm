/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.DocenteJpaController;
import dao.MateriaJpaController;
import dao.MateriaPeriodoGrupoJpaController;
import dao.exceptions.NonexistentEntityException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.AdministrarDocentes;
import negocio.AdministrarGrupos;
import util.Conexion;

/**
 *
 * @author jhoser
 */
@WebServlet(name = "ControladorGrupos", urlPatterns = {"/ControladorGrupos"})
public class ControladorGrupos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion.equalsIgnoreCase("listar")) {
            listar(request, response);

        }
        if (accion.equalsIgnoreCase("Registrar Grupo")) {
            System.out.println("Grupos informacion");
            try {
                crearGrupo(request, response);
            } catch (Exception ex) {
                Logger.getLogger(ControladorGrupos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (accion.equalsIgnoreCase("eliminar")) {
            try {
                eliminarMateriaPG(request, response);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ControladorGrupos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void eliminarMateriaPG(HttpServletRequest request, HttpServletResponse response) throws NonexistentEntityException, IOException {
        System.out.println("mpg");
        String grupo = request.getParameter("grupo");
        int codigoDocente = Integer.parseInt(request.getParameter("docente_codigo"));
        int anio = Integer.parseInt(request.getParameter("anio"));
        int semestre = Integer.parseInt(request.getParameter("semestre_anio"));
        int codigoMateria = Integer.parseInt(request.getParameter("codigo_materia"));
        int pensumCodigo = Integer.parseInt(request.getParameter("codigoPensum"));

        negocio.AdministrarGrupos admin = new AdministrarGrupos();
        admin.eliminarGrupo(grupo,codigoDocente,anio,semestre,codigoMateria,pensumCodigo);
        listar(request, response);
    }

    public void crearGrupo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int codigoPensum = (Integer.parseInt(request.getParameter("optionPensum")));
        int optionMateria = (Integer.parseInt(request.getParameter("optionMateria")));
        int codigoDocente = (Integer.parseInt(request.getParameter("optionDocente")));

        int anio = Integer.parseInt(request.getParameter("anio"));
        int periodo = Integer.parseInt(request.getParameter("periodo"));
        dto.MateriaPeriodoPK materiaPeriodo = validarMateriaPeriodo(request, response, anio, periodo, optionMateria, codigoPensum);
        validarMateriaPeriodoGrupo(request, response, materiaPeriodo,codigoDocente);
     response.sendRedirect("ControladorGrupos?accion=listar");

    }

    public void validarMateriaPeriodoGrupo(HttpServletRequest request, HttpServletResponse response, dto.MateriaPeriodoPK materiaPeriodo, int codigoDocente) throws Exception {
        negocio.AdministrarGrupos admin = new negocio.AdministrarGrupos();
        admin.validarMateriaPeriodoGrupo(materiaPeriodo, codigoDocente);
    }

    public dto.MateriaPeriodoPK validarMateriaPeriodo(HttpServletRequest request, HttpServletResponse response, int anio, int semestre, int codigoMateria, int codigoPensum) throws Exception {
        negocio.AdministrarGrupos admin = new negocio.AdministrarGrupos();
        return admin.validarMateriaPeriodo(anio, semestre, codigoMateria, codigoPensum);
    }

    public void listar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        cargarDocentes(request, response);
        cargarGrupos(request, response);
        //  cargarPensum(request, response);
        response.sendRedirect("jspTest/grupos.jsp");
    }

    public void cargarGrupos(HttpServletRequest request, HttpServletResponse response) {
        negocio.AdministrarGrupos admin = new negocio.AdministrarGrupos();
        dto.Programa programa = (dto.Programa) request.getSession().getAttribute("programaSesion");
        List<dto.MateriaPeriodoGrupo> grupos = admin.obtenerMateriaPeriodoGrupo(programa);
        request.getSession().setAttribute("grupos", grupos);
    }

    public void cargarDocentes(HttpServletRequest request, HttpServletResponse response) {
        negocio.AdministrarGrupos adminGrupos = new negocio.AdministrarGrupos();
        dto.Programa programa = (dto.Programa) request.getSession().getAttribute("programaSesion");
        System.out.println("Programa sesion " + programa.toString());
        List<dto.Docente> docentes = adminGrupos.obtenerDocentes(programa);
        request.getSession().setAttribute("docentesPrograma", docentes);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            switch (request.getParameter("action")) {
                case "optionDocente":
                    //  this.optionDocente(request, response);
                    break;
                case "optionMateria":
                    //      this.optionMateria(request, response);
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

        negocio.AdministrarDocentes admin = new AdministrarDocentes();
        dto.Programa programa = (dto.Programa) admin.obtenerDocentesPrograma((dto.Programa) (request.getSession().getAttribute("programaSesion")));
        List<Docente> docentes = admin.obtenerDocentesPrograma(programa);
        request.getSession().setAttribute("docentesPrograma", docentes);

    }
//
//    public void optionMateria(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
//        PrintWriter pw = response.getWriter();
//        List<Materia> materias = materiaDao.findMateriaEntities();
//        materias.forEach((mat) -> {
//            pw.println("<option value=" + mat.getMateriaPK().getCodigoMateria() + ">" + mat.getNombre() + "</option>");
//        });
//        pw.flush();
//    }

//    public void listarGrupo(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
//        System.out.println("Listando grupos");
//        List<MateriaPeriodoGrupo> mpg = (List<MateriaPeriodoGrupo>) materiaGrupoDao.findMateriaPeriodoGrupoEntities();
//        request.getSession().setAttribute("listaGrupos", mpg);
//        response.sendRedirect("jspTest/grupos.jsp");
//    }
    public void registrarGrupo(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {

//        Integer idMateria = Integer.parseInt(request.getParameter("optionMateria"));
//        Integer idDocente = Integer.parseInt(request.getParameter("optionDocente"));
//        String txtGrupo = request.getParameter("txtGrupo");
//        MateriaPK mpk = new MateriaPK();
//        mpk.setCodigoMateria(idMateria);
//        Materia m = materiaDao.findMateria(mpk);
//        Docente d = docenteDao.findDocente(idDocente);
//        MateriaPeriodoGrupo grupo = new MateriaPeriodoGrupo(txtGrupo, idDocente);
//        MateriaPeriodoGrupoPK grupopk = new MateriaPeriodoGrupoPK(txtGrupo, idDocente);
//        MateriaPeriodoPK mpgk = new MateriaPeriodoPK(idMateria, 2021, 1);
//        MateriaPeriodo mp = new MateriaPeriodo(idMateria, 2021, 1);
//        mp.setMateriaPeriodoPK(mpgk);
//        mp.setMateria(m);
//        grupopk.setDocenteCodigo(d.getCodigoDocente());
//        grupopk.setGrupo(txtGrupo);
//        grupo.setMateriaPeriodoGrupoPK(grupopk);
//        grupo.setDocente(d);
//        mpgk.setMateriaCodigoMateria(idMateria);
//        grupo.getMateriaPeriodoGrupoPK().setDocenteCodigo(idDocente);
//        grupo.getMateriaPeriodoGrupoPK().setGrupo(txtGrupo);
//        grupo.setMateriaPeriodo(mp);
//        System.out.println("materia " + idMateria + " docente " + idDocente + " grupo " + txtGrupo);
//
//        System.out.println("materiaPK " + mpk.toString());
//        System.out.println("materiaPeriodo " + mp.toString());
//        System.out.println("grupo " + grupo.toString());
//        materiaGrupoDao.create(grupo);
//        response.sendRedirect("jspTest/grupos.jsp");
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
