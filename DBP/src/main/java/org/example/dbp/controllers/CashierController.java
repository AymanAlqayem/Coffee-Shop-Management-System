package org.example.dbp.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.dbp.models.Customer;
import org.example.dbp.models.User;
import org.example.dbp.repository.CustomerRepository;
import org.example.dbp.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class CashierController {
    //Basic Controllers.
    @FXML
    private Label lbName;

    @FXML
    private JFXButton btMenu;

    @FXML
    private JFXButton btCustomers;

    @FXML
    private JFXButton btAdd;

    @FXML
    private TableView customerTableView;

    //Forms.
    @FXML
    private AnchorPane menuForm;

    @FXML
    private AnchorPane addNewCustomer;

    @FXML
    private AnchorPane customersPane;

    private String originalCashierName;

    //Controllers in add a new customers form.
    @FXML
    private TextField customerName;

    @FXML
    private TextField customerPhoneNumber;


    /**
     * makeActionsForSignOutButton method that will make actions for sign-out button.
     * */
    public void makeActionsForSignOutButton(ActionEvent event) {
        //close current stage.
        ((Stage) lbName.getScene().getWindow()).close();
        // Load the login stage again.
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/Login.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load(), 1525, 782);
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(loginScene);
            loginStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();  // Handle loading errors
        }
    }

    /**
     * switchWin method that will witch between widows based a specific button.
     * */
    public void switchWin(ActionEvent e) {
        if (e.getSource() == btMenu) {
            menuForm.setVisible(true);
            addNewCustomer.setVisible(false);
            customersPane.setVisible(false);
            loadCashierMenu();
        } else if (e.getSource() == btAdd) {
            addNewCustomer.setVisible(true);
            menuForm.setVisible(false);
            customersPane.setVisible(false);
        } else if (e.getSource() == btCustomers) {
            menuForm.setVisible(false);
            addNewCustomer.setVisible(false);
            customersPane.setVisible(true);
            this.showCustomer();
        }
    }

    /**
     * loadCashierMenu method that will load the cashier menu.
     * */
    private void loadCashierMenu() {
        try {
            CashierMenuController.cashierName = originalCashierName;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/dbp/CashierMenu.fxml"));
            AnchorPane menuPane = loader.load(); // Load the menu content
            // Clear existing content and add the menu pane to menuForm
            menuForm.getChildren().clear();
            menuForm.getChildren().add(menuPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * addNewCustomer method that will add a new customer.
     * */
    public void addNewCustomer(ActionEvent event) {
        if (customerName.getText().isEmpty() || customerPhoneNumber.getText().isEmpty()) {
            showErrorAlert("Invalid Input", "Please enter the name and the phone number");
            return;
        }
        //Check if the customer already exists
        if (CustomerRepository.isCustomerExist(customerPhoneNumber.getText())) {
            showErrorAlert("Invalid Input", "Customer already exists");
            return;
        }

        //Check if the password is valid.
        if (customerPhoneNumber.getText().length() != 10) {
            showErrorAlert("Invalid Input", "Please enter a valid phone number");
            return;
        }
        //Add the new customer.
        Customer newCustomer = new Customer(customerName.getText(), Long.parseLong(customerPhoneNumber.getText()));
        CustomerRepository.addNewCustomer(newCustomer);
        successAlert("Customer added", "Customer successfully added");
        customerName.clear();
        customerPhoneNumber.clear();

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

    /**
     * setUserName method that will set the username
     * */
    public void setUserName(String userName) {
        originalCashierName = userName;

        if (userName != null && !userName.trim().isEmpty()) { // Check for null and empty string
            String[] nameParts = userName.trim().split("\\s+", 2); // Split on spaces, limit to 2 parts

            if (nameParts.length == 2) { // Ensure there are at least two parts
                String firstName = nameParts[0];
                String lastName = nameParts[1];
                lbName.setText(firstName + "\n" + lastName); // Set first name and last name on separate lines
            } else {
                lbName.setText(userName.trim()); // Handle single-word names
            }

            lbName.setPrefHeight(Region.USE_COMPUTED_SIZE);
            lbName.setAlignment(Pos.CENTER_LEFT); // Align text to the left
            lbName.setWrapText(false);
            lbName.setMinHeight(50); // Set a minimum height for two lines
        } else {
            lbName.setText(""); // Handle null or empty username
        }
    }

    public void showCustomer() {
        customerTableView.getColumns().clear();
        customerTableView.setVisible(true);

        ObservableList<Customer> dataList = FXCollections.observableArrayList(CustomerRepository.getAllCustomers());

        customerTableView.setEditable(true);
        customerTableView.setMaxHeight(4000);
        customerTableView.setMaxWidth(4000);

        TableColumn<Customer, String> nameCol = buildTableColumnStringCustomer("customerName", true, 140);
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());


        TableColumn<Customer, Long> phoneCol = buildTableColumnLongUCustomer("customerPhone", true, 100);
        phoneCol.setCellFactory(column -> new TextFieldTableCell<>(new StringConverter<Long>() {
            @Override
            public String toString(Long object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public Long fromString(String string) {
                try {
                    return Long.parseLong(string);
                } catch (NumberFormatException e) {
                    return null; // Or handle invalid input gracefully
                }
            }
        }));
        phoneCol.setOnEditCommit(event -> {
            TableColumn.CellEditEvent<Customer, Long> cellEditEvent = event;
            Customer customer = cellEditEvent.getRowValue();

            Long newValue = cellEditEvent.getNewValue();
            if (newValue != null) {
                customer.setCustomerPhone(newValue);
                // Update the database
                updateRowByKeyCustomer(customer.getId(), "phone_number", newValue.toString());
            } else {
                System.out.println("Invalid phone number input");
            }
        });
        customerTableView.setItems(dataList);
        customerTableView.getColumns().addAll(nameCol, phoneCol);


        customerTableView.setMaxWidth(Double.MAX_VALUE);
        customerTableView.setMaxHeight(Double.MAX_VALUE);

        customerTableView.setRowFactory(tv -> {

            TableRow<Customer> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem deleteItem = new MenuItem("Delete");
            contextMenu.getItems().add(deleteItem);
            deleteItem.setOnAction(actionEvent -> {
                boolean confirmed = showConfirmationDialog("Are you sure you want to delete this customer ? ");
                if (confirmed) {
                    Customer selectedCustomer = row.getItem();
                    if (selectedCustomer != null) {
                        Customer deletedCustomer = CustomerRepository.deleteRowByKey(selectedCustomer.getId());
                        if (deletedCustomer != null) {
                            // Remove the deleted user from the TableView's observable list
                            customerTableView.getItems().remove(selectedCustomer);
                            System.out.println("Row deleted successfully.");
                        }
                    }
                }
            });

            row.contextMenuProperty().bind(javafx.beans.binding.Bindings.when(javafx.beans.binding.Bindings.isNotNull(row.itemProperty())).then(contextMenu).otherwise((ContextMenu) null));

            return row;
        });
    }

    private TableColumn<Customer, String> buildTableColumnStringCustomer(String colname, boolean editable, int width) {
        TableColumn<Customer, String> someCol = new TableColumn<>(colname);
        someCol.setMinWidth(width);
        someCol.setCellValueFactory(new PropertyValueFactory<>(colname));
        if (editable) {
            someCol.setCellFactory(forTableColumn());
            someCol.setOnEditCommit((TableColumn.CellEditEvent<Customer, String> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setCol(colname, t.getNewValue());
                updateRowByKeyCustomer((t.getRowValue().getId()), colname, String.valueOf(t.getNewValue()));
            });
        }
        return someCol;
    }

    private TableColumn<Customer, Long> buildTableColumnLongUCustomer(String colname, boolean editable, int width) {
        TableColumn<Customer, Long> someCol = new TableColumn<>(colname);
        someCol.setMinWidth(width);
        someCol.setCellValueFactory(new PropertyValueFactory<>(colname));
        if (editable) {

            someCol.setOnEditCommit((TableColumn.CellEditEvent<Customer, Long> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setCol(colname, t.getNewValue());
                updateRowByKeyCustomer((t.getRowValue().getId()), colname, String.valueOf(t.getNewValue()));
            });
        }
        return someCol;
    }

    public void updateRowByKeyCustomer(int id, String col, String val) {
        CustomerRepository.updateRowByKey(id, col, val);
    }

    public boolean showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}

