package com.proc.db;

import com.proc.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDB {

    private Connection conn;

    public void dbConnect() {
        try {
            /* Load the driver */
            Class.forName("com.mysql.cj.jdbc.Driver");
            /* Establish the connection */
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hex_march_fsd_2026", "root", "deepcoder");
            System.out.println("driver loaded and conn established");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dbClose(){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> getAllCustomers(){
        List<Customer> list = new ArrayList<>();
        try {
            //Call the proc get_all_customers()
            CallableStatement callableStatement = conn.prepareCall("{CALL get_all_customers()}");
            ResultSet rst =  callableStatement.executeQuery();

            while(rst.next()){
                Customer customer
                        = new Customer(rst.getInt("id"),
                        rst.getString("name"),
                        rst.getString("email"),
                        rst.getString("city"));
                list.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
