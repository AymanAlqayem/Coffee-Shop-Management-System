package org.example.dbp.repository;

import org.example.dbp.db.DataBase;
import org.example.dbp.models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    /**
     * addNewCustomer method that will add new customer to the DB.
     * */
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

    /**
     * isCustomerExist method that will check if the customer already exists.
     * */
    public static boolean isCustomerExist(String phoneNumber) {
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

    /**
     * getAllCustomers method that will get all customers.
     * */
    public static ArrayList<String> getAllCustomersA() {
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


    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("customer_name");
                long phoneNumber = resultSet.getLong("phone_number");
                customers.add(new Customer(id, name, phoneNumber));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static Customer deleteRowByKey(int id) {
        String selectQuery = "SELECT * FROM customer WHERE id = ?";
        String deleteQuery = "DELETE FROM customer WHERE id = ?";
        Customer deletedCustomer = null;

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {

            // Fetch the customer details before deletion
            selectStatement.setInt(1, id);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    int customerId = resultSet.getInt("id");
                    String name = resultSet.getString("customerName");
                    long phoneNumber = resultSet.getLong("phone_number");
                    // Create the customer object to return
                    deletedCustomer = new Customer(customerId, name, phoneNumber);
                } else {
                    System.out.println("No customer found with ID " + id);
                    return null;
                }
            }

            // Perform the deletion
            deleteStatement.setInt(1, id);
            int rowsDeleted = deleteStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("customer with ID " + id + " was deleted successfully.");
            } else {
                System.out.println("Failed to delete the customer with ID " + id);
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting the customer.");
            return null;
        }

        return deletedCustomer;
    }

    public static void updateRowByKey(int id, String col, String val) {
        String query = "UPDATE Customer SET " + col + " = ? WHERE id = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            //  System.out.println("Executing query: " + query);
            //  System.out.println("Parameters: id = " + id + ", col = " + col + ", val = " + val);

            // Set the parameter values
            statement.setString(1, val);
            statement.setInt(2, id);

            // Execute the update
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                //     System.out.println("Row updated successfully.");
            } else {
                //   System.out.println("No row found with the given key.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}





