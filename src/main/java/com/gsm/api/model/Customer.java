package com.gsm.api.model;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.*;

public abstract class Customer extends User{
    private Map<Billable, LocalDate> purchases = new HashMap<>();

    public Customer(int IDUser, String name, String email, String phoneNumber, LocalDate joinDate) {

        super(IDUser, name, email, phoneNumber, joinDate);
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

    //adauga achizitii
    public void addPurchase(Billable item, LocalDate date) {
        this.purchases.put(item, date);

        if (this instanceof Person p) {
            if (item.calculateCost() > 5) {
                ((Person) this).addLoyaltyPoints(100);
            }
            if (item.calculateCost() > 15) {
                ((Person) this).addLoyaltyPoints(300);
            }
            if (item.calculateCost() > 30) {
                ((Person) this).addLoyaltyPoints(500);
            }
        }
    }

    //gettere
    public Map<Billable, LocalDate> getPurchases() {return this.purchases;}
}
