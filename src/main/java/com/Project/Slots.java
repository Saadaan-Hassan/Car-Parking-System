package com.Project;

import javafx.scene.control.Pagination;
import javafx.scene.text.Text;

import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Slots implements Serializable {
    @Serial
    private static final long serialVersionUID = 4494659944483709061L;
    private int id;
    private boolean reserved;

    /*======================================== Constructors ========================================*/
    Slots(int id, boolean reserved) {
        this.id = id;
        this.reserved = reserved;
    }

    //==============================================================================================//

    /*========================================= Show Slots =========================================*/

    //This method show the slots on the screen
    public static void showSlots(Pagination pagination, Text floorName){
        pagination.setPageCount(DatabaseHandling.readFromFloorsTable().size());
        pagination.setPageFactory(pageIndex -> Floor.showFloor(pageIndex, floorName));
    }

    //==============================================================================================//

    /*========================================= Allocate Slots =========================================*/

    //This method allots the slot number to vehicles for parking
    public static void allocateSlot(String selectedFloor, Text slotNo) {
        SecureRandom random = new SecureRandom();

        //Reading the floors data from the FloorsData.ser
        ArrayList<Slots> slots = DatabaseHandling.readFromSlotsTable(selectedFloor);
        int assignSlot;
        for (Slots a :
                slots) {
            assignSlot = random.nextInt(0, slots.size());
            //Checking if the slot being assigned is reserved ot not
            if (!(slots.get(assignSlot).isReserved())) {
                slotNo.setText(Integer.toString(assignSlot));
                break;
            }

        }
    }

    public static int checkAvailability(ArrayList<Slots> slots){
        int status = 0;
        for (Slots s :
                slots) {
            if (!(s.isReserved()))
                status = 1;
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
