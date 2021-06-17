<%-- 
    Document   : MicrocurriculoDocente
    Created on : 16/06/2021, 06:17:44 PM
    Author     : jhoser
--%>

<%@page import="java.util.List"%>
<%@page import="dto.MateriaPeriodo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table>
            <thead>            
            <th>Pensum</th>
            <th>Codigo Materia</th>
            <th>Nombre Materia</th>
            <th>Creditos</th>
            <th>Semestre</th>
            <th>Accion</th>
        </thead>     
        <tbody>
            <%
                List<MateriaPeriodo> mp = (List<MateriaPeriodo>) request.getSession().getAttribute("misMicrocurriculos");
                for (MateriaPeriodo materia : mp) {
            %>
        <td><%=materia.getMateriaPeriodoPK().getMateriaPensumCodigo()%></td> 
        <td><%=materia.getMateriaPeriodoPK().getMateriaPensumCodigo()%></td>         
        <td><%=materia.getMateriaPeriodoPK().getMateriaCodigoMateria()%></td>         
        <td><%=materia.getMateriaPeriodoPK().getSemestreAnio()%></td>         
        <td>ALGO1 ALGO2</td> 
        <%}%>
    </tbody>

</table> 
</body>
</html>
