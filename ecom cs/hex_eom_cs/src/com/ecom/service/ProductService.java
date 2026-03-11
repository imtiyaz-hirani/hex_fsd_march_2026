package com.ecom.service;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    ProductRepository productRepository = new ProductRepository(); //POJO -pure

    public List<Product> getAllProductsWithVendorAndCategoryInfo() throws SQLException {
        return productRepository.getAllProductsWithVendorAndCategoryInfo();
    }
}
