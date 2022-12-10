package com.Project;

import com.Boxes.AlertBox;

import java.io.Serializable;
import java.util.ArrayList;

public class Users implements Serializable {

    private final String name;
    private final String password;
    private final String role;

    public Users(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    //Add User
    public static void addUser(String name, String password, String role){

        String fileName = "src/main/Files/UserData.ser";

        Users newUser = new Users(name, password, role);
        System.out.println(newUser);

        FileHandling.writeToFile(fileName, newUser);
//            Users obj = (Users) FileHandling.readFromFile(fileName);

            AlertBox.display("", "New User Added Successfully");

//            System.out.println(obj);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", name, password, role);
    }
}
