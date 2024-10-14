package org.example.dbp.controllers;

import com.jfoenix.controls.JFXButton;
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
    private Accordion menuAccordion ; // empty Accordion


    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/Menu.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller from the loader (this step ensures FXML elements are linked)
            MenuController controller = fxmlLoader.getController();

            // Load the menu data AFTER the FXML is loaded and injected
            controller.loadMenuData();

            // Set the scene and show the stage
            Scene scene = new Scene(root, 1397, 750);
            stage.setTitle("Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMenuData() {
        // Fetch categories from the repository
        ArrayList<Category> categories = CategoryRepo.getCategories();

        // Iterate over categories
        for (int i = 0; i < categories.size(); i++) {
            TitledPane titledPane = new TitledPane(); // Create a new TitledPane for each category
            titledPane.setText(categories.get(i).getCategoryName()); // Set the category name as title

            VBox vbox = new VBox(10); // VBox to hold items and spacing between them

            // Capture current category for use inside the lambda (effectively final)
            final Category currentCategory = categories.get(i);

            // Iterate over the items in the category
            for (int j = 0; j < currentCategory.getItems().size(); j++) {
                final Item currentItem = currentCategory.getItems().get(j); // Capture the current item

                // Create a button for each item with its name and price
                JFXButton button = new JFXButton(currentItem.getItemName() + " " + currentItem.getPrice() + " - NIS");
                button.setStyle("-fx-background-radius: 90");
                button.setFont(Font.font("Times New Roman", 19));
                vbox.getChildren().add(button);

                // Set an event handler for when the button is clicked
                button.setOnMouseClicked(event -> {
                    // Create a TextInputDialog to ask the user for the quantity
                    TextInputDialog dialog = new TextInputDialog("1"); // Default value is 1
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

            // Add a button to allow adding new items
            JFXButton addItemButton = new JFXButton("Add new Item");
            addItemButton.setStyle("-fx-background-radius: 90");
            addItemButton.setFont(Font.font("Times New Roman", 19));
            vbox.getChildren().add(addItemButton);

            // Wrap the VBox in a ScrollPane to enable scrolling for long lists
            ScrollPane scrollPane = new ScrollPane(vbox);
            scrollPane.setFitToWidth(true); // Make the ScrollPane width match the VBox

            // Set the ScrollPane as the content of the TitledPane
            titledPane.setContent(scrollPane);
            menuAccordion.getPanes().add(titledPane); // Add TitledPane to the Accordion
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
