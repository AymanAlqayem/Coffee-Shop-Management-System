package org.example.dbp.repository;

import java.util.ArrayList;
import java.util.List;
import org.example.dbp.db.DataBase;
import org.example.dbp.models.Address;
import org.example.dbp.models.Vendor;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendorRepo {

    public static List<String> getAllVendorName() {
        List<String> vendorNames = new ArrayList<>();
        String query = "SELECT vendorName FROM Vendor";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String vendorName = resultSet.getString("vendorName");
                vendorNames.add(vendorName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendorNames;
    }


    public static Vendor searchById(int vendorId) {
        Vendor vendor = null;
        String query = "SELECT * FROM Vendor WHERE vendorId = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, vendorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("vendorId");
                    String name = resultSet.getString("vendorName");
                    int addressId = resultSet.getInt("addressId");

                    Address address = AddressRepo.getAddress(addressId);


                    vendor = new Vendor(id,name ,address);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendor;
    }

    public static Vendor searchByName(String vendorName) {
        Vendor vendor = null;
        String query = "SELECT * FROM Vendor WHERE vendorName = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, vendorName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("vendorId");
                    String name = resultSet.getString("vendorName");
                    int addressId = resultSet.getInt("addressId");

                    // Fetch the address using the AddressRepo
                    Address address = AddressRepo.getAddress(addressId);

                    vendor = new Vendor(id, name, address);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendor;
    }



}


