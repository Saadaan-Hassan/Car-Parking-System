package com.Project;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandling {
    private static final Connection con;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking_database","root","Test@123");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void performQuery(String query){
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //=======================================================================================================//

    /*======================================== Vehicle Entry Section ========================================*/

    public static void writeToVehiclesEntryTable(Vehicle vehicle){
        String query = "insert into VehicleEntry value(?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, vehicle.getId());
            ps.setString(2, vehicle.getCustomerName());
            ps.setString(3, vehicle.getMobileNumber());
            ps.setString(4, vehicle.getVehicleNo());
            ps.setString(5, vehicle.getVehicleType());
            ps.setString(6, vehicle.getTimeIn());
            ps.setString(7, vehicle.getDate());
            ps.setString(8, vehicle.getFloorName());
            ps.setInt(9, vehicle.getSlotNo());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Vehicle> readFromVehiclesEntryTable(){
        ArrayList<Vehicle> allVehicles = new ArrayList<>();
        try {
            Statement read = con.createStatement();
            ResultSet rs = read.executeQuery("SELECT * FROM VehicleEntry");

            while (rs.next()) {
                allVehicles.add(new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(9)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allVehicles;
    }

    //=========================================================================================================//

    /*======================================== Vehicle History Section ========================================*/

    public static void writeToVehiclesHistoryTable(Vehicle vehicle){
        String query = "insert into VehiclesHistory value(?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, vehicle.getCustomerName());
            ps.setString(2, vehicle.getMobileNumber());
            ps.setString(3, vehicle.getVehicleNo());
            ps.setString(4, vehicle.getVehicleType());
            ps.setString(5, vehicle.getTimeIn());
            ps.setString(6, vehicle.getTimeOut());
            ps.setString(7, vehicle.getDate());
            ps.setString(8, vehicle.getFloorName());
            ps.setInt(9, vehicle.getSlotNo());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Vehicle> readFromVehiclesHistoryTable(){
        ArrayList<Vehicle> allVehicles = new ArrayList<>();
        try {
            Statement read = con.createStatement();
            ResultSet rs = read.executeQuery("SELECT * FROM VehiclesHistory");

            while (rs.next()) {
                allVehicles.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(9)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allVehicles;
    }



    //================================================================================================================//

    /*======================================== Vehicle Type And Price Section ========================================*/

    public static void writeToTypesAndPricesTable(TypesAndPrices typesAndPrices){
        String query = "insert into TypesAndPrices value(?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, typesAndPrices.getId());
            ps.setString(2, typesAndPrices.getVehicleType());
            ps.setDouble(3, typesAndPrices.getPricePerHour());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<TypesAndPrices> readFromTypesAndPricesTable(){
        ArrayList<TypesAndPrices> allTypesAndPrices = new ArrayList<>();
        try {
            Statement read = con.createStatement();
            ResultSet rs = read.executeQuery("SELECT * FROM TypesAndPrices");

            while (rs.next()) {
                allTypesAndPrices.add(new TypesAndPrices(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allTypesAndPrices;
    }

    //================================================================================================//

    /*======================================== Floors Section ========================================*/
    public static void writeToFloorsTable(Floor floor){
        String query = "insert into Floors value(?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, floor.getId());
            ps.setString(2, floor.getFloorName());
            ps.setInt(3, floor.getNoOfSlots());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Floor> readFromFloorsTable(){
        ArrayList<Floor> allFloors = new ArrayList<>();
        try {
            Statement read = con.createStatement();
            ResultSet rs = read.executeQuery("SELECT * FROM Floors");

            while (rs.next()) {
                allFloors.add(new Floor(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allFloors;
    }

    //===============================================================================================//

    /*======================================== Users Section ========================================*/

    public static ArrayList<Slots> readFromSlotsTable(String slotsTableName){
        ArrayList<Slots> allSlots = new ArrayList<>();
        try {
            Statement read = con.createStatement();
            ResultSet rs = read.executeQuery("SELECT * FROM " + slotsTableName);

            while (rs.next()) {
                allSlots.add(new Slots(rs.getInt(1), rs.getBoolean(2)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allSlots;
    }

    //===============================================================================================//

    /*======================================== Users Section ========================================*/

    public static void writeToUsersTable(Users user){
        String query = "insert into Users value(?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Users> readFromUsersTable(){
        ArrayList<Users> allUsers = new ArrayList<>();
        try {
            Statement read = con.createStatement();
            ResultSet rs = read.executeQuery("SELECT * FROM Users");

            while (rs.next()) {
                allUsers.add(new Users(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allUsers;
    }

    public static Connection getCon() {
        return con;
    }
}
