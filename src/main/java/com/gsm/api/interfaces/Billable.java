package com.gsm.api.interfaces;

public interface Billable {
    int calculateCost();
    int getBillableID();
    String getTypeIdentifier();
}
