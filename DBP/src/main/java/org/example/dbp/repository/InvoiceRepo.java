package org.example.dbp.repository;

import javafx.scene.chart.XYChart;
import org.example.dbp.db.DataBase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class InvoiceRepo {

    /**
     * getTotalAmount method that will get the total amount for all orders.
     * */
    public static double getTotalAmount() {
        String query = "SELECT SUM(amount) FROM invoice";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {

        }
        return -1;
    }

    /**
     * getLastDayTotalAmount method that will get the total amount for the last work day.
     * */
    public static double getLastDayTotalAmount() {
        String query = "SELECT SUM(amount) " +
                "FROM invoice " +
                "WHERE DATE(created_date_time) = (" +
                "    SELECT DATE(created_date_time) " +
                "    FROM invoice " +
                "    ORDER BY created_date_time DESC " +
                "    LIMIT 1" +
                ")";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getDouble(1); // Return the sum of the amount
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return 0; // Return 0 if no result is found
    }

    /**
     * amountPerDate method that will get the total amount for each date.
     * */
    public static List<Map.Entry<String, Double>> amountPerDate() {
        List<Map.Entry<String, Double>> customerDataList = new ArrayList<>();
        String query = "SELECT DATE(created_date_time) AS order_date, SUM(amount) AS total_income " +
                "FROM invoice GROUP BY DATE(created_date_time) ORDER BY order_date";
        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String date = rs.getString("order_date");
                double totalIncome = rs.getDouble("total_income");
                customerDataList.add(new AbstractMap.SimpleEntry<>(date, totalIncome)); // Add the pair to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerDataList;
    }
}
