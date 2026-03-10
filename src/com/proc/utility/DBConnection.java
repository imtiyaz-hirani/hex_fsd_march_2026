package com.proc.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private Connection conn;

    public Connection dbConnect() {
        try {
            /* Load the driver */
            Class.forName("com.mysql.cj.jdbc.Driver");
            /* Establish the connection */
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hex_march_fsd_2026", "root", "deepcoder");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public void dbClose(){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
