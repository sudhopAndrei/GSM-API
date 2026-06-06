package com.gsm.api.controller;

import com.gsm.api.dao.TVSubscriptionDAO;
import com.gsm.api.model.TVSubscription;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/tvsubscription")
public class TVSubscriptionController {
    record TVSubscriptionRecord(String name, int contractLength, int price,
                                int numberOfChannels, boolean hasHDChannels, boolean hasStreamingService) {}

    @GetMapping
    public List<TVSubscription> findAll() {return TVSubscriptionDAO.findAll();}

    @GetMapping("/{subscriptionID}")
    public TVSubscription findByID(@PathVariable int subscriptionID) {
        return TVSubscriptionDAO.findById(subscriptionID);
    }

    @PostMapping
    public TVSubscription create(@RequestBody TVSubscriptionRecord req) {
        return TVSubscriptionDAO.create(req.name, req.contractLength, req.price, req.numberOfChannels, req.hasHDChannels,
                req.hasStreamingService);
    }

    @PutMapping("/{subscriptionID}")
    public void update(@PathVariable int subscriptionID, @RequestBody TVSubscriptionRecord req) {
        TVSubscription tvsub = new TVSubscription(subscriptionID, req.name, req.contractLength, req.price, req.numberOfChannels, req.hasHDChannels,
                req.hasStreamingService);
        TVSubscriptionDAO.update(tvsub);
    }

    @DeleteMapping("/{subscriptionID}")
    public void delete(@PathVariable int subscriptionID) {
        TVSubscriptionDAO.delete(subscriptionID);
    }
}
