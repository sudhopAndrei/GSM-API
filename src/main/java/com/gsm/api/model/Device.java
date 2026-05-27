package com.gsm.api.model;

public abstract class Device implements Billable, Warrantable{
    private final int deviceID;
    private String name;
    private int price;

    public Device(int deviceID, String name) {
        this.deviceID = deviceID;
        this.name = name;
    }

    //device cu pret fix
    public Device(int deviceID, String name, int price) {
        this.deviceID = deviceID;
        this.name = name;
        this.price = price;
    }

    //gettere
    public int getDeviceID() {return this.deviceID;}
    public String getName() {return this.name;}
    public int getPrice() {return this.price;}
}
