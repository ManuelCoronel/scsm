<%-- 
    Document   : registroPensum
    Created on : 5/06/2021, 02:30:48 PM
    Author     : dunke
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>A</title>
    </head>
    <body>
        <h2>Cargar pensum</h2>
        <form action="../ControladorPensum?action=registrar" method="POST" enctype="multipart/form-data">
            <input type="file" name="pensum">
            <input type="submit">
        </form>
    </body>
</html>
