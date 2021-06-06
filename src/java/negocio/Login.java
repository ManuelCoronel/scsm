/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import util.Conexion;

/**
 *
 * @author Manuel
 */
public class Login {

    public Login() {
    }

    public boolean validarUsuario(int codigo, String contrasena, int rol) {
        Conexion con = Conexion.getConexion();
        dao.UsuarioJpaController daoUsuario = new dao.UsuarioJpaController(con.getBd());
        dto.UsuarioPK usuarioPk = new dto.UsuarioPK(rol, codigo);
         dto.Usuario usuario = daoUsuario.findUsuario(usuarioPk);
   
        
        if (usuario.getClave().equals(contrasena)) {
            return true;
        } else {
            return false;
        }

    }
    
    
    public dto.Docente obtenerDocente(int codigo){
       Conexion con = Conexion.getConexion();
       dao.DocenteJpaController daoDocente = new dao.DocenteJpaController(con.getBd());
       return daoDocente.findDocente(codigo);
    }
}
