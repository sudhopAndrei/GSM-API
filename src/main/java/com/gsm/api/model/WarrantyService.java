package com.gsm.api.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WarrantyService {
    private WarrantyService() {};

    //produse cu garantie activa pentru un client
    public List<Billable> activeWarranties(Customer customer) {
        List<Billable> warranties = new ArrayList<>();

        for (Map.Entry<Billable, LocalDate> purchase : customer.getPurchases().entrySet()) {
            if (purchase.getKey() instanceof Warrantable w) {
                if (ChronoUnit.YEARS.between(purchase.getValue(), LocalDate.now()) < w.calculateWarranty()
                        && w.calculateWarranty() != 0) {
                    warranties.add(purchase.getKey());
                }
            }
        }

        return warranties;
    }

    //extindem garantia unui produs cu un numar de luni
    public void extendedWarranty(Customer customer, String identifier, int monthsExtended) {
        for (Map.Entry<Billable, LocalDate> purchase : customer.getPurchases().entrySet()) {
            Billable item = purchase.getKey();
            if (item instanceof Warrantable) {
                if (item instanceof Device device && device.getName().equals(identifier)) {
                    customer.addWarrantyExtension(device, monthsExtended);
                    break;
                } else if (item instanceof InternetSubscription subscription && subscription.getName().equals(identifier)) {
                    customer.addWarrantyExtension(subscription, monthsExtended);
                    break;
                }
            }
        }
    }

}
