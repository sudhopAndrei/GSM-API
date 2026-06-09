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
        AuditService.getServiceInstance().logAction("recurringMonthlyBill");

        Customer customer = PersonDAO.findById(userID);

        double totalCost = 0;

        for (Purchase purchase : customer.getPurchases()) {
            if (purchase.getItem() instanceof TelecomSubscription &&
                    ChronoUnit.MONTHS.between(purchase.getDate(), LocalDate.now()) <
                            (((TelecomSubscription) purchase.getItem()).getContractLength())) {
                totalCost = totalCost + purchase.getItem().getPrice();
            }
        }

        return totalCost;
    }

    //calculul achizitiilor one-time in ultima luna (30 de zile)
    public static double lastMonthBill (int userID) {
        AuditService.getServiceInstance().logAction("lastMonthBill");

        Customer customer = PersonDAO.findById(userID);

        double totalCost = 0;

        for (Purchase purchase : customer.getPurchases()) {
            if (purchase.getItem() instanceof Device &&
                    ChronoUnit.DAYS.between(purchase.getDate(), LocalDate.now()) < 30) {
                totalCost = totalCost + purchase.getItem().getPrice();
            }
        }

        return totalCost;
    }

    //calculul penalizarii in cazul anularii unui abonament
    public static int cancellationPenalty(int userID, int subscriptionID) {
        AuditService.getServiceInstance().logAction("cancellationPenalty");

        Customer customer = PersonDAO.findById(userID);

        int penaltyCost = 0;

        for (Purchase purchase : customer.getPurchases()) {
            if (purchase.getItem() instanceof TelecomSubscription subscription) {
                if (subscription.getSubscriptionID() == subscriptionID) {
                    int remainingPeriod = subscription.getContractLength() - (int) ChronoUnit.MONTHS.between(purchase.getDate(), LocalDate.now());

                    if (remainingPeriod > 0) {
                        penaltyCost = (remainingPeriod * subscription.getPrice()) / 2;
                    }
                    break;
                }
            }
        }

        return penaltyCost;
    }

    //"realizeaza o tranzactie" - adauga o achizitie in baza de date
    public static void makePurchase(int userID, int itemID, String itemType) {
        AuditService.getServiceInstance().logAction("makePurchase");

        Person person = PersonDAO.findById(userID);
        Purchase purchase = PurchaseDAO.create(userID, itemID, itemType, LocalDate.now());
        
        person.addPurchase(purchase);
        PersonDAO.update(person);
    }

    // suma totala cheltuita pe device-uri de catre un client
    public static int totalSpent(int userID) {
        AuditService.getServiceInstance().logAction("totalSpent");

        Customer customer = PersonDAO.findById(userID);

        int total = 0;

        for (Purchase purchase : customer.getPurchases()) {
            if (purchase.getItem() instanceof Device) {
                total += purchase.getItem().getPrice();
            }
        }

        return total;
    }

    //scade punctele clientului si intoarce valoare voucherului
    public static int redeemPoints(int userID, int pointsToRedeem) {
        AuditService.getServiceInstance().logAction("redeemPoints");

        Person person = PersonDAO.findById(userID);

        if (person.getLoyaltyPoints() < pointsToRedeem) {
            return -1;
        }

        int value = pointsToRedeem / 2;

        person.addLoyaltyPoints(-pointsToRedeem);
        PersonDAO.update(person);

        return value;
    }

    //arata categoria clientului in functie de vechimea lui in sistem
    public static String getUserTier(int userID) {
        AuditService.getServiceInstance().logAction("getUserTier");

        Person person = PersonDAO.findById(userID);

        long years = person.calculateTenure();
        if (years >= 10) return "Gold";
        if (years >= 3)  return "Silver";
        return "Bronze";
    }
}
