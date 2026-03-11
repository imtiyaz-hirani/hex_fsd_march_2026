package com.ecom.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection conn;

    public static Connection dbConnect() {
        try {
            /* Load the driver */
            Class.forName("com.mysql.cj.jdbc.Driver");
            /* Establish the connection */
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hec_ecom_proj", "root", "deepcoder");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static void dbClose(){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
