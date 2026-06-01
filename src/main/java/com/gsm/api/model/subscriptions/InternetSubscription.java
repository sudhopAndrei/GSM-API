package com.gsm.api.model.subscriptions;

import com.gsm.api.model.interfaces.Warrantable;

public class InternetSubscription extends TelecomSubscription implements Warrantable {
    private int downloadSpeedMbps;
    private int uploadSpeedMbps;
    private boolean isFiberOptic;
    private boolean hasRouter;

    public InternetSubscription(int subscriptionID, String name, int contractLength, int price,
                                int downloadSpeedMbps, int uploadSpeedMbps, boolean isFiberOptic, boolean hasRouter) {

        super(subscriptionID, name, contractLength, price);
        this.downloadSpeedMbps = downloadSpeedMbps;
        this.uploadSpeedMbps = uploadSpeedMbps;
        this.isFiberOptic = isFiberOptic;
        this.hasRouter = hasRouter;
    }

    public InternetSubscription(int subscriptionID, String name, int contractLength,
                                int downloadSpeedMbps, int uploadSpeedMbps, boolean isFiberOptic, boolean hasRouter) {
        this(subscriptionID, name, contractLength, 2, downloadSpeedMbps, uploadSpeedMbps, isFiberOptic, hasRouter);
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

    //gettere
    public int getDownloadSpeedMbps() {return this.downloadSpeedMbps;}
    public int getUploadSpeedMbps() {return this.uploadSpeedMbps;}
    public boolean getIsFiberOptic() {return this.isFiberOptic;}
    public boolean getHasRouter() {return this.hasRouter;}
}
