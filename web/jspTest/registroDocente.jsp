<%-- 
    Document   : registroDocente
    Created on : 5/06/2021, 10:44:35 PM
    Author     : jhoser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar docente</title>      
        <script src="../js/JQuery.js"></script>
        <script src="../js/index.js"></script>
    </head>
    <body>
        <h1>REGISTRO DOCENTE</h1>
        <!--  nombre, apellido, codigo, correo, contraseña, confirmar contraseña, facultad[], departamento[], tipo[] BUTON iniciar sesion, registrar -->
        <form method="post" action="../ControladorDocente">
            <table>

                <tr> 
                    <td><input type="text" name="txtNombre">Nombre</td>
                    <td><input type="text" name="txtApellido">Apellido</td>
                </tr>
                <tr>
                    <td><input type="number" name="txtCodigo">Codigo</td>
                    <td><input type="text" name="txtCorreo">Correo</td>
                </tr>
                <tr>
                    <td><input type="text" name="txtPassword">Contraseña</td>
                    <td><input type="text" name="txtPassword">Confirmar contraseña</td>
                </tr>
                <tr>
                    <td>
                        <select name="optionFacultad" id="optionFacultad" onchange ="searchDepartamento()">
                        </select>
                    </td>
                    <td>
                        <select name="optionDepartamento" id="optionDepartamento">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit"name="action" value="registrarDocente">Registrarse     
                        <input type="submit"name="action" value="listarDocente" > Docentes adscritos
                    </td>
                </tr>
            </table> 
  

    </body>
</html>
