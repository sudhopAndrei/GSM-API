package com.gsm.api.model.interfaces;

public interface Billable {
    int calculateCost();
    int getBillableID();
    String getTypeIdentifier();
}
