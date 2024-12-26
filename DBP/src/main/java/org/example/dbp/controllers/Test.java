package org.example.dbp.controllers;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test extends Application {
    private TextField paidAmountField;
    private TextField quantityField;
    private ComboBox<String> itemComboBox;
    private TextField searchTextField;
    private ListView<String> searchResultsListView;
    private ObservableList<String> customerNames;
    private TableView<BillItem> tableView;
    private ObservableList<BillItem> billItems;
    private Label totalLabel;
    private double totalAmount = 0.0;
    private TextField customerNameField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        customerNames = FXCollections.observableArrayList(
                "John Doe", "Jane Smith", "Alice Johnson", "Bob Brown", "Charlie White"
        );

        Label headerLabel = new Label("======= Beit Ardi Bill =======");
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Label dateLabel = new Label("Created Date Time: " + now.format(formatter));
        Label orderLabel = new Label("Order No: 10");

        Label customerNameLabel = new Label("Customer Name:");
        customerNameField = new TextField();
        customerNameField.setPromptText("Selected customer");

        searchTextField = new TextField();
        searchTextField.setPromptText("Search customer...");///..............
        
        
        searchResultsListView = new ListView<>();
        searchResultsListView.setVisible(false);


        searchTextField.addEventHandler(KeyEvent.KEY_RELEASED, e -> handleSearch());
        searchResultsListView.setOnMouseClicked(e -> handleSelectCustomer());

        VBox customerBox = new VBox(5, customerNameLabel, searchTextField, searchResultsListView, customerNameField);

        Label itemLabel = new Label("Select Item:");
        itemComboBox = new ComboBox<>();
        itemComboBox.getItems().addAll("Coffee", "Sandwich", "Juice", "Cake", "Tea");

        Label quantityLabel = new Label("Quantity:");
        quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");

        Button addItemButton = new Button("Add Item");
        addItemButton.setOnAction(e -> handleAddItem());

        HBox itemBox = new HBox(10, itemLabel, itemComboBox, quantityLabel, quantityField, addItemButton);

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

        totalLabel = new Label("Total: $0.00");
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button removeItemButton = new Button("Remove Item");
        removeItemButton.setOnAction(e -> handleRemoveItem());

        Label paidAmountLabel = new Label("Paid Amount:");
        paidAmountField = new TextField();
        paidAmountField.setPromptText("Enter paid amount");

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> handleDoneButton());

        HBox paymentBox = new HBox(10, paidAmountLabel, paidAmountField, doneButton);
        HBox buttonBox = new HBox(10, removeItemButton);

        VBox headerBox = new VBox(10, headerLabel, customerBox, dateLabel, orderLabel, itemBox);
        VBox centerBox = new VBox(10, tableView, totalLabel, buttonBox, paymentBox);
        centerBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(centerBox);

        Scene scene = new Scene(root, 700, 600);
        primaryStage.setTitle("Beit Ardi Bill");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleSearch() {
        String query = searchTextField.getText().toLowerCase();
        if (query.isEmpty()) {
            searchResultsListView.setVisible(false);
//            searchResultsListView.getItems().clear();
        } else {
            ObservableList<String> filtered = customerNames.filtered(name -> name.toLowerCase().contains(query));
            searchResultsListView.setItems(filtered);
            searchResultsListView.setVisible(!filtered.isEmpty());
        }
    }

    /////
    private void handleSelectCustomer() {
        String selectedName = searchResultsListView.getSelectionModel().getSelectedItem();
        if (selectedName != null) {
            customerNameField.setText(selectedName);
            searchResultsListView.setVisible(false);
//            searchResultsListView.getItems().clear();
        }
    }

    private void handleAddItem() {
        try {
            String selectedItem = itemComboBox.getValue();
            int quantity = Integer.parseInt(quantityField.getText());

            if (selectedItem == null || quantity <= 0) throw new Exception();

            double price = getItemPrice(selectedItem);
            double totalItemPrice = price * quantity;

            billItems.add(new BillItem(selectedItem, quantity, totalItemPrice));
            totalAmount += totalItemPrice;

            totalLabel.setText("Total: $" + String.format("%.2f", totalAmount));

            quantityField.clear();
            itemComboBox.getSelectionModel().clearSelection();
        } catch (Exception e) {
            showAlert("Invalid Input", "Please select an item and enter a valid quantity.");
        }
    }

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

    private void handleDoneButton() {
        try {
            double paidAmount = Double.parseDouble(paidAmountField.getText());
            if (paidAmount >= totalAmount) {
                double change = paidAmount - totalAmount;
                String customerName = customerNameField.getText().isEmpty() ? "Customer" : customerNameField.getText();

                showAlert("Transaction Complete", "Thank you, " + customerName + "!\nChange to Return: $" + String.format("%.2f", change));

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

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
