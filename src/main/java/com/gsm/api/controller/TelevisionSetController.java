package com.gsm.api.controller;

import com.gsm.api.dao.TelevisionSetDAO;
import com.gsm.api.model.TelevisionSet;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tvset")
public class TelevisionSetController {
    record TelevisionSetRequest(String name, int price, double diagonalInches, String resolution, boolean isSmartTv ) {};

    @GetMapping List<TelevisionSet> findAll() {return TelevisionSetDAO.findAll();}

    @GetMapping("/{deviceID}")
    public TelevisionSet findByID(@PathVariable int deviceID) {
        return TelevisionSetDAO.findById(deviceID);
    }

    @PostMapping
    public TelevisionSet create(@RequestBody TelevisionSetRequest req) {
        return TelevisionSetDAO.create(req.name, req.price, req.diagonalInches, req.resolution, req.isSmartTv);
    }

    @PutMapping("/{deviceID}")
    public void update(@PathVariable int deviceID, @RequestBody TelevisionSetRequest req) {
        TelevisionSet tvset = new TelevisionSet(deviceID, req.name, req.price, req.diagonalInches, req.resolution, req.isSmartTv);
        TelevisionSetDAO.update(tvset);
    }

    @DeleteMapping("/{deviceID}")
    public void delete(@PathVariable int deviceID) {
        TelevisionSetDAO.delete(deviceID);
    }
}