package com.gsm.api.model.users;

import com.gsm.api.model.interfaces.Billable;

import java.time.LocalDate;

public class Company extends Customer {
    private String representativeName;

    public Company (int userID, String name, String email, String phoneNumber, LocalDate joinDate, String representativeName) {

        super(userID, name, email, phoneNumber, joinDate);

        this.representativeName = representativeName;
    }

    public void addPurchase(Billable item, LocalDate date) {
        this.purchases.put(item, date);
    }

    //getter
    public String getRepresentativeName() {return this.representativeName;}
}
