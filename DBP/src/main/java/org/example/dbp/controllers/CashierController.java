package org.example.dbp.controllers;


import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.dbp.models.Category;
import org.example.dbp.models.Item;
import org.example.dbp.repository.CategoryRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class CashierController  {

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
    private AnchorPane menuForm;

    @FXML
    private AnchorPane billForm;


//    @Override
//    public void start(Stage stage) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/Cashier.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 1525, 782);
//            stage.setTitle("Cashier");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * makeActionsForSignOutButton method that will make actions for sign-out button.
     * */
    public void makeActionsForSignOutButton(ActionEvent event) {
        closeConfirmation((Stage) lbName.getScene().getWindow());

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
        if (e.getSource() == btMenu) {
            menuForm.setVisible(true);
            billForm.setVisible(true);
            loadMenu();
        }
    }

    private void loadMenu() {
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

    public void setUserName(String userName) {
        lbName.setText(userName);
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

//
//    public static void main(String[] args) {
//        launch(args);
//    }
}

