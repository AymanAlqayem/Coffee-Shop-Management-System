package org.example.dbp.repository;

import org.example.dbp.db.DataBase;
import org.example.dbp.models.Category;
import org.example.dbp.models.Item;

import javax.imageio.ImageIO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryRepo {

    private static ArrayList<Category> categories = new ArrayList<>();

    /**
     * getCategories method that will get all categories with its items from the DB.
     * */

    public static ArrayList<Category> getCategories() {
        String query = "select * from category inner join item on category_id = category.id";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int categoryId = resultSet.getInt("ID");
                Category currentCategory = null;

                // Check if the category already exists in the list
                for (Category category : categories) {
                    if (category.getCategoryId() == categoryId) {
                        currentCategory = category;
                        break;
                    }
                }

                // If the category does not exist, create a new one
                if (currentCategory == null) {
                    currentCategory = new Category();
                    currentCategory.setCategoryId(categoryId);
                    currentCategory.setCategoryName(resultSet.getString("category_name"));
                    currentCategory.setItems(new ArrayList<>());
                    categories.add(currentCategory); // Add the new category to the list
                }

                // Add the current item to the category's item list
                Item item = new Item();
                item.setItemId(resultSet.getInt("id"));
                item.setItemName(resultSet.getString("item_name"));
                item.setPrice(resultSet.getDouble("price"));
                item.setInventoryId(resultSet.getInt("inventory_id"));
                item.setCategoryId(categoryId);

                currentCategory.getItems().add(item); // Add the item to the current category
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    //    public static void addCategory(Category category) {
//        String query = "insert into category(category_name) values(?)";
//        try (Connection connection = DataBase.getDBConnection()) {
//            for (int i = 0; i < categories.size(); i++) {
//                if (categories.get(i).getCategoryName().equals(category.getCategoryName())) {
//                    return;
//                }
//            }
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, category.getCategoryName());
//            statement.executeUpdate();
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public static void addCategory(Category category) {
        String query = "INSERT INTO category (category_name) " +
                "SELECT ? WHERE NOT EXISTS (SELECT 1 FROM category WHERE category_name = ?)";

        try (Connection connection = DataBase.getDBConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, category.getCategoryName());
            statement.setString(2, category.getCategoryName());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * addItem method that will add the item into DB.
     * */

    public static void addItem(Category category, Item item) {
        String query = "insert into item(item_name,price,inventory_id, category_id) values(?,?,?,?)";

        try (Connection conn = DataBase.getDBConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, item.getItemName());
            statement.setDouble(2, item.getPrice());
            statement.setInt(3, item.getInventoryId());
            statement.setInt(4, item.getCategoryId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * deleteItem method that will delete the item from DB.
     * */

    public static void deleteItem(String itemName) {
        String query = "delete from item where item_name = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, itemName);


            int rowsAffected = statement.executeUpdate();

            // Optionally, you can check if the deletion was successful
            if (rowsAffected > 0) {
                System.out.println("Item deleted successfully.");
            } else {
                System.out.println("No item found with the specified ID.");
            }

        } catch (SQLException e) {
        }
    }


    /**
     * isItemInDatabase method to check if the item already exists in the DB
     * */
    public static boolean isItemInDatabase(String itemName) {
        String query = "SELECT COUNT(item_name) FROM item WHERE item_name = ?";

        try (Connection conn = DataBase.getDBConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, itemName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Returns true if count > 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
