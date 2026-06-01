package com.gsm.api.model.subscriptions;

public class TVSubscription extends TelecomSubscription {
    private int numberOfChannels;
    private boolean hasHDChannels;
    private boolean hasStreamingService;

    public TVSubscription(int subscriptionID, String name, int contractLength, int price,
                          int numberOfChannels, boolean hasHDChannels, boolean hasStreamingService) {

        super(subscriptionID, name, contractLength, price);
        this.numberOfChannels = numberOfChannels;
        this.hasHDChannels = hasHDChannels;
        this.hasStreamingService = hasStreamingService;
    }

    public TVSubscription(int subscriptionID, String name, int contractLength,
                          int numberOfChannels, boolean hasHDChannels, boolean hasStreamingService) {
        this(subscriptionID, name, contractLength, 2, numberOfChannels, hasHDChannels, hasStreamingService);
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

    //gettere
    @Override
    public int getBillableID() {return this.getSubscriptionID();}

    @Override
    public String getTypeIdentifier() {return "TVSubscription";}

    public int getNumberOfChannels() {return this.numberOfChannels;}
    public boolean getHasHDChannels() {return this.hasHDChannels;}
    public boolean getHasStreamingService() {return this.hasStreamingService;}
}
