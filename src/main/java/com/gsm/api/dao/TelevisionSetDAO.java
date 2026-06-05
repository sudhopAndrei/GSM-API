package com.gsm.api.dao;

import com.gsm.api.model.devices.TelevisionSet;
import com.gsm.api.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelevisionSetDAO {
    private TelevisionSetDAO() {}

    //insert
    public static TelevisionSet create(String name, int price,
                                       double diagonalInches, String resolution, boolean isSmartTv) {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet seq = connection.prepareStatement("SELECT SEQ_DEVICE_ID.NEXTVAL FROM DUAL").executeQuery();
            seq.next();
            int deviceID = seq.getInt(1);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO TELEVISION_SETS (DEVICE_ID, NAME, PRICE, DIAGONAL_INCHES, RESOLUTION, IS_SMART_TV) " +
                            "VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, deviceID);
            statement.setString(2, name);
            statement.setInt(3, price);
            statement.setDouble(4, diagonalInches);
            statement.setString(5, resolution);
            statement.setBoolean(6, isSmartTv);
            statement.executeUpdate();

            return new TelevisionSet(deviceID, name, price, diagonalInches, resolution, isSmartTv);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //select where id = x
    public static TelevisionSet findById(int deviceID) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT DEVICE_ID, NAME, PRICE, DIAGONAL_INCHES, RESOLUTION, IS_SMART_TV " +
                            "FROM TELEVISION_SETS WHERE DEVICE_ID = ?");
            statement.setInt(1, deviceID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) return null;

            TelevisionSet televisionSet = new TelevisionSet(
                    resultSet.getInt("DEVICE_ID"), resultSet.getString("NAME"), resultSet.getInt("PRICE"),
                    resultSet.getDouble("DIAGONAL_INCHES"), resultSet.getString("RESOLUTION"), resultSet.getBoolean("IS_SMART_TV"));

            return televisionSet;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //select all
    public static List<TelevisionSet> findAll() {
        try (Connection connection = DatabaseManager.getConnection()) {

            ResultSet rs = connection.prepareStatement(
                    "SELECT DEVICE_ID, NAME, PRICE, DIAGONAL_INCHES, RESOLUTION, IS_SMART_TV " +
                            "FROM TELEVISION_SETS").executeQuery();

            List<TelevisionSet> televisionSets = new ArrayList<>();
            while (rs.next()) {
                TelevisionSet televisionSet = new TelevisionSet(
                        rs.getInt("DEVICE_ID"), rs.getString("NAME"), rs.getInt("PRICE"),
                        rs.getDouble("DIAGONAL_INCHES"), rs.getString("RESOLUTION"), rs.getBoolean("IS_SMART_TV"));
                televisionSets.add(televisionSet);
            }
            return televisionSets;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //update
    public static void update(TelevisionSet televisionSet) {
        try (Connection connection = DatabaseManager.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE TELEVISION_SETS SET NAME = ?, PRICE = ?, DIAGONAL_INCHES = ?, " +
                            "RESOLUTION = ?, IS_SMART_TV = ? WHERE DEVICE_ID = ?");
            statement.setString(1, televisionSet.getName());
            statement.setInt(2, televisionSet.getPrice());
            statement.setDouble(3, televisionSet.getDiagonalInches());
            statement.setString(4, televisionSet.getResolution());
            statement.setBoolean(5, televisionSet.getIsSmartTv());
            statement.setInt(6, televisionSet.getDeviceID());
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
                    "DELETE FROM TELEVISION_SETS WHERE DEVICE_ID = ?");
            statement.setInt(1, deviceID);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
