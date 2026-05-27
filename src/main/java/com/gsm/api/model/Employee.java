package com.gsm.api.model;

import java.time.*;

public class Employee extends User {
    private String role;
    private int salary;

    public Employee(int userID, String name, String email, String phoneNumber, LocalDate joinDate,
             String role, int salary) {

        super(userID, name, email, phoneNumber, joinDate);
        this.role = role;
        this.salary = salary;
    }

    //gettere
    public String getRole() {return this.role;}
    public int getSalary() {return this.salary;}

    //settere
    public void setRole(String role) {this.role = role;}
    public void setSalary(int role) {this.salary = role;}
}
