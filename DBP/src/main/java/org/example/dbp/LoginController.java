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

//                showAlert("Login Successful", "Welcome, " + userName);
                adminStage(username);
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

    @FXML
    Label lbName = new Label();

    public void adminStage(String adminName) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dbp/admin.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 894, 687);
            stage.setTitle("Admin Stage");
            stage.setScene(scene);
            lbName.setText(adminName);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // This will help you identify issues during runtime.
        }

    }


    @FXML
    private JFXButton btDash = new JFXButton();

    @FXML
    private JFXButton btInventory = new JFXButton();

    @FXML
    private JFXButton btMenu = new JFXButton();

    @FXML
    private AnchorPane dash_form = new AnchorPane();
    @FXML
    private AnchorPane inventor_form;



    public void switchWin(ActionEvent e) {
        if (e.getSource() == btDash){
            System.out.println("Dash Form") ;
            dash_form.setVisible(true);
            inventor_form.setVisible(false);
        } else if (e.getSource() == btInventory){
            System.out.println("Inventor Form") ;
            dash_form.setVisible(false);
            inventor_form.setVisible(true);
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


