package com.gsm.api.model;

import com.gsm.api.interfaces.Billable;

import java.util.*;
import java.time.*;

public abstract class Customer extends User{
    protected List<Purchase> purchases = new ArrayList<>();
    private double penalties;

    public Customer(int userID, String name, String email, String phoneNumber, LocalDate joinDate) {

        super(userID, name, email, phoneNumber, joinDate);
        this.penalties = 0;
    }

    //adauga achizitii
    public abstract void addPurchase(Purchase purchase);

    //gettere
    public List<Purchase> getPurchases() {return List.copyOf(purchases);}
    public double getPenalties() {return this.penalties;}

    //settere
    public void loadPurchase(Purchase purchase) {this.purchases.add(purchase);}
    public void setPenalties(int penalties) {this.penalties = penalties;}
}
