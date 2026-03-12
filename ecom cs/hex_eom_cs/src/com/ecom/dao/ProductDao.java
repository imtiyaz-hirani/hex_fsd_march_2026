package com.ecom.dao;

import com.ecom.dto.VendorProductDto;
import com.ecom.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductDao {
    List<Product>  getAllProductsWithVendorAndCategoryInfo() throws SQLException;
    Map<String, Integer> getVendorProductStat() throws SQLException;

    List<VendorProductDto> getVendorWithNumProductsAvgSellingPrice()  throws SQLException;
}
