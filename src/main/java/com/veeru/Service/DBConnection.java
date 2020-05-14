package com.veeru.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String url = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12340284";
    private static String username = "sql12340284";
    private static String password = "hi16vyYkAR";
    private static Connection con = null;
    public static Connection getConnection() throws SQLException {
        if(con != null && !con.isClosed())
            return con;
        return getConnection(url,username,password);
    }

    private static Connection getConnection(String url,String username,String password) {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
