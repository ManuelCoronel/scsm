/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dunke
 */
public class MyConnection {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://madarme.co:3306/estudiante_31?zeroDateTimeBehavior=convertToNull";
    private static final String USERNAME = "estudiante_31";
    private static final String PASSWORD = "pCPstUBGOM";

    public static Connection getConnection() {
        try {
            if(connection != null && !connection.isClosed()){
                return connection;
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return connection;
    }
}
