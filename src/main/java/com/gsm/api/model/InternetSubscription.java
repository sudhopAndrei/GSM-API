package com.gsm.api.model;

public class InternetSubscription extends TelecomSubscription implements Warrantable{
    private int downloadSpeedMbps;
    private int uploadSpeedMbps;
    private boolean isFiberOptic;
    private boolean hasRouter;

    //pentru serviciu cu pret fix
    public InternetSubscription(String name, int contractLength, int price,
                                int downloadSpeedMbps, int uploadSpeedMbps, boolean isFiberOptic, boolean hasRouter) {

        super(name, contractLength, price);
        this.downloadSpeedMbps = downloadSpeedMbps;
        this.uploadSpeedMbps = uploadSpeedMbps;
        this.isFiberOptic = isFiberOptic;
        this.hasRouter = hasRouter;
    }

    public InternetSubscription(String name, int contractLength,
                                int downloadSpeedMbps, int uploadSpeedMbps, boolean isFiberOptic, boolean hasRouter) {

        super(name, contractLength);
        this.downloadSpeedMbps = downloadSpeedMbps;
        this.uploadSpeedMbps = uploadSpeedMbps;
        this.isFiberOptic = isFiberOptic;
        this.hasRouter = hasRouter;
    }

    @Override
    public int calculateCost() {
        int addOn = 0;
        int serviceCost = this.getPrice();

        if (downloadSpeedMbps > 500) {
            addOn = addOn + 3;
        }
        if (uploadSpeedMbps > 500) {
            addOn = addOn + 3;
        }
        if (isFiberOptic == true) {
            serviceCost = serviceCost * 2;
        }
        if (hasRouter == true) {
            serviceCost = serviceCost * 2;
        }

        serviceCost = serviceCost + addOn;

        return serviceCost;
    }

    @Override
    public int calculateWarranty() {
        int warrantyMonths = 0;
        if (isFiberOptic == true) {
            warrantyMonths = Math.max(warrantyMonths, 6);
        }
        if (hasRouter == true) {
            warrantyMonths = Math.max(warrantyMonths, 24);
        }

        return warrantyMonths;
    }
}
