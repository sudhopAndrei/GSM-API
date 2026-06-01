package com.gsm.api.repository;

import com.gsm.api.model.users.Person;
import com.gsm.api.service.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonRepository {
    private PersonRepository() {}

    //insert
    public static Person create(Person person) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet seq = connection.prepareStatement("SELECT SEQ_USER_ID.NEXTVAL FROM DUAL").executeQuery();
            seq.next();
            int newId = seq.getInt(1);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO PERSONS (USER_ID, NAME, EMAIL, PHONE_NUMBER, JOIN_DATE, IBAN) " +
                            "VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, newId);
            statement.setString(2, person.getName());
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getPhoneNumber());
            statement.setDate(5, Date.valueOf(person.getJoinDate()));
            statement.setString(6, person.getIBAN());
            statement.executeUpdate();

            //returnam persoana cu id-ul schimbat din secventa
            return new Person(newId, person.getName(), person.getEmail(),
                    person.getPhoneNumber(), person.getJoinDate(), person.getIBAN());
        }
    }

    //select where id = x
    public static Optional<Person> findById(int userID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT USER_ID, NAME, EMAIL, PHONE_NUMBER, JOIN_DATE, PENALTIES, IBAN, LOYALTY_POINTS " +
                            "FROM PERSONS WHERE USER_ID = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) return Optional.empty();

            Person person = new Person(
                    resultSet.getInt("USER_ID"), resultSet.getString("NAME"), resultSet.getString("EMAIL"),
                    resultSet.getString("PHONE_NUMBER"), resultSet.getDate("JOIN_DATE").toLocalDate(), resultSet.getString("IBAN"));
            person.setPenalties(resultSet.getInt("PENALTIES"));
            person.addLoyaltyPoints(resultSet.getInt("LOYALTY_POINTS"));

            return Optional.of(person);
        }
    }

    //select all
    public static List<Person> findAll() throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet rs = connection.prepareStatement(
                    "SELECT USER_ID, NAME, EMAIL, PHONE_NUMBER, JOIN_DATE, PENALTIES, IBAN, LOYALTY_POINTS " +
                            "FROM PERSONS").executeQuery();

            List<Person> persons = new ArrayList<>();
            while (rs.next()) {
                Person person = new Person(
                        rs.getInt("USER_ID"), rs.getString("NAME"), rs.getString("EMAIL"),
                        rs.getString("PHONE_NUMBER"), rs.getDate("JOIN_DATE").toLocalDate(), rs.getString("IBAN"));
                person.setPenalties(rs.getInt("PENALTIES"));
                person.addLoyaltyPoints(rs.getInt("LOYALTY_POINTS"));
                persons.add(person);
            }
            return persons;
        }
    }

    //update
    public static void update(Person person) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE PERSONS SET NAME = ?, EMAIL = ?, PHONE_NUMBER = ?, " +
                            "PENALTIES = ?, IBAN = ?, LOYALTY_POINTS = ? WHERE USER_ID = ?");
            statement.setString(1, person.getName());
            statement.setString(2, person.getEmail());
            statement.setString(3, person.getPhoneNumber());
            statement.setDouble(4, person.getPenalties());
            statement.setString(5, person.getIBAN());
            statement.setInt(6, person.getLoyaltyPoints());
            statement.setInt(7, person.getUserID());
            statement.executeUpdate();
        }
    }

    //delete
    public static void delete(int userID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM PERSONS WHERE USER_ID = ?");
            statement.setInt(1, userID);
            statement.executeUpdate();
        }
    }
}
