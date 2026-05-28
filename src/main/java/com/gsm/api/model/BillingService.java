package com.gsm.api.model;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class BillingService {
    private BillingService() {};

    //calculul facturii lunare
    public double recurringMonthlyBill (Customer customer) {
        double totalCost = 0;

        for (Map.Entry<Billable, LocalDate> purchase : customer.getPurchases().entrySet()) {
            if (purchase.getKey() instanceof TelecomSubscription &&
                    ChronoUnit.MONTHS.between(purchase.getValue(), LocalDate.now()) <
                            ((TelecomSubscription) purchase.getKey()).getContractLength()) {
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

    //calculul penalizarii in cazul anularii unui abonament
    public int cancellationPenalty(Customer customer, String identifier) {
        int penaltyCost = 0;

        for (Map.Entry<Billable, LocalDate> purchase : customer.getPurchases().entrySet()) {
            if (purchase.getKey() instanceof TelecomSubscription subscription) {
                if (subscription.getName().equals(identifier)) {
                    int remainingPeriod = subscription.getContractLength() - (int) ChronoUnit.MONTHS.between(purchase.getValue(), LocalDate.now());

                    if (remainingPeriod > 0) {
                        penaltyCost = (remainingPeriod * subscription.calculateCost()) / 2;
                    }
                    break;
                }
            }
        }

        return penaltyCost;
    }

    //calculeaza discount ul pentru device uri pe baza punctelor (va fi apelat doar la cumpararea device urilor)
    public int calculateDiscount(Customer customer) {
        int discountPercentage = 0;

        if (customer instanceof Person p) {
            discountPercentage = p.getLoyaltyPoints() / 100;
        }

        if (discountPercentage > 25) {
            return 25;
        }
        return discountPercentage;
    }
}
