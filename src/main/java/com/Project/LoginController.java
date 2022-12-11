package com.Project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    @FXML
    private TextField usernameTextField, passwordTextField;
    @FXML
    private Label invalidDetails;
    private final String USR = "admin";
    private final String PASS = "admin";
    private ArrayList<Users> usersArray;

//    private final ArrayList<String> USR = new ArrayList<>();
//    private final ArrayList<String> PASS = new ArrayList<>();

    {
        usersArray = FileHandling.readFromFile(Files.getUsersFile());
        for (Users u :
                usersArray) {
            System.out.println(u);
        }

    }

    // Strings which hold css elements to easily re-use in the application
    protected
    String successMessage = "-fx-text-fill: GREEN;";
    String errorMessage = "-fx-text-fill: RED;";
    String errorStyle = "-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;";
    String successStyle = "-fx-border-color: #A9A9A9; -fx-border-width: 1; -fx-border-radius: 5;";

    @FXML
    public void checkValidity() throws IOException {
        // In case the Username and Password fields are left blank then display the error message
        if (usernameTextField.getText().isBlank() || passwordTextField.getText().isBlank()) {
            invalidDetails.setStyle(errorMessage);
        }
        // When the username and password are blank
        if (usernameTextField.getText().isBlank() && passwordTextField.getText().isBlank()) {
            invalidDetails.setText("The Login fields are required!");
            usernameTextField.setStyle(errorStyle);
            passwordTextField.setStyle(errorStyle);
        }

        // When the username is blank and password is entered
        else if(usernameTextField.getText().isBlank()){
            usernameTextField.setStyle(errorStyle);

            // If password is entered correctly
            if (passwordTextField.getText().equals(PASS)){
                passwordTextField.setStyle(successStyle);
                invalidDetails.setText("Username is required");
            }
            // if password is incorrect
            else{
                passwordTextField.setStyle(errorStyle);
                invalidDetails.setText("Invalid username and password");
            }
        }
        // When the password is blank and username is entered correctly
        else if (passwordTextField.getText().isBlank()) {
            passwordTextField.setStyle(errorStyle);

            // If username is correct
            if (usernameTextField.getText().equals(USR)){
                usernameTextField.setStyle(successStyle);
                invalidDetails.setText("Password is required");
            }
            // If username is incorrect
            else {
                usernameTextField.setStyle(errorStyle);
                invalidDetails.setText("Invalid username and password");
            }
        }
        //When both field are correctly entered
        else if (usernameTextField.getText().equals(USR) && passwordTextField.getText().equals(PASS)){
            invalidDetails.setText("Login Successful!");
            invalidDetails.setStyle(successMessage);
            usernameTextField.setStyle(successStyle);
            passwordTextField.setStyle(successStyle);

            SystemController.getStage().show();
            Driver.getWindow().close();

        }

        // When both fields are entered incorrect
        else {
            invalidDetails.setText("Invalid username and password");
            invalidDetails.setStyle(errorMessage);
            usernameTextField.setStyle(errorStyle);
            passwordTextField.setStyle(errorStyle);
        }

        if (!(usernameTextField.getText().isBlank()) && !(passwordTextField.getText().isBlank())){
            System.out.println("We are not empty");

            String userName = usernameTextField.getText();
            String password = passwordTextField.getText();

            for (Users user:
                 usersArray) {
                if (user.getName().equals(userName) && user.getPassword().equals(password)) {
                    if (user.getRole().equals("Admin")) {
                        SystemController.getStage().show();
                        Driver.getWindow().close();
                        break;
                    }
                    else{
                        SystemController.getStage().show();
//                        SystemController.disableOptions();
                        Driver.getWindow().close();
                    }
                }
            }
        }
    }
}