package com.gsm.api.repository;

import com.gsm.api.model.devices.MobilePhone;
import com.gsm.api.service.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MobilePhoneRepository {
    private MobilePhoneRepository() {}

    //insert
    public static MobilePhone create(String name, int price,
                                     int storageSpace, String color, boolean hasESim) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet seq = connection.prepareStatement("SELECT SEQ_DEVICE_ID.NEXTVAL FROM DUAL").executeQuery();
            seq.next();
            int deviceID = seq.getInt(1);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO MOBILE_PHONES (DEVICE_ID, NAME, PRICE, STORAGE_SPACE, COLOR, HAS_ESIM) " +
                            "VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, deviceID);
            statement.setString(2, name);
            statement.setInt(3, price);
            statement.setInt(4, storageSpace);
            statement.setString(5, color);
            statement.setBoolean(6, hasESim);
            statement.executeUpdate();

            return new MobilePhone(deviceID, name, price, storageSpace, color, hasESim);
        }
    }

    //select where id = x
    public static Optional<MobilePhone> findById(int deviceID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT DEVICE_ID, NAME, PRICE, STORAGE_SPACE, COLOR, HAS_ESIM " +
                            "FROM MOBILE_PHONES WHERE DEVICE_ID = ?");
            statement.setInt(1, deviceID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) return Optional.empty();

            MobilePhone mobilePhone = new MobilePhone(
                    resultSet.getInt("DEVICE_ID"), resultSet.getString("NAME"), resultSet.getInt("PRICE"),
                    resultSet.getInt("STORAGE_SPACE"), resultSet.getString("COLOR"), resultSet.getBoolean("HAS_ESIM"));

            return Optional.of(mobilePhone);
        }
    }

    //select all
    public static List<MobilePhone> findAll() throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet rs = connection.prepareStatement(
                    "SELECT DEVICE_ID, NAME, PRICE, STORAGE_SPACE, COLOR, HAS_ESIM " +
                            "FROM MOBILE_PHONES").executeQuery();

            List<MobilePhone> mobilePhones = new ArrayList<>();
            while (rs.next()) {
                MobilePhone mobilePhone = new MobilePhone(
                        rs.getInt("DEVICE_ID"), rs.getString("NAME"), rs.getInt("PRICE"),
                        rs.getInt("STORAGE_SPACE"), rs.getString("COLOR"), rs.getBoolean("HAS_ESIM"));
                mobilePhones.add(mobilePhone);
            }
            return mobilePhones;
        }
    }

    //update
    public static void update(MobilePhone mobilePhone) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE MOBILE_PHONES SET NAME = ?, PRICE = ?, STORAGE_SPACE = ?, " +
                            "COLOR = ?, HAS_ESIM = ? WHERE DEVICE_ID = ?");
            statement.setString(1, mobilePhone.getName());
            statement.setInt(2, mobilePhone.getPrice());
            statement.setInt(3, mobilePhone.getStorageSpace());
            statement.setString(4, mobilePhone.getColor());
            statement.setBoolean(5, mobilePhone.getHasESim());
            statement.setInt(6, mobilePhone.getDeviceID());
            statement.executeUpdate();
        }
    }

    //delete
    public static void delete(int deviceID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM MOBILE_PHONES WHERE DEVICE_ID = ?");
            statement.setInt(1, deviceID);
            statement.executeUpdate();
        }
    }
}
