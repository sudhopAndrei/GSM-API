package com.gsm.api.model.subscriptions;

public class MobileSubscription extends TelecomSubscription {
    private int nationalMinutes;
    private int networkGB;
    private int internationalMinutes;
    private boolean hasRoaming;

    public MobileSubscription(int subscriptionID, String name, int contractLength, int price,
                              int nationalMinutes, int networkGB, int internationalMinutes, boolean hasRoaming) {

        super(subscriptionID, name, contractLength, price);
        this.nationalMinutes = nationalMinutes;
        this.networkGB = networkGB;
        this.internationalMinutes = internationalMinutes;
        this.hasRoaming = hasRoaming;
    }

    public MobileSubscription(int subscriptionID, String name, int contractLength,
                       int nationalMinutes, int networkGB, int internationalMinutes, boolean hasRoaming) {
        this(subscriptionID, name, contractLength, 2, nationalMinutes, networkGB, internationalMinutes, hasRoaming);
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
            serviceCost = serviceCost * 2;
        }

        serviceCost = serviceCost + addOn;

        return serviceCost;
    }


}
