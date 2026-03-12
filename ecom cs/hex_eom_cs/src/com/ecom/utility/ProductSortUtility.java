package com.ecom.utility;

import com.ecom.model.Product;

import java.util.Comparator;

public class ProductSortUtility implements Comparator<Product> {

    private String field;

    public ProductSortUtility(String field) {
        this.field = field;
    }

    public ProductSortUtility() {
    }

    @Override
    public int compare(Product p1, Product p2) {
        if(field.equals("id"))
            return p1.getId() - p2.getId();
        if(field.equals("price"))
            return p1.getPrice().compareTo(p2.getPrice());
        return 0;
    }
}
