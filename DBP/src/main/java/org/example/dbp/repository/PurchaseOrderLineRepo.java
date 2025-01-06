package org.example.dbp.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.dbp.models.Ingredient;
import org.example.dbp.models.PurchaseOrderLine;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.example.dbp.db.DataBase;
import org.example.dbp.models.Unit;
import org.example.dbp.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PurchaseOrderLineRepo {

    public static List<PurchaseOrderLine> getAllPurchaseOrderLines() {
        List<PurchaseOrderLine> purchaseOrderLines = new ArrayList<>();
        String query = "SELECT * FROM PurchaseOrderLine";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int lineId = resultSet.getInt("lineId");
                int purchaseOrderId = resultSet.getInt("purchaseOrderId");
                int ingredientId = resultSet.getInt("ingredientId");
                double quantity = resultSet.getDouble("quantity");
                double costPerUnit = resultSet.getDouble("cost_per_unit");
                Ingredient ingredient = searchById(ingredientId);
                // Create a PurchaseOrderLine object and add it to the list
                purchaseOrderLines.add(new PurchaseOrderLine(lineId, purchaseOrderId,ingredient, quantity, costPerUnit, quantity * costPerUnit));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchaseOrderLines;
    }


    public static Ingredient searchById(int id) {
        Ingredient ingredient = null;
        String query = "SELECT * FROM ingredient WHERE ingredientId = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int ingredientId = resultSet.getInt("ingredientId");
                    String name = resultSet.getString("ingredientName");
                    Unit unit = Unit.valueOf(resultSet.getString("unit"));
                    double quantity = resultSet.getDouble("quantity");


                    // Assuming Ingredient has a matching constructor
                    ingredient = new Ingredient(ingredientId, name, unit, quantity);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredient;
    }


    public static ObservableList<PurchaseOrderLine> getAllPurchaseOrderLinesForPurchaseOrder(int purchaseOrderId) {
        ObservableList<PurchaseOrderLine> purchaseOrderLines = FXCollections.observableArrayList();
        String query = "SELECT * FROM PurchaseOrderLine WHERE purchaseOrderId = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, purchaseOrderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int lineId = resultSet.getInt("lineId");
                    int ingredientId = resultSet.getInt("ingredientId");
                    double quantity = resultSet.getDouble("quantity");
                    double costPerUnit = resultSet.getDouble("cost_per_unit");
                    Ingredient ingredient = searchById(ingredientId);

                    // Create a PurchaseOrderLine object and add it to the list
                    purchaseOrderLines.add(new PurchaseOrderLine(lineId, ingredient, quantity, costPerUnit, quantity * costPerUnit));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchaseOrderLines;
    }










    public static void updateRowByKey(int id, String col, String val) {
        // PurchaseOrderLineRepo

        String query = "UPDATE PurchaseOrderLine SET " + col + " = ? WHERE lineId = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the parameter values
            statement.setString(1, val); // Value to set in the column
            statement.setInt(2, id);     // Unique key to identify the row

            // Execute the update
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                // Row updated successfully
            } else {
                // No row found with the given key
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PurchaseOrderLine deleteRowByKey(int id) {
        String selectQuery = "SELECT * FROM PurchaseOrderLine WHERE lineId = ?";
        String deleteQuery = "DELETE FROM PurchaseOrderLine WHERE lineId = ?";
        PurchaseOrderLine deletedLine = null;

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {

            // Fetch the purchase order line details before deletion
            selectStatement.setInt(1, id);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    int lineId = resultSet.getInt("lineId");
                    int ingredientId = resultSet.getInt("ingredient");
                    double quantity = resultSet.getDouble("quantity");
                    double costPerUnit = resultSet.getDouble("cost_per_unit");
                    Ingredient ingredient = searchById(ingredientId);

                    // Create the PurchaseOrderLine object to return
                    deletedLine = new PurchaseOrderLine(lineId, ingredient, quantity, costPerUnit, quantity * costPerUnit);
                } else {
                    System.out.println("No purchase order line found with ID " + id);
                    return null;
                }
            }

            // Perform the deletion
            deleteStatement.setInt(1, id);
            int rowsDeleted = deleteStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Purchase order line with ID " + id + " was deleted successfully.");
            } else {
                System.out.println("Failed to delete the purchase order line with ID " + id);
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting the purchase order line.");
            return null;
        }

        return deletedLine;
    }



}
