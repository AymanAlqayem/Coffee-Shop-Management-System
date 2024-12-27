package org.example.dbp.repository;

import org.example.dbp.db.DataBase;
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
import java.util.Date;

public class UserRepository {

    /**
     * getUserByUsernameAndPassword method that will get a specific user based the name and password.
     * */
    public static User getUserByUsernameAndPassword(String name, String password) {
        User user = null;
        String query = "SELECT * FROM user WHERE name = ? AND password = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(resultSet.getInt("id"),resultSet.getString("name"), resultSet.getString("role"), resultSet.getString("email"),
                            resultSet.getDate("hire_date"), resultSet.getInt("phone_number"),
                            resultSet.getString("password"), resultSet.getDouble("salary"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * getUserRole method that will get the role for a specific user.
     * */
    public static String getUserRole(String username, String password) {
        String query = "SELECT * FROM user WHERE name = ? AND password = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getUserRole method that will get the role for a specific user.
     * */
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");
                String email = resultSet.getString("email");
                Date hireDate = resultSet.getDate("hire_date");
                long phoneNumber = resultSet.getLong("phone_number");
                String pass = resultSet.getString("password");
                double salary = resultSet.getDouble("salary");
                users.add(new User(id,name, role, email, hireDate, phoneNumber, pass, salary));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    public static void updateRowByKey(int id, String col, String val) {
        String query = "UPDATE user SET " + col + " = ? WHERE id = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the parameter values
            statement.setString(1, val); // Value to set in the column
            statement.setInt(2, id);  // Unique key to identify the row

            // Execute the update
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
           //     System.out.println("Row updated successfully.");
            } else {
            //    System.out.println("No row found with the given key.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static User deleteRowByKey(int id) {
        String selectQuery = "SELECT * FROM user WHERE id = ?";
        String deleteQuery = "DELETE FROM user WHERE id = ?";
        User deletedUser = null;

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {

            // Fetch the user details before deletion
            selectStatement.setInt(1, id);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String role = resultSet.getString("role");
                    String email = resultSet.getString("email");
                    Date hireDate = resultSet.getDate("hire_date");
                    long phoneNumber = resultSet.getLong("phone_number");
                    String password = resultSet.getString("password");
                    double salary = resultSet.getDouble("salary");

                    // Create the User object to return
                    deletedUser = new User(userId, name, role, email, hireDate, phoneNumber, password, salary);
                } else {
                    System.out.println("No user found with ID " + id);
                    return null;
                }
            }

            // Perform the deletion
            deleteStatement.setInt(1, id);
            int rowsDeleted = deleteStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("User with ID " + id + " was deleted successfully.");
            } else {
                System.out.println("Failed to delete the user with ID " + id);
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting the user.");
            return null;
        }

        return deletedUser;
    }



    /**
     * isEmailExist method that will check if the given email already exists in the DB.
     * */
    public static boolean isEmailExist(String email) {
        String query = "SELECT * FROM user WHERE email = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;// Returns true if count > 0
            }

        } catch (SQLException s) {

        }
        return false;
    }

    private static Date convertFromStringToDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat(str);
        try {
            return formatter.parse(str); // Converts the String to Date
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Returns null if the date format is incorrect
        }
    }

    /**
     * addNewEmployee method that will add new employee to DB.
     * */
    public static void addNewEmployee(User user) {
        String query = "insert into user(name , role , email , hire_date , phone_number , password , salary)" +
                " values(?,?,?,?,?,?,?)";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getRole());
            preparedStatement.setString(3, user.getEmail());

            // Convert User's hire_date to java.sql.Date.
            java.sql.Date sqlHireDate = new java.sql.Date(user.getHireDate().getTime());
            preparedStatement.setDate(4, sqlHireDate);

            preparedStatement.setLong(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getPass());
            preparedStatement.setDouble(7, user.getSalary());

            // Execute the update
            preparedStatement.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

}
