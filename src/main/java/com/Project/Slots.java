package com.Project;

import javafx.scene.control.Pagination;

import java.io.Serial;
import java.io.Serializable;

public class Slots implements Serializable {

    @Serial
    private static final long serialVersionUID = 4494659944483709061L;
    private boolean reserved;

    public Slots() {
        this.reserved = false;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public boolean isReserved() {
        return reserved;
    }

    public static void showSlots(Pagination pagination){
        pagination.setPageCount(FileHandling.readFromFile(Files.getFloorFile()).size());
        pagination.setPageFactory(pageIndex -> Floor.showFloor(pageIndex));
    }

    @Override
    public String toString() {
        return String.format("%b", reserved);
    }
}
