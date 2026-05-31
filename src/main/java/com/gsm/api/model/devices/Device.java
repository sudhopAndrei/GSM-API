package com.gsm.api.model.devices;

import com.gsm.api.model.interfaces.Billable;
import com.gsm.api.model.interfaces.Warrantable;

public abstract class Device implements Billable, Warrantable {
    private final int deviceID;
    private String name;
    private int price;

    protected Device(int deviceID, String name, int price) {
        this.deviceID = deviceID;
        this.name = name;
        this.price = price;
    }

    //pret default
    protected Device(int deviceID, String name) {
        this(deviceID, name, 0);
    }

    //gettere
    public int getDeviceID() {return this.deviceID;}
    public String getName() {return this.name;}
    public int getPrice() {return this.price;}
}
