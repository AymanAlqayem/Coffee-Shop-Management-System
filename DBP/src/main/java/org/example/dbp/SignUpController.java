package org.example.dbp;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//import com.jfoenix.controls.JFXTextField;
//import com.jfoenix.controls.JFXPasswordField;

public class SignUpController extends Application {

    @FXML
    TextField tfFirstName;
    @FXML
    TextField tfLastName;
    @FXML
    TextField tfUserName;
    @FXML
    PasswordField tfPass;
    @FXML
    PasswordField tfConfirm;
    @FXML
    Button btReg;
    @FXML
    Button btClose;
    @FXML
    Text login;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SignUpController.class.getResource("/regProject/signUp.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 750, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // This will help you identify issues during runtime.
        }

    }

    public void register(ActionEvent e) {
        boolean correct = true;

        if (tfFirstName.getText().isEmpty()) {
            showAlert("Error, First name cannot be empty.");
            correct = false;
        }

        if (tfLastName.getText().isEmpty()) {
            showAlert("Error, Last name cannot be empty.");
            correct = false;
        }

        if (tfUserName.getText().isEmpty()) {
            showAlert("Error, User name cannot be empty.");
            correct = false;
        }

        if (tfPass.getText().isEmpty()) {
            showAlert("Error, Password cannot be empty.");
            correct = false;
        }

        if (isPassWeak(tfPass.getText())) {
            showAlert("Error, Password is weak.");
            correct = false;
        }

        if (!tfConfirm.getText().equals(tfPass.getText())) {
            showAlert("Error, Passwords do not match.");
            correct = false;  // Ensure correct is set to false here
        }

        // Stop execution if any validation failed
        if (!correct) {
            return;  // Return early to prevent the registration logic from running
        }

        if (correct) {
            User user = new User(tfFirstName.getText(), tfLastName.getText(), tfUserName.getText(), tfPass.getText());

            try {
                // Establish connection
                Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/regproject", "root", "root");

                // Prepare the SQL statement
                String sql = "INSERT INTO user_table (first_name, last_name, user_name, pass) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                // Set the values for the PreparedStatement
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getUserName());
                preparedStatement.setString(4, user.getPass());

                // Execute the insert statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    showAlert("Registration Successful");
                } else {
                    showAlert("Error: Could not register user.");
                }

                // Clear the input fields
                tfFirstName.clear();
                tfLastName.clear();
                tfUserName.clear();
                tfPass.clear();
                tfConfirm.clear();

                // Close the connection and statement
                preparedStatement.close();
                connection.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Error: Database connection problem.");
            }
        }
    }


    public boolean isPassWeak(String password) {
        // Define the minimum length
        int minLength = 8;

        // Check for minimum length
        if (password.length() < minLength) {
            return true;
        }

        // Check for at least one uppercase letter, one lowercase letter, one digit, and one special character
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        // If any of the required conditions are not met, the password is weak
        return !(hasUppercase && hasLowercase && hasDigit && hasSpecialChar);
    }


    public void close(ActionEvent e) {
        // Get the current stage from the event source
        Node source = (Node) e.getSource(); // Get the source node of the event
        Stage stage = (Stage) source.getScene().getWindow(); // Get the stage from the scene

        // Close the stage
        stage.close();
    }

    public static void showAlert(String content) {
        Alert alert;

        // Check if the content contains a keyword indicating success
        if (content.toLowerCase().contains("success")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
        } else {
            // Default to an error alert for other content
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
        }

        alert.setContentText(content); // Set the content text (message)
        alert.showAndWait(); // Show the alert and wait for user to dismiss it
    }

    public static void main(String[] args) {
        launch();
    }
}

