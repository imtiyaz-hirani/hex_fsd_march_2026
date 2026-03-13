package com.ecom.service;

import com.ecom.dto.VendorProductDto;
import com.ecom.model.Product;
import com.ecom.model.Vendor;
import com.ecom.repository.ProductRepository;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductService {

    ProductRepository productRepository = new ProductRepository(); //POJO -pure

    public List<Product> getAllProductsWithVendorAndCategoryInfo() throws SQLException {
        return productRepository.getAllProductsWithVendorAndCategoryInfo();
    }

    public Map<String, Integer> getVendorProductStat() throws SQLException {

        Map<String, Integer> map =  productRepository.getVendorProductStat();
//        System.out.println("--After giving to Map--");
//        for(Map.Entry<String,Integer> entry: map.entrySet()){
//            System.out.println(entry.getKey() + "\t" + entry.getValue());
//        }
//        System.out.println("-----check ends----");
        return map;
    }

    public List<VendorProductDto> getVendorWithNumProductsAvgSellingPrice() throws SQLException {

       return productRepository.getVendorWithNumProductsAvgSellingPrice();
    }

    public List<Product> getAllProductsWithVendorAndCategoryFullInfo() throws SQLException {
        List<Product> list =  productRepository.getAllProductsWithVendorAndCategoryFullInfo();
        return list;
    }

    public List<Vendor> getAllVendorInfo(List<Product> list) {
        return list.stream()
                .map(Product :: getVendor)
                .distinct() //unique vendors --> v1 v2
                .toList(); //List<Vendor>

    }

    public List<Product> getProductsByVendorId(List<Product> list, int vid) { //1=1   2=1   1=1  3=1
        return list.stream() //Stream<Product>
                .filter(product -> product.getVendor().getId() == vid) //Stream<Product> --> filtered list
                .toList(); //List<Product>

    }
}
