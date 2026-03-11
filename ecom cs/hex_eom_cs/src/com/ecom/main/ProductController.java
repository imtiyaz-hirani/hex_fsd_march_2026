package com.ecom.main;

import com.ecom.model.Product;
import com.ecom.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductController {
    public static void main(String[] args) {
        // create Service class object
        ProductService productService = new ProductService();
        System.out.println("-----List of Products------");

        try {
            List<Product> list = productService.getAllProductsWithVendorAndCategoryInfo();
            list.forEach(product -> {
                System.out.println("Product ID " + product.getId());
                System.out.println("Product Title " + product.getTitle());
                System.out.println("Product Stock Count " + product.getNumberStock());
                System.out.println("Vendor Name: " + product.getVendor().getName());
                System.out.println("Category Name " + product.getCategory().getName());
                System.out.println("   ");
            });
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }

    }
}
