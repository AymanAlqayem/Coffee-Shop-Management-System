//package org.example.dbp.controllers;
//
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class Test extends Application {
//    private Label changeLabel; // Label to display the change
//    private TextField paidAmountField; // Input for paid amount
//    private TextField customerNameField; // Input for customer name
//    private TableView<BillItem> tableView; // TableView to display bill items
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        // Header Section
//        Label headerLabel = new Label("======= Beit Ardi Bill =======");
//        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
//
//        // Date and Order Info
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        Label dateLabel = new Label("Created Date Time: " + now.format(formatter));
//        Label orderLabel = new Label("Order No: 10");
//
//        // Customer Name Input
//        Label customerNameLabel = new Label("Customer Name:");
//        customerNameField = new TextField();
//        customerNameField.setPromptText("Enter customer name");
//
//        HBox customerBox = new HBox(10, customerNameLabel, customerNameField);
//        VBox headerBox = new VBox(10, headerLabel, customerBox, dateLabel, orderLabel);
//        headerBox.setPadding(new Insets(10));
//
//        // TableView for Bill Items
//        tableView = new TableView<>();
//        TableColumn<BillItem, String> itemColumn = new TableColumn<>("Item Name");
//        itemColumn.setMinWidth(150);
//        itemColumn.setCellValueFactory(data -> data.getValue().itemNameProperty());
//
//        TableColumn<BillItem, Integer> quantityColumn = new TableColumn<>("Quantity");
//        quantityColumn.setMinWidth(100);
//        quantityColumn.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());
//
//        TableColumn<BillItem, Double> priceColumn = new TableColumn<>("Price");
//        priceColumn.setMinWidth(100);
//        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
//
//        tableView.getColumns().addAll(itemColumn, quantityColumn, priceColumn);
//
//        // Add Sample Data to Table
//        ObservableList<BillItem> billItems = FXCollections.observableArrayList(
//                new BillItem("Coffee", 2, 5.50),
//                new BillItem("Sandwich", 1, 8.00),
//                new BillItem("Juice", 3, 4.75)
//        );
//        tableView.setItems(billItems);
//
//        // Total Price Calculation
//        double total = billItems.stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
//        Label totalLabel = new Label("Total: $" + String.format("%.2f", total));
//        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
//
//        // Paid Amount Input
//        Label paidAmountLabel = new Label("Paid Amount:");
//        paidAmountField = new TextField();
//        paidAmountField.setPromptText("Enter paid amount");
//
//        Button doneButton = new Button("Done");
//        doneButton.setOnAction(e -> handleDoneButton(total));
//
//        HBox paymentBox = new HBox(10, paidAmountLabel, paidAmountField, doneButton);
//        VBox centerBox = new VBox(10, tableView, totalLabel, paymentBox);
//        centerBox.setPadding(new Insets(10));
//
//        // Layout Setup
//        BorderPane root = new BorderPane();
//        root.setTop(headerBox);
//        root.setCenter(centerBox);
//
//        // Scene Setup
//        Scene scene = new Scene(root, 500, 500);
//        primaryStage.setTitle("Beit Ardi Bill");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    /**
//     * Handles the Done button click:
//     * - Calculates change.
//     * - Displays an alert with the change amount.
//     * - Clears the TableView content.
//     */
//    private void handleDoneButton(double total) {
//        try {
//            double paidAmount = Double.parseDouble(paidAmountField.getText());
//            if (paidAmount >= total) {
//                double change = paidAmount - total;
//
//                String customerName = customerNameField.getText().isEmpty() ? "Customer" : customerNameField.getText();
//
//                // Show Alert with Change
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Transaction Complete");
//                alert.setHeaderText("Thank you, " + customerName + "!");
//                alert.setContentText("Change to Return: $" + String.format("%.2f", change));
//                alert.showAndWait();
//
//                // Clear TableView content
//                tableView.getItems().clear();
//                paidAmountField.clear();
//                customerNameField.clear();
//            } else {
//                // Insufficient amount alert
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText("Insufficient Payment");
//                alert.setContentText("The amount paid is not enough to cover the total.");
//                alert.showAndWait();
//            }
//        } catch (NumberFormatException e) {
//            // Invalid input alert
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Input Error");
//            alert.setHeaderText("Invalid Input");
//            alert.setContentText("Please enter a valid number for the paid amount.");
//            alert.showAndWait();
//        }
//    }
//}


package org.example.dbp.controllers;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test extends Application {
    private TextField paidAmountField; // Input for paid amount
    private TextField quantityField; // Input for quantity
    private ComboBox<String> itemComboBox; // Item selector
    private TextField customerNameField; // Input for customer name
    private TableView<BillItem> tableView; // Table to display items
    private ObservableList<BillItem> billItems; // List to hold bill items
    private Label totalLabel; // Total price label

    private double totalAmount = 0.0; // Total price

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Header Section
        Label headerLabel = new Label("======= Beit Ardi Bill =======");
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Date and Order Info
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Label dateLabel = new Label("Created Date Time: " + now.format(formatter));
        Label orderLabel = new Label("Order No: 10");

        // Customer Name Input
        Label customerNameLabel = new Label("Customer Name:");
        customerNameField = new TextField();
        customerNameField.setPromptText("Enter customer name");
        HBox customerBox = new HBox(10, customerNameLabel, customerNameField);

        // Item Selection and Quantity Input
        Label itemLabel = new Label("Select Item:");
        itemComboBox = new ComboBox<>();
        itemComboBox.getItems().addAll("Coffee", "Sandwich", "Juice", "Cake", "Tea"); // Items to choose

        Label quantityLabel = new Label("Quantity:");
        quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");

        Button addItemButton = new Button("Add Item");
        addItemButton.setOnAction(e -> handleAddItem());

        HBox itemBox = new HBox(10, itemLabel, itemComboBox, quantityLabel, quantityField, addItemButton);

        // TableView for Bill Items
        tableView = new TableView<>();
        TableColumn<BillItem, String> itemColumn = new TableColumn<>("Item Name");
        itemColumn.setMinWidth(150);
        itemColumn.setCellValueFactory(data -> data.getValue().itemNameProperty());

        TableColumn<BillItem, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());

        TableColumn<BillItem, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());

        tableView.getColumns().addAll(itemColumn, quantityColumn, priceColumn);
        billItems = FXCollections.observableArrayList();
        tableView.setItems(billItems);

        // Total Price Label
        totalLabel = new Label("Total: $0.00");
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Remove Item Button
        Button removeItemButton = new Button("Remove Item");
        removeItemButton.setOnAction(e -> handleRemoveItem());

        // Paid Amount Input and Done Button
        Label paidAmountLabel = new Label("Paid Amount:");
        paidAmountField = new TextField();
        paidAmountField.setPromptText("Enter paid amount");

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> handleDoneButton());

        HBox paymentBox = new HBox(10, paidAmountLabel, paidAmountField, doneButton);
        HBox buttonBox = new HBox(10, removeItemButton); // Holds the Remove Item button

        // Layout Setup
        VBox headerBox = new VBox(10, headerLabel, customerBox, dateLabel, orderLabel, itemBox);
        VBox centerBox = new VBox(10, tableView, totalLabel, buttonBox, paymentBox);
        centerBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(centerBox);

        // Scene Setup
        Scene scene = new Scene(root, 700, 600);
        primaryStage.setTitle("Beit Ardi Bill");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Handle adding items to the table
    private void handleAddItem() {
        try {
            String selectedItem = itemComboBox.getValue();
            int quantity = Integer.parseInt(quantityField.getText());

            if (selectedItem == null || quantity <= 0) {
                throw new Exception();
            }

            double price = getItemPrice(selectedItem); // Get item price
            double totalItemPrice = price * quantity;

            // Add item to the table
            billItems.add(new BillItem(selectedItem, quantity, totalItemPrice));
            totalAmount += totalItemPrice;

            totalLabel.setText("Total: $" + String.format("%.2f", totalAmount));

            // Clear input fields
            quantityField.clear();
            itemComboBox.getSelectionModel().clearSelection();
        } catch (Exception e) {
            showAlert("Invalid Input", "Please select an item and enter a valid quantity.");
        }
    }

    // Handle removing an item from the table
    private void handleRemoveItem() {
        BillItem selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            totalAmount -= selectedItem.getPrice();
            totalLabel.setText("Total: $" + String.format("%.2f", totalAmount));

            billItems.remove(selectedItem);
        } else {
            showAlert("No Selection", "Please select an item to remove.");
        }
    }

    // Handle Done button click
    private void handleDoneButton() {
        try {
            double paidAmount = Double.parseDouble(paidAmountField.getText());
            if (paidAmount >= totalAmount) {
                double change = paidAmount - totalAmount;
                String customerName = customerNameField.getText().isEmpty() ? "Customer" : customerNameField.getText();

                showAlert("Transaction Complete", "Thank you, " + customerName + "!\nChange to Return: $" + String.format("%.2f", change));

                // Clear all data
                billItems.clear();
                totalAmount = 0.0;
                totalLabel.setText("Total: $0.00");
                paidAmountField.clear();
                customerNameField.clear();
            } else {
                showAlert("Insufficient Payment", "The paid amount is not enough to cover the total.");
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid amount for payment.");
        }
    }

    // Helper to get item price
    private double getItemPrice(String item) {
        switch (item) {
            case "Coffee": return 5.50;
            case "Sandwich": return 8.00;
            case "Juice": return 4.75;
            case "Cake": return 6.00;
            case "Tea": return 3.50;
            default: return 0.0;
        }
    }

    // Show alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // BillItem Class (Nested)
    public static class BillItem {
        private final StringProperty itemName;
        private final IntegerProperty quantity;
        private final DoubleProperty price;

        public BillItem(String itemName, int quantity, double price) {
            this.itemName = new SimpleStringProperty(itemName);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.price = new SimpleDoubleProperty(price);
        }

        public StringProperty itemNameProperty() { return itemName; }
        public IntegerProperty quantityProperty() { return quantity; }
        public DoubleProperty priceProperty() { return price; }

        public double getPrice() { return price.get(); }
    }
}
