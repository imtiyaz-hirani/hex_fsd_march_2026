package com.proc.db;

import com.proc.model.Customer;
import com.proc.utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDB {

    private DBConnection dbConnection = new DBConnection();
    private Connection conn;

    public List<Customer> getAllCustomers(){
        this.conn = dbConnection.dbConnect();
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
        dbConnection.dbClose();
        return list;
    }

    public List<Customer> getCustomerByCity(String city){
        this.conn = dbConnection.dbConnect();
        List<Customer> list = new ArrayList<>();
        try {
            //Call the proc get_all_customers()
            CallableStatement callableStatement = conn.prepareCall("{CALL get_customers_by_city(?)}");
            //set the value of ?
            callableStatement.setString(1, city);
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
            System.out.println(e.getMessage());
        }
        dbConnection.dbClose();
        return list;
    }

    public Integer getTotalCustomers(String city){
        this.conn = dbConnection.dbConnect();
        int total = 0;
         try {
             // CALL total_customers("london", @total_cust);
            CallableStatement callableStatement = conn.prepareCall("{CALL total_customers(?,?)}");
            //set the value of ?
            callableStatement.setString(1, city);
             callableStatement.registerOutParameter(2,Types.INTEGER);
            callableStatement.executeQuery();
            total = callableStatement.getInt(2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        dbConnection.dbClose();
    return total;
    }

    public List<Customer> getCustomersView(){
        this.conn = dbConnection.dbConnect();
        List<Customer> list = new ArrayList<>();
        String sql = "select * from customer_view";
        try {
            //Call the proc get_all_customers()
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rst =  stmt.executeQuery();

            while(rst.next()){
                Customer customer
                        = new Customer(rst.getInt("id"),
                        rst.getString("name"),
                        rst.getString("city"));
                list.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dbConnection.dbClose();
        return list;
    }


}
