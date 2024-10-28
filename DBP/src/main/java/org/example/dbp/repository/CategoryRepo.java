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

//    public static ArrayList<Category> getCategories() {
//        String query = "select * from category left join item on category_id = category.id";
//
//        try (Connection connection = DataBase.getDBConnection();
//             PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                int categoryId = resultSet.getInt("ID");
//                Category currentCategory = null;
//
//                // Check if the category already exists in the list
//                for (Category category : categories) {
//                    if (category.getCategoryId() == categoryId) {
//                        currentCategory = category;
//                        break;
//                    }
//                }
//
//                // If the category does not exist, create a new one
//                if (currentCategory == null) {
//                    currentCategory = new Category();
//                    currentCategory.setCategoryId(categoryId);
//                    currentCategory.setCategoryName(resultSet.getString("category_name"));
//                    currentCategory.setItems(new ArrayList<>());
//                    categories.add(currentCategory); // Add the new category to the list
//                }
//
//                // Add the current item to the category's item list
//                if (resultSet.getString("item.id") != null) {
//                    Item item = new Item();
//                    item.setItemId(resultSet.getInt("item.id"));
//                    item.setItemName(resultSet.getString("item_name"));
//                    item.setPrice(resultSet.getDouble("price"));
//                    item.setCategoryId(categoryId);
//
//                    currentCategory.getItems().add(item); // Add the item to the current category
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return categories;
//    }


    public static ArrayList<Category> getCategories() {
        // Clear the existing categories list to prevent duplicates
        categories.clear();

        String query = "SELECT * FROM category LEFT JOIN item ON category_id = category.id";

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
                if (resultSet.getString("item.id") != null) {
                    Item item = new Item();
                    item.setItemId(resultSet.getInt("item.id"));
                    item.setItemName(resultSet.getString("item_name"));
                    item.setPrice(resultSet.getDouble("price"));
                    item.setCategoryId(categoryId);

                    currentCategory.getItems().add(item); // Add the item to the current category
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }


    /**
     * addCategory method that will add the new category into DB.
     * */
    public static void addCategory(String categoryName) {
        String query = "insert into category(category_name) values(?)";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, categoryName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * addItem method that will add an item into DB.
     * */

    public static void addItem(Category category, Item item) {
        String query = "insert into item(item_name,price, category_id) values(?,?,?)";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, item.getItemName());
            statement.setDouble(2, item.getPrice());
            statement.setInt(3, item.getCategoryId());

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

            statement.executeUpdate();

        } catch (SQLException e) {
        }
    }

    /**
     * deleteCategory method that will remove the category from the DB.
     * */
//    public static void deleteCategory(String categoryName) {
//        String query = "delete from category where category_name = ?";
//
//        try (Connection connection = DataBase.getDBConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setString(1, categoryName);
//            statement.executeUpdate();
//
//        } catch (SQLException e) {
//
//        }
//
//    }

    public static boolean deleteCategory(String categoryName) {
        String query = "DELETE FROM category WHERE category_name = ?";

        // Validate input
        if (categoryName == null || categoryName.trim().isEmpty()) {
            System.out.println("Invalid category name provided.");
            return false; // Return false for invalid input
        }

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, categoryName);
            int rowsAffected = statement.executeUpdate();

            // Return true if at least one row was affected (deleted)
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
            return false; // Indicate failure
        }
    }



    /**
     * isItemInDatabase method to check if the item already exists in the DB
     * */
    public static boolean isItemInDB(String itemName) {
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

    /**
     * isCategoryInDB method that will check if the category in the DB.
     * */
    public static boolean isCategoryInDB(String categoryName) {
        String query = "SELECT COUNT(category_name) FROM category WHERE category_name = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, categoryName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;// Returns true if count > 0
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
