package com.Project;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SystemController implements Initializable, Serializable {
    @FXML
    private Button showVehicleHistoryBtn;
    @FXML
    private Button floorsBtn;
    @FXML
    private VBox vehicleHistoryPane;
    @FXML
    private TableView<Vehicle> tbVehicleHistory;
    @FXML
    private TableColumn<Vehicle, String> customerNameCol;
    @FXML
    private TableColumn<Vehicle, Long> mobileNoCol;
    @FXML
    private TableColumn<Vehicle, String> vehicleTypeCol;
    @FXML
    private TableColumn<Vehicle, String> vehicleNoCol;
    @FXML
    private TableColumn<Vehicle, String> timeInCol;
    @FXML
    private TableColumn<Vehicle, String> timeOutCol;
    @FXML
    private TableColumn<Vehicle, String> floorCol;
    @FXML
    private TableColumn<Vehicle, Integer> slotNoCol;
    @FXML
    private TableColumn<Vehicle, String> dateCol;
    @FXML
    private Button userBtn;
    @FXML
    private Text tCustomerName;
    @FXML
    private Text tMobileNo;
    @FXML
    private Text tVehicleType;
    @FXML
    private Text tVehiclePlateNo;
    @FXML
    private Text tTimeIn;
    @FXML
    private Text tFloor;
    @FXML
    private Text tTotalBill;
    @FXML
    private Text tPricePerHour;
    @FXML
    private TableView<Vehicle> tbUnparkVehicle;
    @FXML
    private TableColumn<Vehicle, Integer> vehicleIdCol;
    @FXML
    private TableColumn<Vehicle, String> vehiclePlateNoCol;
    @FXML
    private TableColumn<Vehicle, String> vehicleCustomerCol;
    @FXML
    private TableColumn<Vehicle, String> vehicleTimeInCol;
    @FXML
    private TextField tfTimeOut;
    @FXML
    private GridPane unparkVehiclePane;
    @FXML
    private Text tUnparkSlotNo;
    @FXML
    private ComboBox<String> cbFloor;
    @FXML
    private Text tvehiclePrice;
    @FXML
    private TextField tfTimeIn;
    @FXML
    private ComboBox<String> cbVehicleType;
    @FXML
    private TextField tfVehiclePlateNo;
    @FXML
    private TextField tfPersonName;
    @FXML
    private TextField tfMobileNo;
    @FXML
    private GridPane vehicleEntryPane;
    @FXML
    private Text tSlotNo;
    @FXML
    private AnchorPane SlotsPane;
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
    private Pagination pagination;
    @FXML
    private TextField tfFloorName;
    @FXML
    private TextField tfNumberOfSlots;
    @FXML
    private GridPane floorPane;
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
    private ComboBox<String> cbRole;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPassword;

    /*==================================== Controlling User Access ====================================*/
    private static String status = null;

    //If user is entered as "Admin"
    public static Scene getAdminControl() throws IOException {
        status = "Admin";
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/com/Project/System.fxml"));
        return new Scene(fxmlLoader.load());
    }

    //If user is entered as "Controller"
    public static Scene getControllerControl() throws IOException {
        status = "Controller";
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/com/Project/System.fxml"));
        return new Scene(fxmlLoader.load());
    }

    //Disable options for "Controller"
    public void disableOptions(){
        showVehicleHistoryBtn.setDisable(true);
        floorsBtn.setDisable(true);
        userBtn.setDisable(true);
    }

    //Enable options for "Admin"
    public void enableOptions(){
        showVehicleHistoryBtn.setDisable(false);
        floorsBtn.setDisable(false);
        userBtn.setDisable(false);
    }

    //================================================================================================//

    /*======================================== Panes Toggling ========================================*/

    //This method toggles the visibility of Panes
    private<T extends Pane> void showPane(T t){
        for (Pane p :
                allPanes) {
            p.setVisible(t == p);
        }
    }

    //Toggling Vehicle Entry Pane
    public void showVehicleEntryPane(){
     showPane(vehicleEntryPane);
    }

    //Toggling Vehicle Un-park Pane
    public void showUnparkVehiclePane(){
        showPane(unparkVehiclePane);
    }

    //Toggling Vehicle History Pane
    public void showVehicleHistoyPane(){
        showPane(vehicleHistoryPane);
    }

    //Toggling Slots Pane
    public void showSlotsPane(){
        showPane(SlotsPane);
    }

    //Toggling Floor Pane
    public void showFloorPane(){
        showPane(floorPane);
    }

    //Toggling User Pane
    public void showUsersPane(){
        showPane(UsersPane);
    }

    //Logout Btn Action
    public void logoutBtnAction() throws IOException {

        if (Boxes.confirmBox("Logout", "Are you sure you want to logout?")) {
            FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Driver.getWindow().setScene(scene);
        }
    }

    //Exit Button Action
    public void handleExit(){
        if (Boxes.confirmBox("Exit", "Are you sure you want to exit?"))
            Platform.exit();
    }

    //====================================================================================================//

    /*======================================== Vehicle Entry Pane ========================================*/
    //Taking Entry of Vehicle
    public void addVehicleEntryBtnAction(){
        //If any field is empty it will an alert box is displayed
        if (tfPersonName.getText().equals("") || tfMobileNo.getText().equals("") || tfVehiclePlateNo.getText().equals("") || tfTimeIn.getText().equals("") || cbVehicleType.getValue() == null)
            Boxes.alertBox("Empty Fields", "Fields are empty!");
        else if (DateTime.timeValidity(tfTimeIn.getText())){
            Vehicle.addVehicle(new Vehicle(tfPersonName.getText(), Long.parseLong(tfMobileNo.getText()), tfVehiclePlateNo.getText(), cbVehicleType.getValue(), tfTimeIn.getText(), DateTime.setDate(), cbFloor.getValue(), Integer.parseInt(tSlotNo.getText())), tbUnparkVehicle);

            //Clearing the fields after entering the Vehicle data
            tfPersonName.clear();
            tfMobileNo.clear();
            tfVehiclePlateNo.clear();
            tfTimeIn.clear();
            tSlotNo.setText("");
            tvehiclePrice.setText("");

            //Updating the Slots Pane
            Slots.showSlots(pagination);
        }
    }

    //Setting Current Time in TimeIn Field of Vehicle Entry Pane
    public void setTimeInBtnAction(){
        DateTime.setTime(tfTimeIn);
    }

    //Setting Current Time in TimeOut Field in Unpark Pane
    public void setTimeOutBtnAction(){
        DateTime.setTime(tfTimeOut);
    }

    //Sets the Bill for parking vehicle in tTotalBill
    public void calculateBillBtnAction(){
        tTotalBill.setText(String.format("Rs. %.2f", Vehicle.calculateTotalBill(Double.parseDouble(tPricePerHour.getText().split(" ")[1]), tTimeIn.getText(), tfTimeOut.getText())));
    }

    //======================================================================================================//

    /*======================================== Un-park Vehicle Pane ========================================*/

    //Un-Park Vehicle Btn Action
    public void unparkVehicleBtnAction(){
        //Check if the un-park table row is selected or not
        if (selectUnparkTableRow() != null) {
            //Check if the timeOut field is empty or not
            if (tfTimeOut.getText().isEmpty())
                Boxes.alertBox("Empty Fields", "Enter Time Out of Vehicle!");
            else {
                if (Boxes.confirmBox("Pay Bill", "Does bill has been paid?"))
                    Vehicle.unparkVehicle(tbUnparkVehicle, tbVehicleHistory, selectUnparkTableRow(), tfTimeOut.getText());   //Calls the unparkVehicle Function from the Vehicle Class

                //Clearing the data from the field after un-parking the car
                tCustomerName.setText("");
                tMobileNo.setText("");
                tVehiclePlateNo.setText("");
                tVehicleType.setText("");
                tFloor.setText("");
                tUnparkSlotNo.setText("");
                tTimeIn.setText("");
                tfTimeOut.clear();
                tPricePerHour.setText("");
                tTotalBill.setText("");
            }
        }

    }

    //Selects the Row in Un-Park Table and returns the selected object as Vehicle
    public Vehicle selectUnparkTableRow(){
        Vehicle selectedVehicle = tbUnparkVehicle.getSelectionModel().getSelectedItem();

        //Sets the values of selected vehicle in the Un-Park Vehicle Pane text fields and texts
        if (selectedVehicle != null){
            tCustomerName.setText(selectedVehicle.getCustomerName());
            tMobileNo.setText(Long.toString(selectedVehicle.getMobileNumber()));
            tVehiclePlateNo.setText(selectedVehicle.getVehicleNo());
            tVehicleType.setText(selectedVehicle.getVehicleType());
            tFloor.setText(selectedVehicle.getFloorName());
            tUnparkSlotNo.setText(Integer.toString(selectedVehicle.getSlotNo()));
            tTimeIn.setText(selectedVehicle.getTimeIn());

            if (tVehicleType.getText().equals("Rickshaw"))
                tPricePerHour.setText("Rs. 75");
            else if (tVehicleType.getText().equals("Bike"))
                tPricePerHour.setText("Rs. 100");
            else if (tVehicleType.getText().equals("Car"))
                tPricePerHour.setText("Rs. 150");
            else if (tVehicleType.getText().equals("Commercial Vehicle"))
                tPricePerHour.setText("Rs. 200");

        }
        return selectedVehicle;
    }

    //=======================================================================================================//

    /*======================================== Floors Pane ========================================*/

    //Add Floor Button Action
    public void addFloorBtnAction(){
        //Check if the floor name and number of slots fields are empty or not
        if (tfFloorName.getText().equals("") || tfNumberOfSlots.getText().equals(""))
            Boxes.alertBox("Empty Fields", "Fields are empty!");
        else {
            //Calls the addFloor Function from the Floor Class
            Floor.addFloor(new Floor(tfFloorName.getText(), Integer.parseInt(tfNumberOfSlots.getText())), tbFloors, cbFloor);

            //Clearing the data from the fields after adding the floor
            tfFloorName.clear();
            tfNumberOfSlots.clear();

            //Updating the Slots Pane
            Slots.showSlots(pagination);
        }
    }

    //Edit Floor Button Action
    public void editFloorBtnAction(){
        if (selectFloorTableRow() != null)
            //Calls the editFloor Function from the Floor Class
            Floor.editFloor(tbFloors, pagination, selectFloorTableRow());
    }

    //Selects the Row in Floors Table and returns the selected object as Floor
    public Floor selectFloorTableRow(){
        Floor selectedFloor = tbFloors.getSelectionModel().getSelectedItem();
        if (selectedFloor != null){
            //Sets the values of selected floor in the Floor Pane text fields
            tfFloorName.setText(selectedFloor.getFloorName());
            tfNumberOfSlots.setText(Integer.toString(selectedFloor.getNoOfSlots()));
        }

    return selectedFloor;
    }

    //Delete Floor Button Action
    public void deleteFloorBtnAction(){
        Floor.delFloor(tbFloors, pagination);   //Calls the delFloor Function from the Floor Class
    }

    //============================================================================================//

    /*======================================== Users Pane ========================================*/

    //Add User Button Action
    public void addUserBtnAction(){
        //Check if the Username, Password, and Role fields are empty or not
        if (tfName.getText().equals("") || tfPassword.getText().equals("") || cbRole.getValue() == null)
            Boxes.alertBox("Empty Fields", "Fields are empty or role not selected!");
        else {
            //Calls the addUser Function from the Users Class
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
            Users.editUser(usersTable, selectedUser);   //Calls the editUser Function from the Users Class
    }

    //Delete User Button Action
    public void deleteUserBtnAction(){
        //Calls the delUser Function from the Users Class
        Users.delUsers(usersTable);
    }

    //Selects the User in User Table and returns the selected object as Users
    public Users selectUserTableRow(){
        Users selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null){
            //Sets the values of selected user in the User Pane text fields
            tfName.setText(selectedUser.getName());
            tfPassword.setText(selectedUser.getPassword());
            cbRole.setValue(selectedUser.getRole());
        }

        return selectedUser;
    }

    //=============================================================================================//

    //Array list to store Panes
    private ArrayList<Pane> allPanes;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Checking if the Logged-In user is Admin or Controller
        if (status.equals("Controller"))
            disableOptions();
        else if (status.equals("Admin")) {
            enableOptions();
        }

        //==============================================================================//

        //Adding all the panes to the arraylist of Pane to handle the visibility of panes
        allPanes = new ArrayList<>();
        allPanes.add(vehicleEntryPane);
        allPanes.add(unparkVehiclePane);
        allPanes.add(vehicleHistoryPane);
        allPanes.add(SlotsPane);
        allPanes.add(floorPane);
        allPanes.add(UsersPane);

        //=====================================================================================//

        /*==================== Initializing fields on Vehicle Entry Pane ====================*/

        //Adding items to "Vehicle Type" Combo Box on Vehicle Entry Pane
        cbVehicleType.getItems().addAll("Car", "Bike", "Rickshaw", "Commercial Vehicle");

        //Adding items to "Floor No." Combo box on Vehicle Entry Pane
        ArrayList<Floor> floors = FileHandling.readFromFile(Files.getFloorFile());
        for (Floor f :
                floors) {
            cbFloor.getItems().add(f.getFloorName());

        }

        cbFloor.setOnAction(event -> Slots.allocateSlot(cbFloor.getValue(), tSlotNo));


        //Setting prices of vehicle
        cbVehicleType.setOnAction(event -> {
            if (cbVehicleType.getValue().equals("Rickshaw"))
                tvehiclePrice.setText("Rs. 75");
            else if (cbVehicleType.getValue().equals("Bike"))
                tvehiclePrice.setText("Rs. 100");
            else if (cbVehicleType.getValue().equals("Car"))
                tvehiclePrice.setText("Rs. 150");
            else if (cbVehicleType.getValue().equals("Commercial Vehicle"))
                tvehiclePrice.setText("Rs. 200");
        });

        //=====================================================================================//

        /*==================== Initializing Table on Un-Park Vehicle Pane ====================*/

        //Showing All Vehicles on the Table
        vehicleIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        vehicleCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        vehiclePlateNoCol.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        vehicleTimeInCol.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        tbUnparkVehicle.setItems(Vehicle.showVehicles());

        //=====================================================================================//

        /*==================== Initializing Table on Vehicles History Pane ====================*/

        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        mobileNoCol.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        vehicleTypeCol.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        vehicleNoCol.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        timeInCol.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        timeOutCol.setCellValueFactory(new PropertyValueFactory<>("timeOut"));
        floorCol.setCellValueFactory(new PropertyValueFactory<>("floorName"));
        slotNoCol.setCellValueFactory(new PropertyValueFactory<>("slotNo"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        tbVehicleHistory.setItems(Vehicle.showVehiclesHistory());

        //===============================================================================//

        /*==================== Initializing Pagination on Slots Pane ====================*/
        //Showing All Parking Slots
        Slots.showSlots(pagination);

        //===============================================================================//
        /*====================== Initializing Table on Floors Pane ======================*/

        //Showing All Floor on the table
        floorIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        floorNameCol.setCellValueFactory(new PropertyValueFactory<>("floorName"));
        NoOfSlotsCol.setCellValueFactory(new PropertyValueFactory<>("noOfSlots"));
        tbFloors.setItems(Floor.showFloor());

        //=============================================================================================//
        /*=============== Initializing Table and Combo Box on Un-Park Vehicle Pane ====================*/

        //Adding items to Combo Box on Users Pane
        cbRole.getItems().addAll("Admin", "Controller");

        //Showing All user on the table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        usersTable.setItems(Users.showUsers());
    }
}
