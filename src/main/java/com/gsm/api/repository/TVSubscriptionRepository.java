package com.gsm.api.repository;

import com.gsm.api.model.subscriptions.TVSubscription;
import com.gsm.api.service.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TVSubscriptionRepository {
    private TVSubscriptionRepository() {}

    //insert
    public static TVSubscription create(String name, int contractLength, int price,
                                        int numberOfChannels, boolean hasHDChannels,
                                        boolean hasStreamingService) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet seq = connection.prepareStatement("SELECT SEQ_SUBSCRIPTION_ID.NEXTVAL FROM DUAL").executeQuery();
            seq.next();
            int subscriptionID = seq.getInt(1);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO TV_SUBSCRIPTIONS (SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "NUMBER_OF_CHANNELS, HAS_HD_CHANNELS, HAS_STREAMING_SERVICE) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, subscriptionID);
            statement.setString(2, name);
            statement.setInt(3, contractLength);
            statement.setInt(4, price);
            statement.setInt(5, numberOfChannels);
            statement.setBoolean(6, hasHDChannels);
            statement.setBoolean(7, hasStreamingService);
            statement.executeUpdate();

            return new TVSubscription(subscriptionID, name, contractLength, price,
                    numberOfChannels, hasHDChannels, hasStreamingService);
        }
    }

    //select where id = x
    public static Optional<TVSubscription> findById(int subscriptionID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "NUMBER_OF_CHANNELS, HAS_HD_CHANNELS, HAS_STREAMING_SERVICE " +
                            "FROM TV_SUBSCRIPTIONS WHERE SUBSCRIPTION_ID = ?");
            statement.setInt(1, subscriptionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) return Optional.empty();

            TVSubscription tvSubscription = new TVSubscription(
                    resultSet.getInt("SUBSCRIPTION_ID"), resultSet.getString("NAME"),
                    resultSet.getInt("CONTRACT_LENGTH"), resultSet.getInt("PRICE"),
                    resultSet.getInt("NUMBER_OF_CHANNELS"), resultSet.getBoolean("HAS_HD_CHANNELS"),
                    resultSet.getBoolean("HAS_STREAMING_SERVICE"));

            return Optional.of(tvSubscription);
        }
    }

    //select all
    public static List<TVSubscription> findAll() throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet rs = connection.prepareStatement(
                    "SELECT SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "NUMBER_OF_CHANNELS, HAS_HD_CHANNELS, HAS_STREAMING_SERVICE " +
                            "FROM TV_SUBSCRIPTIONS").executeQuery();

            List<TVSubscription> tvSubscriptions = new ArrayList<>();
            while (rs.next()) {
                TVSubscription tvSubscription = new TVSubscription(
                        rs.getInt("SUBSCRIPTION_ID"), rs.getString("NAME"),
                        rs.getInt("CONTRACT_LENGTH"), rs.getInt("PRICE"),
                        rs.getInt("NUMBER_OF_CHANNELS"), rs.getBoolean("HAS_HD_CHANNELS"),
                        rs.getBoolean("HAS_STREAMING_SERVICE"));
                tvSubscriptions.add(tvSubscription);
            }
            return tvSubscriptions;
        }
    }

    //update
    public static void update(TVSubscription tvSubscription) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE TV_SUBSCRIPTIONS SET NAME = ?, CONTRACT_LENGTH = ?, PRICE = ?, " +
                            "NUMBER_OF_CHANNELS = ?, HAS_HD_CHANNELS = ?, " +
                            "HAS_STREAMING_SERVICE = ? WHERE SUBSCRIPTION_ID = ?");
            statement.setString(1, tvSubscription.getName());
            statement.setInt(2, tvSubscription.getContractLength());
            statement.setInt(3, tvSubscription.getPrice());
            statement.setInt(4, tvSubscription.getNumberOfChannels());
            statement.setBoolean(5, tvSubscription.getHasHDChannels());
            statement.setBoolean(6, tvSubscription.getHasStreamingService());
            statement.setInt(7, tvSubscription.getSubscriptionID());
            statement.executeUpdate();
        }
    }

    //delete
    public static void delete(int subscriptionID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM TV_SUBSCRIPTIONS WHERE SUBSCRIPTION_ID = ?");
            statement.setInt(1, subscriptionID);
            statement.executeUpdate();
        }
    }
}
