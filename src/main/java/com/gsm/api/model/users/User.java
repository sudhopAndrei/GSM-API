package com.gsm.api.model.users;

import java.time.temporal.ChronoUnit;
import java.time.*;

public abstract class User {
    private final int userID;
    private String name;
    private String email;
    private String phoneNumber;
    private final LocalDate joinDate;

    protected User(int userID, String name, String email, String phoneNumber, LocalDate joinDate) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.joinDate = joinDate;
    }

    //vechimea angajatului / clientului
    public long calculateTenure() {
        return ChronoUnit.YEARS.between(joinDate, LocalDate.now());
    }

    //gettere
    public int getUserID() {return this.userID;}
    public String getName() {return this.name;}
    public String getEmail() {return this.email;}
    public String getPhoneNumber() {return this.phoneNumber;}
    public LocalDate getJoinDate() {return this.joinDate;}

    //settere
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
}
