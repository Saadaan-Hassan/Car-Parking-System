package com.Project;

import com.Boxes.AlertBox;
import com.Boxes.ConfirmBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
    private VBox slotsOptionPane;
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

    public void showSlotsOptionPane(){
        showPane(slotsOptionPane);
    }

    public void showSlotsPane(){
        pagination.setVisible(!pagination.isVisible());
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

    public void showAddFloorPane(){
        showPane(addFloorPane);
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
            Floor.addFloor(new Floor(tfFloorName.getText(), Integer.parseInt(tfNumberOfSlots.getText())));
            tfFloorName.clear();
            tfNumberOfSlots.clear();
        }
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

    public void deleteUserBtnAction(){
        if(ConfirmBox.display("Delete User", "Are you sure you want to delete the user?")) {

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

        //Showing All Parking Slots
        pagination.setPageCount(FileHandling.readFromFile(Files.getFloorFile()).size());
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return showFloor(pageIndex);
            }
        });

    }

    private ScrollPane showFloor(int index){
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20));
        ArrayList<Floor> floor = FileHandling.readFromFile(Files.getFloorFile());

        double numberOfSlots = floor.get(index).getSlots().length;
        double columns = 8;
        double rows = Math.ceil(numberOfSlots/columns);

        int count = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns && count <= numberOfSlots; j++) {
                VBox vBox = new VBox(15);
                vBox.setAlignment(Pos.CENTER);
                vBox.setPadding(new Insets(25));
                String status;

                if (floor.get(index).getSlots()[j].isReserved()){
                    status = "Reserved";
                    vBox.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else{
                    status = "Available";
                    vBox.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                Text tStatus = new Text(status);
                tStatus.setFill(Color.WHITE);
                Text tSlotNumber = new Text(String.format("Slot-%d",count));
                tSlotNumber.setFill(Color.WHITE);
                vBox.getChildren().addAll(tStatus,tSlotNumber);
                grid.add(vBox,j,i);
                count++;
            }
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(grid);
        return scrollPane;

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
