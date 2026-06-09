package com.gsm.api.dao;

import com.gsm.api.model.TelevisionSet;
import com.gsm.api.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelevisionSetDAO {
    private TelevisionSetDAO() {}

    //insert
    public static TelevisionSet create(String name, double diagonalInches, String resolution, boolean isSmartTv) {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO TELEVISION_SETS (NAME, DIAGONAL_INCHES, RESOLUTION, IS_SMART_TV) " +
                            "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setDouble(2, diagonalInches);
            statement.setString(3, resolution);
            statement.setBoolean(4, isSmartTv);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            int deviceID = 0;
            if (rs.next() == true) {
                deviceID = rs.getInt(1);
            }

            TelevisionSet tvset = new TelevisionSet(deviceID, name, diagonalInches, resolution, isSmartTv);
            tvset.setPrice(tvset.calculateCost());

            return tvset;
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

            TelevisionSet tvset = new TelevisionSet(
                    resultSet.getInt("DEVICE_ID"), resultSet.getString("NAME"),
                    resultSet.getDouble("DIAGONAL_INCHES"), resultSet.getString("RESOLUTION"), resultSet.getBoolean("IS_SMART_TV"));

            tvset.setPrice(tvset.calculateCost());

            return tvset;
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
                TelevisionSet tvset = new TelevisionSet(
                        rs.getInt("DEVICE_ID"), rs.getString("NAME"),
                        rs.getDouble("DIAGONAL_INCHES"), rs.getString("RESOLUTION"), rs.getBoolean("IS_SMART_TV"));
                televisionSets.add(tvset);

                tvset.setPrice(tvset.calculateCost());
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
