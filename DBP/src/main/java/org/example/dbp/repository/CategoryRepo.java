package org.example.dbp.repository;

import org.example.dbp.db.DataBase;
import org.example.dbp.models.Category;
import org.example.dbp.models.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryRepo {

    public static void getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        String categoryQuery = "SELECT * FROM category";
        String itemQuery = "SELECT * FROM item WHERE category_id = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(categoryQuery);
             ResultSet categoryResultSet = statement.executeQuery()) {

            while (categoryResultSet.next()) {
                Category category = new Category();

                category.setCategoryId(categoryResultSet.getInt("id"));
                category.setCategoryName(categoryResultSet.getString("category_name"));

                ArrayList<Item> items = new ArrayList<>();

                try (PreparedStatement itemStatement = connection.prepareStatement(itemQuery)) {
                    itemStatement.setInt(1, category.getCategoryId());

                    try (ResultSet itemResult = itemStatement.executeQuery()) {
                        while (itemResult.next()) {
                            Item item = new Item();

                            item.setItemId(itemResult.getInt("id"));
                            item.setItemName(itemResult.getString("item_name"));
                            item.setPrice(Double.parseDouble(itemResult.getString("price")));
                            item.setInventoryId(itemResult.getInt("inventory_id"));
                            item.setCategoryId(category.getCategoryId());
                            items.add(item);
                        }
                    }
                }
                categories.add(category);
                category.setItems(items);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static void getCategories2() {
        ArrayList<Category> categories = new ArrayList<>();
        String query = "select * from category inner join item on category_id = category.id";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            Category currentCategory = null;
            int lastCategoryId = -1;

            while (resultSet.next()) {
                int categoryId = resultSet.getInt("ID");

                if (categoryId != lastCategoryId) {
                    currentCategory = new Category();

                    currentCategory.setCategoryId(categoryId);
                    currentCategory.setCategoryName(resultSet.getString("category_name"));
                    currentCategory.setItems(new ArrayList<>());
                    categories.add(currentCategory); // Add the category to the list
                    lastCategoryId = categoryId; // Update the last seen category ID
                }

                // Add the current item to the category's item list
                Item item = new Item();
                item.setItemId(resultSet.getInt("id"));
                item.setItemName(resultSet.getString("item_name"));
                item.setPrice(resultSet.getDouble("price"));
                item.setInventoryId(resultSet.getInt("inventory_id"));
                item.setCategoryId(categoryId);

                currentCategory.getItems().add(item); // Add the item to the category's list
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(categories);
    }


    public static void main(String[] args) {
        getCategories2();
    }

//    public List<Category> getCategoriesWithItems() throws SQLException {
//        List<Category> categories = new ArrayList<>();
//        String query = "SELECT c.id AS category_id, c.name AS category_name, i.id AS item_id, i.name AS item_name, i.price AS item_price " +
//                "FROM category c LEFT JOIN item i ON c.id = i.category_id";
//
//        try (Connection conn = connect();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//
//            Category currentCategory = null;
//
//            while (rs.next()) {
//                int categoryId = rs.getInt("category_id");
//                String categoryName = rs.getString("category_name");
//
//                // Check if a new category needs to be created
//                if (currentCategory == null || currentCategory.getId() != categoryId) {
//                    currentCategory = new Category(categoryId, categoryName, new ArrayList<>());
//                    categories.add(currentCategory);
//                }
//
//                // Add the item to the current category (if it exists)
//                if (rs.getInt("item_id") != 0) {
//                    Item item = new Item(rs.getInt("item_id"), rs.getString("item_name"), rs.getDouble("item_price"));
//                    currentCategory.getItems().add(item);
//                }
//            }
//        }
//        return categories;
//    }
}
