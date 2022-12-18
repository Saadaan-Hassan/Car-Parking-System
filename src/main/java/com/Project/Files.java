package com.Project;

public class Files {
    private static final String usersFile = "src/main/resources/Files/UserData.ser";    //File to store users data
    private static final String floorFile = "src/main/resources/Files/FloorsData.ser";  //File to store floors data
    private static final String vehiclesFile = "src/main/resources/Files/VehicleData.ser";  //File to store vehicles data
    private static final String vehiclesHistoryFile = "src/main/resources/Files/VehiclesHistoryData.ser";   //File to store vehicles history data

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
