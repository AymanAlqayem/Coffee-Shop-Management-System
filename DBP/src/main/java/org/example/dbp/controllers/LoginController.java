package org.example.dbp.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.example.dbp.models.User;
import org.example.dbp.repository.UserRepository;

import java.io.IOException;

public class LoginController extends Application {
    @FXML
    TextField tfUserName;
    @FXML
    PasswordField tfPass;
    @FXML
    JFXButton btLogin;

    UserRepository userRepo = new UserRepository();

    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1525, 782);
            stage.setTitle("Log in");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void initialize() {
        // Handle Enter key press in tfUserName
        tfUserName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Move focus to tfPass
                tfPass.requestFocus();
            }
        });

        // Handle Enter key press in tfPass
        tfPass.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Trigger login action by clicking the login button
                btLogin.fire(); // This simulates a button click
            }
        });
    }

    /**
     * login method that login into admin stage or cashier stage based the role.
     * */
    public void login() throws IOException {
        String username = tfUserName.getText();
        String password = tfPass.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and password cannot be empty.");
            return;
        }
        User user = UserRepository.getUserByUsernameAndPassword(tfUserName.getText(), tfPass.getText());
        if (user == null) {
            showAlert("Login Failed", "Invalid username or password.");
        } else if (user.getRole().equalsIgnoreCase("Admin")) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/admin.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1525, 782);

            // Get the AdminController and pass the username to it
            AdminController adminController = fxmlLoader.getController();
            adminController.setUserName(username);

            // Show the new stage
            Stage adminStage = new Stage();
            adminStage.setTitle("Admin Stage");
            adminStage.setScene(scene);
            adminStage.show();

            // Close the login stage
            Stage loginStage = (Stage) tfUserName.getScene().getWindow();
            loginStage.close();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/Cashier.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1525, 782);

            // Get the AdminController and pass the username to it
            CashierController cashierController = fxmlLoader.getController();
            cashierController.setUserName(username);

            // Show the new stage
            Stage adminStage = new Stage();
            adminStage.setTitle("Cashier Stage");
            adminStage.setScene(scene);
            adminStage.show();

            // Close the login stage
            Stage loginStage = (Stage) tfUserName.getScene().getWindow();
            loginStage.close();
        }
    }


    //Using hash
//    public void login() throws IOException {
//        String username = tfUserName.getText();
//        String password = tfPass.getText();
//
//        if (username.isEmpty() || password.isEmpty()) {
//            showAlert("Error", "Username and password cannot be empty.");
//            return;
//        }
//
//        // Hash the entered password
//        String hashedPassword = PasswordHash.hashPassword(password);
//
//        // Fetch user by username and hashed password
//        User user = UserRepository.getUserByUsernameAndPassword(username, hashedPassword);
//
//        if (user == null) {
//            showAlert("Login Failed", "Invalid username or password.");
//        } else if (user.getRole().equalsIgnoreCase("Admin")) {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/admin.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 1525, 782);
//
//            AdminController adminController = fxmlLoader.getController();
//            adminController.setUserName(username);
//
//            Stage adminStage = new Stage();
//            adminStage.setTitle("Admin Stage");
//            adminStage.setScene(scene);
//            adminStage.show();
//
//            Stage loginStage = (Stage) tfUserName.getScene().getWindow();
//            loginStage.close();
//        } else {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/Cashier.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 1525, 782);
//
//            CashierController cashierController = fxmlLoader.getController();
//            cashierController.setUserName(username);
//
//            Stage cashierStage = new Stage();
//            cashierStage.setTitle("Cashier Stage");
//            cashierStage.setScene(scene);
//            cashierStage.show();
//
//            Stage loginStage = (Stage) tfUserName.getScene().getWindow();
//            loginStage.close();
//        }
//    }


    /**
     * showAlert method that will show an alert.
     * */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}