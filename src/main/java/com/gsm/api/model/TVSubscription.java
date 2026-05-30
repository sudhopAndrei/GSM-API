package com.gsm.api.model;

public class TVSubscription extends TelecomSubscription {
    private int numberOfChannels;
    private boolean hasHDChannels;
    private boolean hasStreamingService;

    public TVSubscription(int subscriptionID, String name, int contractLength,
                          int numberOfChannels, boolean hasHDChannels, boolean hasStreamingService) {

        super(subscriptionID, name, contractLength);
        this.numberOfChannels = numberOfChannels;
        this.hasHDChannels = hasHDChannels;
        this.hasStreamingService = hasStreamingService;
    }

    //pentru serviciu cu pret dat
    public TVSubscription(int subscriptionID, String name, int contractLength, int price,
                          int numberOfChannels, boolean hasHDChannels, boolean hasStreamingService) {

        super(subscriptionID, name, contractLength, price);
        this.numberOfChannels = numberOfChannels;
        this.hasHDChannels = hasHDChannels;
        this.hasStreamingService = hasStreamingService;
    }

    @Override
    public int calculateCost() {
        int addOn = 0;
        int serviceCost = this.getPrice();

        if (numberOfChannels > 100) {
            addOn = addOn + 5;
        }
        if (hasHDChannels == true) {
            serviceCost = serviceCost * 2;
        }
        if (hasStreamingService == true) {
            addOn = addOn + 12;
        }
        serviceCost = serviceCost + addOn;

        return serviceCost;
    }
}
