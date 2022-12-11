package com.Project;

import com.Boxes.AlertBox;
import com.Boxes.ConfirmBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SystemController implements Initializable, Serializable {
    private static final FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("System.fxml"));
    @FXML
    private GridPane deleteUserPane;
    @FXML
    private TextField tfDeleteUserId;
    @FXML
    private Button deleteUserBtn;
    @FXML
    private TableView<Users> usersTable;
    @FXML
    private TableColumn<Users, ? extends Object> idColumn;
    @FXML
    private TableColumn<Users, ? extends Object> usernameColumn;
    @FXML
    private TableColumn<Users, ? extends Object> passwordColumn;
    @FXML
    private TableColumn<Users, ? extends Object> roleColumn;
    @FXML
    private AnchorPane showUserPane;
    @FXML
    private Label nameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Button addUserBtn;
    @FXML
    private GridPane addUserPane;
    @FXML
    private Button exitBtn;
    @FXML
    private Button parkOption;
    @FXML
    private Button moveOption;
    @FXML
    private Button showVehiclesOption;
    @FXML
    private Button showSlotsOption;
    @FXML
    private static Button addUserOption;
    @FXML
    private static Button showUserOption;
    @FXML
    private static Button deleteUserOption;

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
        stage.setMaximized(true);

        stage.setOnCloseRequest(e ->{
            e.consume();
            closeProgram();
        });

        return stage;
    }

    //Add User Option
    public void showAddUserPane(){
        showPane(addUserPane);
    }

    //Show All Users Option
    public void showAllUser(){
        showPane(showUserPane);
    }

    public void showDeleteUserPane(){
        showPane(deleteUserPane);
    }

    //Exit Button Action
    public void handleExit(){
        if (ConfirmBox.display("Exit", "Are you sure you want to exit?"))
            Platform.exit();
    }

    //---------------------------------------------//

    //Add User Button Action
    public void addUserBtnAction(){
        if (tfName.getText() == null || tfPassword.getText() == null || cbRole.getValue() == null)
            AlertBox.display("Empty Fields", "Fields are empty or role not selected!");
        else {
            Users.addUser(new Users(tfName.getText(), tfPassword.getText(), cbRole.getValue()), usersTable);

            // Clearing the Fields
            tfName.clear();
            tfPassword.clear();
        }
    }

    public void deleteUserBtnAction(){
        ArrayList<Users> users = FileHandling.readFromFile(Files.getUsersFile());

        File file = new File(Files.getUsersFile());
        file.delete();

        for (Users u :
                users) {
            if (!(u.getId() == Integer.parseInt(tfDeleteUserId.getText()))) {
                FileHandling.writeToFile(Files.getUsersFile(), u);
            }
        }
        usersTable.getItems().clear();
        usersTable.setItems(Users.showUsers());


    }

    public static void disableOptions(){
        addUserOption.setDisable(true);
        showUserOption.setDisable(true);
        deleteUserOption.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Adding items to Combo Box
        cbRole.getItems().addAll("Admin", "Controller");

        //Showing All user on the table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        usersTable.setItems(Users.showUsers());

    }

    private static void closeProgram(){
        if (ConfirmBox.display("Exit", "Are you sure you want to exit?"))
            Platform.exit();
    }

    private void showPane(Pane pane){
        if (pane.isVisible())
            pane.setVisible(false);
        else
            pane.setVisible(true);
    };
}
