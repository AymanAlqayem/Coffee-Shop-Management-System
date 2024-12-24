package org.example.dbp.controllers;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.dbp.models.Customer;
import org.example.dbp.repository.CustomerRepository;

import java.io.IOException;
import java.util.Optional;

public class CashierController {

    //Basic Controllers.

    @FXML
    private Label lbName;

    @FXML
    private JFXButton btInventory;

    @FXML
    private JFXButton btMenu;

    @FXML
    private JFXButton btCustomers;

    @FXML
    private JFXButton btAdd;

    @FXML
    private JFXButton createBillButton;

    //Forms.
    @FXML
    private AnchorPane menuForm;

    @FXML
    private AnchorPane addNewCustomer;


    //Controllers in add a new customers form.
    @FXML
    private TextField customerName;

    @FXML
    private TextField customerPhoneNumber;

    //Controllers in add a new bill form.
    @FXML
    private Label cashNameTextFiled;

    @FXML
    private Label custNameTextFiled;

    @FXML
    private Label dateTimeTextFiled;
    @FXML
    private Label orderNoTextFiled;


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
            loadCashierMenu();
            addNewCustomer.setVisible(false);
        } else if (e.getSource() == btAdd) {
            addNewCustomer.setVisible(true);
            menuForm.setVisible(false);
        }
    }

    /**
     * loadCashierMenu method that will load the cashier menu.
     * */
    private void loadCashierMenu() {
        try {
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
        Customer newCustomer = new Customer(customerName.getText(), customerPhoneNumber.getText());
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
        lbName.setText(userName);
    }
}

