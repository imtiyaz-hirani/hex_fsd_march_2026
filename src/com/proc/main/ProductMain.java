package com.proc.main;

import com.proc.db.ProductDB;

import java.sql.SQLException;
import java.util.Scanner;

public class ProductMain {
    public static void main(String[] args) {
        ProductDB productDB = new ProductDB();
        productDB.fetchAllProducts()
                .forEach(System.out :: println);
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the id of the product u wish to buy: ");
        int productId = sc.nextInt();
        System.out.println("How many items du u wish yo purchase(Qty): ");
        int qty = sc.nextInt();
        try {
            productDB.addTransaction(productId,qty);
            System.out.println("Transaction Success!!!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sc.close();
    }
}
