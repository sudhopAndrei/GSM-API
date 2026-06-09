package com.gsm.api.model;

public class TelevisionSet extends Device {
    private final double diagonalInches;
    private final String resolution;
    private final boolean isSmartTv;

    public TelevisionSet(int deviceID, String name,
                         double diagonalInches, String resolution, boolean isSmartTv) {

        super(deviceID, name);
        this.diagonalInches = diagonalInches;
        this.resolution = resolution;
        this.isSmartTv = isSmartTv;

        this.setPrice(1000);
    }

    @Override
    public int calculateCost() {
        int addOn = 0;
        int deviceCost = this.getPrice();

        if (diagonalInches <= 32) {
            addOn += 200;
        } else if (diagonalInches <= 43) {
            addOn += 400;
        } else if (diagonalInches <= 55) {
            addOn = 650;
        } else {
            addOn= 1000;
        }

        if (resolution.equals("4K")) {
            addOn += 300;
        }

        if (isSmartTv == true) {
            addOn += 150;
        }

        deviceCost += addOn;

        return deviceCost;
    }

    @Override
    public int calculateWarranty() {
        if (isSmartTv == true|| diagonalInches > 55) {
            return 24;
        }
        return 12;
    }

    //gettere
    public double getDiagonalInches() {return this.diagonalInches;}
    public String getResolution() {return this.resolution;}
    public boolean getIsSmartTv() {return this.isSmartTv;}
}
