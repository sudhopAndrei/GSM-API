package com.gsm.api.model.purchases;

import java.time.LocalDate;

public class Purchase {
    private int purchaseID;
    private int userID; //fk pt user
    private int itemID; //fk pt produs
    private String itemType;
    private LocalDate date;

    public Purchase(int purchaseID, int userID, int itemID, String itemType, LocalDate date) {
        this.purchaseID = purchaseID;
        this.userID = userID;
        this.itemID = itemID;
        this.itemType = itemType;
        this.date = date;
    }

    //gettere
    public int getPurchaseID() {return this.purchaseID;}
    public int getUserID() {return this.userID;}
    public int getItemID() {return this.itemID;}
    public String getItemType() {return this.itemType;}
    public LocalDate getDate() {return this.date;}

    //settere
    public void setPurchaseID(int purchaseID) {this.purchaseID = purchaseID;}
}
