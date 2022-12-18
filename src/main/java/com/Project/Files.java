package com.Project;

public class Files {
    private static final String usersFile = "src/main/.Files/.UserData.ser";    //File to store users data
    private static final String floorFile = "src/main/.Files/.FloorsData.ser";  //File to store floors data
    private static final String vehiclesFile = "src/main/.Files/.VehicleData.ser";  //File to store vehicles data
    private static final String vehiclesHistoryFile = "src/main/.Files/.VehiclesHistoryData.ser";   //File to store vehicles history data

    public static String getUsersFile() {
        return usersFile;
    }

    public static String getFloorFile(){
        return floorFile;
    }

    public static String getVehiclesFile(){
        return vehiclesFile;
    }

    public static String getVehiclesHistoryFile(){
        return vehiclesHistoryFile;
    }


}
