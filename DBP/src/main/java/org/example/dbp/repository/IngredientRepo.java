package org.example.dbp.repository;

import org.example.dbp.db.DataBase;
import org.example.dbp.models.Ingredient;
import org.example.dbp.models.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class IngredientRepo {

    public static List<String> getAllIngredientName() {
        List<String> ingredientNames = new ArrayList<>();
        String query = "SELECT ingredientName FROM ingredient";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String ingredientName = resultSet.getString("ingredientName");
                ingredientNames.add(ingredientName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredientNames;
    }

    public static List<Ingredient> getAllIngredient() {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT ingredientId, ingredientName, unit, quantity FROM ingredient";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ingredientId");
                String name = resultSet.getString("ingredientName");
                Unit unit = Unit.valueOf(resultSet.getString("unit"));
                double quantity = resultSet.getDouble("quantity");

                Ingredient ingredient = new Ingredient(id, name, unit, quantity);
                ingredients.add(ingredient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredients;
    }


    public static void updateRowByKey(int id, String col, String val) {
        // IngredientRepo

        String query = "UPDATE Ingredient SET " + col + " = ? WHERE ingredientId = ?";

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

    public static void updateRowByKeyName(int id, String col, String val) {
        String query = "UPDATE Ingredient SET " + col + " = ? WHERE ingredientId = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) FROM Ingredient WHERE " + col + " = ?");
             PreparedStatement updateStatement = connection.prepareStatement(query)) {

            // Check for duplicate values before updating
            checkStatement.setString(1, val);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Error: Duplicate value for " + col + ". Update aborted.");
                return;
            }

            // Set the parameter values for the update statement
            updateStatement.setString(1, val); // Value to set in the column
            updateStatement.setInt(2, id);     // Unique key to identify the row

            // Execute the update
            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Row updated successfully.");
            } else {
                System.out.println("No row found with the given key.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Ingredient searchByName(String name) {
        Ingredient ingredient = null;
        String query = "SELECT * FROM ingredient WHERE ingredientName = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int ingredientId = resultSet.getInt("ingredientId");
                    String ingredientName = resultSet.getString("ingredientName");
                    Unit unit = Unit.valueOf(resultSet.getString("unit"));
                    double quantity = resultSet.getDouble("quantity");

                    // Assuming Ingredient has a matching constructor
                    ingredient = new Ingredient(ingredientId, ingredientName, unit, quantity);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredient;
    }

    public static void addIngredient(Ingredient ingredient) {
        String query = "INSERT INTO ingredient(ingredientName, unit, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the ingredientName
            preparedStatement.setString(1, ingredient.getIngredientName());

            // Set the unit (enum type), assuming it's stored as a String in the Ingredient class
            preparedStatement.setString(2, ingredient.getUnit().toString());

            // Set the quantity
            preparedStatement.setDouble(3, ingredient.getQuantity());

            // Execute the update
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
