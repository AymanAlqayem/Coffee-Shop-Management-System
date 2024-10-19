package org.example.dbp.controllers;

import com.jfoenix.controls.JFXButton;
import com.sun.jdi.InconsistentDebugInfoException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
            TitledPane titledPane = new TitledPane(); // Create a new TitledPane for each category.
            titledPane.setText(categories.get(i).getCategoryName());
            titledPane.setStyle("-fx-font-size: 15px");

            VBox vbox = new VBox(10);// VBox to store items.

            final Category currentCategory = categories.get(i);
            int invId = currentCategory.getItems().isEmpty() ? 0 : currentCategory.getItems().get(0).getInventoryId();

            // Iterate over the items in the current category.
            for (int j = 0; j < currentCategory.getItems().size(); j++) {

                final Item currentItem = currentCategory.getItems().get(j);
                invId = currentItem.getInventoryId();

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
//            btAddNewItem.setStyle("-fx-background-radius: 90");
            btAddNewItem.setStyle("-fx-background-radius: 90; -fx-font-weight: bold;");
            btAddNewItem.setFont(Font.font("Times New Roman", 19));

            // Add a button to allow delete an existing items.
            JFXButton btDeleteItem = new JFXButton("Delete an item");
            btDeleteItem.setStyle("-fx-background-radius: 90; -fx-font-weight: bold;");
            btDeleteItem.setFont(Font.font("Times New Roman", 19));
            vbox.getChildren().addAll(btAddNewItem, btDeleteItem);

            /**
             * make actions to the added new item button.
             * */
            final int finalInvId = invId;
            btAddNewItem.setOnAction(e -> {
                addItem(currentCategory, finalInvId);
            });

            /**
             * make actions for delete button.
             * */

            btDeleteItem.setOnAction(e -> {
                deleteItem(currentCategory);
            });

            // Wrap the VBox in a ScrollPane to enable scrolling for long lists
            ScrollPane scrollPane = new ScrollPane(vbox);
            scrollPane.setFitToWidth(true); // Make the ScrollPane width match the VBox

            // Set the ScrollPane as the content of the TitledPane
            titledPane.setContent(scrollPane);
            menuAccordion.getPanes().add(titledPane); // Add TitledPane to the Accordion
        }
    }

    public void deleteItem(Category category) {
        ObservableList<String> itemNames = FXCollections.observableArrayList();

        for (int i = 0; i < category.getItems().size(); i++) {
            itemNames.add(category.getItems().get(i).getItemName());
        }

        // Show a dialog to select an item to delete
        ChoiceDialog<String> dialog = new ChoiceDialog<>(itemNames.get(0), itemNames);
        dialog.setTitle("Delete Item");
        dialog.setHeaderText("Select an item to delete");
        dialog.setContentText("Item:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(itemNameToDelete -> {
            // Find the corresponding item in the category
            Item itemToDelete = null;
            for (Item item : category.getItems()) {
                if (item.getItemName().equals(itemNameToDelete)) {
                    itemToDelete = item;
                    break;
                }
            }

            // Show confirmation alert
            if (itemToDelete != null) {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Deletion");
                confirmAlert.setHeaderText("Are you sure you want to delete this item?");
                confirmAlert.setContentText("Item: " + itemToDelete.getItemName());

                Optional<ButtonType> confirmationResult = confirmAlert.showAndWait();
                if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
                    deleteItemFromCategory(category, itemToDelete);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Item Deleted");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Item '" + itemToDelete.getItemName() + "' deleted successfully.");
                    successAlert.showAndWait();
                }
            }
        });
    }


    public void deleteItemFromCategory(Category category, Item itemToDelete) {
        // Iterate over the panes in the accordion using a normal for loop
        for (int i = 0; i < menuAccordion.getPanes().size(); i++) {
            TitledPane titledPane = menuAccordion.getPanes().get(i);
            if (titledPane.getText().equals(category.getCategoryName())) {
                // Get the ScrollPane that holds the VBox
                ScrollPane scrollPane = (ScrollPane) titledPane.getContent();
                VBox vbox;

                // Check if the ScrollPane's content is already a VBox
                if (scrollPane.getContent() instanceof VBox) {
                    vbox = (VBox) scrollPane.getContent(); // Retrieve the existing VBox

                    // Use a for loop to remove the button corresponding to the item to delete
                    for (int j = 0; j < vbox.getChildren().size(); j++) {
                        Node node = vbox.getChildren().get(j);

                        if (node instanceof JFXButton) {
                            JFXButton button = (JFXButton) node;

                            // Check if the button text contains the item name to delete
                            if (button.getText().contains(itemToDelete.getItemName())) {
                                // Remove the button from the VBox
                                vbox.getChildren().remove(j);
                                // Adjust the index to avoid skipping the next item
                                j--;
                            }
                        }
                    }

                    // After removing the item from the UI, delete it from the database as well
//                    CategoryRepo.deleteItemFromCategory(category, itemToDelete);
                }
                break; // Break after finding the category to avoid unnecessary iterations
            }
        }
    }


    public void addItem(Category category, int inv_id) {
        // First dialog to capture the item name
        TextInputDialog itemDialog = new TextInputDialog("");
        itemDialog.setTitle("New Item");
        itemDialog.setHeaderText("Add New Item");
        itemDialog.setContentText("Item Name:");

        // Show the item name dialog and capture the user's input
        Optional<String> itemNameResult = itemDialog.showAndWait();

        itemNameResult.ifPresent(itemName -> {
            // Check if the entered item name is not null or empty, and does not already exist in the category
            if (itemName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Item Name");
                alert.setHeaderText(null);
                alert.setContentText("Item name cannot be empty.");
                alert.showAndWait();
            } else if (CategoryRepo.isItemInDatabase(itemName)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Item Exists");
                alert.setHeaderText(null);
                alert.setContentText("This item already exists in this category!");
                alert.showAndWait();
            } else {
                // Second dialog to capture the price
                TextInputDialog priceDialog = new TextInputDialog("");
                priceDialog.setTitle("New Item Price");
                priceDialog.setHeaderText("Add Item Price");
                priceDialog.setContentText("Item Price:");

                // Show the price dialog and capture the user's input
                Optional<String> priceResult = priceDialog.showAndWait();

                priceResult.ifPresent(priceStr -> {
                    try {
                        double price = Double.parseDouble(priceStr);

                        // Create a new item
                        Item newItem = new Item(itemName, price, inv_id, category.getCategoryId());

                        // Add item to the database
                        CategoryRepo.addItemToCategory(category, newItem);

                        // Add the item to the UI
                        addItemToCategoryDisplay(category, newItem);

                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Item Added");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("New item '" + itemName + "' with price " + price + " added successfully.");
                        successAlert.showAndWait();
                    } catch (NumberFormatException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Price");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter a valid numeric value for the price.");
                        alert.showAndWait();
                    }
                });
            }
        });
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

    public void addItemToCategoryDisplay(Category category, Item newItem) {
        // Iterate over the panes in the accordion using a normal for loop
        for (int i = 0; i < menuAccordion.getPanes().size(); i++) {
            TitledPane titledPane = menuAccordion.getPanes().get(i);
            if (titledPane.getText().equals(category.getCategoryName())) {
                // Get the ScrollPane that holds the VBox
                ScrollPane scrollPane = (ScrollPane) titledPane.getContent();
                VBox vbox;

                // Check if the ScrollPane's content is already a VBox
                if (scrollPane.getContent() instanceof VBox) {
                    vbox = (VBox) scrollPane.getContent(); // Retrieve the existing VBox
                } else {
                    // Create a new VBox and set it as the content of the ScrollPane
                    vbox = new VBox(10); // Adjust spacing as needed
                    scrollPane.setContent(vbox);
                }

                // Create a button for the new item with its name and price
                JFXButton newItemButton = new JFXButton(newItem.getItemName() + " " + newItem.getPrice() + " - NIS");
                newItemButton.setStyle("-fx-background-radius: 90");
                newItemButton.setFont(Font.font("Times New Roman", 19));

                // Add action for the new item button
                newItemButton.setOnMouseClicked(event -> {
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
                                System.out.println("User wants to purchase " + qty + " of " + newItem.getItemName());
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

                // Add the new item button at the top of the VBox
                vbox.getChildren().add(0, newItemButton); // Add new item button at the top

                // Break after adding the item to avoid unnecessary iterations
                break;
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
