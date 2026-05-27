package com.gsm.api.model;

import java.time.LocalDate;
import java.util.Map;

public class Company extends Customer {
    private final String cui;
    private String representativeName;

    public Company (int userID, String name, String email, String phoneNumber, LocalDate joinDate,
                    String cui, String representativeName) {

        super(userID, name, email, phoneNumber, joinDate);

        this.cui = cui;
        this.representativeName = representativeName;
    }
}
