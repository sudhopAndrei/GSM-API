package com.gsm.api.model.users;

import com.gsm.api.model.interfaces.Billable;
import com.gsm.api.model.purchases.Purchase;

import java.util.*;
import java.time.*;

public abstract class Customer extends User{
    protected List<Purchase> purchases = new ArrayList<>();
    private Map<Billable, Integer> extendedWarranties = new HashMap<>();
    private double penalties;

    public Customer(int userID, String name, String email, String phoneNumber, LocalDate joinDate) {

        super(userID, name, email, phoneNumber, joinDate);
        this.penalties = 0;
    }

    //adauga achizitii
    public abstract void addPurchase(Purchase purchase);

    public void addWarrantyExtension(Billable item, int monthsExtended) {
        this.extendedWarranties.put(item, monthsExtended);
    }

    //gettere
    public List<Purchase> getPurchases() {return List.copyOf(purchases);}
    public Map<Billable, Integer> getExtendedWarranties() {return Map.copyOf(this.extendedWarranties);}
    public double getPenalties() {return this.penalties;}

    //settere
    public void loadPurchase(Purchase purchase) {this.purchases.add(purchase);}
    public void setPenalties(int penalties) {this.penalties = penalties;}
}
