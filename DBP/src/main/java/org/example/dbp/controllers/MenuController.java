package org.example.dbp.controllers;

import com.jfoenix.controls.JFXButton;
import com.sun.jdi.InconsistentDebugInfoException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        loadMenuData();  // Load the menu data after FXML components are initialized
        addNewCategory();  // Configure the Add Category button
//        deleteCategory();

    }

    /**
     * loadMenuData method that will load all categories and items.
     * */

    public void loadMenuData() {
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
            int invId = currentCategory.getItems().isEmpty() ? 0 : currentCategory.getItems().get(0).getInventoryId();

            /**
             *  Iterate over the items in the current category.
             * */
            for (int j = 0; j < currentCategory.getItems().size(); j++) {

                final Item currentItem = currentCategory.getItems().get(j);

                invId = currentItem.getInventoryId();

                HBox hBox = new HBox(10);// hold item info VBox and trash VBox.
                hBox.setAlignment(Pos.CENTER_LEFT);

                VBox itemInfoVBox = new VBox(10);
                itemInfoVBox.setAlignment(Pos.TOP_LEFT);
                itemInfoVBox.setPrefWidth(250);
                itemInfoVBox.setPadding(new Insets(10, 10, 10, 10));

                Label lbItemName = new Label(currentItem.getItemName());
                Label lbItmePrice = new Label(currentItem.getPrice() + " NIS");
                JFXButton btPurchaseItem = new JFXButton();

                lbItemName.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20px; -fx-text-fill: #090808;");
                lbItmePrice.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 16px; -fx-text-fill: #e85c0d;");


                btPurchaseItem.setStyle("-fx-font-family: 'Times New Roman';-fx-background-radius: 90 ; -fx-font-size: 21 ");

                Image image = new Image("C:\\Users\\a-z\\Desktop\\DBProject\\DBP\\src\\main\\resources\\AddForMenu.png");
                ImageView imageView = new ImageView(image);

                btPurchaseItem.setGraphic(imageView);

                itemInfoVBox.getChildren().addAll(lbItemName, lbItmePrice, btPurchaseItem);

                itemInfoVBox.setStyle("-fx-background-color: #f0f0f0;");
                itemInfoVBox.setPadding(new Insets(10, 10, 10, 10));

                Image trashImage = new Image("C:\\Users\\a-z\\Desktop\\DBProject\\DBP\\src\\main\\resources\\trash.png");
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
                 * make actions when the item button clicked.
                 * */
                btPurchaseItem.setOnMouseClicked(e -> {
                    makeActionsToPurchaseButton(currentItem);
                });

                /**
                 * make actions for trash button that will delete the item.
                 * */

                btTrash.setOnMouseClicked(e -> {
                    makeActionsToTrashButton(currentCategory, currentItem);
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
            final int finalInvId = invId;
            btAddNewItem.setOnAction(e -> {
                addNewItem(currentCategory, finalInvId);
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
     * makeActionsToPurchaseButton that will show a text input dialog to enter the quantity of the item.
     * */
    public void makeActionsToPurchaseButton(Item item) {

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
                    System.out.println("User wants to purchase " + qty + " of " + item.getItemName());
                    // Handle logic to add the selected item and quantity to the bill
                } else {
                    // Show an error alert if the user entered a non-positive number.
                    showErrorAlert("Invalid Quantity", "Please enter a valid quantity greater than 0.");
                }
            } catch (NumberFormatException e) {
                // Show an error alert if the user entered an invalid number.
                showErrorAlert("Invalid Input", "Please enter a valid number.");
            }
        });
    }

    /**
     * makeActionsToTrashButton method that will delete a specific item.
     * */
    public void makeActionsToTrashButton(Category category, Item itemToDelete) {
        if (showConfirmationAlert("Confirm Delete", "Are you sure you want to delete this item?")) {
            //delete the item from UI.
            deleteItem(category, itemToDelete);
        }

    }

    /**
     * addNewItem method that will add the new item into both DB and the UI.
     * */
    public void addNewItem(Category category, int inv_id) {
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
                        Item newItem = new Item(itemName, price, inv_id, category.getCategoryId());

                        // Add item to the database
                        CategoryRepo.addItem(category, newItem);

                        // Add the item to the UI
                        addItemToUI(category, newItem);

                        successAlert("Item Added", "New item '" + itemName + "' with price " + price + " added successfully.");

                    } catch (NumberFormatException ex) {
                        showErrorAlert("Invalid Price", "Please enter a valid numeric value for the price.");
                    }
                });
            }
        });
    }


    /**
     * addItemToCategory method that will add the new item into UI.
     * */
    public void addItemToUI(Category category, Item newItem) {
        // Iterate over the panes in the accordion using a normal for loop
        for (int i = 0; i < menuAccordion.getPanes().size(); i++) {
            TitledPane titledPane = menuAccordion.getPanes().get(i);
            if (titledPane.getText().equals(category.getCategoryName())) {
                // Get the ScrollPane that holds the VBox
                ScrollPane scrollPane = (ScrollPane) titledPane.getContent();
                VBox base;

                // Check if the ScrollPane's content is already a VBox
                if (scrollPane.getContent() instanceof VBox) {
                    base = (VBox) scrollPane.getContent(); // Retrieve the existing VBox
                } else {
                    return;
                }

                HBox hbox = new HBox(10);

                VBox itemInfoVBox = new VBox(10);
                itemInfoVBox.setAlignment(Pos.TOP_LEFT);
                itemInfoVBox.setPrefWidth(250);
                itemInfoVBox.setPadding(new Insets(10, 10, 10, 10));

                Label lbItemName = new Label(newItem.getItemName());
                Label lbItmePrice = new Label(newItem.getPrice() + " NIS");
                JFXButton btPurchaseItem = new JFXButton();

                lbItemName.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20px; -fx-text-fill: #090808;");
                lbItmePrice.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 16px; -fx-text-fill: #e85c0d;");


                btPurchaseItem.setStyle("-fx-font-family: 'Times New Roman';-fx-background-radius: 90 ; -fx-font-size: 21 ");

                Image image = new Image("C:\\Users\\a-z\\Desktop\\DBProject\\DBP\\src\\main\\resources\\AddForMenu.png");
                ImageView imageView = new ImageView(image);

                btPurchaseItem.setGraphic(imageView);

                itemInfoVBox.getChildren().addAll(lbItemName, lbItmePrice, btPurchaseItem);

                itemInfoVBox.setStyle("-fx-background-color: #f0f0f0;");
                itemInfoVBox.setPadding(new Insets(10, 10, 10, 10));

                Image trashImage = new Image("C:\\Users\\a-z\\Desktop\\DBProject\\DBP\\src\\main\\resources\\trash.png");
                ImageView trashImageView = new ImageView(trashImage);

                VBox trashVBox = new VBox();
                JFXButton btTrash = new JFXButton();
                btTrash.setStyle("-fx-background-radius: 90;");

                btTrash.setGraphic(trashImageView);

                trashVBox.getChildren().add(btTrash);

                trashVBox.setAlignment(Pos.CENTER_LEFT);

                btPurchaseItem.setOnMouseClicked(e -> {
                    makeActionsToPurchaseButton(newItem);
                });

                btTrash.setOnMouseClicked(e -> {
                    makeActionsToTrashButton(category, newItem);
                });

                hbox.getChildren().addAll(itemInfoVBox, trashVBox);
                hbox.setStyle("-fx-background-color: #f0f0f0;");
                base.getChildren().add(0, hbox);
                break;
            }
        }
    }

    /**
     * deleteItemFromUI method that will remove the item from the UI and the DB.
     * */
    public void deleteItem(Category category, Item itemToDelete) {
        for (int i = 0; i < menuAccordion.getPanes().size(); i++) {
            TitledPane titledPane = menuAccordion.getPanes().get(i);
            if (titledPane.getText().equals(category.getCategoryName())) {
                // Get the ScrollPane that holds the VBox
                ScrollPane scrollPane = (ScrollPane) titledPane.getContent();
                VBox base; // base contains VBoxes

                // Check if the ScrollPane's content is already a VBox
                if (scrollPane.getContent() instanceof VBox) {
                    base = (VBox) scrollPane.getContent();

                    // Iterate through the children of the VBox to find the item
                    for (int j = 0; j < base.getChildren().size(); j++) {
                        HBox hBox = (HBox) base.getChildren().get(j); // each one contains 2 VBoxes: one for item info and another for the trash button.

                        // Get the VBox that contains the item info
                        VBox infoVBox = (VBox) hBox.getChildren().get(0);
                        Label lbItemName = (Label) infoVBox.getChildren().get(0);

                        if (lbItemName.getText().equals(itemToDelete.getItemName())) {
                            base.getChildren().remove(j);//remove item from UI
                            CategoryRepo.deleteItem(itemToDelete.getItemName());
                            break;
                        }
                    }
                }
                return; // Exit the method after processing the category
            }
        }
    }


    /**
     * addNewCategory method that will added the new category into DB and UI.
     * */

    public void addNewCategory() {

        //make actions for added new category button.
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

                    //Add the new category to UI.
                    addCategoryToUI(categoryName);
                }
            });
        });
    }


    public void addCategoryToUI(String categoryName) {
        TitledPane titledPane = new TitledPane();
        titledPane.setText(categoryName);
        titledPane.setStyle("-fx-font-size: 15px");


        VBox base = new VBox(2);
        ScrollPane scrollPane = new ScrollPane(base);
        scrollPane.setFitToWidth(true);
        titledPane.setContent(scrollPane);
        menuAccordion.getPanes().add(titledPane);

        base.setPadding(new Insets(2, 2, 2, 2));
        base.setStyle("-fx-background-color: Black;");

        VBox addNewItemVBox = new VBox(2);
        addNewItemVBox.setStyle("-fx-background-color: #f0f0f0;");

        // Add a button to allow adding new items.
        JFXButton btAddNewItem = new JFXButton("Add new Item");
        btAddNewItem.setStyle("-fx-font-family: 'Times New Roman';-fx-background-radius: 90 ; -fx-font-size: 21 ;-fx-font-weight: bold ");
        addNewItemVBox.getChildren().add(btAddNewItem);

        btAddNewItem.setOnAction(e -> {

        });

        base.getChildren().add(addNewItemVBox);
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
     * showInfoAlert method that will show specific information.
     * */

    public void showInfoAlert(String title, String context) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(context);
        infoAlert.showAndWait();
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


    //
//    public void deleteCategory() {
//
//        btDeleteCategory.setOnMouseClicked(event -> {
//            ArrayList<Category> categories = CategoryRepo.getCategories();
//
//            ArrayList<String> categoryNames = new ArrayList<>();
//
//            for (int i = 0; i < categories.size(); i++) {
//                categoryNames.add(categories.get(i).getCategoryName());
//            }
//            // Show a choice dialog for category selection
//            ChoiceDialog<String> dialog = new ChoiceDialog<>(categoryNames.get(0), categoryNames);
//            dialog.setTitle("Select Category to Delete");
//            dialog.setHeaderText("Choose a category to delete:");
//            dialog.setContentText("Categories:");
//
//            // Get the user's selection
//            Optional<String> result = dialog.showAndWait();
//            result.ifPresent(selectedCategory -> {
//                // Confirm deletion with the user
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Delete Category");
//                alert.setHeaderText("Are you sure you want to delete '" + selectedCategory + "'?");
//                alert.setContentText("This action cannot be undone.");
//
//                Optional<ButtonType> confirmationResult = alert.showAndWait();
//                if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
//                    // Find and remove the selected category from the UI (Accordion) and database
//                    for (int i = 0; i < menuAccordion.getPanes().size(); i++) {
//                        TitledPane pane = menuAccordion.getPanes().get(i);
//                        if (pane.getText().equalsIgnoreCase(selectedCategory)) {
//                            menuAccordion.getPanes().remove(i); // Remove the pane
//                            break; // Exit the loop once the category is found and removed
//                        }
//                    }
//
//                    // Optionally, remove the category from the database
////                    CategoryRepo.deleteCategoryByName(selectedCategory);  // Implement this method in your repo
//
//                    // Optionally print to console or log the deletion
////                    System.out.println("Category '" + selectedCategory + "' has been deleted.");
//                }
//            });
//        });
//    }
//
//    public boolean isItemExist(Category category, String itemName) {
//        for (int i = 0; i < category.getItems().size(); i++) {
//            if (category.getItems().get(i).getItemName().equalsIgnoreCase(itemName)) {
//                return true;
//            }
//        }
//        return false;
//    }
    public static void main(String[] args) {
        launch(args);
    }
}
