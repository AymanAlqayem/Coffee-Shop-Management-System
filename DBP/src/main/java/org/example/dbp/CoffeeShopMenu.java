package org.example.dbp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;

public class CoffeeShopMenu extends Application {

    // HashMap to store drink prices
    private HashMap<String, Double> drinkPrices = new HashMap<>();
    private VBox orderSummaryPane = new VBox(10);
    private Label totalLabel = new Label("Total: $0.00");
    private double totalAmount = 0.0;

    @Override
    public void start(Stage primaryStage) {
        // Root layout
        HBox root = new HBox(20);

        // Accordion to hold drink types (Hot and Cold)
        Accordion drinkTypesAccordion = new Accordion();

        // Add Hot Drinks section
        TitledPane hotDrinksPane = createDrinkPane("Hot Drinks");
        drinkTypesAccordion.getPanes().add(hotDrinksPane);

        // Add Cold Drinks section
        TitledPane coldDrinksPane = createDrinkPane("Cold Drinks");
        drinkTypesAccordion.getPanes().add(coldDrinksPane);

        // Add new type button
        Button addNewTypeBtn = new Button("Add New Type");
        addNewTypeBtn.setOnAction(e -> addNewType(drinkTypesAccordion));

        // VBox for drink types and button
        VBox leftPane = new VBox(10, drinkTypesAccordion, addNewTypeBtn);

        // Order summary pane
        VBox rightPane = new VBox(10);
        rightPane.getChildren().addAll(new Label("Order Summary:"), orderSummaryPane, totalLabel);

        // Payment input and button
        Label paymentLabel = new Label("Amount Paid: ");
        TextField paymentField = new TextField();
        Button sendOrderBtn = new Button("Send Order");
        sendOrderBtn.setOnAction(e -> processPayment(paymentField));

        rightPane.getChildren().addAll(paymentLabel, paymentField, sendOrderBtn);

        // Add both panes to the root layout
        root.getChildren().addAll(leftPane, rightPane);

        // Create and display the scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Coffee Shop Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TitledPane createDrinkPane(String typeName) {
        ListView<HBox> drinkListView = new ListView<>();

        // Add some example drinks with prices
        if (typeName.equals("Hot Drinks")) {
            addDrinkToList(drinkListView, "Espresso", 3.0);
            addDrinkToList(drinkListView, "Cappuccino", 4.5);
        } else if (typeName.equals("Cold Drinks")) {
            addDrinkToList(drinkListView, "Iced Coffee", 3.5);
            addDrinkToList(drinkListView, "Cold Brew", 4.0);
        }

        // Add new drink button
        Button addNewDrinkBtn = new Button("Add New Drink");
        addNewDrinkBtn.setOnAction(e -> addNewDrink(drinkListView));

        // Layout for each type
        VBox typeLayout = new VBox(10, drinkListView, addNewDrinkBtn);
        return new TitledPane(typeName, typeLayout);
    }

    private void addDrinkToList(ListView<HBox> drinkListView, String drinkName, double price) {
        Label nameLabel = new Label(drinkName + " ($" + price + ")");
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addToOrder(drinkName, price));

        HBox drinkItem = new HBox(10, nameLabel, addButton);
        drinkListView.getItems().add(drinkItem);

        // Store the drink price
        drinkPrices.put(drinkName, price);
    }

    // Method to add a new drink
    private void addNewDrink(ListView<HBox> drinkListView) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Drink");
        dialog.setHeaderText("Add a new drink");
        dialog.setContentText("Drink name:");

        dialog.showAndWait().ifPresent(drinkName -> {
            TextInputDialog priceDialog = new TextInputDialog();
            priceDialog.setTitle("Drink Price");
            priceDialog.setHeaderText("Set the price for " + drinkName);
            priceDialog.setContentText("Price:");

            priceDialog.showAndWait().ifPresent(price -> {
                double drinkPrice = Double.parseDouble(price);
                addDrinkToList(drinkListView, drinkName, drinkPrice);
            });
        });
    }

    // Method to add a drink to the order summary
    private void addToOrder(String drinkName, double price) {
        TextInputDialog quantityDialog = new TextInputDialog("1");
        quantityDialog.setTitle("Quantity");
        quantityDialog.setHeaderText("How many " + drinkName + "s?");
        quantityDialog.setContentText("Quantity:");

        quantityDialog.showAndWait().ifPresent(quantity -> {
            int qty = Integer.parseInt(quantity);
            double subTotal = price * qty;

            // Add to the order summary
            Label orderItem = new Label(drinkName + " x " + qty + " - $" + subTotal);
            orderSummaryPane.getChildren().add(orderItem);

            // Update the total amount
            totalAmount += subTotal;
            totalLabel.setText("Total: $" + String.format("%.2f", totalAmount));
        });
    }

    // Method to add a new type to the accordion
    private void addNewType(Accordion accordion) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Type");
        dialog.setHeaderText("Add a new drink type");
        dialog.setContentText("Type name:");

        dialog.showAndWait().ifPresent(typeName -> {
            TitledPane newTypePane = createDrinkPane(typeName);
            accordion.getPanes().add(newTypePane);
        });
    }

    // Method to process the payment and show the change
    private void processPayment(TextField paymentField) {
        double amountPaid = Double.parseDouble(paymentField.getText());
        double change = amountPaid - totalAmount;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Processed");
        alert.setHeaderText("Payment Complete");
        alert.setContentText("Total: $" + String.format("%.2f", totalAmount) + "\n" +
                "Amount Paid: $" + String.format("%.2f", amountPaid) + "\n" +
                "Change: $" + String.format("%.2f", change));
        alert.showAndWait();

        // Reset order summary after payment
        orderSummaryPane.getChildren().clear();
        totalAmount = 0.0;
        totalLabel.setText("Total: $0.00");
        paymentField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

