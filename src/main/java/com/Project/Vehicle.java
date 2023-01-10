package com.Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.Serial;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Vehicle implements Serializable {
    @Serial
    private static final long serialVersionUID = 12L;
    private int id;
    private final String customerName;
    private final String  mobileNumber;
    private final String vehicleNo;
    private final String vehicleType;
    private final String timeIn;
    private String timeOut;
    private final String date;
    private final String floorName;
    private final int slotNo;

    /*======================================== Constructors ========================================*/
    public Vehicle(String customerName, String mobileNumber, String vehicleNo, String vehicleType, String timeIn, String date, String floorName, int slotNo) {

        SecureRandom random = new SecureRandom();

        //Reading vehicles data from the file to keep in check that same id is not being assigned
        ArrayList<Vehicle> vehicles = DatabaseHandling.readFromVehiclesEntryTable();

        //Assigning random id and keeping in record that same id is not being assigned
        int ran = random.nextInt(1,100000);
        if (!(vehicles.isEmpty())){
            for (Vehicle v :
                    vehicles) {
                if (ran!= v.id)
                    id = ran;

            }
        }
        else id = ran;

        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.timeIn = timeIn;
        this.date = date;
        this.floorName = floorName;
        this.slotNo = slotNo;
    }

    public Vehicle(int id, String customerName, String mobileNumber, String vehicleNo, String vehicleType, String timeIn, String date, String floorName, int slotNo) {
        this.id = id;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.timeIn = timeIn;
//        this.timeOut = timeOut;
        this.date = date;
        this.floorName = floorName;
        this.slotNo = slotNo;
    }

    public Vehicle(String customerName, String mobileNumber, String vehicleNo, String vehicleType, String timeIn, String timeOut, String date, String floorName, int slotNo) {

        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.date = date;
        this.floorName = floorName;
        this.slotNo = slotNo;
    }

    //================================================================================================//

    /*========================================= Add Vehicle ==========================================*/

    //Adding new vehicle entry
    public static void addVehicle(Vehicle newVehicle, TableView<Vehicle> table){
        //Writing new vehicle to VehicleData.ser
        DatabaseHandling.writeToVehiclesEntryTable(newVehicle);

        //Showing newly added vehicle on table of Un-Park Vehicle Pane
        table.getItems().add(newVehicle);

        //Reading Floors data from FloorsData.ser
//        ArrayList<Floor> floors = DatabaseHandling.readFromFloorsTable();

        DatabaseHandling.performQuery("update " + newVehicle.floorName.replace(" ", "") + "Slots set ReservedStatus ='1' where SlotsID=" + newVehicle.slotNo);
        //Setting the status of slot to reserved
//        for (Floor f :
//                floors) {
//            if (f.getFloorName().equals(newVehicle.floorName)) {
//                f.getSlots().get(newVehicle.slotNo).setReserved(true);
////                DatabaseHandling.editFloorInfo("update Floors set FloorName='" + tfName.getText() + "' ,UserPassword='");
//                break;
//            }
//        }


//        //Deleting the FloorData.ser
//        new File(Files.getFloorFile()).delete();
//
//        //Rewriting the floors data in the FloorData File
//        for (Floor f :
//                floors) {
//            FileHandling.writeToFile(Files.getFloorFile(),f);
//        }


        Boxes.alertBox("", "New Vehicle Added Successfully");
    }


    //====================================================================================================//

    /*========================================= Un-Park Vehicle ==========================================*/

    /*Un-park Vehicle Function
    * This function is used to remove/un-park a vehicle from the slot/system */
    public static void unparkVehicle(TableView<Vehicle> tbUnpark, TableView<Vehicle> tbVehicleHistory, Vehicle unparkVehicle, String timeOut){

        DatabaseHandling.deleteVehiclesEntry("delete from VehicleEntry where VehicleID=" + unparkVehicle.id);
        DatabaseHandling.performQuery("update " + unparkVehicle.floorName.replace(" ", "") + "Slots set ReservedStatus ='0' where SlotsID=" + unparkVehicle.slotNo);


//        //Reading vehicles data from VehicleData.ser
//        ArrayList<Vehicle> vehiclesArray = FileHandling.readFromFile(Files.getVehiclesFile());
//
//        //Deleting the VehicleData.ser
//        File file = new File(Files.getVehiclesFile());
//        file.delete();
//
//        //Rewriting VehicleData.ser File
//        for (Vehicle v :
//                vehiclesArray) {
//            if (!(v.id == unparkVehicle.getId())) {
//                FileHandling.writeToFile(Files.getVehiclesFile(), v);
//            }
//
//        }

//        //Reading Floors data from FloorsData.ser
//        ArrayList<Floor> floors = DatabaseHandling.readFromFloorsTable();
//
//        //Setting the status of slot to available
//        for (Floor f :
//                floors) {
//            if (f.getFloorName().equals(unparkVehicle.floorName)) {
//                f.getSlots().get(unparkVehicle.slotNo).setReserved(false);
//                break;
//            }
//        }
//
//        //Deleting the FloorData.ser
//        new File(Files.getFloorFile()).delete();
//
//        //Rewriting the floors data in the FloorData File
//        for (Floor f :
//                floors) {
//            FileHandling.writeToFile(Files.getFloorFile(),f);
//        }
//
//        //Creating VehicleData.ser file if it does not exist
//        if (!(file.exists())) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }


        tbUnpark.getItems().clear();   //Clearing the vehicles data in table of Un-Park Vehicle Pane
        tbUnpark.setItems(Vehicle.showVehicles()); //Showing the vehicles data in the table of Un-Park Vehicle Pane

        Vehicle vehicleHistory = new Vehicle(unparkVehicle.customerName, unparkVehicle.mobileNumber, unparkVehicle.vehicleNo, unparkVehicle.vehicleType, unparkVehicle.timeIn, timeOut,unparkVehicle.date, unparkVehicle.floorName, unparkVehicle.slotNo);
//        FileHandling.writeToFile(Files.getVehiclesHistoryFile(), vehicleHistory);
        DatabaseHandling.writeToVehiclesHistoryTable(vehicleHistory);
        tbVehicleHistory.getItems().add(vehicleHistory);

    }

    //=================================================================================================//

    /*========================================= Show Vehicle ==========================================*/

    /*Show Vehicles
    * This function returns the Observable list of Vehicle to show data in the table*/
    public static ObservableList<Vehicle> showVehicles() {
        ArrayList<Vehicle> vehicles = DatabaseHandling.readFromVehiclesEntryTable();
        return FXCollections.observableList(vehicles);
    }

    //==========================================================================================================//

    /*========================================= Show Vehicles History ==========================================*/

    /*Show Vehicles History
     * This function returns the Observable list of Vehicles History to show data in the table*/
    public static ObservableList<Vehicle> showVehiclesHistory() {
//        ArrayList<Vehicle> vehicles = FileHandling.readFromFile(Files.getVehiclesHistoryFile());
        ArrayList<Vehicle> vehicles = DatabaseHandling.readFromVehiclesHistoryTable();
        return FXCollections.observableList(vehicles);
    }

    //===================================================================================================//

    /*========================================= Calculate Bill ==========================================*/

    //Calculating total bill for parking vehicles
    public static double calculateTotalBill(double pricePerHour, String timeIn, String timeOut){
        double result;
        double pricePerMinute = pricePerHour / 60;  //Converting pricePerHour to pricePerMinute

        ////Splitting timeIn and timeOut to get hours, minutes, and seconds
        String [] tInSplit = timeIn.split(":");
        String [] tOutSplit = timeOut.split(":");

        //Calculating hours, minutes, and seconds and converting hours and seconds to minutes
        double hours = (Integer.parseInt(tOutSplit[0]) - Integer.parseInt(tInSplit[0])) * 60;
        double min = (Integer.parseInt(tOutSplit[1]) - Integer.parseInt(tInSplit[1]));
        double sec = (Integer.parseInt(tOutSplit[2]) - Integer.parseInt(tInSplit[2])) / 60;

        //Calculating the time in minutes
        double time = hours + min + sec;

        //Calculating the total price of parking
        result = time * pricePerMinute;

        //Returning the total price of parking
        return result;
    }

    //============================================================================================//

    /*========================================= Getters ==========================================*/

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getTimeIn() {
        return timeIn;
    }
    public String getTimeOut() {
        return timeOut;
    }

    public String getDate() {
        return date;
    }

    public String getFloorName() {
        return floorName;
    }

    public int getSlotNo() {
        return slotNo;
    }

    //toString function
    @Override
    public String toString() {
        return String.format("%d %s %s %s %s %s %s %s %d", id, customerName, mobileNumber, vehicleNo, vehicleType, timeIn, date, floorName, slotNo);
    }
}
