package com.Project;

import java.util.Date;

public class Vehicle {
    private static int vehicleId = 0;
    private String plateNo;
    private String customerName;
    private int mobileNumber;
    private Date date;

    public Vehicle(String plateNo, String customerName, int mobileNumber, Date date) {
        this.plateNo = plateNo;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.date = date;

        vehicleId++;
    }
}
