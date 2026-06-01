package com.gsm.api.model.devices;

public class TelevisionSet extends Device {
    private final double diagonalInches;
    private final String resolution;
    private final boolean isSmartTv;

    public TelevisionSet(int deviceID, String name, int price,
                         double diagonalInches, String resolution, boolean isSmartTv) {

        super(deviceID, name, price);
        this.diagonalInches = diagonalInches;
        this.resolution = resolution;
        this.isSmartTv = isSmartTv;
    }

    public TelevisionSet(int deviceID, String name,
                         double diagonalInches, String resolution, boolean isSmartTv) {

        this(deviceID, name, 0, diagonalInches, resolution, isSmartTv);
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
    public double getDiagonalInches() {return this.diagonalInches;}
    public String getResolution() {return this.resolution;}
    public boolean getIsSmartTv() {return this.isSmartTv;}
}
