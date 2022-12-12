package com.Project;

import java.io.Serializable;

public class Slots implements Serializable {
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

    @Override
    public String toString() {
        return String.format("%b", reserved);
    }
}
