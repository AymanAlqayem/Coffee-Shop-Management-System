package org.example.dbp.repository;

import org.example.dbp.db.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItemRepo {
    /**
     * getMenuItemId method that will get the item id or a specific item name.
     * */
    public static int getMenuItemId(String name) {
        String query = "SELECT id FROM menu_item WHERE item_name =?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if there is a result and return the ID
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (SQLException e) {

        }
        return -1;
    }
}
