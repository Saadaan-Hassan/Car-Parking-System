package com.Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Users implements Serializable {
    private final String name;
    private final String password;
    private final String role;
    private int id;


    @Serial
    private static final long serialVersionUID = 5030062279249430796L;

    /*======================================== Constructors ========================================*/
    public Users(int id, String name, String password, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }
    public Users(String name, String password, String role) {
        SecureRandom random = new SecureRandom();
        ArrayList<Users> users = FileHandling.readFromFile(Files.getUsersFile());
        int ran = random.nextInt(1,10000);

        for (Users u :
                users) {
           if (ran!= u.id)
              id = ran;

        }
        this.name = name;
        this.password = password;
        this.role = role;
    }

    /*=============================================================================================*/

    /*========================================= Add User ==========================================*/
    public static void addUser(Users newUser, TableView<Users> table) {
        FileHandling.appendToFile(Files.getUsersFile(), newUser);

        table.getItems().add(newUser);

        Boxes.alertBox("", "New User Added Successfully");

    }

    /*==============================================================================================*/

    /*========================================= Show Users =========================================*/
    public static ObservableList<Users> showUsers() {
        ArrayList<Users> usersArray = FileHandling.readFromFile(Files.getUsersFile());
        return FXCollections.observableList(usersArray);
    }

    /*==============================================================================================*/

    /*========================================= Edit Users =========================================*/
    public static void editUser(TableView<Users> table, Users editUser){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setX(100);
        stage.setY(100);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        //Id Area
        Label labelId = new Label("ID");
        grid.add(labelId, 0,0);

        Text tId = new Text();
        tId.setText(Integer.toString(editUser.id));
        grid.add(tId, 1,0);

        //Name Area
        Label labelName = new Label("User Name");
        grid.add(labelName, 0,1);

        TextField tfName = new TextField();
        tfName.setPromptText("Enter User Name");
        tfName.setText(editUser.name);
        grid.add(tfName, 1,1);

        //Slots Area
        Label labelPassword = new Label("Password");
        grid.add(labelPassword, 0,2);

        TextField tfPassword = new TextField();
        tfPassword.setPromptText("Enter Password");
        tfPassword.setText(editUser.password);
        grid.add(tfPassword, 1,2);

        Label labelRole = new Label("Role");
        grid.add(labelRole, 0, 3);

        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "Controller");
        ComboBox<String> comboBox = new ComboBox<>(roles);
        comboBox.setValue(editUser.getRole());
        grid.add(comboBox, 1,3);

        //Button
        Button btn = new Button("OK");
        btn.setPrefWidth(110);
        btn.setPrefHeight(30);
        HBox hBox = new HBox(btn);
        hBox.setAlignment(Pos.CENTER);

        grid.add(hBox, 0,4, 2,1);

        btn.setOnAction(e ->{

            if (tfName.getText().isEmpty() || tfPassword.getText().isEmpty() || comboBox.getValue() == null){
                Boxes.alertBox("Empty Fields", "Fields are empty!");
            }
            else {
                ArrayList<Users> usersArray = FileHandling.readFromFile(Files.getUsersFile());
                if (Boxes.confirmBox("Edit User", "Do you want to save changes?")) {
                    File file = new File(Files.getUsersFile());
                    file.delete();

                    for (Users u :
                            usersArray) {
                        if (u.id == Integer.parseInt(tId.getText())) {
                            FileHandling.writeToFile(Files.getUsersFile(), new Users(u.id, tfName.getText(), tfPassword.getText(), comboBox.getValue().toString()));
                        } else
                            FileHandling.writeToFile(Files.getUsersFile(), u);
                    }

                    table.getItems().clear();
                    table.setItems(Users.showUsers());
                }
            }
        });

        Scene scene = new Scene(grid, 330, 200);

        stage.setTitle("Edit Floor");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();

    }

    /*==============================================================================================*/

    /*======================================== Delete Users ========================================*/
    public static void delUsers(TableView<Users> table){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setX(100);
        stage.setY(100);

        Label label = new Label("ID");

        TextField textField = new TextField();
        textField.setPromptText("Enter the ID");

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(label,textField);

        Button btn = new Button("OK");
        btn.setPrefWidth(110);
        btn.setPrefHeight(30);
        btn.setOnAction(e->{
            ArrayList<Users> users = FileHandling.readFromFile(Files.getUsersFile());
            if (textField.getText().isEmpty()){
                Boxes.alertBox("Empty Field", "Enter the ID!");
            }
            else {
                if (users.size() == 1 && users.get(0).role.equals("Admin"))
                    Boxes.alertBox("", "At least one Admin is required!");
                else {
                    if (Boxes.confirmBox("Delete User", "Are you sure you want to delete User?")) {
                        File file = new File(Files.getUsersFile());
                        file.delete();

                        for (Users u :
                                users) {
                            if (!(u.id == Integer.parseInt(textField.getText())))
                                FileHandling.writeToFile(Files.getUsersFile(), u);
                        }

                        if (!(file.exists())) {
                            try {
                                file.createNewFile();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }

                        textField.clear();
                        table.getItems().clear();
                        table.setItems(Users.showUsers());


                    }
                }
            }
        });

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().addAll(hBox, btn);

        Scene scene = new Scene(vBox, 300, 150);

        stage.setTitle("Delete User");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    /*==============================================================================================*/

    /*========================================== Getters ===========================================*/

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
