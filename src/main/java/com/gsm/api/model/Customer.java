package com.gsm.api.model;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.*;

public abstract class Customer extends User{
    private Map<Billable, LocalDate> purchases = new HashMap<>();
    private double penalties;

    public Customer(int userID, String name, String email, String phoneNumber, LocalDate joinDate) {

        super(userID, name, email, phoneNumber, joinDate);
        this.penalties = 0;
    }

    public List<Billable> activeWarranties() {
        List<Billable> warranties = new ArrayList<>();

        for (Map.Entry<Billable, LocalDate> purchase : purchases.entrySet()) {
            if (purchase.getKey() instanceof Warrantable w) {
                if (ChronoUnit.YEARS.between(purchase.getValue(), LocalDate.now()) < w.calculateWarranty()
                        && w.calculateWarranty() != 0) {
                    warranties.add(purchase.getKey());
                }
            }
        }

        return warranties;
    }

    //adauga achizitii + puncte bonus
    public void addPurchase(Billable item, LocalDate date) {
        this.purchases.put(item, date);

        if (this instanceof Person p) {
            ((Person) this).addLoyaltyPoints(item.calculateCost() * 10);
        }
    }

    //gettere
    public Map<Billable, LocalDate> getPurchases() {return this.purchases;}
    public double getPenalties() {return this.penalties;}

    //settere
    public void setPenalties(int penalties) {this.penalties = penalties;}
}
