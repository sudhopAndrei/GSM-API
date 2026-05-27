package com.gsm.api.model;

public abstract class TelecomService implements Billable {
    private final String name;
    private int contractLength;

    private int price = 2;

    protected TelecomService(String name, int contractLength) {
        this.name = name;
        this.contractLength = contractLength;
    }

    //pentru serviciu cu pret dat
    protected TelecomService(String name, int contractLength, int price) {
        this.name = name;
        this.contractLength = contractLength;
        this.price = price;
    }

    //gettere
    public String getName() {return this.name;}
    public int getPrice() {return this.price;}
    public int getContractLength() {return this.contractLength;}

    //setter
    public void setPrice(int price) {this.price = price;}
    public void setContractLength(int contractLength) {this.contractLength = contractLength;}
}
