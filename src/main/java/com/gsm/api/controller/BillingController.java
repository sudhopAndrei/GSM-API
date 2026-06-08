package com.gsm.api.controller;

import com.gsm.api.service.BillingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    @GetMapping("/recurring/{userID}")
    public double recurringMonthlyBill(@PathVariable int userID) {
        return BillingService.recurringMonthlyBill(userID);
    }

    @GetMapping("/lastmonth/{userID}")
    public double lastMonthBill(@PathVariable int userID) {
        return BillingService.lastMonthBill(userID);
    }

    @GetMapping("/penalty/{userID}/{subscriptionID}")
    public int cancellationPenalty(@PathVariable int userID, @PathVariable int subscriptionID) {
        return BillingService.cancellationPenalty(userID, subscriptionID);
    }

    @PostMapping("/purchase/{userID}/{itemID}/{itemType}")
    public void purchase(@PathVariable int userID, @PathVariable int itemID, @PathVariable String itemType) {
        BillingService.makePurchase(userID, itemID, itemType);
    }
}
