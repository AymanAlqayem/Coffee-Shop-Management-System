package org.example.dbp.repository;

import org.example.dbp.models.Address;
import org.example.dbp.db.DataBase;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class AddressRepo {

    public static List<Address> getAllAddresses() {
        List<Address> addresses = new ArrayList<>();
        String query = "SELECT * FROM Address";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int addressId = resultSet.getInt("addressId");
                String street = resultSet.getString("street");
                String city = resultSet.getString("city");

                // Create an Address object and add it to the list
                addresses.add(new Address(addressId, street, city));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    // Method to get an address by ID
    public static Address getAddress(int id) {
        Address address = null;
        String query = "SELECT * FROM Address WHERE addressId = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int addressId = resultSet.getInt("addressId");
                    String street = resultSet.getString("street");
                    String city = resultSet.getString("city");

                    // Create an Address object
                    address = new Address(addressId, street, city);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return address;
    }

    // Method to update a row by key
    public static void updateRowByKey(int id, String col, String val) {
        String query = "UPDATE Address SET " + col + " = ? WHERE addressId = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the parameter values
            statement.setString(1, val); // Value to set in the column
            statement.setInt(2, id);     // Unique key to identify the row

            // Execute the update
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
}
