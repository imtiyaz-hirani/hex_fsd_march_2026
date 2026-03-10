package com.proc.db;

import com.proc.model.Product;
import com.proc.utility.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ProductDB {  //500 --> 200-300 classes -- copy paste

    private DBConnection dbConnection = new DBConnection();
    private Connection conn;

    public List<Product> fetchAllProducts(){
        this.conn = dbConnection.dbConnect();
        List<Product> list = new ArrayList<>();
        try {
            //Call the proc get_all_products()
            CallableStatement callableStatement = conn.prepareCall("{CALL get_all_products()}");
            ResultSet rst =  callableStatement.executeQuery();

            while(rst.next()){
                Product product
                        = new Product(rst.getInt("id"),
                        rst.getString("name"),
                        rst.getBigDecimal("price"),
                        rst.getInt("stock"));
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dbConnection.dbClose();
        return list;
    }

    public void addTransaction(int productId, int qty) throws SQLException {
        this.conn = dbConnection.dbConnect();
        String sql = "insert into transactions(product_id, quantity, sale_date) values (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, productId);
        stmt.setInt(2, qty);
        stmt.setString(3, LocalDate.now().toString());

        stmt.executeUpdate();
        dbConnection.dbClose();
    }
}
