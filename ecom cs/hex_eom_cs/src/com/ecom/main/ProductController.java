package com.ecom.main;

import com.ecom.dto.VendorProductDto;
import com.ecom.model.Product;
import com.ecom.model.Vendor;
import com.ecom.service.ProductService;
import com.ecom.utility.ProductSortUtility;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProductController {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductService productService = new ProductService();
        ProductSortUtility productSortUtilityPrice = new ProductSortUtility("price");
        ProductSortUtility productSortUtilityId = new ProductSortUtility("id");

        while(true){
            System.out.println("1. All Products with vendor and Category Info");
            System.out.println("2. Sorting Features");
            System.out.println("3. Vendor - Product stats");
            System.out.println("4. Vendor Adv Stats");
            System.out.println("5. Filter Options");
            System.out.println("0. Exit");
            int input = sc.nextInt();
            if(input == 0){
                break;
            }
            switch(input){
                case 1:
                    System.out.println("-----List of Products------");
                    try {
                        List<Product> list = productService.getAllProductsWithVendorAndCategoryInfo();

                        list.forEach(product -> {
                            System.out.println("Product ID " + product.getId());
                            System.out.println("Product Title " + product.getTitle());
                            System.out.println("Product Stock Count " + product.getNumberStock());
                            System.out.println("Product Price " + product.getPrice());
                            System.out.println("Vendor Name: " + product.getVendor().getName());
                            System.out.println("Category Name " + product.getCategory().getName());
                            System.out.println("   ");
                        });

                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                        //e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("Sorting Options");
                    System.out.println("Press 1. to sort by ID ASC");
                    System.out.println("Press 2. to sort by price DESC");
                    int sortInput = sc.nextInt();
                    try {
                        List<Product> list = productService.getAllProductsWithVendorAndCategoryInfo();
                        if(sortInput == 1){
                            list.sort(productSortUtilityId);
                        }
                        if(sortInput == 2){
                            list.sort(productSortUtilityPrice);
                        }
                        list.forEach(product -> {
                            System.out.println("Product ID " + product.getId());
                            System.out.println("Product Title " + product.getTitle());
                            System.out.println("Product Stock Count " + product.getNumberStock());
                            System.out.println("Product Price " + product.getPrice());
                            System.out.println("Vendor Name: " + product.getVendor().getName());
                            System.out.println("Category Name " + product.getCategory().getName());
                            System.out.println("   ");
                        });
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case 3:
                    System.out.println("---Vendor Product Stats---");

                    try {
                        Map<String,Integer> map = productService.getVendorProductStat();
                        System.out.println("   Vendor  " + "\t" + "Number of Products Sold");
                        for(Map.Entry<String,Integer> entry: map.entrySet()){
                            System.out.println(entry.getKey() + "\t" + entry.getValue());
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 4:
                    try {
                        List<VendorProductDto> list = productService.getVendorWithNumProductsAvgSellingPrice();
                        System.out.println("Vendor Name " + " Number of Products " + " Avg Selling Price ");
                        list.forEach(record -> {
                            System.out.println(record.vendorName() +
                                    "\t\t" + record.numberOfProducts() +
                                    "\t\t" + record.avgSellingPrice());
                        });
                    } catch (SQLException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("1. Filter products by Vendor");
                    System.out.println("2. Filter products by Category");
                    int filterInput = sc.nextInt();
                    switch(filterInput){
                        case 1:
                            try {
                                //i am fetching all products with vendor and category info without filter
                                List<Product> list = productService.getAllProductsWithVendorAndCategoryFullInfo();
                                // i need to fetch vendor info from the above list -- no db access
                                List<Vendor> vendorList =   productService.getAllVendorInfo(list);
                                System.out.println("Vendor ID \t Vendor Name");
                                //show vendor info to user with id
                                vendorList.forEach(vendor->{
                                    System.out.println(vendor.getId() + "\t\t" + vendor.getName());
                                });
                                //reading the id
                                System.out.println("please select vendor ID: ");
                                int vid = sc.nextInt();
                                // now i need to filter the products from original list by vid
                                List<Product> listProductsByVendorId = productService.getProductsByVendorId(list,vid);
                                listProductsByVendorId.forEach(product -> {
                                    System.out.println("Product ID " + product.getId());
                                    System.out.println("Product Title " + product.getTitle());
                                    System.out.println("Product Stock Count " + product.getNumberStock());
                                    System.out.println("Product Price " + product.getPrice());
                                    System.out.println("Vendor Name: " + product.getVendor().getName());
                                    System.out.println("Category Name " + product.getCategory().getName());
                                    System.out.println("   ");
                                });
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 2:
                            break;
                    }
                    break;
                default:
                    System.out.println("Invalid input selected...");
            }

        }
        sc.close();
    }
}
