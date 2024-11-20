package com.example.firebasetest.Model;

import java.io.Serializable;
import java.util.Vector;

public class TimeDate implements Serializable {
    private String date;
    private Vector<String> slot;

    public TimeDate() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Vector<String> getSlot() {
        return slot;
    }

    public void apppend(String slot){
        this.slot.add(slot);
    }

    public void setSlot(Vector<String> slot) {
        this.slot = slot;
    }
}
