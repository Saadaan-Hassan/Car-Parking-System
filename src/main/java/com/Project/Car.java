package com.Project;

import java.util.Date;

public class Car extends Vehicle {

    private double rate = 100.00;
    public Car(String plateNo, String customerName, int mobileNumber, Date date) {
        super(plateNo, customerName, mobileNumber, date);
    }
}
