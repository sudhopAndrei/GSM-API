package com.gsm.api.controller;

import com.gsm.api.interfaces.Billable;
import com.gsm.api.model.TelecomSubscription;
import com.gsm.api.service.WarrantyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/warranty")
public class WarrantyController {
    @GetMapping("/activewaranties/{userID}")
    public List<Billable> activeWarranties(@PathVariable int userID) {
        return WarrantyService.activeWarranties(userID);
    }

    @GetMapping("/remainingmonths/{userID}/{purchaseIdentifier}")
    public int remainingWarrantyMonths(@PathVariable int userID, @PathVariable int purchaseIdentifier) {
        return WarrantyService.remainingWarrantyMonths(userID, purchaseIdentifier);
    }

    @GetMapping("/activesubs/{userID}")
    public List<Billable> activeSubscriptions(@PathVariable int userID) {
        return WarrantyService.activeSubscriptions(userID);
    }
}
