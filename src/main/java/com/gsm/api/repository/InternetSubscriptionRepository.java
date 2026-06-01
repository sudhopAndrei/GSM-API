package com.gsm.api.repository;

import com.gsm.api.model.subscriptions.InternetSubscription;
import com.gsm.api.service.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InternetSubscriptionRepository {
    private InternetSubscriptionRepository() {}

    //insert
    public static InternetSubscription create(String name, int contractLength, int price,
                                              int downloadSpeedMbps, int uploadSpeedMbps,
                                              boolean isFiberOptic, boolean hasRouter) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet seq = connection.prepareStatement("SELECT SEQ_SUBSCRIPTION_ID.NEXTVAL FROM DUAL").executeQuery();
            seq.next();
            int subscriptionID = seq.getInt(1);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO INTERNET_SUBSCRIPTIONS (SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "DOWNLOAD_SPEED_MBPS, UPLOAD_SPEED_MBPS, IS_FIBER_OPTIC, HAS_ROUTER) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, subscriptionID);
            statement.setString(2, name);
            statement.setInt(3, contractLength);
            statement.setInt(4, price);
            statement.setInt(5, downloadSpeedMbps);
            statement.setInt(6, uploadSpeedMbps);
            statement.setBoolean(7, isFiberOptic);
            statement.setBoolean(8, hasRouter);
            statement.executeUpdate();

            return new InternetSubscription(subscriptionID, name, contractLength, price,
                    downloadSpeedMbps, uploadSpeedMbps, isFiberOptic, hasRouter);
        }
    }

    //select where id = x
    public static Optional<InternetSubscription> findById(int subscriptionID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "DOWNLOAD_SPEED_MBPS, UPLOAD_SPEED_MBPS, IS_FIBER_OPTIC, HAS_ROUTER " +
                            "FROM INTERNET_SUBSCRIPTIONS WHERE SUBSCRIPTION_ID = ?");
            statement.setInt(1, subscriptionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) return Optional.empty();

            InternetSubscription internetSubscription = new InternetSubscription(
                    resultSet.getInt("SUBSCRIPTION_ID"), resultSet.getString("NAME"),
                    resultSet.getInt("CONTRACT_LENGTH"), resultSet.getInt("PRICE"),
                    resultSet.getInt("DOWNLOAD_SPEED_MBPS"), resultSet.getInt("UPLOAD_SPEED_MBPS"),
                    resultSet.getBoolean("IS_FIBER_OPTIC"), resultSet.getBoolean("HAS_ROUTER"));

            return Optional.of(internetSubscription);
        }
    }

    //select all
    public static List<InternetSubscription> findAll() throws SQLException {
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
    }

    //update
    public static void update(InternetSubscription internetSubscription) throws SQLException {
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
    }

    //delete
    public static void delete(int subscriptionID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM INTERNET_SUBSCRIPTIONS WHERE SUBSCRIPTION_ID = ?");
            statement.setInt(1, subscriptionID);
            statement.executeUpdate();
        }
    }
}
