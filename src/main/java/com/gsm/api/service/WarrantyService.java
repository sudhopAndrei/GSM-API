package com.gsm.api.service;

import com.gsm.api.model.devices.Device;
import com.gsm.api.model.interfaces.Billable;
import com.gsm.api.model.interfaces.Warrantable;
import com.gsm.api.model.purchases.Purchase;
import com.gsm.api.model.subscriptions.InternetSubscription;
import com.gsm.api.model.users.Customer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WarrantyService {
    private WarrantyService() {};

    //produse cu garantie activa pentru un client
    public static List<Billable> activeWarranties(Customer customer) {
        List<Billable> warranties = new ArrayList<>();

        for (Purchase purchase : customer.getPurchases()) {
            if (purchase.getItem() instanceof Warrantable w) {
                long monthsPassed = ChronoUnit.MONTHS.between(purchase.getDate(), LocalDate.now());
                int baseWarrantyMonths = w.calculateWarranty();
                int extendedMonths = customer.getExtendedWarranties().getOrDefault(purchase.getItem(), 0);

                if (baseWarrantyMonths != 0 && monthsPassed < (baseWarrantyMonths + extendedMonths)) {
                    warranties.add(purchase.getItem());
                }
            }
        }

        return warranties;
    }

    //extindem garantia unui produs cu un numar de luni
    public static void extendedWarranty(Customer customer, int itemIdentifier, int monthsExtended) {
        for (Purchase purchase : customer.getPurchases()) {
            Billable item = purchase.getItem();
            if (item instanceof Warrantable) {
                if (item instanceof Device device && device.getDeviceID() == itemIdentifier) {
                    customer.addWarrantyExtension(device, monthsExtended);
                    break;
                } else if (item instanceof InternetSubscription subscription && subscription.getSubscriptionID() == itemIdentifier) {
                    customer.addWarrantyExtension(subscription, monthsExtended);
                    break;
                }
            }
        }
    }

    //anuleaza o garantie
    public static void cancelWarranty(Customer customer, int itemIdentifier) {
        for (Purchase purchase : customer.getPurchases()) {
            Billable item = purchase.getItem();
            if (item instanceof Warrantable) {
                if ((item instanceof Device device && device.getDeviceID() == itemIdentifier) ||
                        (item instanceof InternetSubscription subscription && subscription.getSubscriptionID() == itemIdentifier)) {
                    customer.addWarrantyExtension(item, -100000);
                    break;
                }
            }
        }
    }

    // returneaza numarul de luni ramase din garantie sau -1 daca a expirat
    public static int remainingWarrantyMonths(Customer customer, int itemIdentifier) {
        for (Purchase purchase : customer.getPurchases()) {
            Billable item = purchase.getItem();
            if (item instanceof Warrantable w) {
                if ((item instanceof Device device && device.getDeviceID() == itemIdentifier) ||
                        (item instanceof InternetSubscription subscription && subscription.getSubscriptionID() == itemIdentifier)) {
                    
                    int baseWarrantyMonths = w.calculateWarranty();
                    long monthsPassed = ChronoUnit.MONTHS.between(purchase.getDate(), LocalDate.now());
                    int extendedMonths = customer.getExtendedWarranties().getOrDefault(item, 0);
                    
                    int remaining = (int) (baseWarrantyMonths + extendedMonths - monthsPassed);
                    if (remaining > 0) {
                        return remaining;
                    }
                    break;
                }
            }
        }
        return -1;
    }
}
