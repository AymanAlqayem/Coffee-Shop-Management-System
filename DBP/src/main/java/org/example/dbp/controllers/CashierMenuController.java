package org.example.dbp.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.dbp.models.Category;
import org.example.dbp.models.MenuItem;
import org.example.dbp.repository.CategoryRepo;

import java.util.ArrayList;
import java.util.Optional;

public class CashierMenuController {
    @FXML
    private Accordion menuAccordion;


    @FXML
    public void initialize() {
        loadMenuData();  // Load the menu data after FXML components are initialized
    }

    /**
     * loadMenuData method that will load all categories and items.
     * */

    public void loadMenuData() {
        menuAccordion.getPanes().clear();
        ArrayList<Category> categories = CategoryRepo.getCategories();  // Fetch categories from the repository.

        // Iterate over categories.
        for (int i = 0; i < categories.size(); i++) {
            TitledPane titledPane = new TitledPane(); // Create a new TitledPane for each category.
            titledPane.setText(categories.get(i).getCategoryName());
            titledPane.setStyle("-fx-font-size: 15px");

            VBox base = new VBox(2);

            base.setPadding(new Insets(4, 4, 4, 4));
            base.setStyle("-fx-background-color: Black;");

            final Category currentCategory = categories.get(i);


            /**
             *  Iterate over the items in the current category.
             * */

            for (int j = 0; j < currentCategory.getItems().size(); j++) {

                final MenuItem currentItem = currentCategory.getItems().get(j);

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

                Image image = new Image("C:\\Users\\a-z\\Desktop\\DB_PROJECT\\DBProject\\DBP\\src\\main\\resources\\\\AddForMenu.png");
                ImageView imageView = new ImageView(image);

                btPurchaseItem.setGraphic(imageView);

                itemInfoVBox.getChildren().addAll(lbItemName, lbItmePrice, btPurchaseItem);

                itemInfoVBox.setStyle("-fx-background-color: #f0f0f0;");
                itemInfoVBox.setPadding(new Insets(10, 10, 10, 10));

                base.getChildren().add(itemInfoVBox);

                /**
                 * make actions when the item button clicked.
                 * */
                btPurchaseItem.setOnMouseClicked(e -> {
                    makeActionsToPurchaseButton(currentItem);
                });
            }


            // Wrap the VBox in a ScrollPane to enable scrolling for long lists
            ScrollPane scrollPane = new ScrollPane(base);
            scrollPane.setFitToWidth(true); // Make the ScrollPane width match the VBox.

            // Set the ScrollPane as the content of the TitledPane
            titledPane.setContent(scrollPane);
            menuAccordion.getPanes().add(titledPane); // Add TitledPane to the Accordion
        }
    }

    /**
     * makeActionsToPurchaseButton that will show a text input dialog to enter the quantity of the item.
     * */
    public void makeActionsToPurchaseButton(MenuItem item) {
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
}
