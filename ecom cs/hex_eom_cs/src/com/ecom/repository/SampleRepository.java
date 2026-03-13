package com.ecom.repository;

import com.ecom.utility.DBConnection;

public class SampleRepository {
    DBConnection dbConnection = DBConnection.getInstance();

    public void sampleMethod(){
        System.out.println(dbConnection);
        dbConnection.dbConnect();
        System.out.println("this is my test service ");
        dbConnection.dbClose();
    }
}
