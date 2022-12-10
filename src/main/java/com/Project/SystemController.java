package com.Project;

import com.Boxes.AlertBox;
import com.Boxes.ConfirmBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SystemController implements Initializable, Serializable {
    private static final FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("System.fxml"));

    @FXML
    public Button exitBtn;

    @FXML
    private ComboBox<String> cbRole;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPassword;

    @FXML
    private Text tError;


    //Getting Scene
    public static Stage getStage() throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setTitle("Parking System");
        stage.setResizable(false);
//        stage.setFullScreen(true);
        stage.setMaximized(true);

        stage.setOnCloseRequest(e ->{
            e.consume();
            closeProgram();
        });

        return stage;
    }

    //Exit Button Action
    public void handleExit(){
        if (ConfirmBox.display("Exit", "Are you sure you want to exit?"))
            Platform.exit();
    }

    //Add User Button Action
    public void addUserBtnAction(){
        if (tfName.getText() == null || tfPassword.getText() == null || cbRole.getValue() == null)
            AlertBox.display("Empty Fields", "Fields are empty or role not selected!");
        else {
            Users.addUser(tfName.getText(), tfPassword.getText(), cbRole.getValue());

            // Resetting the Fields
            tfName.setText("");
            tfPassword.setText("");
            cbRole.setPromptText("Select Role");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Adding items to Combo Box
        cbRole.getItems().addAll("Admin", "Controller");
    }

    private static void closeProgram(){
        if (ConfirmBox.display("Exit", "Are you sure you want to exit?"))
            Platform.exit();
    }
}
