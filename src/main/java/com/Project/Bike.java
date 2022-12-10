package com.Project;

import java.util.Date;

public class Bike extends Vehicle {
    private double rate = 50.00;
    public Bike(String plateNo, String customerName, int mobileNumber, Date date) {
        super(plateNo, customerName, mobileNumber, date);
    }
}
