package com.gsm.api.model;

import java.time.LocalDate;
import java.util.Map;

public class Company extends Customer {
    private String representativeName;

    public Company (int userID, String name, String email, String phoneNumber, LocalDate joinDate, String representativeName) {

        super(userID, name, email, phoneNumber, joinDate);

        this.representativeName = representativeName;
    }
}
