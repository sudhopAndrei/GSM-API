package com.gsm.api.model;

import java.time.LocalDate;
import java.util.Map;

public class Person extends Customer {
    private final String CNP;
    private String IBAN;
    private int loyaltyPoints;

    public Person(int IDUser, String name, String email, String phoneNumber, LocalDate joinDate,
                  String CNP, String IBAN) {
        super(IDUser, name, email, phoneNumber, joinDate);

        this.CNP = CNP;
        this.IBAN = IBAN;
    }

    //adauga puncte
    public void addLoyaltyPoints(int addedPoints) {
        this.loyaltyPoints += addedPoints;
    }

    //getters
    public int getLoyaltyPoints() {return this.loyaltyPoints;};
}
