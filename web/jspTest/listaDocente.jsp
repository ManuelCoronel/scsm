<%-- 
    Document   : listaDocente
    Created on : 6/06/2021, 01:52:47 PM
    Author     : jhoser
--%>

<%@page import="util.Conexion"%>
<%@page import="dao.DocenteJpaController"%>
<%@page import="dto.Docente"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Docente</title>
    </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th>Codigo</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Departamento</th>
                    <th>Estado</th>
                </tr>
            </thead>

            <%
                List<Docente> docentes = (List<Docente>) request.getSession().getAttribute("listaDocentes");
                System.out.println(docentes.toString());
                for (Docente teacher : docentes) {
            %>
            <tbody>
                <tr>
                    <td ><%= teacher.getCodigoDocente()%></td>
                    <td ><%= teacher.getNombre()%></td>
                    <td ><%= teacher.getApellido()%></td>
                    <td ><%= teacher.getDepartamentoId().getNombreDepartamento()%></td>
                    <td ><%= teacher.getEstado()%></td>
                    <!--
                    <td class="text-center">
                        <a class="btn btn-warning" href="../../ControladorPersonal?accion=formupdate&id=<%= teacher.getCodigoDocente()%>" style=" width: 90px">Editar</a>
                        <a class="btn btn-danger"  href="../../ControladorPersonal?accion=Delete&id=<%= teacher.getCodigoDocente()%>">Eliminar</a>
                    </td>
                    -->
                </tr>
                <%}%>
            </tbody>

        </table>
        <h1>Hello World!</h1>
    </body>
    
</html>
