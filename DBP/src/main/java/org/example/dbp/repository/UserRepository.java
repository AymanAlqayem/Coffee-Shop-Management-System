package org.example.dbp.repository;

import com.mysql.cj.xdevapi.UpdateResult;
import org.example.dbp.db.DataBase;
import org.example.dbp.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    user = new User(resultSet.getString("name"), resultSet.getString("role"), resultSet.getString("email"),
                            convertFromStringToDate(resultSet.getString("hire_date")), resultSet.getString("phone_number"),
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

    /**
     * convertFromStringToDate method that will convert from String to date.
     * */
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
            java.sql.Date sqlHireDate = new java.sql.Date(user.getHire_date().getTime());
            preparedStatement.setDate(4, sqlHireDate);

            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getPass());
            preparedStatement.setDouble(7, user.getSalary());

            // Execute the update
            preparedStatement.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    /**
     * getCashierId method that will get the cashier id.
     * */
    public static int getCashierId(String cashierName) {
        String query = "SELECT id FROM user WHERE name = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the parameter for the prepared statement
            statement.setString(1, cashierName);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if there is a result and return the ID
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (SQLException e) {
        }
        return -1; // Return -1 if no matching cashier is found
    }
}
