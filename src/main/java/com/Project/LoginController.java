package com.Project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    public Button loginBtn;
    @FXML
    private TextField usernameTextField, passwordTextField;
    @FXML
    private Label invalidDetails;
    private final ArrayList<Users> usersArray;

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
        //When both field are correctly entered
        else if (!(usernameTextField.getText().isBlank()) && !(passwordTextField.getText().isBlank())){
                invalidDetails.setText("Login Successful!");
                invalidDetails.setStyle(successMessage);
                usernameTextField.setStyle(successStyle);
                passwordTextField.setStyle(successStyle);

                String userName = usernameTextField.getText();
                String password = passwordTextField.getText();

                boolean status = false;

                for (Users user:
                        usersArray) {
                    if (user.getName().equals(userName) && user.getPassword().equals(password)) {
                        if (user.getRole().equals("Admin")) {
                            Driver.getWindow().setScene(SystemController.getAdminControl());
                            status = true;
                            break;
                        }
                        else{
                            Driver.getWindow().setScene(SystemController.getControllerControl());
                            status = true;
                            break;
                        }
                    }
                }


            // When both fields are entered incorrect
            if (!status){
                invalidDetails.setText("Invalid username and password");
                invalidDetails.setStyle(errorMessage);
                usernameTextField.setStyle(errorStyle);
                passwordTextField.setStyle(errorStyle);
            }
            }
    }
}