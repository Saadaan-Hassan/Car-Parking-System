package com.Project;

import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Slots implements Serializable {
    @Serial
    private static final long serialVersionUID = 4494659944483709061L;
    private boolean reserved;

    /*======================================== Constructors ========================================*/
    Slots() {
        this.reserved = false;
    }

    //==============================================================================================//

    /*========================================= Show Slots =========================================*/

    //This method show the slots on the screen
    public static void showSlots(Pagination pagination, Text floorName){
        pagination.setPageCount(FileHandling.readFromFile(Files.getFloorFile()).size());
        pagination.setPageFactory(pageIndex -> Floor.showFloor(pageIndex, floorName));
    }

    //==============================================================================================//

    /*========================================= Allocate Slots =========================================*/

    //This method allots the slot number to vehicles for parking
    public static void allocateSlot(String selectedFloor, Text slotNo){
        SecureRandom random = new SecureRandom();

        //Reading the floors data from the FloorsData.ser
        ArrayList<Floor> floors = FileHandling.readFromFile(Files.getFloorFile());

        int assignSlot;
        for (Floor f:
                floors) {
            if (f.getFloorName().equals(selectedFloor)){
                int slots = f.getNoOfSlots();

                assignSlot = random.nextInt(0,slots);

                //Checking if the slot being assigned is reserved ot not
                if (!(f.getSlots().get(assignSlot).isReserved()))
                    slotNo.setText(Integer.toString(assignSlot));

            }
        }
    }

    public static boolean checkAvailability(ArrayList<Slots> slots){
        boolean status = false;
        for (Slots s :
                slots) {
            if (!(s.isReserved()))
                status = true;
        }

        return status;
    }

    /*=============================================================================================*/

    /*========================================== Setter ===========================================*/

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    /*==============================================================================================*/

    /*========================================== Getters ===========================================*/

    public boolean isReserved() {
        return reserved;
    }

    @Override
    public String toString() {
        return String.format("%b", reserved);
    }
}
