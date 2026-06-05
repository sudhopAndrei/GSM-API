package com.gsm.api.dao;

import com.gsm.api.model.interfaces.Billable;
import com.gsm.api.model.purchases.Purchase;
import com.gsm.api.db.DatabaseManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {
    private PurchaseDAO() {}

    //insert
    public static Purchase create(int userID, Billable item, LocalDate date) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet seq = connection.prepareStatement("SELECT SEQ_PURCHASE_ID.NEXTVAL FROM DUAL").executeQuery();
            seq.next();
            int purchaseID = seq.getInt(1);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO PURCHASES (PURCHASE_ID, USER_ID, ITEM_ID, ITEM_TYPE, PURCHASE_DATE) " +
                            "VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, purchaseID);
            statement.setInt(2, userID);
            statement.setInt(3, item.getBillableID());
            statement.setString(4, item.getTypeIdentifier());
            statement.setDate(5, Date.valueOf(date));
            statement.executeUpdate();

            return new Purchase(purchaseID, userID, item.getBillableID(), item.getTypeIdentifier(), date);
        }
    }

    //select where id = x
    public static Purchase findById(int purchaseID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT PURCHASE_ID, USER_ID, ITEM_ID, ITEM_TYPE, PURCHASE_DATE " +
                            "FROM PURCHASES WHERE PURCHASE_ID = ?");
            statement.setInt(1, purchaseID);
            ResultSet rs = statement.executeQuery();

            if (rs.next() == false) return null;

            return new Purchase(
                    rs.getInt("PURCHASE_ID"),
                    rs.getInt("USER_ID"),
                    rs.getInt("ITEM_ID"),
                    rs.getString("ITEM_TYPE"),
                    rs.getDate("PURCHASE_DATE").toLocalDate());
        }
    }

    //select all purchases for a given customer
    public static List<Purchase> findByUserId(int userID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT PURCHASE_ID, USER_ID, ITEM_ID, ITEM_TYPE, PURCHASE_DATE " +
                            "FROM PURCHASES WHERE USER_ID = ?");
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();

            List<Purchase> purchases = new ArrayList<>();
            while (rs.next()) {
                purchases.add(new Purchase(
                        rs.getInt("PURCHASE_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("ITEM_ID"),
                        rs.getString("ITEM_TYPE"),
                        rs.getDate("PURCHASE_DATE").toLocalDate()));
            }
            return purchases;
        }
    }

    //delete
    public static void delete(int purchaseID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM PURCHASES WHERE PURCHASE_ID = ?");
            statement.setInt(1, purchaseID);
            statement.executeUpdate();
        }
    }
}
