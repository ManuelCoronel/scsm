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
        <table>
            <thead>
                <tr>
                    <th>Codigo</th>
                    <th>Creditos</th>
                    <th>Materias</th>
                    <th>Accion</th>
                </tr>
            </thead>
            <%
                AdministrarPensum admin = new AdministrarPensum();
                Usuario u = (Usuario) request.getSession().getAttribute("usuario");

                List<Pensum> pensum = admin.obtenerPensum(u.getDocente().getProgramaList().get(0));
                pensum.get(0).getPensumPK().getProgramaCodigo();
                pensum.get(0).getPensumPK().getCodigo();
                //creditos 
                //cantidamaterias

                for (Pensum p : pensum) {
                    if (u.getDocente().getProgramaList().get(0).getDirectorPrograma().getCodigoDocente() == p.getPrograma().getDirectorPrograma().getCodigoDocente()) {
                        int materiaXcreditos[] = admin.creditosMateriasPensum(p.getPensumPK().getCodigo(), p.getPensumPK().getProgramaCodigo());
            %>
            <tbody>
                <tr>
                    <td><%= p.getPensumPK().getProgramaCodigo() + "-" + p.getPensumPK().getCodigo()%></td>
                    <td ><%= materiaXcreditos[1]%></td>
                    <td ><%= materiaXcreditos[0]%></td>
                    <td>Descargar<td>Visualizar</td></td>
                </tr>
            </tbody>
            <%}
                }%>
        </table>
    </body>
</html>
