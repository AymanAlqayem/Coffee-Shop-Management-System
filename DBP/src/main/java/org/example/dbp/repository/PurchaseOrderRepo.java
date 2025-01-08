package org.example.dbp.repository;

import javafx.collections.ObservableList;
import org.example.dbp.db.DataBase;
import org.example.dbp.models.PurchaseOrder;
import org.example.dbp.models.PurchaseOrderLine;
import org.example.dbp.models.Vendor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderRepo {

    public static List<PurchaseOrder> getAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        String query = "SELECT * FROM PurchaseOrder";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("purchaseOrderId");
                int vendorId = resultSet.getInt("vendorId");
                ObservableList<PurchaseOrderLine> purchaseOrderLines;
                purchaseOrderLines = PurchaseOrderLineRepo.getAllPurchaseOrderLinesForPurchaseOrder(id);

                Double totalPrice = resultSet.getDouble("totalPrice");
                Date orderDate = resultSet.getDate("orderDate");

                Vendor vendor = VendorRepo.searchById(vendorId); // Assuming VendorRepo has a searchById method

                purchaseOrders.add(new PurchaseOrder(id, vendor, purchaseOrderLines, totalPrice, orderDate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchaseOrders;
    }

    public static List<String> getAllOrderDate() {
        List<String> orderDates = new ArrayList<>();
        String query = "SELECT orderDate FROM PurchaseOrder";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String orderDate = resultSet.getString("orderDate");
                orderDates.add(orderDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderDates;
    }

    public static PurchaseOrder searchById(int id) {
        PurchaseOrder purchaseOrder = null;
        String query = "SELECT * FROM PurchaseOrder WHERE purchaseOrderId = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int vendorId = resultSet.getInt("vendorId");
                    Date orderDate = resultSet.getDate("orderDate");
                    Double totalPrice = resultSet.getDouble("totalPrice");
                    List<PurchaseOrderLine> purchaseOrderLines;
                    purchaseOrderLines = PurchaseOrderLineRepo.getAllPurchaseOrderLinesForPurchaseOrder(id);

                    Vendor vendor = VendorRepo.searchById(vendorId);

                    purchaseOrder = new PurchaseOrder(id, vendor, (ObservableList<PurchaseOrderLine>) purchaseOrderLines, totalPrice, orderDate);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchaseOrder;
    }

    public static void updateRowByKey(int id, String col, String val) {
        String query = "UPDATE PurchaseOrder SET " + col + " = ? WHERE purchaseOrderId = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, val);
            statement.setInt(2, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Row updated successfully.");
            } else {
                System.out.println("No row found with the given key.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PurchaseOrder deleteRowByKey(int id) {
        String selectQuery = "SELECT * FROM PurchaseOrder WHERE purchaseOrderId = ?";
        String deletePurchaseOrderLineQuery = "DELETE FROM PurchaseOrderLine WHERE purchaseOrderId = ?";
        String deleteQuery = "DELETE FROM PurchaseOrder WHERE purchaseOrderId = ?";
        PurchaseOrder deletedOrder = null;

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement deletePurchaseOrderLineStatement = connection.prepareStatement(deletePurchaseOrderLineQuery);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {

            // Fetch the purchase order details before deletion
            selectStatement.setInt(1, id);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    int orderId = resultSet.getInt("purchaseOrderId");
                    int vendorId = resultSet.getInt("vendorId");
                    Date orderDate = resultSet.getDate("orderDate");

                    List<PurchaseOrderLine> purchaseOrderLines;
                    purchaseOrderLines = PurchaseOrderLineRepo.getAllPurchaseOrderLinesForPurchaseOrder(id);

                    Vendor vendor = VendorRepo.searchById(vendorId);

                    // Create the PurchaseOrder object to return
                    deletedOrder = new PurchaseOrder(orderId, vendor, (ObservableList<PurchaseOrderLine>) purchaseOrderLines, orderDate);
                } else {
                    System.out.println("No purchase order found with ID " + id);
                    return null;
                }
            }

            // Delete dependent rows in PurchaseOrderLine
            deletePurchaseOrderLineStatement.setInt(1, id);
            deletePurchaseOrderLineStatement.executeUpdate();

            // Perform the deletion in PurchaseOrder
            deleteStatement.setInt(1, id);
            int rowsDeleted = deleteStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Purchase order with ID " + id + " was deleted successfully.");
            } else {
                System.out.println("Failed to delete the purchase order with ID " + id);
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting the purchase order.");
            return null;
        }

        return deletedOrder;
    }

    public static void addPurchaseOrder(PurchaseOrder purchaseOrder) {
        String query = "INSERT INTO PurchaseOrder(vendorId,totalPrice, orderDate) VALUES (?, ?, ?)";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, purchaseOrder.getVendor().getVenderId());
            preparedStatement.setDouble(2, purchaseOrder.getTotalPrice());

            // Convert PurchaseOrder's order_date to java.sql.Date.
            java.sql.Date sqlOrderDate = new java.sql.Date(purchaseOrder.getOrderDate().getTime());
            preparedStatement.setDate(3, sqlOrderDate);

            // Execute the update
            preparedStatement.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }
}
