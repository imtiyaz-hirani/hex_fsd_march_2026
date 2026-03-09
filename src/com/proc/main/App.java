package com.proc.main;

import com.proc.db.CustomerDB;
import com.proc.model.Customer;

import java.util.List;

public class App {
    public static void main(String[] args) {
        CustomerDB customerDB = new CustomerDB();
        customerDB.dbConnect();
        System.out.println("----------ALL CUSTOMERS-----------");
        List<Customer> list=  customerDB.getAllCustomers();
        list.forEach(System.out::println);

        System.out.println("----------GET BY CITY-----------");
        customerDB.getCustomerByCity("").forEach(System.out::println);
        customerDB.dbClose();
    }
}
