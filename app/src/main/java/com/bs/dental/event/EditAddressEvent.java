package com.bs.dental.event;
import com.bs.dental.model.CustomerAddress;
/**
 * Created by bs-110 on 12/17/2015.
 */
public class EditAddressEvent {
    private int index;
    private CustomerAddress address;

    public CustomerAddress getAddress() {
        return address;
    }

    public void setAddress(CustomerAddress address) {
        this.address = address;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public EditAddressEvent(int index, CustomerAddress address) {
        this.index = index;
        this.address = address;
    }
}
