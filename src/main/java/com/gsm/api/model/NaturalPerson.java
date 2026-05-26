package com.gsm.api.model;

import java.time.LocalDate;
import java.util.Map;

public class NaturalPerson extends Customer {
    private final String CNP;
    private String IBAN;
    private int loyaltyPoints;

    public NaturalPerson(int IDUser, String name, String email, String phoneNumber, LocalDate joinDate,
                  Map<Billable, LocalDate> purchases,String CNP, String IBAN, int loyaltyPoints) {
        super(IDUser, name, email, phoneNumber, joinDate, purchases);

        this.CNP = CNP;
        this.IBAN = IBAN;
        this.loyaltyPoints = loyaltyPoints;
    }

    //getters
    public int getLoyaltyPoints() {return this.loyaltyPoints;};
}
