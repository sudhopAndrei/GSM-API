package com.gsm.api.model.users;

import com.gsm.api.model.interfaces.Billable;
import com.gsm.api.model.purchases.Purchase;

import java.time.LocalDate;

public class Company extends Customer {
    private String representativeName;

    public Company (int userID, String name, String email, String phoneNumber, LocalDate joinDate, String representativeName) {

        super(userID, name, email, phoneNumber, joinDate);

        this.representativeName = representativeName;
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }

    //getter
    public String getRepresentativeName() {return this.representativeName;}
}
