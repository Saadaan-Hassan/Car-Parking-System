package com.Project;

import com.Boxes.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.Serial;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Users implements Serializable {
    private final String name;
    private final String password;
    private final String role;
    private int id;


    @Serial
    private static final long serialVersionUID = 5030062279249430796L;

    public Users(String name, String password, String role) {
        SecureRandom random = new SecureRandom();
        ArrayList<Users> users = FileHandling.readFromFile(Files.getUsersFile());
        int ran = random.nextInt(1,1000);

        for (Users u :
                users) {
           if (ran!= u.id)
              id = ran;

        }
        this.name = name;
        this.password = password;
        this.role = role;
    }
    //Add User

    public static void addUser(Users newUser, TableView<Users> table) {
        FileHandling.appendToFile(Files.getUsersFile(), newUser);

        table.getItems().add(newUser);

        AlertBox.display("", "New User Added Successfully");

    }

    public static ObservableList<Users> showUsers() {
        ArrayList<Users> usersArray = FileHandling.readFromFile(Files.getUsersFile());
        return FXCollections.observableList(usersArray);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return String.format("%d %s %s %s",id , name, password, role);
    }
}
