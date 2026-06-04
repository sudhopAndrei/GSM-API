package com.gsm.api.dao;

import com.gsm.api.model.subscriptions.MobileSubscription;
import com.gsm.api.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MobileSubscriptionDAO {
    private MobileSubscriptionDAO() {}

    //insert
    public static MobileSubscription create(String name, int contractLength, int price,
                                            int nationalMinutes, int networkGB,
                                            int internationalMinutes, boolean hasRoaming) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet seq = connection.prepareStatement("SELECT SEQ_SUBSCRIPTION_ID.NEXTVAL FROM DUAL").executeQuery();
            seq.next();
            int subscriptionID = seq.getInt(1);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO MOBILE_SUBSCRIPTIONS (SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "NATIONAL_MINUTES, NETWORK_GB, INTERNATIONAL_MINUTES, HAS_ROAMING) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, subscriptionID);
            statement.setString(2, name);
            statement.setInt(3, contractLength);
            statement.setInt(4, price);
            statement.setInt(5, nationalMinutes);
            statement.setInt(6, networkGB);
            statement.setInt(7, internationalMinutes);
            statement.setBoolean(8, hasRoaming);
            statement.executeUpdate();

            return new MobileSubscription(subscriptionID, name, contractLength, price,
                    nationalMinutes, networkGB, internationalMinutes, hasRoaming);
        }
    }

    //select where id = x
    public static MobileSubscription findById(int subscriptionID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "NATIONAL_MINUTES, NETWORK_GB, INTERNATIONAL_MINUTES, HAS_ROAMING " +
                            "FROM MOBILE_SUBSCRIPTIONS WHERE SUBSCRIPTION_ID = ?");
            statement.setInt(1, subscriptionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) return null;

            MobileSubscription mobileSubscription = new MobileSubscription(
                    resultSet.getInt("SUBSCRIPTION_ID"), resultSet.getString("NAME"),
                    resultSet.getInt("CONTRACT_LENGTH"), resultSet.getInt("PRICE"),
                    resultSet.getInt("NATIONAL_MINUTES"), resultSet.getInt("NETWORK_GB"),
                    resultSet.getInt("INTERNATIONAL_MINUTES"), resultSet.getBoolean("HAS_ROAMING"));

            return mobileSubscription;
        }
    }

    //select all
    public static List<MobileSubscription> findAll() throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet rs = connection.prepareStatement(
                    "SELECT SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "NATIONAL_MINUTES, NETWORK_GB, INTERNATIONAL_MINUTES, HAS_ROAMING " +
                            "FROM MOBILE_SUBSCRIPTIONS").executeQuery();

            List<MobileSubscription> mobileSubscriptions = new ArrayList<>();
            while (rs.next()) {
                MobileSubscription mobileSubscription = new MobileSubscription(
                        rs.getInt("SUBSCRIPTION_ID"), rs.getString("NAME"),
                        rs.getInt("CONTRACT_LENGTH"), rs.getInt("PRICE"),
                        rs.getInt("NATIONAL_MINUTES"), rs.getInt("NETWORK_GB"),
                        rs.getInt("INTERNATIONAL_MINUTES"), rs.getBoolean("HAS_ROAMING"));
                mobileSubscriptions.add(mobileSubscription);
            }
            return mobileSubscriptions;
        }
    }

    //update
    public static void update(MobileSubscription mobileSubscription) throws SQLException {
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
    }

    //delete
    public static void delete(int subscriptionID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM MOBILE_SUBSCRIPTIONS WHERE SUBSCRIPTION_ID = ?");
            statement.setInt(1, subscriptionID);
            statement.executeUpdate();
        }
    }
}
