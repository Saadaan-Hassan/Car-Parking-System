package com.Project;

public class Files {

    /*=================================== Files Paths ===================================*/
    //File to store users data
    private static final String usersFile = "src/main/resources/com/Project/Files/UserData.ser";

    //File to store floors data
    private static final String floorFile = "src/main/resources/com/Project/Files/FloorsData.ser";

    //File to store vehicles data
    private static final String vehiclesFile = "src/main/resources/com/Project/Files/VehicleData.ser";

    //File to store vehicles history data
    private static final String vehiclesHistoryFile = "src/main/resources/com/Project/Files/VehiclesHistoryData.ser";

    //=====================================================================================//

    /*=================================== Files Getters ===================================*/
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
