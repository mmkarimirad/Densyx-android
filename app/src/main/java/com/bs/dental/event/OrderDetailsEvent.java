package com.bs.dental.event;

/**
 * Created by bs-110 on 12/18/2015.
 */
public class OrderDetailsEvent {
    int id;
    public OrderDetailsEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
