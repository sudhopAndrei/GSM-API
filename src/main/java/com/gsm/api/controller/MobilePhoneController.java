package com.gsm.api.controller;

import com.gsm.api.dao.MobilePhoneDAO;
import com.gsm.api.model.MobilePhone;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mobilephone")
public class MobilePhoneController {

    record MobilePhoneRequest(String name, int storageSpace, String color, boolean hasESim) {}

    @GetMapping
    public List<MobilePhone> findAll() {
        return MobilePhoneDAO.findAll();
    }

    @GetMapping("/{deviceID}")
    public MobilePhone findById(@PathVariable int deviceID) {
        return MobilePhoneDAO.findById(deviceID);
    }

    @PostMapping
    public MobilePhone create(@RequestBody MobilePhoneRequest req) {
        return MobilePhoneDAO.create(req.name(), req.storageSpace(), req.color(), req.hasESim());
    }

    @PutMapping("/{deviceID}")
    public void update(@PathVariable int deviceID, @RequestBody MobilePhoneRequest req) {
        MobilePhone phone = new MobilePhone(deviceID, req.name(), req.storageSpace(), req.color(), req.hasESim());
        MobilePhoneDAO.update(phone);
    }

    @DeleteMapping("/{deviceID}")
    public void delete(@PathVariable int deviceID) {
        MobilePhoneDAO.delete(deviceID);
    }
}
