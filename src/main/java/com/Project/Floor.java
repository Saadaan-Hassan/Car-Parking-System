package com.Project;

import com.Boxes.AlertBox;

import java.io.Serial;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Floor implements Serializable {
    @Serial
    private static final long serialVersionUID = 733648599315947096L;
    private final String floorName;
    private final Slots [] slots;

    private int id;

    Floor(String floorName, int slotsNumber) {
        SecureRandom random = new SecureRandom();
        ArrayList<Floor> floors = FileHandling.readFromFile(Files.getFloorFile());
        int ran = random.nextInt(1,1000);

        for (Floor f :
                floors) {
            if (ran != f.id) {
                id = ran;
            }

        }

        this.floorName = floorName;
        slots = new Slots[slotsNumber];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new Slots();
        }
    }

    public static void addFloor(Floor floor){
        FileHandling.appendToFile(Files.getFloorFile(), floor);



        AlertBox.display("", "New floor Added Successfully");
    }

    public Slots[] getSlots() {
        return slots;
    }

    @Override
    public String toString() {
        return String.format("%d %s %d", id, floorName, slots.length);
    }
}
