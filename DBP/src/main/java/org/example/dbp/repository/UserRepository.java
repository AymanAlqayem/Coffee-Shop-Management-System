package org.example.dbp.repository;

import org.example.dbp.db.DataBase;
import org.example.dbp.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public static User getUserByUsernameAndPassword(String username, String password) {
        User user = null;
        String query = "SELECT * FROM user_table WHERE user_name = ? AND pass = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(resultSet.getInt("id"), resultSet.getString("user_name"), resultSet.getString("user_role"), resultSet.getString("hire_date"),
                            resultSet.getString("email"), resultSet.getString("salary"), resultSet.getString("pass"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static String getUserRole(String username, String password) {
        String query = "SELECT * FROM user_table WHERE user_name = ? AND pass = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("user_role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
