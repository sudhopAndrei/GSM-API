package com.gsm.api.dao;

import com.gsm.api.model.MobilePhone;
import com.gsm.api.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MobilePhoneDAO {
    private MobilePhoneDAO() {}

    //insert
    public static MobilePhone create(String name,
                                     int storageSpace, String color, boolean hasESim) {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO MOBILE_PHONES (NAME, STORAGE_SPACE, COLOR, HAS_ESIM) " +
                            "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setInt(2, storageSpace);
            statement.setString(3, color);
            statement.setBoolean(4, hasESim);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            int deviceID = 0;
            if (rs.next() == true) {
                deviceID = rs.getInt(1);
            }

            MobilePhone phone = new MobilePhone(deviceID, name, storageSpace, color, hasESim);
            phone.setPrice(phone.calculateCost());

            return phone;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //select where id = x
    public static MobilePhone findById(int deviceID) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT DEVICE_ID, NAME, PRICE, STORAGE_SPACE, COLOR, HAS_ESIM " +
                            "FROM MOBILE_PHONES WHERE DEVICE_ID = ?");
            statement.setInt(1, deviceID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) return null;

            MobilePhone phone = new MobilePhone(
                    resultSet.getInt("DEVICE_ID"), resultSet.getString("NAME"),
                    resultSet.getInt("STORAGE_SPACE"), resultSet.getString("COLOR"), resultSet.getBoolean("HAS_ESIM"));

            phone.setPrice(resultSet.getInt("PRICE"));

            return phone;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //select all
    public static List<MobilePhone> findAll() {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet rs = connection.prepareStatement(
                    "SELECT DEVICE_ID, NAME, PRICE, STORAGE_SPACE, COLOR, HAS_ESIM " +
                            "FROM MOBILE_PHONES").executeQuery();

            List<MobilePhone> mobilePhones = new ArrayList<>();
            while (rs.next()) {
                MobilePhone phone = new MobilePhone(
                        rs.getInt("DEVICE_ID"), rs.getString("NAME"),
                        rs.getInt("STORAGE_SPACE"), rs.getString("COLOR"), rs.getBoolean("HAS_ESIM"));

                phone.setPrice(rs.getInt("PRICE"));

                mobilePhones.add(phone);
            }
            return mobilePhones;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //update
    public static void update(MobilePhone mobilePhone) {
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
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //delete
    public static void delete(int deviceID) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM MOBILE_PHONES WHERE DEVICE_ID = ?");
            statement.setInt(1, deviceID);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
