package org.example.dbp.repository;

import org.example.dbp.db.DataBase;
import org.example.dbp.models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository {

    public static void addNewCustomer(Customer customer) {
        String query = "INSERT INTO customer (customer_name,phone_number) VALUES (?, ?)";
        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getCustomerName());
            statement.setLong(2, customer.getCustomerPhone());
            statement.executeUpdate();

        } catch (SQLException e) {

        }
    }

    public  static boolean isCustomerExist(String phoneNumber) {
        String query = "SELECT * FROM customer WHERE phone_number=?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, phoneNumber);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;// Returns true if count > 0
            }

        } catch (SQLException e) {

        }
        return false;
    }
}
