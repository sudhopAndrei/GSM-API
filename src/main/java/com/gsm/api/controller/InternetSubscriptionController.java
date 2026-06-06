package com.gsm.api.controller;

import com.gsm.api.dao.InternetSubscriptionDAO;
import com.gsm.api.model.InternetSubscription;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internetsub")
public class InternetSubscriptionController {
    private record InternetSubscriptionRecord(String name, int contractLength, int price,
                                              int downloadSpeedMbps, int uploadSpeedMbps, boolean isFiberOptic, boolean hasRouter) {}

    @GetMapping
    public List<InternetSubscription> findAll() {return InternetSubscriptionDAO.findAll();}

    @GetMapping("/{subscriptionID}")
    public InternetSubscription findByID(@PathVariable int subscriptionID) {
        return  InternetSubscriptionDAO.findById(subscriptionID);
    }

    @PostMapping
    public InternetSubscription create(@RequestBody InternetSubscriptionRecord req) {
        return InternetSubscriptionDAO.create(req.name, req.contractLength, req.price, req.downloadSpeedMbps,
                req.uploadSpeedMbps, req.isFiberOptic, req.hasRouter);
    }

    @PutMapping("/{subscriptionID}")
    public void update(@PathVariable int subscriptionID, @RequestBody InternetSubscriptionRecord req) {
        InternetSubscription internetsub = new InternetSubscription(subscriptionID, req.name, req.contractLength, req.price, req.downloadSpeedMbps,
                req.uploadSpeedMbps, req.isFiberOptic, req.hasRouter);
        InternetSubscriptionDAO.update(internetsub);
    }

    @DeleteMapping("/{subscriptionID}")
    public void delete(@PathVariable int subscriptionID) {
        InternetSubscriptionDAO.delete(subscriptionID);
    }
}
