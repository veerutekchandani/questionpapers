package com.veeru.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String url = "jdbc:mysql://br7rsuu9e0pe6dmhqn4g-mysql.services.clever-cloud.com:3306/br7rsuu9e0pe6dmhqn4g";
    private static String username = "ufcinxmeulgablza";
    private static String password = "gUvbeQTZnWvCp2L9hUhO";
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
