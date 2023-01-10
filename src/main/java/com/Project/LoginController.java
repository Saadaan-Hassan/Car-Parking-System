package com.Project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    private static final ArrayList<Users> usersArray;
    static {
        usersArray = DatabaseHandling.readFromUsersTable();
    }

    public Button loginBtn;
    @FXML
    private TextField usernameTextField, passwordTextField;
    @FXML
    private Label invalidDetails;

    // Strings which hold css elements to easily re-use in the application
    protected
    String successMessage = "-fx-text-fill: GREEN;";
    String errorMessage = "-fx-text-fill: RED;";
    String errorStyle = "-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;";
    String successStyle = "-fx-border-color: #A9A9A9; -fx-border-width: 1; -fx-border-radius: 5;";

    @FXML
    public void checkValidity() {
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

        else if (!(usernameTextField.getText().isBlank()) && !(passwordTextField.getText().isBlank())){
                invalidDetails.setText("Login Successful!");
                invalidDetails.setStyle(successMessage);
                usernameTextField.setStyle(successStyle);
                passwordTextField.setStyle(successStyle);


                String userName = usernameTextField.getText();
                String password = passwordTextField.getText();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     

                //Reading users data from the UserData.ser
//                ArrayList<Users> usersArray = FileHandling.readFromFile(Files.getUsersFile());

                boolean status = false;

                for (Users user:
                        usersArray) {
                    //When both field are correctly entered
                    if (user.getName().equals(userName) && user.getPassword().equals(password)) {

                        //If the user is Admin
                        if (user.getRole().equals("Admin")) {
                            Driver.getWindow().setScene(SystemController.getAdminControl());

                            Driver.windowSetting();
                            Driver.getWindow().setResizable(false);
                       }

                        //if the user is Controller
                        else{
                            Driver.getWindow().setScene(SystemController.getControllerControl());

                            Driver.windowSetting();
                            Driver.getWindow().setResizable(false);
                        }
                        status = true;
                        break;
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