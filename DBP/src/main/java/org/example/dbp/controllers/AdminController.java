package org.example.dbp.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXComboBox;

import java.io.IOException;

public class AdminController {

    //Forms.

    @FXML
    private AnchorPane dashBoard_form;
    @FXML
    private AnchorPane addNewEmployee_form;

    @FXML
    private AnchorPane menuForm;


    // Basic Controllers.

    @FXML
    private JFXButton btDashboard;

    @FXML
    private JFXButton btInventory;

    @FXML
    private JFXButton btMenu;

    @FXML
    private JFXButton btCustomers;

    @FXML
    private JFXButton btEmployees;

    @FXML
    private JFXButton btAddNewRole;

    @FXML
    private JFXButton btSignOut;

    @FXML
    private Label lbUserName;


    //Controllers in addNewEmployee_form.
    @FXML
    private JFXComboBox<String> RoleComboBox;

    @FXML
    JFXButton btAddNewEmployee;


    public void initialize() {
        // Populate the combo box with options
        RoleComboBox.getItems().addAll("Admin", "Employee");


        // Add close request handler for the admin stage
        Platform.runLater(() -> {
            Stage stage = (Stage) lbUserName.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                event.consume();
                closeConfirmation(stage);
            });
        });
    }

    @FXML
    public void comboOptions(ActionEvent e) {
        // Get the selected option when an action occurs
        String selectedOption = RoleComboBox.getSelectionModel().getSelectedItem();
        System.out.println("Selected option: " + selectedOption);
    }

    /**
     * makeActionsForSignOutButton method that will make actions for sign-out button.
     * */

    public void makeActionsForSignOutButton(ActionEvent event) {
        closeConfirmation((Stage) lbUserName.getScene().getWindow());

        // Load the login stage again
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
     * closeConfirmation method that will close the admin stage.
     * */
    private void closeConfirmation(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Unsaved changes might be lost.");

        // Handle the user's response
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }

    /**
     * switchWin method that will witch between widows based a specific button.
     * */
    public void switchWin(ActionEvent e) {
        if (e.getSource() == btDashboard) {
            dashBoard_form.setVisible(true);
            addNewEmployee_form.setVisible(false);
            menuForm.setVisible(false);
        } else if (e.getSource() == btAddNewRole) {
            dashBoard_form.setVisible(false);
            addNewEmployee_form.setVisible(true);
            menuForm.setVisible(false);
        } else if (e.getSource() == btMenu) {
            dashBoard_form.setVisible(false);
            addNewEmployee_form.setVisible(false);
            menuForm.setVisible(true);
            loadMenu(); // Call the method to load the Admin Menu FXML
        }
    }

    private void loadMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/dbp/AdminMenu.fxml"));
            AnchorPane menuPane = loader.load(); // Load the menu content
            // Clear existing content and add the menu pane to menuForm
            menuForm.getChildren().clear();
            menuForm.getChildren().add(menuPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setUserName(String userName) {
        lbUserName.setText(userName);
    }

    public static void main(String[] args) {
    }

}
