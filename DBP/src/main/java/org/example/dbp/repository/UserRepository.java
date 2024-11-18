package org.example.dbp.repository;

import org.example.dbp.db.DataBase;
import org.example.dbp.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                            resultSet.getString("hire_date"), resultSet.getString("phone_number"),
                            resultSet.getString("password"), resultSet.getString("salary"));
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
}
