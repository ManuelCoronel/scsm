<%-- 
    Document   : listaMicrocurriculo
    Created on : 05-jun-2021, 18:38:01
    Author     : Manuel
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table border="1">
            <thead>
                <tr>
                    <th>Pensum</th>
                    <th>Codigo Materia</th>
                    <th>Nombre Materia</th>
                    <th>Creditos</th>
                    <th>Semestre</th>
                    <th>Accion</th>
                </tr>
            </thead>
            <tbody>
                <%
              List<dto.Microcurriculo> microcurriculos = (List<dto.Microcurriculo>)request.getSession().getAttribute("microcurriculos");
                for (dto.Microcurriculo elem : microcurriculos) {
                        
                    
                %>
                <tr>
                    <td><%=elem.getMateria().getPensum().getPensumPK().getProgramaCodigo() %> - <%=elem.getMateria().getPensum().getPensumPK().getCodigo() %></td>
                    <td><%=elem.getMateria().getMateriaPK().getCodigoMateria() %></td>
                    <td><%=elem.getMateria().getNombre() %></td>
                    <td><%=elem.getMateria().getCreditos()%></td>
                    <td><%=elem.getMateria().getSemestre() %></td>
                    <td> <a href="../ControladorMicrocurriculo?accion=Editar&idMicrocurriculo=<%=elem.getMicrocurriculoPK().getId()%>&materia=<%=elem.getMateria().getMateriaPK().getCodigoMateria()%>"><button type="button">Editar </button></a>   </td>
                </tr>
                <%
                }
                %>
            </tbody>
        </table>

    </body>
</html>
