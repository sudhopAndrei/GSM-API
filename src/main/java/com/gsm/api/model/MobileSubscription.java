package com.gsm.api.model;

public class MobileSubscription extends TelecomService{
    private int nationalMinutes;
    private int networkGB;
    private int internationalMinutes;
    private boolean hasRoaming;

    public MobileSubscription(String name, int contractLength,
                       int nationalMinutes, int networkGB, int internationalMinutes, boolean hasRoaming) {

        super(name, contractLength);
        this.nationalMinutes = nationalMinutes;
        this.networkGB = networkGB;
        this.internationalMinutes = internationalMinutes;
        this.hasRoaming = hasRoaming;
    }

    //pentru serviciu cu pret dat
    public MobileSubscription(String name, int contractLength, int price,
                       int nationalMinutes, int networkGB, int internationalMinutes, boolean hasRoaming) {

        super(name, price, contractLength);
        this.nationalMinutes = nationalMinutes;
        this.networkGB = networkGB;
        this.internationalMinutes = internationalMinutes;
        this.hasRoaming = hasRoaming;
    }

    @Override
    public int calculateCost() {
        int addOn = 0;
        int serviceCost = this.getPrice();

        if (nationalMinutes > 5000) {
            addOn = addOn + 2;
        }
        if (networkGB > 200) {
            addOn = addOn + 5;
        }
        if (internationalMinutes > 1000) {
            addOn = addOn + 3;
        }
        if (hasRoaming == true) {
            serviceCost = this.getPrice() * 2;
        }

        serviceCost = serviceCost + addOn;

        return serviceCost;
    }


}
