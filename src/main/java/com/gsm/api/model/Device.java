package com.gsm.api.model;

import com.gsm.api.interfaces.Billable;
import com.gsm.api.interfaces.Warrantable;

public abstract class Device implements Billable, Warrantable {
    private final int deviceID;
    private String name;
    private int price;

    protected Device(int deviceID, String name) {
        this.deviceID = deviceID;
        this.name = name;
    }

    //gettere
    public int getDeviceID() {return this.deviceID;}
    public String getName() {return this.name;}
    public int getPrice() {return this.price;}

    //settere
    public void setPrice(int price) {this.price = price;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device d)) return false;
        return this.deviceID == d.deviceID;
    }

    @Override
    public int hashCode() { return Integer.hashCode(deviceID); }
}
