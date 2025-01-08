package org.example.dbp.repository;

import org.example.dbp.db.DataBase;
import org.example.dbp.models.PurchaseOrderDetails;
import org.example.dbp.models.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseOrderDetailsRepo {
    public static List<PurchaseOrderDetails> getAllPurchaseOrderDetails() {
        List<PurchaseOrderDetails> purchaseOrderDetails = new ArrayList<>();
        String query = "SELECT * FROM PurchaseOrderDetails";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int purchaseOrderId = resultSet.getInt("purchaseOrderId");
                int vendorId = resultSet.getInt("vendorId");
                double totalPrice = resultSet.getDouble("totalPrice");
                Date orderDate = resultSet.getDate("orderDate");

                int lineId = resultSet.getInt("lineId");
                double lineQuantity = resultSet.getDouble("lineQuantity");
                double cost_per_unit = resultSet.getDouble("lineCostPerUnit");

                int ingredientId = resultSet.getInt("ingredientId");
                String ingredientName = resultSet.getString("ingredientName");
                Unit unit = Unit.valueOf(resultSet.getString("ingredientUnit"));
                double quantity = resultSet.getDouble("ingredientStock");

                purchaseOrderDetails.add(new PurchaseOrderDetails(purchaseOrderId, vendorId, totalPrice, orderDate, lineId,
                        lineQuantity, cost_per_unit, ingredientId, ingredientName, unit, quantity));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchaseOrderDetails;
    }

}
