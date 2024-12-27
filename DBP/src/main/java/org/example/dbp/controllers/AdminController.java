package org.example.dbp.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXComboBox;
import org.example.dbp.models.User;
import org.example.dbp.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
    private JFXComboBox<String> roleComboBox;

    @FXML
    JFXButton btAddNewEmployee;
    @FXML
    private TextField tfEmployeeName;
    @FXML
    private TextField employeeEmail;

    @FXML
    private DatePicker employeeHireDate;

    @FXML
    private TextField employeeSalary;
    @FXML
    private TextField employeePhoneNumber;
    @FXML
    private TextField employeePassword;


    public void initialize() {
        // Populate the combo box with options
        roleComboBox.getItems().addAll("Admin", "Cashier");

        // Add close request handler for the admin stage
//        Platform.runLater(() -> {
//            Stage stage = (Stage) lbUserName.getScene().getWindow();
//            stage.setOnCloseRequest(event -> {
//                event.consume();
//                closeConfirmation(stage);
//            });
//        });
    }

    /**
     * comboOptions method that will set the Options in the combobox.
     * */
    @FXML
    public void comboOptions(ActionEvent e) {
        // Get the selected option when an action occurs
        String selectedOption = roleComboBox.getSelectionModel().getSelectedItem();
    }

    /**
     * makeActionsForSignOutButton method that will make actions for sign-out button.
     * */

    public void makeActionsForSignOutButton(ActionEvent event) {
        //close current stage.
        ((Stage) lbUserName.getScene().getWindow()).close();
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
            loadAdminMenu(); // Call the method to load the Admin Menu FXML
        }
    }

    /**
     * loadAdminMenu method that will load the admin menu.
     * */
    private void loadAdminMenu() {
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

    /**
     * addNewEmployee method that will add new Employee.
     * */
    public void addNewEmployee(ActionEvent event) {
        if (tfEmployeeName.getText().isEmpty() || employeeEmail.getText().isEmpty() ||
                employeeHireDate.getValue() == null || employeeSalary.getText().isEmpty()
                || roleComboBox.getSelectionModel().getSelectedItem() == null || employeePhoneNumber.getText().isEmpty()
                || employeePassword.getText().isEmpty()) {
            showErrorAlert("Invalid Input", "Invalid Input,try again");
            return;
        }
        //Check if the email already exists.
        if (UserRepository.isEmailExist(employeeEmail.getText())) {
            showErrorAlert("Invalid Input", "This Employee Email already exists!");
            return;
        }
        //Check if the phone number is valid.
        if (employeePhoneNumber.getText().length() != 10 || convertStringToInt(employeePhoneNumber.getText()) == -1) {
            showErrorAlert("Invalid Input", "Invalid Phone Number,it should be 10 digit, and it should be a numbers.!");
            return;
        }

        //Check if the salary is a valid number.
        if (convertStringToDouble(employeeSalary.getText()) == null || convertStringToDouble(employeeSalary.getText()) <= 0) {
            showErrorAlert("Invalid Input", "Please enter a valid positive salary!");
            return;
        }

        //Check if the password length is valid.
        if (employeePassword.getText().length() < 8) {
            showErrorAlert("Invalid Input", "Please enter a valid password(Must be 8 characters or longer. )!");
            return;
        }

        String hashedPassword = PasswordHash.hashPassword(employeePassword.getText());

        //Convert hire date to Date.
        LocalDate localDate = employeeHireDate.getValue();
        Date hireDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        //Create new user.
        User user = new User(tfEmployeeName.getText(), roleComboBox.getValue(), employeeEmail.getText(),
                hireDate, employeePhoneNumber.getText(), hashedPassword,
                convertStringToDouble(employeeSalary.getText()));

        //Add the user to DB.
        UserRepository.addNewEmployee(user);

        successAlert("Add Successfully", "Employee added successfully!");
        tfEmployeeName.clear();
        employeeEmail.clear();
        employeeHireDate.setValue(null);
        employeeSalary.clear();
        employeePhoneNumber.clear();
        employeePassword.clear();
        roleComboBox.getSelectionModel().clearSelection();

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
        lbUserName.setText(userName);
    }

    /**
     * convertStringToDouble method that will convert from string to double.
     * */
    public static Double convertStringToDouble(String str) {
        try {
            // Try to parse the String to a double
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null; // Return null or handle as appropriate
        }
    }

    /**
     * convertStringToInt method that will convert from Sting to int.
     * */
    public static int convertStringToInt(String str) {
        try {
            // Try to parse the String to a double
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1; // Return null or handle as appropriate
        }
    }
}
