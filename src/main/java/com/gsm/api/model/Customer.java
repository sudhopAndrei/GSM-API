package com.gsm.api.model;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.*;

public abstract class Customer extends User{
    private Map<Billable, LocalDate> purchases = new HashMap<>();
    private Map<Billable, Integer> extendedWarranties = new HashMap<>();
    private double penalties;

    public Customer(int userID, String name, String email, String phoneNumber, LocalDate joinDate) {

        super(userID, name, email, phoneNumber, joinDate);
        this.penalties = 0;
    }

    //adauga achizitii + puncte bonus
    public void addPurchase(Billable item, LocalDate date) {
        this.purchases.put(item, date);

        if (this instanceof Person p) {
            ((Person) this).addLoyaltyPoints(item.calculateCost() * 10);
        }
    }

    public void addWarrantyExtension(Billable item, int monthsExtended) {
        this.extendedWarranties.put(item, monthsExtended);
    }

    //gettere
    public Map<Billable, LocalDate> getPurchases() {return this.purchases;}
    public double getPenalties() {return this.penalties;}

    //settere
    public void setPenalties(int penalties) {this.penalties = penalties;}
}
