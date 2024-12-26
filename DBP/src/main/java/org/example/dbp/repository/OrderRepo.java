package org.example.dbp.repository;

import javafx.collections.ObservableList;
import org.example.dbp.db.DataBase;
import org.example.dbp.models.InvoiceItems;

import java.sql.*;


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
                        System.out.println("The generated order ID is " + orderId);
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

                        System.out.println(menuItemId + "....");
                        System.out.println(orderedQuantity + "...");

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
}
