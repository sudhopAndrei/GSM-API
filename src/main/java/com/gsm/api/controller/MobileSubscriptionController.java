package com.gsm.api.controller;

import com.gsm.api.dao.MobileSubscriptionDAO;
import com.gsm.api.model.MobileSubscription;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/tvsubscription")
public class MobileSubscriptionController {
    private record MobileSubscriptionRecord(String name, int contractLength, int price,
                                    int nationalMinutes, int networkGB, int internationalMinutes, boolean hasRoaming) {}

    @GetMapping
    public List<MobileSubscription> findAll() {return MobileSubscriptionDAO.findAll();}

    @GetMapping("/{subscriptionID}")
    public MobileSubscription findByID(@PathVariable int subscriptionID) {
        return MobileSubscriptionDAO.findById(subscriptionID);
    }

    @PostMapping
    public MobileSubscription create(@RequestBody MobileSubscriptionRecord req) {
        return MobileSubscriptionDAO.create(req.name, req.contractLength, req.price, req.nationalMinutes,
                req.networkGB, req.internationalMinutes, req.hasRoaming);
    }

    @PutMapping("/{subscriptionID}")
    public void update(@PathVariable int subscriptionID, @RequestBody MobileSubscriptionRecord req) {
        MobileSubscription tvsub = new MobileSubscription(subscriptionID, req.name, req.contractLength, req.price,
                req.nationalMinutes, req.networkGB, req.internationalMinutes, req.hasRoaming);
        MobileSubscriptionDAO.update(tvsub);
    }

    @DeleteMapping("/{subscriptionID}")
    public void delete(@PathVariable int subscriptionID) {
        MobileSubscriptionDAO.delete(subscriptionID);
    }
}

