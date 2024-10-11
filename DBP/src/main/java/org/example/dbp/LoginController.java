package org.example.dbp;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.sql.*;

public class LoginController extends Application {
    @FXML
    TextField tfUserName;
    @FXML
    PasswordField tfPass;
    @FXML
    JFXButton btLogin;


    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1378, 750);
            stage.setTitle("Log in");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // This will help you identify issues during runtime.
        }

    }


    public void login() {
        String username = tfUserName.getText();
        String password = tfPass.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and password cannot be empty.");
            return;
        }

        // Database connection and validation
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dpproject", "root", "root");
            String query = "SELECT * FROM usert WHERE user_name = ? AND pass = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Load the Admin stage
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/admin.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1525, 782);

                // Show the new stage
                Stage adminStage = new Stage();
                adminStage.setTitle("Admin Stage");
                adminStage.setScene(scene);
                adminStage.show();

                // Close the login stage
                Stage loginStage = (Stage) tfUserName.getScene().getWindow();
                loginStage.close();
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }

            // Close the connection
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

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