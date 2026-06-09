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
    }

    @Override
    public int calculateCost() {
        return 0;
    }

    @Override
    public int calculateWarranty() {
        return 0;
    }

    //gettere
    public int getStorageSpace() {return this.storageSpace;}
    public String getColor() {return this.color;}
    public boolean getHasESim() {return this.hasESim;}
}
