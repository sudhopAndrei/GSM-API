package com.gsm.api.model;

import java.time.LocalDate;
import java.util.Map;

public class Person extends Customer {
    private String IBAN;
    private int loyaltyPoints;

    public Person(int userID, String name, String email, String phoneNumber, LocalDate joinDate, String IBAN) {
        super(userID, name, email, phoneNumber, joinDate);

        this.IBAN = IBAN;
        this.loyaltyPoints = 0;
    }

    //adauga achizita + puncte bonus
    public void addPurchase(Billable item, LocalDate date) {
        this.purchases.put(item, date);
        this.addLoyaltyPoints(item.calculateCost() * 10);
    }

    //adauga puncte
    public void addLoyaltyPoints(int addedPoints) {
        this.loyaltyPoints += addedPoints;
    }

    //getters
    public int getLoyaltyPoints() {return this.loyaltyPoints;}
}
