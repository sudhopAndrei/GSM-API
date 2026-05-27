package com.gsm.api.model;

import java.time.temporal.ChronoUnit;
import java.time.*;

public abstract class User {
    private final int IDUser;
    private String name;
    private String email;
    private String phoneNumber;
    private final LocalDate joinDate;

    protected User(int IDUser, String name, String email, String phoneNumber, LocalDate joinDate) {
        this.IDUser = IDUser;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.joinDate = joinDate;
    }

    long calculateTenure() {
        return ChronoUnit.YEARS.between(joinDate, LocalDate.now());
    }

    //gettere
    public int getIDUser() {return this.IDUser;};
    public String getName() {return this.name;};
    public String getEmail() {return this.email;};
    public String getPhoneNumber() {return this.phoneNumber;};
    public LocalDate getJoinDate() {return this.joinDate;};

    //settere
    public void setName(String name) {this.name = name;};
    public void setEmail(String email) {this.email = email;};
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;};
}
