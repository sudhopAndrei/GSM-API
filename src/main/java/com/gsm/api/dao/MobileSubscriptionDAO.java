package com.gsm.api.dao;

import com.gsm.api.model.MobileSubscription;
import com.gsm.api.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MobileSubscriptionDAO {
    private MobileSubscriptionDAO() {}

    //insert
    public static MobileSubscription create(String name, int contractLength,
                                            int nationalMinutes, int networkGB,
                                            int internationalMinutes, boolean hasRoaming) {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO MOBILE_SUBSCRIPTIONS (NAME, PRICE, CONTRACT_LENGTH, " +
                            "NATIONAL_MINUTES, NETWORK_GB, INTERNATIONAL_MINUTES, HAS_ROAMING) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setInt(2, 0);
            statement.setInt(3, contractLength);
            statement.setInt(4, nationalMinutes);
            statement.setInt(5, networkGB);
            statement.setInt(6, internationalMinutes);
            statement.setBoolean(7, hasRoaming);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            int subscriptionID = 0;

            if (rs.next() == true) {
                subscriptionID = rs.getInt(1);
            }

            MobileSubscription mobilesub =  new MobileSubscription(subscriptionID, name, contractLength,
                    nationalMinutes, networkGB, internationalMinutes, hasRoaming);

            mobilesub.setPrice(mobilesub.calculateCost());

            update(mobilesub);
            return mobilesub;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //select where id = x
    public static MobileSubscription findById(int subscriptionID) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "NATIONAL_MINUTES, NETWORK_GB, INTERNATIONAL_MINUTES, HAS_ROAMING " +
                            "FROM MOBILE_SUBSCRIPTIONS WHERE SUBSCRIPTION_ID = ?");
            statement.setInt(1, subscriptionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) return null;

            MobileSubscription mobilesub = new MobileSubscription(
                    resultSet.getInt("SUBSCRIPTION_ID"), resultSet.getString("NAME"),
                    resultSet.getInt("CONTRACT_LENGTH"), resultSet.getInt("NATIONAL_MINUTES"),
                    resultSet.getInt("NETWORK_GB"), resultSet.getInt("INTERNATIONAL_MINUTES"),
                    resultSet.getBoolean("HAS_ROAMING"));

            mobilesub.setPrice(resultSet.getInt("PRICE"));

            return mobilesub;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //select all
    public static List<MobileSubscription> findAll() {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet rs = connection.prepareStatement(
                    "SELECT SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "NATIONAL_MINUTES, NETWORK_GB, INTERNATIONAL_MINUTES, HAS_ROAMING " +
                            "FROM MOBILE_SUBSCRIPTIONS").executeQuery();

            List<MobileSubscription> mobileSubscriptions = new ArrayList<>();
            while (rs.next()) {
                MobileSubscription mobilesub = new MobileSubscription(
                        rs.getInt("SUBSCRIPTION_ID"), rs.getString("NAME"),
                        rs.getInt("CONTRACT_LENGTH"), rs.getInt("NATIONAL_MINUTES"),
                        rs.getInt("NETWORK_GB"), rs.getInt("INTERNATIONAL_MINUTES"),
                        rs.getBoolean("HAS_ROAMING"));

                mobilesub.setPrice(rs.getInt("PRICE"));

                mobileSubscriptions.add(mobilesub);
            }
            return mobileSubscriptions;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //update
    public static void update(MobileSubscription mobileSubscription) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE MOBILE_SUBSCRIPTIONS SET NAME = ?, CONTRACT_LENGTH = ?, PRICE = ?, " +
                            "NATIONAL_MINUTES = ?, NETWORK_GB = ?, INTERNATIONAL_MINUTES = ?, " +
                            "HAS_ROAMING = ? WHERE SUBSCRIPTION_ID = ?");
            statement.setString(1, mobileSubscription.getName());
            statement.setInt(2, mobileSubscription.getContractLength());
            statement.setInt(3, mobileSubscription.getPrice());
            statement.setInt(4, mobileSubscription.getNationalMinutes());
            statement.setInt(5, mobileSubscription.getNetworkGB());
            statement.setInt(6, mobileSubscription.getInternationalMinutes());
            statement.setBoolean(7, mobileSubscription.getHasRoaming());
            statement.setInt(8, mobileSubscription.getSubscriptionID());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //delete
    public static void delete(int subscriptionID) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM MOBILE_SUBSCRIPTIONS WHERE SUBSCRIPTION_ID = ?");
            statement.setInt(1, subscriptionID);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
