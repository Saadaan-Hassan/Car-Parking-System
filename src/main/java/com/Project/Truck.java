package com.Project;

import java.util.Date;

public class Truck extends Vehicle{
    private double rate = 200.00;
    public Truck(String plateNo, String customerName, int mobileNumber, Date date) {
        super(plateNo, customerName, mobileNumber, date);
    }
}
