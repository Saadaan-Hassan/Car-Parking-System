package com.Project;

import com.Boxes.AlertBox;
import com.Boxes.ConfirmBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SystemController implements Initializable, Serializable {
    private static final FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("System.fxml"));
    @FXML
    private AnchorPane SlotsPane;
    @FXML
    private Button delFloorBtn;
    @FXML
    private Button editFloorBtn;
    @FXML
    private GridPane UsersPane;
    @FXML
    private TableView<Floor> tbFloors;
    @FXML
    private TableColumn<Floor, Integer> floorIdCol;
    @FXML
    private TableColumn<Floor, String> floorNameCol;
    @FXML
    private TableColumn<Floor, Integer> NoOfSlotsCol;
    @FXML
    private AnchorPane parkingSlotPane;
    @FXML
    private Button SlotsOption;
    @FXML
    private Pagination pagination;
    @FXML
    private TextField tfFloorName;
    @FXML
    private TextField tfNumberOfSlots;
    @FXML
    private GridPane addFloorPane;
    @FXML
    private Button logoutBtn;
    @FXML
    private GridPane deleteUserPane;
    @FXML
    private TextField tfDeleteUserId;
    @FXML
    private Button deleteUserBtn;
    @FXML
    private TableView<Users> usersTable;
    @FXML
    private TableColumn<Users, Integer> idColumn;
    @FXML
    private TableColumn<Users, String> usernameColumn;
    @FXML
    private TableColumn<Users, String> passwordColumn;
    @FXML
    private TableColumn<Users, String> roleColumn;
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

    public void logoutBtnAction() {

    }

    public void showSlotsPane(){
        pagination.setVisible(!pagination.isVisible());
    }
    //Add User Option
    public void showAddFloorPane(){
        showPane(addFloorPane);
    }

    public void showUsersPane(){
        showPane(UsersPane);
    }

    //Exit Button Action
    public void handleExit(){
        if (ConfirmBox.display("Exit", "Are you sure you want to exit?"))
            Platform.exit();
    }

    //---------------------------------------------//

    //Add Floor Button Action
    public void addFloorBtnAction(){
        if (tfFloorName.getText() == null || tfNumberOfSlots.getText() == null)
            AlertBox.display("Empty Fields", "Fields are empty or role not selected!");
        else {
            Floor.addFloor(new Floor(tfFloorName.getText(), Integer.parseInt(tfNumberOfSlots.getText())), tbFloors);
            tfFloorName.clear();
            tfNumberOfSlots.clear();

            Slots.showSlots(pagination);
        }
    }

    //Edit Floor Button Action
    public void editFloorBtnAction(){
        if (selectFloorTableRow() != null)
            Floor.editFloor(tbFloors, pagination, selectFloorTableRow());
//
    }

    public Floor selectFloorTableRow(){
        Floor selectedFloor = tbFloors.getSelectionModel().getSelectedItem();
        if (selectedFloor != null){
            tfFloorName.setText(selectedFloor.getFloorName());
            tfNumberOfSlots.setText(Integer.toString(selectedFloor.getNoOfSlots()));
        }

    return selectedFloor;
    }

    public void deleteFloorBtnAction(){
        Floor.delFloor(tbFloors, pagination);
    }

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

    //Edit User Button Action
    public void editUserBtnAction(){
        Users selectedUser = selectUserTableRow();
        if (selectedUser != null)
            Users.editUser(usersTable, selectedUser);
    }

    //Delete User Button Action
    public void deleteUserBtnAction(){
            Users.delUsers(usersTable);
    }

    public Users selectUserTableRow(){
        Users selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null){
            tfName.setText(selectedUser.getName());
            tfPassword.setText(selectedUser.getPassword());
            cbRole.setValue(selectedUser.getRole());
        }

        return selectedUser;
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

        //Showing All Parking Slots
        Slots.showSlots(pagination);

        //Showing All Floor on the table
        floorIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        floorNameCol.setCellValueFactory(new PropertyValueFactory<>("floorName"));
        NoOfSlotsCol.setCellValueFactory(new PropertyValueFactory<>("noOfSlots"));
        tbFloors.setItems(Floor.showFloor());
    }

    private static void closeProgram(){
        if (ConfirmBox.display("Exit", "Are you sure you want to exit?"))
            Platform.exit();
    }

    private<T extends Pane> void showPane(T t){
        if (t.isVisible())
            t.setVisible(false);
        else
            t.setVisible(true);
    };
}
