package org.example.dbp.repository;

import org.example.dbp.db.DataBase;
import org.example.dbp.models.Category;
import org.example.dbp.models.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryRepo {

    private static ArrayList<Category> categories = new ArrayList<>();

    public static ArrayList<Category> getCategories() {
//        ArrayList<Category> categories = new ArrayList<>();
//        categories = new ArrayList<>();
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
        System.out.println(categories);
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


    public void addItemToCategory(Item item, Category category) {
        String query = "insert into item(item_name,price,inventory_id, category_id , ) values(?,?,?,?)";
        for (int i = 0; i < category.getItems().size(); i++) {
            if (item.getItemName().equals(category.getItems().get(i).getItemId())) {
                return;
            }
        }

        //Add the item.
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



    public static void main(String[] args) {
        getCategories();
        /////////////////////////////////////////////
        addCategory(new Category("hot drinks"));
        System.out.println("------------------------------------------------------");
        getCategories();
    }
}
