package com.gsm.api.model;

import org.springframework.cglib.core.Local;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.*;

public class Customer extends User{
    private Map<Billable, LocalDate> purchases = new HashMap<>();

    public Customer(int IDUser, String name, String email, String phoneNumber, LocalDate joinDate,
             Map<Billable, LocalDate> purchases) {

        super(IDUser, name, email, phoneNumber, joinDate);
        this.purchases = purchases;
    }

//    public List<Billable> activeWarranties() {
//        List<Billable> warranties = new ArrayList<>();
//
//        for (Map.Entry<Billable, LocalDate> purchase : purchases.entrySet()) {
//            if (ChronoUnit.YEARS.between(purchase.getValue(), LocalDate.now()) < Billable.warranty) {
//                warranties.add(purchase.getKey());
//            }
//        }
//
//        return warranties;
//    }

    //gettere
    public Map<Billable, LocalDate> getPurchases() {return this.purchases;};

    //settere
    public void setPurchases(Billable item, LocalDate date) {this.purchases.put(item, date);};
}
