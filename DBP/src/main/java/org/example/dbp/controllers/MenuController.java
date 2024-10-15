package org.example.dbp.controllers;

import com.jfoenix.controls.JFXButton;
import com.sun.jdi.InconsistentDebugInfoException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.dbp.models.Category;
import org.example.dbp.models.Item;
import org.example.dbp.repository.CategoryRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class MenuController extends Application {
    @FXML
    private Accordion menuAccordion;
    @FXML
    private JFXButton btAddCategory;
    @FXML
    private JFXButton btDeleteCategory;


//    @Override
//    public void start(Stage stage) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/Menu.fxml"));
//            Parent root = fxmlLoader.load();
//
//            // Get the controller from the loader (this step ensures FXML elements are linked)
//            MenuController controller = fxmlLoader.getController();
//
//            // Load the menu data AFTER the FXML is loaded and injected
//            controller.loadMenuData();
//
//            // Set the scene and show the stage
//            Scene scene = new Scene(root, 1397, 750);
//            stage.setTitle("Menu");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/Menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 600);
            stage.setTitle("Menu");
            stage.setScene(scene);
            stage.show();
//            loadMenuData();
//            addNewCategory(); // Setup the button action here

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        loadMenuData();  // Load the menu data after FXML components are initialized
        addNewCategory();  // Configure the Add Category button
        deleteCategory();

    }

    public void loadMenuData() {
        // Fetch categories from the repository
        ArrayList<Category> categories = CategoryRepo.getCategories();

        // Iterate over categories
        for (int i = 0; i < categories.size(); i++) {
            TitledPane titledPane = new TitledPane(); // Create a new TitledPane for each category
            titledPane.setText(categories.get(i).getCategoryName());
//            titledPane.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
            titledPane.setStyle("-fx-font-size: 15px");
            VBox vbox = new VBox(10);

            final Category currentCategory = categories.get(i);

            // Iterate over the items in the category
            for (int j = 0; j < currentCategory.getItems().size(); j++) {

                final Item currentItem = currentCategory.getItems().get(j);

                // Create a button for each item with its name and price
                JFXButton btItem = new JFXButton(currentItem.getItemName() + " " + currentItem.getPrice() + " - NIS");
                btItem.setStyle("-fx-background-radius: 90");
                btItem.setFont(Font.font("Times New Roman", 19));
                vbox.getChildren().add(btItem);

                /**
                 * make actions when the item button clicked.
                 * */
                btItem.setOnMouseClicked(event -> {
                    TextInputDialog dialog = new TextInputDialog("1");
                    dialog.setTitle("Item Purchase");
                    dialog.setHeaderText("Enter the number of items to purchase");
                    dialog.setContentText("Quantity:");

                    // Show the dialog and capture the user's input
                    Optional<String> result = dialog.showAndWait();

                    result.ifPresent(quantity -> {
                        try {
                            int qty = Integer.parseInt(quantity); // Parse the input to an integer
                            if (qty > 0) {
                                // Process the quantity entered by the user (e.g., add to bill)
                                System.out.println("User wants to purchase " + qty + " of " + currentItem.getItemName());
                                // Handle logic to add the selected item and quantity to the bill
                            } else {
                                // Show an error alert if the user entered a non-positive number
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Invalid Quantity");
                                alert.setHeaderText(null);
                                alert.setContentText("Please enter a valid quantity greater than 0.");
                                alert.showAndWait();
                            }
                        } catch (NumberFormatException e) {
                            // Show an error alert if the user entered an invalid number
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Invalid Input");
                            alert.setHeaderText(null);
                            alert.setContentText("Please enter a valid number.");
                            alert.showAndWait();
                        }
                    });
                });
            }

            // Add a button to allow adding new items.
            JFXButton btAddNewItem = new JFXButton("Add new Item");
            btAddNewItem.setStyle("-fx-background-radius: 90");
            btAddNewItem.setFont(Font.font("Times New Roman", 19));
            vbox.getChildren().add(btAddNewItem);

            /**
             * make actions to the added new item button.
             * */

            btAddNewItem.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("New Item");
                dialog.setHeaderText("Add New Item");
                dialog.setContentText("Item Name:");

                // Show the dialog and capture the user's input
                Optional<String> result = dialog.showAndWait();

                result.ifPresent(itemName -> {
                    //check if the entered item is not null, and does not exist in the category.

                    if (itemName.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Item Name");
                        alert.setHeaderText(null);
                        alert.setContentText("Item name cannot be empty.");
                        alert.showAndWait();
                    } else if (isItemExist(currentCategory, itemName)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Category Name");
                        alert.setHeaderText(null);
                        alert.setContentText("This Item already exist in this category!.");
                        alert.showAndWait();
                    } else {
                        //Added the new Item.
                        Item newCategory = new Item(itemName);
                        //CategoryRepo.addCategory(newCategory); // Add category to the database
//                        addCategoryToAccordion(newCategory); // Add category to the UI

                    }
                });
            });


            // Wrap the VBox in a ScrollPane to enable scrolling for long lists
            ScrollPane scrollPane = new ScrollPane(vbox);
            scrollPane.setFitToWidth(true); // Make the ScrollPane width match the VBox

            // Set the ScrollPane as the content of the TitledPane
            titledPane.setContent(scrollPane);
            menuAccordion.getPanes().add(titledPane); // Add TitledPane to the Accordion
        }
    }

    public void addNewCategory() {
        /**
         * make actions for added new category button.
         * */

        btAddCategory.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("New Category");
            dialog.setHeaderText("Add New Category");
            dialog.setContentText("Category Name:");

            // Show the dialog and capture the user's input
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(categoryName -> {
                //check if the entered category is not null, and does not exist.

                if (categoryName.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Category Name");
                    alert.setHeaderText(null);
                    alert.setContentText("Category name cannot be empty.");
                    alert.showAndWait();
                } else if (isCategoryExist(categoryName)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Category Name");
                    alert.setHeaderText(null);
                    alert.setContentText("This Category already exist!.");
                    alert.showAndWait();
                } else {
                    Category newCategory = new Category(categoryName);
                    //CategoryRepo.addCategory(newCategory); // Add category to the database
                    addCategoryToAccordion(newCategory); // Add category to the UI
                }
            });
        });
    }

    public boolean isCategoryExist(String categoryName) {

        ArrayList<Category> categories = CategoryRepo.getCategories();

        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryName().equalsIgnoreCase(categoryName.trim())) {
                return true;
            }
        }

        // Check in the Accordion (UI) using a normal loop
        for (int i = 0; i < menuAccordion.getPanes().size(); i++) {
            TitledPane pane = menuAccordion.getPanes().get(i);
            if (pane.getText().equalsIgnoreCase(categoryName)) {
                return true; // Category already exists in the UI
            }
        }

        return false;
    }

    public void addCategoryToAccordion(Category category) {
        TitledPane titledPane = new TitledPane();
        titledPane.setText(category.getCategoryName());
        VBox vbox = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        titledPane.setContent(scrollPane);
        menuAccordion.getPanes().add(titledPane);
    }

    public void deleteCategory() {

        btDeleteCategory.setOnMouseClicked(event -> {
            ArrayList<Category> categories = CategoryRepo.getCategories();

            ArrayList<String> categoryNames = new ArrayList<>();

            for (int i = 0; i < categories.size(); i++) {
                categoryNames.add(categories.get(i).getCategoryName());
            }
            // Show a choice dialog for category selection
            ChoiceDialog<String> dialog = new ChoiceDialog<>(categoryNames.get(0), categoryNames);
            dialog.setTitle("Select Category to Delete");
            dialog.setHeaderText("Choose a category to delete:");
            dialog.setContentText("Categories:");

            // Get the user's selection
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(selectedCategory -> {
                // Confirm deletion with the user
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Category");
                alert.setHeaderText("Are you sure you want to delete '" + selectedCategory + "'?");
                alert.setContentText("This action cannot be undone.");

                Optional<ButtonType> confirmationResult = alert.showAndWait();
                if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
                    // Find and remove the selected category from the UI (Accordion) and database
                    for (int i = 0; i < menuAccordion.getPanes().size(); i++) {
                        TitledPane pane = menuAccordion.getPanes().get(i);
                        if (pane.getText().equalsIgnoreCase(selectedCategory)) {
                            menuAccordion.getPanes().remove(i); // Remove the pane
                            break; // Exit the loop once the category is found and removed
                        }
                    }

                    // Optionally, remove the category from the database
//                    CategoryRepo.deleteCategoryByName(selectedCategory);  // Implement this method in your repo

                    // Optionally print to console or log the deletion
//                    System.out.println("Category '" + selectedCategory + "' has been deleted.");
                }
            });
        });
    }

    public boolean isItemExist(Category category, String itemName) {
        for (int i = 0; i < category.getItems().size(); i++) {
            if (category.getItems().get(i).getItemName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
