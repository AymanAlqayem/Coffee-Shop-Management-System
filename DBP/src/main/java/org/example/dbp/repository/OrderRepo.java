package org.example.dbp.repository;

import javafx.collections.ObservableList;
import org.example.dbp.db.DataBase;
import org.example.dbp.models.InvoiceItems;

import java.sql.*;
import java.util.*;


public class OrderRepo {
    /**
     * addOrder method that will add new order to the DB.
     * */
    public static int addOrder(int cashierId, int customerId, double totalAmount, ObservableList<InvoiceItems> itemsObservableList) {
        String insertOrderSQL = "INSERT INTO Order_table (created_date_time, cashier_id, customer_id) VALUES (NOW(), ?, ?)";
        String insertOrderLineSQL = "INSERT INTO Order_Line (order_id, menu_item_id, ordered_quantity) VALUES (?, ?, ?)";
        String insertInvoiceSQL = "INSERT INTO Invoice (created_date_time, amount, cashier_id, order_id) VALUES (NOW(), ?, ?, ?)";

        int orderId = -1; // Initialize orderId

        try (Connection connection = DataBase.getDBConnection()) {
            // Insert into Order_table
            try (PreparedStatement stmtOrder = connection.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmtOrder.setInt(1, cashierId); // Set cashier ID
                stmtOrder.setInt(2, customerId); // Set customer ID

                // Execute the update
                stmtOrder.executeUpdate();

                // Retrieve the generated keys
                try (ResultSet resultSet = stmtOrder.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        orderId = resultSet.getInt(1); // Get the generated order ID
                    }
                }
            }

            // Insert into Order_Line
            if (orderId != -1) { // Proceed only if orderId was generated
                try (PreparedStatement stmtOrderLine = connection.prepareStatement(insertOrderLineSQL)) {
                    // Iterate over the ObservableList of InvoiceItems
                    for (InvoiceItems item : itemsObservableList) {
                        Integer menuItemId = MenuItemRepo.getMenuItemId(item.getItemName()); // Get menu_item_id
                        Integer orderedQuantity = item.getQuantity(); // Get ordered_quantity

                        stmtOrderLine.setInt(1, orderId); // Set order_id
                        stmtOrderLine.setInt(2, menuItemId); // Set menu_item_id
                        stmtOrderLine.setInt(3, orderedQuantity); // Set ordered_quantity
                        stmtOrderLine.addBatch(); // Add to batch
                    }
                    stmtOrderLine.executeBatch(); // Execute the batch insert
                }
            }


            // Insert into InvoiceItems
            if (orderId != -1) { // Proceed only if orderId was generated
                try (PreparedStatement stmtInvoice = connection.prepareStatement(insertInvoiceSQL)) {
                    stmtInvoice.setDouble(1, totalAmount); // Set total amount
                    stmtInvoice.setInt(2, cashierId); // Set cashier ID
                    stmtInvoice.setInt(3, orderId); // Set order ID
                    stmtInvoice.executeUpdate(); // Execute the insert
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions for debugging
        }

        return orderId; // Return the generated order ID or -1 if there was an error
    }

    /**
     * totalNumberOfCustomers method that will get the total number of customers for the current day.
     * */
    public static int totalNumberOfCustomers() {
        String query = "SELECT COUNT(DISTINCT customer_id) " +
                "FROM order_table " +
                "WHERE DATE(created_date_time) = CURDATE()"; // CURDATE() returns the current date

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1); // Return the count of distinct customers
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging
        }

        return 0; // Return 0 if no result is found
    }

    /**
     * numberOfSoldProduct method that will get the total number of unique sold products.
     * */
    public static int numberOfSoldProduct() {
        String query = "SELECT COUNT(DISTINCT menu_item_id)" +
                "FROM menu_item" +
                "         JOIN order_line ON menu_item.id = order_line.menu_item_id" +
                "         JOIN order_table ON order_line.order_id = order_table.id";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

        }
        return -1;


    }

    /**
     * customerPerDate method that will find the total number of customers for each date.
     * */
    public static List<Map.Entry<String, Integer>> customerPerDate() {
        List<Map.Entry<String, Integer>> customerDataList = new ArrayList<>();

        String query = "SELECT DATE(created_date_time) AS order_date, COUNT(DISTINCT customer_id) AS customer_count " +
                "FROM order_table GROUP BY DATE(created_date_time) ORDER BY order_date";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String date = rs.getString("order_date");
                int customerCount = rs.getInt("customer_count");
                customerDataList.add(new AbstractMap.SimpleEntry<>(date, customerCount)); // Add the pair to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerDataList;
    }
}
