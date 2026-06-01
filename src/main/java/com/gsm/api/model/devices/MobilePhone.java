package com.gsm.api.model.devices;

public class MobilePhone extends Device{
    private final int storageSpace;
    private final String color;
    private final boolean hasESim;

    public MobilePhone(int deviceID, String name, int price,
                       int storageSpace, String color, boolean hasESim) {
        super(deviceID, name, price);
        this.storageSpace = storageSpace;
        this.color = color;
        this.hasESim = hasESim;
    }

    public MobilePhone(int deviceID, String name,
                       int storageSpace, String color, boolean hasESim) {
        this(deviceID, name, 0, storageSpace, color, hasESim);
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
