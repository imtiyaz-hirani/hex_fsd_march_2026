package com.ecom.dao;

import com.ecom.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    List<Product>  getAllProductsWithVendorAndCategoryInfo() throws SQLException;
}
