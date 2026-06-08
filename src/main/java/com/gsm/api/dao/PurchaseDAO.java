package com.gsm.api.dao;

import com.gsm.api.interfaces.Billable;
import com.gsm.api.model.Purchase;
import com.gsm.api.db.DatabaseManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {
    private PurchaseDAO() {}

    //pe un codebase mare am fi folosit un mapper de tip <itemID, Billable item>
    //care ar fi incarcat itemele la inceput si mapat dupa dinamic
    private static Billable itemResolver(int itemID, String itemType) {
        if (itemType.equals("MobileSubscription")) {
            return MobileSubscriptionDAO.findById(itemID);
        }
        if (itemType.equals("InternetSubscription")) {
            return InternetSubscriptionDAO.findById(itemID);
        }
        if (itemType.equals("TVSubscription")) {
            return TVSubscriptionDAO.findById(itemID);
        }
        if (itemType.equals("MobilePhone")) {
            return MobilePhoneDAO.findById(itemID);
        }
        if (itemType.equals("TelevisionSet")) {
            return TelevisionSetDAO.findById(itemID);
        }
        return null;
    }

    //insert
    public static Purchase create(int userID, int itemID, String itemType, LocalDate date) {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO PURCHASES (USER_ID, ITEM_ID, ITEM_TYPE, PURCHASE_DATE) " +
                            "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userID);
            statement.setInt(2, itemID);
            statement.setString(3, itemType);
            statement.setDate(4, Date.valueOf(date));
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            int purchaseID = 0;

            if (rs.next() == true) {
                purchaseID = rs.getInt(1);
            }

            Purchase purchase = new Purchase(purchaseID, userID, itemID, itemType, date);
            purchase.setItem(itemResolver(itemID, itemType));
            return purchase;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //select where id = x
    public static Purchase findById(int purchaseID) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT PURCHASE_ID, USER_ID, ITEM_ID, ITEM_TYPE, PURCHASE_DATE " +
                            "FROM PURCHASES WHERE PURCHASE_ID = ?");
            statement.setInt(1, purchaseID);
            ResultSet rs = statement.executeQuery();

            if (rs.next() == false) return null;

            Purchase purchase = new Purchase(
                    rs.getInt("PURCHASE_ID"),
                    rs.getInt("USER_ID"),
                    rs.getInt("ITEM_ID"),
                    rs.getString("ITEM_TYPE"),
                    rs.getDate("PURCHASE_DATE").toLocalDate());
            purchase.setItem(itemResolver(rs.getInt("ITEM_ID"), rs.getString("ITEM_TYPE")));
            return purchase;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //select all purchases for a given customer
    public static List<Purchase> findByUserId(int userID) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT PURCHASE_ID, USER_ID, ITEM_ID, ITEM_TYPE, PURCHASE_DATE " +
                            "FROM PURCHASES WHERE USER_ID = ?");
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();

            List<Purchase> purchases = new ArrayList<>();
            while (rs.next()) {
                Purchase purchase = new Purchase(
                        rs.getInt("PURCHASE_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("ITEM_ID"),
                        rs.getString("ITEM_TYPE"),
                        rs.getDate("PURCHASE_DATE").toLocalDate());
                purchase.setItem(itemResolver(rs.getInt("ITEM_ID"), rs.getString("ITEM_TYPE")));
                purchases.add(purchase);
            }
            return purchases;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //delete
    public static void delete(int purchaseID) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM PURCHASES WHERE PURCHASE_ID = ?");
            statement.setInt(1, purchaseID);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
