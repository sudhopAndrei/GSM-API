package com.gsm.api.model;

public class TVSubscription extends TelecomService {
    private int numberOfChannels;
    private boolean hasHDChannels;
    private boolean hasStreamingService;

    public TVSubscription(String name, int contractLength,
                          int numberOfChannels, boolean hasHDChannels, boolean hasStreamingService) {

        super(name, contractLength);
        this.numberOfChannels = numberOfChannels;
        this.hasHDChannels = hasHDChannels;
        this.hasStreamingService = hasStreamingService;
    }

    //pentru serviciu cu pret dat
    public TVSubscription(String name, int contractLength, int price,
                          int numberOfChannels, boolean hasHDChannels, boolean hasStreamingService) {

        super(name, price, contractLength);
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
