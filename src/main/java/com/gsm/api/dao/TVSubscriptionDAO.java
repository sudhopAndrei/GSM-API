package com.gsm.api.dao;

import com.gsm.api.model.TVSubscription;
import com.gsm.api.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TVSubscriptionDAO {
    private TVSubscriptionDAO() {}

    //insert
    public static TVSubscription create(String name, int contractLength,
                                        int numberOfChannels, boolean hasHDChannels,
                                        boolean hasStreamingService) {
        try (Connection connection = DatabaseManager.getConnection()) {
    PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO TV_SUBSCRIPTIONS (NAME, CONTRACT_LENGTH," +
                            "NUMBER_OF_CHANNELS, HAS_HD_CHANNELS, HAS_STREAMING_SERVICE) " +
                            "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setInt(2, contractLength);
            statement.setInt(3, numberOfChannels);
            statement.setBoolean(4, hasHDChannels);
            statement.setBoolean(5, hasStreamingService);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            int subscriptionID = 0;

            if (rs.next() == true) {
                subscriptionID = rs.getInt(1);
            }

            TVSubscription tvsub = new TVSubscription(subscriptionID, name, contractLength,
                    numberOfChannels, hasHDChannels, hasStreamingService);

            tvsub.setPrice(tvsub.calculateCost());

            return tvsub;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //select where id = x
    public static TVSubscription findById(int subscriptionID) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "NUMBER_OF_CHANNELS, HAS_HD_CHANNELS, HAS_STREAMING_SERVICE " +
                            "FROM TV_SUBSCRIPTIONS WHERE SUBSCRIPTION_ID = ?");
            statement.setInt(1, subscriptionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) return null;

            TVSubscription tvsub = new TVSubscription(
                    resultSet.getInt("SUBSCRIPTION_ID"), resultSet.getString("NAME"),
                    resultSet.getInt("CONTRACT_LENGTH"), resultSet.getInt("NUMBER_OF_CHANNELS"),
                    resultSet.getBoolean("HAS_HD_CHANNELS"), resultSet.getBoolean("HAS_STREAMING_SERVICE"));

            tvsub.setPrice(tvsub.calculateCost());

            return tvsub;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //select all
    public static List<TVSubscription> findAll() {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet rs = connection.prepareStatement(
                    "SELECT SUBSCRIPTION_ID, NAME, CONTRACT_LENGTH, PRICE, " +
                            "NUMBER_OF_CHANNELS, HAS_HD_CHANNELS, HAS_STREAMING_SERVICE " +
                            "FROM TV_SUBSCRIPTIONS").executeQuery();

            List<TVSubscription> tvSubscriptions = new ArrayList<>();
            while (rs.next()) {
                TVSubscription tvsub = new TVSubscription(
                        rs.getInt("SUBSCRIPTION_ID"), rs.getString("NAME"),
                        rs.getInt("CONTRACT_LENGTH"), rs.getInt("NUMBER_OF_CHANNELS"),
                        rs.getBoolean("HAS_HD_CHANNELS"), rs.getBoolean("HAS_STREAMING_SERVICE"));

                tvsub.setPrice(tvsub.calculateCost());

                tvSubscriptions.add(tvsub);
            }
            return tvSubscriptions;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //update
    public static void update(TVSubscription tvSubscription) {
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
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //delete
    public static void delete(int subscriptionID) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM TV_SUBSCRIPTIONS WHERE SUBSCRIPTION_ID = ?");
            statement.setInt(1, subscriptionID);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
