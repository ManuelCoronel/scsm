<%-- 
    Document   : inicioSesion
    Created on : 05-jun-2021, 21:18:08
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
        <form action="../ControladorLogin" method="POST">
            <input type="number" name="codigo" placeholder="Codigo">
            <input type="number" name="codigo" placeholder="Documento">
            <input type="text" name="codigo" placeholder="Contraseña">
            <input type="radio" name="Tipo" value="Director">
            <input type="radio"  name="Tipo" value="Docente">
           <input type="submit" name="accion" value="Registrar">
      
        </form>
    </body>
</html>
