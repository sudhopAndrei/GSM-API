package com.gsm.api.service;

import com.gsm.api.dao.PersonDAO;
import com.gsm.api.dao.PurchaseDAO;
import com.gsm.api.interfaces.Billable;
import com.gsm.api.model.*;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class BillingService {
    private BillingService() {};

    //calculul facturii lunare
    public static double recurringMonthlyBill (int userID) {
        Customer customer = PersonDAO.findById(userID);

        double totalCost = 0;

        for (Purchase purchase : customer.getPurchases()) {
            if (purchase.getItem() instanceof TelecomSubscription &&
                    ChronoUnit.MONTHS.between(purchase.getDate(), LocalDate.now()) <
                            (((TelecomSubscription) purchase.getItem()).getContractLength())) {
                totalCost = totalCost + purchase.getItem().calculateCost();
            }
        }

        return totalCost;
    }

    //calculul achizitiilor one-time in ultima luna (30 de zile)
    public static double lastMonthBill (int userID) {
        Customer customer = PersonDAO.findById(userID);

        double totalCost = 0;

        for (Purchase purchase : customer.getPurchases()) {
            if (purchase.getItem() instanceof Device &&
                    ChronoUnit.DAYS.between(purchase.getDate(), LocalDate.now()) < 30) {
                totalCost = totalCost + purchase.getItem().calculateCost();
            }
        }

        return totalCost;
    }

    //calculul penalizarii in cazul anularii unui abonament
    public static int cancellationPenalty(int userID, int subscriptionID) {
        Customer customer = PersonDAO.findById(userID);

        int penaltyCost = 0;

        for (Purchase purchase : customer.getPurchases()) {
            if (purchase.getItem() instanceof TelecomSubscription subscription) {
                if (subscription.getSubscriptionID() == subscriptionID) {
                    int remainingPeriod = subscription.getContractLength() - (int) ChronoUnit.MONTHS.between(purchase.getDate(), LocalDate.now());

                    if (remainingPeriod > 0) {
                        penaltyCost = (remainingPeriod * subscription.calculateCost()) / 2;
                    }
                    break;
                }
            }
        }

        return penaltyCost;
    }

    //"realizeaza o tranzactie" - adauga o achizitie in baza de date
    public static void makePurchase(int userID, int itemID, String itemType) {
        Person person = PersonDAO.findById(userID);
        Purchase purchase = PurchaseDAO.create(userID, itemID, itemType, LocalDate.now());
        
        person.addPurchase(purchase);
        PersonDAO.update(person);
    }


    // de aici in jos mutat in CUSTOMER ca functie pt polimorfism

    //calculeaza discount ul pentru device uri pe baza punctelor (va fi apelat doar la cumpararea device urilor)
    // public static int calculateDiscount(int userID) {
    //     Customer customer = PersonDAO.findById(userID);

    //     int discountPercentage = 0;

    //     if (customer instanceof Person p) {
    //         discountPercentage = p.getLoyaltyPoints() / 100;
    //     }

    //     if (discountPercentage > 25) {
    //         return 25;
    //     }
    //     return discountPercentage;
    // }

    //calculeaza discount ul pentru angajat in functie de vechime

}
