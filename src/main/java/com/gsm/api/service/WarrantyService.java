package com.gsm.api.service;

import com.gsm.api.dao.PersonDAO;
import com.gsm.api.model.*;
import com.gsm.api.interfaces.Billable;
import com.gsm.api.interfaces.Warrantable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class WarrantyService {
    private WarrantyService() {};

    //produse cu garantie activa pentru un client
    public static List<Billable> activeWarranties(int userID) {
        Customer customer = PersonDAO.findById(userID);

        List<Billable> warranties = new ArrayList<>();

        for (Purchase purchase : customer.getPurchases()) {
            if (purchase.getItem() instanceof Warrantable w) {
                long monthsPassed = ChronoUnit.MONTHS.between(purchase.getDate(), LocalDate.now());
                int warrantyMonths = w.calculateWarranty();

                if (warrantyMonths != 0 && monthsPassed < warrantyMonths) {
                    warranties.add(purchase.getItem());
                }
            }
        }

        return warranties;
    }

    // returneaza numarul de luni ramase din garantie sau -1 daca a expirat
    public static int remainingWarrantyMonths(int userID, int purchaseIdentifier) {
        Customer customer = PersonDAO.findById(userID);

        for (Purchase purchase : customer.getPurchases()) {
            if (purchase.getPurchaseID() == purchaseIdentifier) {
                Billable item = purchase.getItem();
                if (item instanceof Warrantable w) {
                    int warrantyMonths = w.calculateWarranty();
                    long monthsPassed = ChronoUnit.MONTHS.between(purchase.getDate(), LocalDate.now());

                    int remaining = (int) (warrantyMonths - monthsPassed);
                    if (remaining > 0) {
                        return remaining;
                    }
                    break;
                }
            }
        }
        return -1;
    }

    // returneaza abonamentele activeale unui client
    public static List<Billable> activeSubscriptions(int userID) {
        Customer customer = PersonDAO.findById(userID);

        List<Billable> active = new ArrayList<>();

        for (Purchase purchase : customer.getPurchases()) {
            if (purchase.getItem() instanceof TelecomSubscription subscription) {
                long monthsPassed = ChronoUnit.MONTHS.between(purchase.getDate(), LocalDate.now());

                if (monthsPassed < subscription.getContractLength()) {
                    active.add(subscription);
                }
            }
        }

        return active;
    }
}
