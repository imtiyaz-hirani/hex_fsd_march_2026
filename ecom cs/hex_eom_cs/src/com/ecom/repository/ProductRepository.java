package com.ecom.repository;

import com.ecom.dao.ProductDao;
import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.Vendor;
import com.ecom.utility.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class ProductRepository implements ProductDao {
    @Override
    public List<Product> getAllProductsWithVendorAndCategoryInfo() throws SQLException {
       List<Product> list = new ArrayList<>();
        //open DB connection
        Connection conn = DBConnection.dbConnect();
        String sql = "select p.id,p.title,p.number_stock,p.price,v.name as vendorName,c.name as categoryName" +
                " from product p " +
                " join vendor v on v.id= p.vendor_id " +
                " join category c on c.id= p.category_id ";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rst =  stmt.executeQuery();

        while(rst.next()){
            Product product = new Product();
            int id = rst.getInt("id");
            String title = rst.getString("title");
            int numberStock = rst.getInt("number_stock");
            BigDecimal price = rst.getBigDecimal("price");
            product.setId(id);
            product.setTitle(title);
            product.setNumberStock(numberStock);
            product.setPrice(price);
            String vendorName = rst.getString("vendorName");
            Vendor vendor = new Vendor();
            vendor.setName(vendorName);

            String categoryName = rst.getString("categoryName");
            Category category = new Category();
            category.setName(categoryName);

            //Attach vendor and category to product
            product.setCategory(category);
            product.setVendor(vendor);
            list.add(product);
        }
        DBConnection.dbClose();
        return list;
    }

    @Override
    public Map<String, Integer> getVendorProductStat() throws SQLException {
        Connection conn  = DBConnection.dbConnect();
        Map<String, Integer> map = new LinkedHashMap<>(); //maintains insertion order
        String sql = "select v.name as vendor , count(p.id) as number_of_products" +
                " from product p RIGHT JOIN vendor v ON p.vendor_id = v.id " +
                " group by v.name " +
                " order by number_of_products DESC";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rst =  stmt.executeQuery();
        while(rst.next()){
            String vendorName = rst.getString("vendor");
            int numProducts = rst.getInt("number_of_products");
            // System.out.println("Before giving to Map");
            // System.out.println(vendorName + " --" + numProducts);
            map.put(vendorName,numProducts);
        }
        DBConnection.dbClose();
        return map;
    }


}
