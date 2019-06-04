package com.bs.dental.event;

/**
 * Created by bs-110 on 1/4/2016.
 */
public class RemoveWishlistItemEvent {
    int position;
    int id;
    int count;

    public RemoveWishlistItemEvent(int count){
        this.count = count;
    }

    public RemoveWishlistItemEvent(int position, int id) {
        this.position = position;
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
