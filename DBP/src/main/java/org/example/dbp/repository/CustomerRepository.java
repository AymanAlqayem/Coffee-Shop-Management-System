package org.example.dbp.repository;

import org.example.dbp.db.DataBase;
import org.example.dbp.models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerRepository {

    /**
     * addNewCustomer method that will add new customer to the DB.
     * */
    public static void addNewCustomer(Customer customer) {
        String query = "INSERT INTO customer (customer_name,phone_number) VALUES (?, ?)";
        try (Connection connection = DataBase.getDBConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getCustomerName());
            statement.setLong(2, customer.getCustomerPhone());
            statement.executeUpdate();

        } catch (SQLException e) {

        }
    }

    /**
     * isCustomerExist method that will check if the customer already exists.
     * */
    public static boolean isCustomerExist(String phoneNumber) {
        String query = "SELECT * FROM customer WHERE phone_number=?";

        try (Connection connection = DataBase.getDBConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, phoneNumber);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;// Returns true if count > 0
            }

        } catch (SQLException e) {

        }
        return false;
    }

    /**
     * getAllCustomers method that will get all customers.
     * */
    public static ArrayList<String> getAllCustomers() {
        String query = "select customer_name from customer";

        ArrayList<String> customers = new ArrayList<>();
        try (Connection connection = DataBase.getDBConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                customers.add(resultSet.getString("customer_name"));
            }

        } catch (SQLException e) {

        }
        return customers;
    }

    /**
     * getCustomerId method that will get the customer id for a specific customer.
     * */
    public static int getCustomerId(String customerName) {
        String query = "SELECT id FROM customer WHERE customer_name = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the parameter for the prepared statement
            statement.setString(1, customerName);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if there is a result and return the ID
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging
        }

        return -1; // Return -1 if no matching customer is found
    }

}
