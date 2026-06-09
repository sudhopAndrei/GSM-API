package com.gsm.api.model;

public class MobilePhone extends Device{

    private final int storageSpace;
    private final String color;
    private final boolean hasESim;

    public MobilePhone(int deviceID, String name,
                       int storageSpace, String color, boolean hasESim) {
        super(deviceID, name);
        this.storageSpace = storageSpace;
        this.color = color;
        this.hasESim = hasESim;

        this.setPrice(500);
    }

    @Override
    public int calculateCost() {
        int addOn = 0;
        int deviceCost = this.getPrice();

        if (storageSpace <= 128) {
            addOn += 250;
        } else if (storageSpace <= 256) {
            addOn += 450;
        } else if (storageSpace <= 512) {
            addOn += 700;
        } else {
            addOn += 1000;
        }

        if (hasESim == true) {
            addOn += 200;
        }

        deviceCost += addOn;

        return deviceCost;
    }

    @Override
    public int calculateWarranty() {
        if (hasESim == true) {
            return 24;
        }
        return 12;
    }

    //gettere
    public int getStorageSpace() {return this.storageSpace;}
    public String getColor() {return this.color;}
    public boolean getHasESim() {return this.hasESim;}
}
