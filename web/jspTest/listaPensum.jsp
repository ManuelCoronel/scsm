<%-- 
    Document   : listaPensum
    Created on : 15/06/2021, 07:35:12 PM
    Author     : jhoser
--%>

<%@page import="dto.Pensum"%>
<%@page import="java.util.List"%>
<%@page import="dto.Usuario"%>
<%@page import="negocio.AdministrarPensum"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Pensum</title>
    </head>
    <body>
        <h1>Lista Pensum</h1>
        <%
            AdministrarPensum admin = new AdministrarPensum();
            Usuario u = (Usuario) request.getSession().getAttribute("usuario");

            List<Pensum> pensum = admin.obtenerPensum(u.getDocente().getProgramaList().get(0));
            pensum.get(0).getPensumPK().getProgramaCodigo();
            pensum.get(0).getPensumPK().getCodigo();
            //creditos 
            //cantidamaterias

        %>
        <table>
            <thead>
                <tr>
                    <th>Codigo</th>
                    <th>Creditos</th>
                    <th>Materias</th>
                    <th>Accion</th>
                </tr>
            </thead>
            <tbody>
                
            </tbody>
        </table>
    </body>
</html>
