package org.example.dbp.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.dbp.models.Category;
import org.example.dbp.models.MenuItem;
import org.example.dbp.repository.CategoryRepo;

import java.util.ArrayList;
import java.util.Optional;

public class AdminMenuController {
    @FXML
    private Accordion menuAccordion;
    @FXML
    private JFXButton btAddCategory;
    @FXML
    private JFXButton btDeleteCategory;

    @FXML
    public void initialize() {
        loadMenuData();  // Load the menu data after FXML components are initialized
        addNewCategory();
    }

    /**
     * loadMenuData method that will load all categories and its items.
     * */

    public void loadMenuData() {
        menuAccordion.getPanes().clear();
        ArrayList<Category> categories = CategoryRepo.getCategories();  // Fetch categories from the repository.

        // Iterate over categories.
        for (int i = 0; i < categories.size(); i++) {
            TitledPane titledPane = new TitledPane(); // Create a new TitledPane for each category.
            titledPane.setText(categories.get(i).getCategoryName());
            titledPane.setStyle("-fx-font-size: 15px");

            VBox base = new VBox(2);// base VBox that contains HBoxes(of item info VBox and trash VBox)and another VBox(contains add new item VBox).

            base.setPadding(new Insets(4, 4, 4, 4));
            base.setStyle("-fx-background-color: Black;");

            final Category currentCategory = categories.get(i);

            /**
             *  Iterate over the items in the current category.
             * */

            for (int j = 0; j < currentCategory.getItems().size(); j++) {

                final MenuItem currentItem = currentCategory.getItems().get(j);

                HBox hBox = new HBox(10);// hold item info VBox and trash VBox.
                hBox.setAlignment(Pos.CENTER_LEFT);

                VBox itemInfoVBox = new VBox(10);
                itemInfoVBox.setAlignment(Pos.TOP_LEFT);
                itemInfoVBox.setPrefWidth(250);
                itemInfoVBox.setPadding(new Insets(10, 10, 10, 10));

                Label lbItemName = new Label(currentItem.getItemName());
                Label lbItmePrice = new Label(currentItem.getPrice() + " NIS");

                lbItemName.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20px; -fx-text-fill: #090808;");
                lbItmePrice.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 16px; -fx-text-fill: #e85c0d;");


                itemInfoVBox.getChildren().addAll(lbItemName, lbItmePrice);

                itemInfoVBox.setStyle("-fx-background-color: #f0f0f0;");
                itemInfoVBox.setPadding(new Insets(10, 10, 10, 10));

                Image trashImage = new Image("C:\\Users\\abdal\\Downloads\\DBProject\\DBP\\src\\main\\resources\\trash.png");

                ImageView trashImageView = new ImageView(trashImage);


                VBox trashVBox = new VBox();
                JFXButton btTrash = new JFXButton();
                btTrash.setStyle("-fx-background-radius: 90;");

                btTrash.setGraphic(trashImageView);

                trashVBox.getChildren().add(btTrash);

                trashVBox.setAlignment(Pos.CENTER_LEFT);

                hBox.getChildren().addAll(itemInfoVBox, trashVBox);
                hBox.setStyle("-fx-background-color: #f0f0f0;");

                base.getChildren().add(hBox);

                /**
                 * make actions for trash button that will delete the item.
                 * */

                btTrash.setOnMouseClicked(e -> {
                    makeActionsToTrashButton(currentItem);
                });
            }

            VBox addNewItemVBox = new VBox(2);
            addNewItemVBox.setStyle("-fx-background-color: #f0f0f0;");


            // Add a button to allow adding new items.
            JFXButton btAddNewItem = new JFXButton("Add new Item");
            btAddNewItem.setStyle("-fx-font-family: 'Times New Roman';-fx-background-radius: 90 ; -fx-font-size: 21 ;-fx-font-weight: bold ");
            addNewItemVBox.getChildren().add(btAddNewItem);


            base.getChildren().add(addNewItemVBox);


            /**
             * make actions to the added new item button.
             * */
            btAddNewItem.setOnAction(e -> {
                addNewItem(currentCategory);
            });

            /**
             * make actions to delete category button.
             * */
            btDeleteCategory.setOnAction(e -> {
                deleteCategory();
            });

            // Wrap the VBox in a ScrollPane to enable scrolling for long lists
            ScrollPane scrollPane = new ScrollPane(base);
            scrollPane.setFitToWidth(true); // Make the ScrollPane width match the VBox

            // Set the ScrollPane as the content of the TitledPane
            titledPane.setContent(scrollPane);
            menuAccordion.getPanes().add(titledPane); // Add TitledPane to the Accordion
        }
    }

    /**
     * makeActionsToTrashButton method that will delete a specific item.
     * */
    public void makeActionsToTrashButton(MenuItem itemToDelete) {
        if (showConfirmationAlert("Confirm Delete", "Are you sure you want to delete this item?")) {
            CategoryRepo.deleteItem(itemToDelete.getItemName());
            loadMenuData();
        }
    }

    /**
     * addNewItem method that will add the new item into both DB.
     * */
    public void addNewItem(Category category) {
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
                showErrorAlert("Invalid Item Name", "Item name cannot be empty.");
            } else if (CategoryRepo.isItemInDB(itemName)) {
                showErrorAlert("Item Exists", "This item already exists in this category!");
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
                        MenuItem newItem = new MenuItem(itemName, price, category.getCategoryId());

                        // Add item to the database
                        CategoryRepo.addItem(newItem);

                        // Reload Items.
                        loadMenuData();

                        successAlert("Item Added", "New item '" + itemName + "' with price " + price + " added successfully.");

                    } catch (NumberFormatException ex) {
                        showErrorAlert("Invalid Price", "Please enter a valid numeric value for the price.");
                    }
                });
            }
        });
    }

    /**
     * addNewCategory method that will add the new category into DB.
     * */

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
                    showErrorAlert("Invalid Category Name", "Category name cannot be empty.");
                } else if (CategoryRepo.isCategoryInDB(categoryName)) {
                    showErrorAlert("Invalid Category Name", "This Category already exist!.");
                } else {
                    //Add the new category to DB
                    CategoryRepo.addCategory(categoryName);
                    loadMenuData();
                }
            });
        });
    }

    /**
     * deleteCategory method that will delete a specific category.
     * */

    public void deleteCategory() {

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
                //remove the category from the DB.
                CategoryRepo.deleteCategory(selectedCategory);
                loadMenuData();
            }
        });
    }

    /**
     * showErrorAlert method that will show an error alert due to entered input.
     * */
    public void showErrorAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(context);
        alert.showAndWait();
    }

    /**
     * showConfirmationAlert that will show a confirmation alert to confirm an operation.
     * */

    public boolean showConfirmationAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(context);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * successAlert method that will show a success alert that the operation done successfully.
     * */
    public void successAlert(String title, String context) {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle(title);
        successAlert.setHeaderText(null);
        successAlert.setContentText(context);
        successAlert.showAndWait();

    }
}
