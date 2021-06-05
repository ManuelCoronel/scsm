<%-- 
    Document   : registrarMicrocurriculo
    Created on : 05-jun-2021, 11:51:37
    Author     : Manuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Registrar Microcurriculo</h1>
        <form action="../ControladorMicrocurriculo?accion=registrar" method="POST" >
            
             <table border="1">
            <thead>
                <tr>
                    <th>CONTENIDO POR UNIDADES</th>
                    <th>ACTIVIDADES PRESENCIALES</th>
                    <th>TRABAJO INDEPENDIENTE</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><input  name="contenido" value=""></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </tbody>
        </table>
            
            
            <input type="submit" name="accion" value="Registrar">
        </form>

        
    </body>
</html>
