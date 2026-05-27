package com.gsm.api.model;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class BillingService {
    private BillingService() {};

    //calculul facturii lunare
    public double recurringMonthlyBill (Customer customer) {
        double totalCost = 0;

        for (Map.Entry<Billable, LocalDate> purchase : customer.getPurchases().entrySet()) {
            if (purchase.getKey() instanceof TelecomService &&
                    ChronoUnit.MONTHS.between(purchase.getValue(), LocalDate.now()) <
                            ((TelecomService) purchase.getKey()).getContractLength()) {
                totalCost = totalCost + purchase.getKey().calculateCost();
            }
        }

        return totalCost;
    }

    //calculul achizitiilor one-time in ultima luna (30 de zile)
    public double lastMonthBill (Customer customer) {
        double totalCost = 0;

        for (Map.Entry<Billable, LocalDate> purchase : customer.getPurchases().entrySet()) {
            if (purchase.getKey() instanceof Device &&
                    ChronoUnit.DAYS.between(purchase.getValue(), LocalDate.now()) < 30) {
                totalCost = totalCost + purchase.getKey().calculateCost();
            }
        }

        return totalCost;
    }


}
