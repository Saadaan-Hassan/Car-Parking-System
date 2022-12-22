package com.Project;

public class Files {

    /*=================================== Files Paths ===================================*/
    //File to store users data
    private static final String usersFile = "src/main/resources/Files/UserData.ser";
//    private static final String usersFile = "/Files/UserData.ser";

    //File to store floors data
    private static final String floorFile = "src/main/resources/Files/FloorsData.ser";
//    private static final String floorFile = "/Files/FloorsData.ser";

    //File to store vehicles data
    private static final String vehiclesFile = "src/main/resources/Files/VehicleData.ser";
//    private static final String vehiclesFile = "/Files/VehicleData.ser";

    //File to store vehicles history data
    private static final String vehiclesHistoryFile = "src/main/resources/Files/VehiclesHistoryData.ser";
//    private static final String vehiclesHistoryFile = "/Files/VehiclesHistoryData.ser";

    private static final String typesAndPricesFile = "src/main/resources/Files/TypesAndPricesData.ser";

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

    public static String getTypesAndPricesFile(){
        return typesAndPricesFile;
    }
}
