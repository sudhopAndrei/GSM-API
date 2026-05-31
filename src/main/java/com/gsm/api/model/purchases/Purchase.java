package com.gsm.api.model.purchases;

import com.gsm.api.model.interfaces.Billable;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class Purchase {
    private final int purchaseID;
    private final Billable item;
    private final LocalDate date;

    public Purchase(int purchaseID, Billable item, LocalDate date) {
        this.purchaseID = purchaseID;
        this.item = item;
        this.date = date;
    }

    //gettere
    public int getPurchaseID() {return this.purchaseID;}
    public Billable getItem() {return this.item;}
    public LocalDate getDate() {return this.date;}
}
