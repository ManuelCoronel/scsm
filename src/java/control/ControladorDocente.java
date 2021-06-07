/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.DocenteJpaController;
import dto.Departamento;
import dto.Docente;
import dto.Materia;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
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
@WebServlet(name = "ControladorDocente", urlPatterns = {"/ControladorDocente"})
public class ControladorDocente extends HttpServlet {

    DocenteJpaController docenteDao = new DocenteJpaController(Conexion.getConexion().getBd());

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
            out.println("<title>Servlet ControladorDocente</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorDocente at " + request.getContextPath() + "</h1>");
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
                case "registrarDocente":
                    this.guardarDocente(request, response);
                    break;
                case "listarDocente":
                    this.listarDocente(request, response);
                    break;
            }
            pw.println("<h1>Hizo algo</h1>");

        } catch (Exception e) {

            pw.println("<h1>Error</h1>");
            System.err.println(e);
        }
    }

    public void guardarDocente(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        Docente docente = new Docente();
        int codigo = Integer.parseInt(request.getParameter("txtCodigo"));
        String nombre = request.getParameter("txtNombre");
        String apellido = request.getParameter("txtApellido");
        int departamento = Integer.parseInt(request.getParameter("optionDepartamento"));
        short estado = 1;
        String password = request.getParameter("txtPassword");
        docente.setNombre(nombre);
        docente.setApellido(apellido);
        docente.setDepartamentoId(new Departamento(departamento));
        docente.setCodigoDocente(codigo);
        docente.setEstado(estado);
        docenteDao.create(docente);
        response.sendRedirect("jspTest/registroDocente.jsp");
    }
    

    public void listarDocente(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        System.out.println("Listando docentes");
        List<Docente> docentes = (List<Docente>) docenteDao.findDocenteEntities();
        docentes.forEach((d) -> {
            System.out.println(d.getNombre() + " " + d.getEstado() + " " + d.getProgramaList() + " " + d.getDepartamentoId());
        });
        System.out.println("que es esto " + docentes.toString());
        request.getSession().setAttribute("listaDocentes", docentes);
        response.sendRedirect("jspTest/listaDocente.jsp");
    }


    /*public void editarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cedula = request.getParameter("cedula");
        Cliente clienteEditar = new Cliente();
        clienteEditar = clienteDAO.findCliente(cedula);
        List<TipoCliente> tiposClientes = tipoDAO.findTipoClienteEntities();
        request.getSession().setAttribute("tiposDeClientes", tiposClientes);
        request.getSession().setAttribute("editarCliente", clienteEditar);
        response.sendRedirect("vistas/jsp/editarCliente.jsp");
    }

    public void actualizarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        Cliente clienteDTO = new Cliente();
        String cedula = request.getParameter("txtCedula");
        String telefono = request.getParameter("txtTelefono");
        String direccion = request.getParameter("txtDireccion");
        String nombre = request.getParameter("txtNombre");
        String correo = request.getParameter("txtCorreo");
        Date fechaRegistro = Date.valueOf(request.getParameter("txtFechaRegistro"));
        Date fechaNacimiento = Date.valueOf(request.getParameter("txtFechaNacimiento"));
        TipoCliente tipoC = tipoDAO.findTipoCliente(Integer.parseInt(request.getParameter("opciones")));
        clienteDTO = clienteDAO.findCliente(cedula);
        clienteDTO.setCedula(cedula);
        clienteDTO.setTelefono(telefono);
        clienteDTO.setDireccion(direccion);
        clienteDTO.setNombre(nombre);
        clienteDTO.setCorreo(correo);
        clienteDTO.setFechaRegistro(fechaRegistro);
        clienteDTO.setFechaNacimiento(fechaNacimiento);
        clienteDTO.setTipoClienteIdTipoCliente(tipoC);
        clienteDAO.edit(clienteDTO);
        List<DTO.Cliente> clientes = clienteDAO.findClienteEntities();
        request.getSession().setAttribute("listaClientes", clientes);
        response.sendRedirect("vistas/jsp/listaClientes.jsp");
    }

    public void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException, NonexistentEntityException {
        String cedula = request.getParameter("cedula");
        clienteDAO.destroy(cedula);
        List<DTO.Cliente> clientes = clienteDAO.findClienteEntities();
        request.getSession().setAttribute("listaClientes", clientes);
        response.sendRedirect("vistas/jsp/listaClientes.jsp");
    }

    public void buscarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cedula = request.getParameter("cedulaCliente");
        DTO.Cliente cliente = new DTO.Cliente();
        cliente = clienteDAO.findCliente(cedula);
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        clientes.add(cliente);
        request.getSession().setAttribute("listaClientes", clientes);
        response.sendRedirect("vistas/jsp/listaClientes.jsp");
    }
     */
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
