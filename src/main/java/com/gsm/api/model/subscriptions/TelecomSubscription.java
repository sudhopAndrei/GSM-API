package com.gsm.api.model.subscriptions;

import com.gsm.api.model.interfaces.Billable;

public abstract class TelecomSubscription implements Billable {
    private final int subscriptionID;
    private final String name;
    private int contractLength;
    private int price;

    protected TelecomSubscription(int subscriptionID, String name, int contractLength, int price) {
        this.subscriptionID = subscriptionID;
        this.name = name;
        this.contractLength = contractLength;
        this.price = price;
    }

    protected TelecomSubscription(int subscriptionID, String name, int contractLength) {
        this(subscriptionID, name, contractLength, 2);
    }

    //gettere
    public int getSubscriptionID() {return this.subscriptionID;}
    public String getName() {return this.name;}
    public int getPrice() {return this.price;}
    public int getContractLength() {return this.contractLength;}

    //setter
    public void setPrice(int price) {this.price = price;}
    public void setContractLength(int contractLength) {this.contractLength = contractLength;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TelecomSubscription d)) return false;
        return this.subscriptionID == d.subscriptionID;
    }

    @Override
    public int hashCode() { return Integer.hashCode(subscriptionID); }
}
