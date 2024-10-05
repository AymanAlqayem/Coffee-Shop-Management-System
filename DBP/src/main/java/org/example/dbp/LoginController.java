package org.example.dbp;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
            Scene scene = new Scene(fxmlLoader.load(), 800, 550);
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
                String userName = resultSet.getString("user_name");

                showAlert("Login Successful", "Welcome, " + userName);

                // Display user's information or navigate to the main application
            } else {
                // No user found
                showAlert("Login Failed", "Invalid username or password.");
            }

            // Close the connection
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Unable to connect to the database.");
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


