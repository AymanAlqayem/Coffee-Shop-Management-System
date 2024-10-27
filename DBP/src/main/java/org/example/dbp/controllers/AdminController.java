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

    @FXML
    Label lbName;


//    public void initialize() {
//        // Add close request handler for the admin stage
//        Platform.runLater(() -> {
//            Stage stage = (Stage) lbName.getScene().getWindow();
//            stage.setOnCloseRequest(event -> {
//                event.consume();  // Consume the event so the default action (closing) is prevented
//                closeConfirmation(stage);
//            });
//        });
//    }

    @FXML
    private JFXComboBox<String> comboBox; // This links to the JFXComboBox in Scene Builder


    public void initialize() {
        // Populate the combo box with options
        comboBox.getItems().addAll("Admin", "Employee");

        // Add close request handler for the admin stage
        Platform.runLater(() -> {
            Stage stage = (Stage) lbName.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                event.consume();  // Consume the event so the default action (closing) is prevented
                closeConfirmation(stage);
            });
        });
    }

    @FXML
    public void comboOptions(ActionEvent e) {
        // Get the selected option when an action occurs
        String selectedOption = comboBox.getSelectionModel().getSelectedItem();
        System.out.println("Selected option: " + selectedOption);
    }


    @FXML
    private void handleSignOut() {
        // Close the current admin stage
        closeConfirmation((Stage) lbName.getScene().getWindow());

        // Load the login stage again
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/login.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load(), 1378, 750);  // Adjust the size as needed
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(loginScene);
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Handle loading errors
        }
    }

    private void closeConfirmation(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Unsaved changes might be lost.");

        // Handle the user's response
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();  // If the user clicks OK, close the stage
        }
    }

    @FXML
    private JFXButton btDash = new JFXButton();

    @FXML
    private JFXButton btInventory = new JFXButton();

    @FXML
    private JFXButton btMenu = new JFXButton();

    @FXML
    private JFXButton btCustomers = new JFXButton();

    @FXML
    private JFXButton btAdd = new JFXButton();


    @FXML
    private AnchorPane dash_form = new AnchorPane();
    @FXML
    private AnchorPane inventor_form;

    @FXML
    private AnchorPane add_form;


    public void switchWin(ActionEvent e) {
        dash_form.setVisible(true);
        add_form.setVisible(false);

        if (e.getSource() == btDash) {
            dash_form.setVisible(true);
//            inventor_form.setVisible(false);
            add_form.setVisible(false);
        } else if (e.getSource() == btAdd) {
            System.out.println("add Form");
            dash_form.setVisible(false);
            add_form.setVisible(true);
        }
    }

    public static void main(String[] args) {
    }

}
