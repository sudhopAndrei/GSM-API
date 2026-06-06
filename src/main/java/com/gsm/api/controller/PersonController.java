package com.gsm.api.controller;

import com.gsm.api.dao.PersonDAO;
import com.gsm.api.model.Person;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private record PersonRecord(String name, String email, String phoneNumber, LocalDate joinDate, String IBAN) {}

    @GetMapping
    public List<Person> findAll() {return PersonDAO.findAll();}

    @GetMapping("/{userID}")
    public Person findByID(@PathVariable int userID) {
        return PersonDAO.findById(userID);
    }

    @PostMapping
    public Person create(@RequestBody PersonRecord req) {
        return PersonDAO.create(req.name, req.email, req.phoneNumber, req.joinDate, req.IBAN);
    }

    @PutMapping("/{userID}")
    public void update(@PathVariable int userID, @RequestBody PersonRecord req) {
        Person person = new Person(userID, req.name, req.email, req.phoneNumber, req.joinDate, req.IBAN);
        PersonDAO.update(person);
    }

    @DeleteMapping("/{userID}")
    public void delete(@PathVariable int userID) {
        PersonDAO.delete(userID);
    }
}
