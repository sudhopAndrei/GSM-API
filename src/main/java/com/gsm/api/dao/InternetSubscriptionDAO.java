package com.gsm.api.dao;

import com.gsm.api.model.InternetSubscription;
import com.gsm.api.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InternetSubscriptionDAO {
    private InternetSubscriptionDAO() {}

    //insert
    public static InternetSubscription create(String name, int contractLength, int price,
                                              int downloadSpeedMbps, int uploadSpeedMbps,
                                              boolean isFiberOptic, boolean hasRouter) {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO INTERNET_SUBSCRIPTIONS (NAME, CONTRACT_LENGTH, PRICE, " +
                            "DOWNLOAD_SPEED_MBPS, UPLOAD_SPEED_MBPS, IS_FIBER_OPTIC, HAS_ROUTER) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setInt(2, contractLength);
            statement.setInt(3, price);
            statement.setInt(4, downloadSpeedMbps);
            statement.setInt(5, uploadSpeedMbps);
            statement.setBoolean(6, isFiberOptic);
            statement.setBoolean(7, hasRouter);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            int subscriptionID = 0;

            if (rs.next() == true) {
                subscriptionID = rs.getInt(1);
            }

            return new InternetSubscription(subscriptionID, name, contractLength, price,
                    downloadSpeedMbps, uploadSpeedMbps, isFiberOptic, hasRouter);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //select where id = x
    public static InternetSubscription findById(int subscriptionID) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "DOWNLOAD_SPEED_MBPS, UPLOAD_SPEED_MBPS, IS_FIBER_OPTIC, HAS_ROUTER " +
                            "FROM INTERNET_SUBSCRIPTIONS WHERE SUBSCRIPTION_ID = ?");
            statement.setInt(1, subscriptionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) return null;

            InternetSubscription internetSubscription = new InternetSubscription(
                    resultSet.getInt("SUBSCRIPTION_ID"), resultSet.getString("NAME"),
                    resultSet.getInt("CONTRACT_LENGTH"), resultSet.getInt("PRICE"),
                    resultSet.getInt("DOWNLOAD_SPEED_MBPS"), resultSet.getInt("UPLOAD_SPEED_MBPS"),
                    resultSet.getBoolean("IS_FIBER_OPTIC"), resultSet.getBoolean("HAS_ROUTER"));

            return internetSubscription;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //select all
    public static List<InternetSubscription> findAll() {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet rs = connection.prepareStatement(
                    "SELECT SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "DOWNLOAD_SPEED_MBPS, UPLOAD_SPEED_MBPS, IS_FIBER_OPTIC, HAS_ROUTER " +
                            "FROM INTERNET_SUBSCRIPTIONS").executeQuery();

            List<InternetSubscription> internetSubscriptions = new ArrayList<>();
            while (rs.next()) {
                InternetSubscription internetSubscription = new InternetSubscription(
                        rs.getInt("SUBSCRIPTION_ID"), rs.getString("NAME"),
                        rs.getInt("CONTRACT_LENGTH"), rs.getInt("PRICE"),
                        rs.getInt("DOWNLOAD_SPEED_MBPS"), rs.getInt("UPLOAD_SPEED_MBPS"),
                        rs.getBoolean("IS_FIBER_OPTIC"), rs.getBoolean("HAS_ROUTER"));
                internetSubscriptions.add(internetSubscription);
            }
            return internetSubscriptions;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //update
    public static void update(InternetSubscription internetSubscription) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE INTERNET_SUBSCRIPTIONS SET NAME = ?, CONTRACT_LENGTH = ?, PRICE = ?, " +
                            "DOWNLOAD_SPEED_MBPS = ?, UPLOAD_SPEED_MBPS = ?, " +
                            "IS_FIBER_OPTIC = ?, HAS_ROUTER = ? WHERE SUBSCRIPTION_ID = ?");
            statement.setString(1, internetSubscription.getName());
            statement.setInt(2, internetSubscription.getContractLength());
            statement.setInt(3, internetSubscription.getPrice());
            statement.setInt(4, internetSubscription.getDownloadSpeedMbps());
            statement.setInt(5, internetSubscription.getUploadSpeedMbps());
            statement.setBoolean(6, internetSubscription.getIsFiberOptic());
            statement.setBoolean(7, internetSubscription.getHasRouter());
            statement.setInt(8, internetSubscription.getSubscriptionID());
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
                    "DELETE FROM INTERNET_SUBSCRIPTIONS WHERE SUBSCRIPTION_ID = ?");
            statement.setInt(1, subscriptionID);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
