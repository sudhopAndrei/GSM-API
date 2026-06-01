package com.gsm.api.model.purchases;

import com.gsm.api.model.interfaces.Billable;

import java.time.LocalDate;

public class Purchase {
    private int purchaseID;
    private Billable item;
    private LocalDate date;

    public Purchase(int purchaseID, Billable item, LocalDate date) {
        this.purchaseID = purchaseID;
        this.item = item;
        this.date = date;
    }

    //gettere
    public int getPurchaseID() {return this.purchaseID;}
    public Billable getItem() {return this.item;}
    public LocalDate getDate() {return this.date;}

    //settere
    public void setPurchaseID(int purchaseID) {this.purchaseID = purchaseID;}
}
