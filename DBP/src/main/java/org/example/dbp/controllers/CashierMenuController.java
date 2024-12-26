package org.example.dbp.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.example.dbp.models.Category;
import org.example.dbp.models.InvoiceItems;
import org.example.dbp.models.MenuItem;
import org.example.dbp.repository.CategoryRepo;
import org.example.dbp.repository.CustomerRepository;
import org.example.dbp.repository.OrderRepo;
import org.example.dbp.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CashierMenuController {
    @FXML
    private Accordion menuAccordion;

    @FXML
    private JFXButton btDone;
    @FXML
    private TextField amountTextFiled;

    @FXML
    private Label orderNoTextFiled;
    @FXML
    private Label cashNameTextFiled;
    @FXML
    private Label custNameTextFiled;
    @FXML
    private Label dateTimeTextFiled;


    private double totalBillAmount = 0.0;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private TableView<InvoiceItems> billTableView;
    @FXML
    private TableColumn<Map<String, Object>, String> itemTableColumn;
    @FXML
    private TableColumn<Map<String, Object>, Integer> quantityTableColumn;
    @FXML
    private TableColumn<Map<String, Object>, Double> priceTableColumn;

    @FXML
    private ListView<String> searchResultsListView;

    private ObservableList<String> customerNames;
    @FXML
    private TextField searchTextField;

    private ObservableList<InvoiceItems> itemsObservableList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        loadMenuData(); // Load the menu data
        itemsObservableList = FXCollections.observableArrayList();

        // Set up the columns
        itemTableColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set bill items in the bill table view.
        billTableView.setItems(itemsObservableList);

        // Initialize a customer names list.
        customerNames = FXCollections.observableArrayList(CustomerRepository.getAllCustomers());

        // Add event handlers
        searchTextField.addEventHandler(KeyEvent.KEY_RELEASED, e -> searchCustomer());
        searchResultsListView.setOnMouseClicked(e -> handleSelectCustomer());
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
     * searchCustomer method that will allow users to search for a customers.
     * */
    private void searchCustomer() {
        String result = searchTextField.getText().toLowerCase();
        if (result.isEmpty()) {
            searchResultsListView.setVisible(false);
        } else {
            ObservableList<String> filtered = customerNames.filtered(name -> name.toLowerCase().contains(result));
            searchResultsListView.setItems(filtered);
            searchResultsListView.setVisible(!filtered.isEmpty());
        }
    }

    /**
     * handleSelectCustomer method that will allow users to select a customer after searching.
     * */
    private void handleSelectCustomer() {
        String selectedName = searchResultsListView.getSelectionModel().getSelectedItem();
        if (selectedName != null) {
            custNameTextFiled.setText(selectedName);
            searchResultsListView.setVisible(false);
            searchTextField.clear();
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
                    // Process the quantity entered by the user
                    System.out.println("User wants to purchase " + qty + " of " + item.getItemName());

                    //Add the item information to the tableview.
                    addItemToBill(item.getItemName(), qty, item.getPrice());
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
     * addItemToBill method that will add the given item and calculate its total price.
     * */
    public void addItemToBill(String itemName, int qty, double price) {

        double totalPrice = price * qty; // Calculate total price

        InvoiceItems itemInfo = new InvoiceItems(itemName, qty, totalPrice);

        // Add the BillItem to the observable list
        itemsObservableList.add(itemInfo);

        // Update the total bill amount.
        totalBillAmount += totalPrice;

        // Update the total amount price.
        totalPriceLabel.setText("Price: " + totalBillAmount + " NIS");
    }

    /**
     * removeSelectedItem method that will remove the selected item from the table view.
     * */
    @FXML
    public void removeSelectedItem() {
        // Get the selected item from the TableView
        InvoiceItems selectedItem = billTableView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Get the price of the selected item
            double itemPrice = selectedItem.getPrice();

            // Subtract the item's price from the total bill amount
            totalBillAmount -= itemPrice;

            // Remove the selected item from the ObservableList
            itemsObservableList.remove(selectedItem);

            totalPriceLabel.setText("Price :" + totalBillAmount + " NIS");
        } else {
            // Show an alert if no item is selected
            showErrorAlert("No Selection", "Please select an item to remove.");
        }
    }

    /**
     * finishBill method that will end the current order.
     * */
    @FXML
    public void finishBill() {
        if (amountTextFiled.getText().isEmpty()) {
            showErrorAlert("Invalid Input", "Please enter a valid amount.");
            return;
        }

        try {
            double paidAmount = Double.parseDouble(amountTextFiled.getText());
            if (paidAmount >= totalBillAmount) {
                double change = paidAmount - totalBillAmount;
                successAlert("Transaction Complete", "Change to Return: " + String.format("%.2f", change) + " NIS");
            } else {
                showErrorAlert("Insufficient Payment", "The paid amount is not enough to cover the total.");
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Input Error", "Please enter a valid amount for payment.");
        }

    }

    static String cashierName = null;

    @FXML
    public void generateBill() {
        //Set customer name.
        if (custNameTextFiled.getText().equalsIgnoreCase("F")) {
            showAlert("Enter customer name", "You should choose the customer.");
            return;
        }

        int cashierId = UserRepository.getCashierId(cashierName);
        int customerId = CustomerRepository.getCustomerId(custNameTextFiled.getText());

        System.out.println(cashierId + ",,,,," + customerId);

        //Add the order to the database.
        int orderNo = OrderRepo.addOrder(cashierId, customerId,
                totalBillAmount, itemsObservableList);

        orderNoTextFiled.setText(orderNo + " ");


        //Set the current data time.
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTimeTextFiled.setText(now.format(formatter));

        //Set cashier name.
        cashNameTextFiled.setText(cashierName);

//        //clear order content
//        itemsObservableList.clear();
//        totalBillAmount = 0.0;
//        amountTextFiled.setText("Total:0.00 NIS");
//        amountTextFiled.clear();
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
     * successAlert method that will show a success alert that the operation done successfully.
     * */
    public void successAlert(String title, String context) {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle(title);
        successAlert.setHeaderText(null);
        successAlert.setContentText(context);
        successAlert.showAndWait();

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
