package org.example.dbp.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXComboBox;
import org.example.dbp.models.User;
import org.example.dbp.repository.InvoiceRepo;
import org.example.dbp.repository.OrderRepo;
import org.example.dbp.repository.SpecialRepo;
import org.example.dbp.repository.UserRepository;
import javafx.util.StringConverter;
import org.example.dbp.models.Customer;
import org.example.dbp.repository.CustomerRepository;


import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class AdminController {

    //Forms.
    @FXML
    private AnchorPane tableCustomerPane;
    @FXML
    private TableView tableCustomer;
    @FXML
    private TableView tableUser;

    @FXML
    public AnchorPane dashBoard_form;
    @FXML
    private AnchorPane addNewEmployee_form;

    @FXML
    private AnchorPane menuForm;
    @FXML
    private AnchorPane tableUserPane;

    @FXML
    private AnchorPane specialInfoForm;


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
    private JFXButton btSpecialInfo;

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

    //Controllers in Dashboard form.
    @FXML
    private Label numberOfCustomerLabel;
    @FXML
    private Label numberOfSoldProductLabel;
    @FXML
    private Label todayIncomeLabel;
    @FXML
    private Label totalIncomeLabel;

    @FXML
    private BarChart<String, Number> customerBarChart;

    @FXML
    private LineChart<String, Number> incomeChart;

    //Controllers in special Info form.
    @FXML
    JFXButton totalSalesPerCahierButton;
    @FXML
    JFXButton cashierWithHighestInvoiceButton;
    @FXML
    JFXButton mostPopularMenuItemButton;
    @FXML
    JFXButton totalRevenuePerCategory2024Button;
    @FXML
    JFXButton totalOrdersPerCustomerButton;
    @FXML
    JFXButton topSellingMenuItemButton;
    @FXML
    JFXButton customerWithNoPurchasesButton;
    @FXML
    JFXButton ordersRevenuePerDayButton;
    @FXML
    TextArea resultTextArea;


    public void initialize() {
        // Populate the combo box with options
        roleComboBox.getItems().addAll("Admin", "Cashier");

        //get the total number of customers for the last day.
        numberOfCustomerLabel.setText(OrderRepo.totalNumberOfCustomers() + " ");

        //get the total amount.
        totalIncomeLabel.setText(InvoiceRepo.getTotalAmount() + " NIS");

        //get the last day total income.
        todayIncomeLabel.setText(InvoiceRepo.getLastDayTotalAmount() + " NIS");

        //get the unique number of sold products.
        numberOfSoldProductLabel.setText(OrderRepo.numberOfSoldProduct() + "");

        resultTextArea.setEditable(false);
        this.setupCustomerBarChart();
        this.setupIncomeChart();
    }

    /**
     * setupCustomerBarChart method that will initialize the customer Bar chart.
     * */
    public void setupCustomerBarChart() {
        // Clear previous data
        customerBarChart.getData().clear();
        customerBarChart.setTitle("Daily Customer Statistics");

        // Create a new series for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Number of Customers");

        // Get data from the database
        List<Map.Entry<String, Integer>> customerDataList = OrderRepo.customerPerDate();
        for (Map.Entry<String, Integer> entry : customerDataList) {
            String date = entry.getKey();
            int customerCount = entry.getValue();
            // Add data points to the series
            series.getData().add(new XYChart.Data<>(date, customerCount));
        }

        // Add the series to the chart
        customerBarChart.getData().add(series);
    }

    /**
     * setupIncomeChart method that will initialize the income line chart.
     * */
    public void setupIncomeChart() {
        incomeChart.getData().clear();
        incomeChart.setTitle("Daily Income Trends");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Income");

        // Get data from the database
        List<Map.Entry<String, Double>> customerDataList = InvoiceRepo.amountPerDate();

        for (Map.Entry<String, Double> entry : customerDataList) {
            String date = entry.getKey();
            double amount = entry.getValue();
            series.getData().add(new XYChart.Data<>(date, amount));
        }

        incomeChart.getData().add(series);
    }

    /**
     * comboOptions method that will set the Options in the combobox.
     */
    @FXML
    public void comboOptions(ActionEvent e) {
        // Get the selected option when an action occurs
        String selectedOption = roleComboBox.getSelectionModel().getSelectedItem();
    }

    /**
     * makeActionsForSignOutButton method that will make actions for sign-out button.
     */

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
     */
    public void switchWin(ActionEvent e) throws IOException {
        if (e.getSource() == btDashboard) {
            tableUserPane.setVisible(false);
            tableCustomerPane.setVisible(false);
            tableCustomer.setVisible(false);
            tableUser.setVisible(false);


            dashBoard_form.setVisible(true);
            addNewEmployee_form.setVisible(false);
            menuForm.setVisible(false);
            tableUserPane.setVisible(false);

            specialInfoForm.setVisible(false);
        } else if (e.getSource() == btAddNewRole) {
            tableUserPane.setVisible(false);
            tableCustomerPane.setVisible(false);
            tableCustomer.setVisible(false);
            tableUser.setVisible(false);

            dashBoard_form.setVisible(false);
            addNewEmployee_form.setVisible(true);
            menuForm.setVisible(false);
            tableUserPane.setVisible(false);

            specialInfoForm.setVisible(false);
        } else if (e.getSource() == btMenu) {
            tableUserPane.setVisible(false);
            tableCustomerPane.setVisible(false);
            tableCustomer.setVisible(false);
            tableUser.setVisible(false);

            dashBoard_form.setVisible(false);
            addNewEmployee_form.setVisible(false);
            menuForm.setVisible(true);
            tableUserPane.setVisible(false);
            specialInfoForm.setVisible(false);
            loadAdminMenu(); // Call the method to load the Admin Menu FXML
        } else if (e.getSource() == btSpecialInfo) {
            dashBoard_form.setVisible(false);
            menuForm.setVisible(false);
            addNewEmployee_form.setVisible(false);
            specialInfoForm.setVisible(true);
        }
    }


    /**
     * switchInSpecialInfoForm method that will switch the buttons in the special info form.
     * */
    public void switchInSpecialInfoForm(ActionEvent e) {
        resultTextArea.setFont(Font.font("Book Antiqua", FontWeight.BOLD, 20));

        if (e.getSource() == totalSalesPerCahierButton) {
            resultTextArea.clear();
            List<String> resultList = SpecialRepo.getTotalSalesPerCashier();
            for (String result : resultList) {
                resultTextArea.appendText(result + '\n');
            }


        } else if (e.getSource() == cashierWithHighestInvoiceButton) {
            resultTextArea.clear();
            List<String> resultList = SpecialRepo.getCashierWithHighestAverageInvoiceAmount();
            for (String result : resultList) {
                resultTextArea.appendText(result + '\n');
            }


        } else if (e.getSource() == mostPopularMenuItemButton) {
            resultTextArea.clear();
            List<String> resultList = SpecialRepo.getMostPopularMenuItems();
            for (String result : resultList) {
                resultTextArea.appendText(result + '\n');
            }


        } else if (e.getSource() == totalRevenuePerCategory2024Button) {
            resultTextArea.clear();
            List<String> resultList = SpecialRepo.getTotalRevenuePerCategoryIn2024();
            for (String result : resultList) {
                resultTextArea.appendText(result + '\n');
            }


        } else if (e.getSource() == totalOrdersPerCustomerButton) {
            resultTextArea.clear();
            List<String> resultList = SpecialRepo.getTotalOrdersAndRevenuePerCustomer();
            for (String result : resultList) {
                resultTextArea.appendText(result + '\n');
            }

        } else if (e.getSource() == topSellingMenuItemButton) {
            resultTextArea.clear();
            List<String> resultList = SpecialRepo.getTopSellingMenuItemsByRevenue();
            for (String result : resultList) {
                resultTextArea.appendText(result + '\n');
            }

        } else if (e.getSource() == customerWithNoPurchasesButton) {
            resultTextArea.clear();
            List<String> resultList = SpecialRepo.getCustomersWithNoPurchases();
            for (String result : resultList) {
                resultTextArea.appendText(result + '\n');
            }

        } else if (e.getSource() == ordersRevenuePerDayButton) {
            resultTextArea.clear();
            List<String> resultList = SpecialRepo.getOrdersAndRevenuePerDay();
            for (String result : resultList) {
                resultTextArea.appendText(result + '\n');
            }
        }
    }


    /**
     * loadAdminMenu method that will load the admin menu.
     */
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


    public void showCustomer(ActionEvent e) throws IOException {
        //    tableCustomerPane.getChildren().clear();
        tableCustomer.getColumns().clear();
        tableCustomer.setVisible(true);


        dashBoard_form.setVisible(false);
        addNewEmployee_form.setVisible(false);
        menuForm.setVisible(false);
        tableUserPane.setVisible(false);
        tableCustomerPane.setVisible(true);

        tableCustomerPane.setPadding(new Insets(10, 10, 10, 10));

        ObservableList<Customer> dataList = FXCollections.observableArrayList(CustomerRepository.getAllCustomers());

        Label label = new Label("Customer Table");
        label.setFont(new Font("Arial", 20));
        label.setTextFill(Color.BLUE);
        BorderPane.setAlignment(label, Pos.CENTER);


        tableCustomer.setEditable(true);
        tableCustomer.setMaxHeight(4000);
        tableCustomer.setMaxWidth(4000);

        TableColumn<Customer, String> nameCol = buildTableColumnStringCustomer("customerName", true, 140);
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());


        TableColumn<Customer, Long> phoneCol = buildTableColumnLongUCustomer("customerPhone", true, 100);
        phoneCol.setCellFactory(column -> new TextFieldTableCell<>(new StringConverter<Long>() {
            @Override
            public String toString(Long object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public Long fromString(String string) {
                try {
                    return Long.parseLong(string);
                } catch (NumberFormatException e) {
                    return null; // Or handle invalid input gracefully
                }
            }
        }));
        phoneCol.setOnEditCommit(event -> {
            TableColumn.CellEditEvent<Customer, Long> cellEditEvent = event;
            Customer customer = cellEditEvent.getRowValue();

            Long newValue = cellEditEvent.getNewValue();
            if (newValue != null) {
                customer.setCustomerPhone(newValue);
                // Update the database
                updateRowByKeyCustomer(customer.getId(), "phone_number", newValue.toString());
            } else {
                System.out.println("Invalid phone number input");
            }
        });
        tableCustomer.setItems(dataList);
        tableCustomer.getColumns().addAll(nameCol, phoneCol);


        tableCustomer.setMaxWidth(Double.MAX_VALUE);
        tableCustomer.setMaxHeight(Double.MAX_VALUE);

        tableCustomer.setRowFactory(tv -> {

            TableRow<Customer> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem deleteItem = new MenuItem("Delete");
            contextMenu.getItems().add(deleteItem);
            deleteItem.setOnAction(actionEvent -> {
                boolean confirmed = showConfirmationDialog("Are you sure you want to delete this item?");
                if (confirmed) {
                    Customer selectedCustomer = row.getItem();
                    if (selectedCustomer != null) {
                        Customer deletedCustomer = CustomerRepository.deleteRowByKey(selectedCustomer.getId());
                        if (deletedCustomer != null) {
                            // Remove the deleted user from the TableView's observable list
                            tableCustomer.getItems().remove(selectedCustomer);
                            System.out.println("Row deleted successfully.");
                        }
                    }
                }
            });

            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(
                                    javafx.beans.binding.Bindings.isNotNull(row.itemProperty()))
                            .then(contextMenu)
                            .otherwise((ContextMenu) null)
            );

            return row;
        });


        // tableCustomerPane.getChildren().add(tableCustomer);

    }


    public void showUsers(ActionEvent e) throws IOException {
        // tableUserPane.getChildren().clear();
        tableUser.getColumns().clear();
        tableUser.setVisible(true);

        dashBoard_form.setVisible(false);
        addNewEmployee_form.setVisible(false);
        menuForm.setVisible(false);
        tableCustomerPane.setVisible(false);
        tableUserPane.setVisible(true);


        tableUserPane.setPadding(new Insets(10, 10, 10, 10));

        ObservableList<User> dataList = FXCollections.observableArrayList(UserRepository.getAllUsers());

        Label label = new Label("User Table");
        label.setFont(new Font("Arial", 20));
        label.setTextFill(Color.BLUE);
        // tableUserPane.setTop(label);
        BorderPane.setAlignment(label, Pos.CENTER);


        tableUser.setEditable(true);
        tableUser.setMaxHeight(4000);
        tableUser.setMaxWidth(4000);

        TableColumn<User, String> nameCol = buildTableColumnStringUser("name", true, 140);
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<User, String> roleCol = buildTableColumnStringUser("role", true, 140);
        roleCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<User, String> emailCol = buildTableColumnStringUser("email", true, 140);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<User, Date> hireDateCol = buildTableColumnDate("hireDate", true, 100);
        hireDateCol.setCellFactory(column -> new TableCell<>() {
            private final DatePicker datePicker = new DatePicker();

            {
                datePicker.setOnAction(event -> {
                    Date newDate = java.sql.Date.valueOf(datePicker.getValue());
                    User user = getTableView().getItems().get(getIndex());
                    user.setHireDate(newDate);
                    // Call method to update the database
                    updateRowByKeyUser(user.getId(), "hire_date", newDate.toString());
                });
            }

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    datePicker.setValue(LocalDate.parse(item.toString()));
                    setGraphic(datePicker);
                    setText(null);
                }
            }
        });


        TableColumn<User, Long> phoneCol = buildTableColumnLongUser("phoneNumber", true, 100);
        phoneCol.setCellFactory(column -> new TextFieldTableCell<>(new StringConverter<Long>() {
            @Override
            public String toString(Long object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public Long fromString(String string) {
                try {
                    return Long.parseLong(string);
                } catch (NumberFormatException e) {
                    return null; // Or handle invalid input gracefully
                }
            }
        }));
        phoneCol.setOnEditCommit(event -> {
            TableColumn.CellEditEvent<User, Long> cellEditEvent = event;
            User user = cellEditEvent.getRowValue();

            Long newValue = cellEditEvent.getNewValue();
            if (newValue != null) {
                user.setPhoneNumber(newValue);
                // Update the database
                updateRowByKeyUser(user.getId(), "phone_number", newValue.toString());
            } else {
                System.out.println("Invalid phone number input");
            }
        });

        TableColumn<User, Double> salaryCol = buildTableColumnDoubleUser("salary", true, 100);
        salaryCol.setCellFactory(column -> new TextFieldTableCell<>(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public Double fromString(String string) {
                try {
                    return Double.parseDouble(string);
                } catch (NumberFormatException e) {
                    return null; // Or handle invalid input gracefully
                }
            }
        }));

        salaryCol.setOnEditCommit(event -> {
            TableColumn.CellEditEvent<User, Double> cellEditEvent = event;
            User user = cellEditEvent.getRowValue();

            Double newValue = cellEditEvent.getNewValue();
            if (newValue != null) {
                user.setSalary(newValue);
                // Update the database
                UserRepository.updateRowByKey(user.getId(), "salary", newValue.toString());
            } else {
                System.out.println("Invalid salary input");
            }
        });


        tableUser.setItems(dataList);
        tableUser.getColumns().addAll(nameCol, roleCol, emailCol, hireDateCol, phoneCol, salaryCol);


        tableUser.setMaxWidth(Double.MAX_VALUE);
        tableUser.setMaxHeight(Double.MAX_VALUE);

        tableUser.setRowFactory(tv -> {

            TableRow<User> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem deleteItem = new MenuItem("Delete");
            contextMenu.getItems().add(deleteItem);
            deleteItem.setOnAction(actionEvent -> {
                boolean confirmed = showConfirmationDialog("Are you sure you want to delete this item?");
                if (confirmed) {
                    User selectedUser = row.getItem();
                    if (selectedUser != null) {
                        User deletedUser = UserRepository.deleteRowByKey(selectedUser.getId());
                        if (deletedUser != null) {
                            // Remove the deleted user from the TableView's observable list
                            tableUser.getItems().remove(selectedUser);
                            System.out.println("Row deleted successfully.");
                        }
                    }
                }
            });

            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(
                                    javafx.beans.binding.Bindings.isNotNull(row.itemProperty()))
                            .then(contextMenu)
                            .otherwise((ContextMenu) null)
            );

            return row;
        });



    }

    public void updateRowByKeyUser(int id, String col, String val) {
        UserRepository.updateRowByKey(id, col, val);
    }

    public void updateRowByKeyCustomer(int id, String col, String val) {
        CustomerRepository.updateRowByKey(id, col, val);
    }


    private TableColumn<User, String> buildTableColumnStringUser(String colname, boolean editable, int width) {
        TableColumn<User, String> someCol = new TableColumn<>(colname);
        someCol.setMinWidth(width);
        someCol.setCellValueFactory(new PropertyValueFactory<>(colname));
        if (editable) {
            someCol.setCellFactory(forTableColumn());
            someCol.setOnEditCommit((TableColumn.CellEditEvent<User, String> t) -> {
                ((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCol(colname, t.getNewValue());
                updateRowByKeyUser((t.getRowValue().getId()), colname, String.valueOf(t.getNewValue()));
            });
        }
        return someCol;
    }

    private TableColumn<Customer, String> buildTableColumnStringCustomer(String colname, boolean editable, int width) {
        TableColumn<Customer, String> someCol = new TableColumn<>(colname);
        someCol.setMinWidth(width);
        someCol.setCellValueFactory(new PropertyValueFactory<>(colname));
        if (editable) {
            someCol.setCellFactory(forTableColumn());
            someCol.setOnEditCommit((TableColumn.CellEditEvent<Customer, String> t) -> {
                ((Customer) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCol(colname, t.getNewValue());
                updateRowByKeyCustomer((t.getRowValue().getId()), colname, String.valueOf(t.getNewValue()));
            });
        }
        return someCol;
    }

    private TableColumn<User, Date> buildTableColumnDate(String colname, boolean editable, int width) {
        TableColumn<User, Date> someCol = new TableColumn<>(colname);
        someCol.setMinWidth(width);
        someCol.setCellValueFactory(new PropertyValueFactory<>(colname));
        if (editable) {

            someCol.setOnEditCommit((TableColumn.CellEditEvent<User, Date> t) -> {
                ((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCol(colname, t.getNewValue());
                updateRowByKeyUser((t.getRowValue().getId()), colname, String.valueOf(t.getNewValue()));
            });
        }
        return someCol;
    }

    private TableColumn<User, Long> buildTableColumnLongUser(String colname, boolean editable, int width) {
        TableColumn<User, Long> someCol = new TableColumn<>(colname);
        someCol.setMinWidth(width);
        someCol.setCellValueFactory(new PropertyValueFactory<>(colname));
        if (editable) {

            someCol.setOnEditCommit((TableColumn.CellEditEvent<User, Long> t) -> {
                ((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCol(colname, t.getNewValue());
                updateRowByKeyUser((t.getRowValue().getId()), colname, String.valueOf(t.getNewValue()));
            });
        }
        return someCol;
    }

    private TableColumn<User, Double> buildTableColumnDoubleUser(String colname, boolean editable, int width) {
        TableColumn<User, Double> someCol = new TableColumn<>(colname);
        someCol.setMinWidth(width);
        someCol.setCellValueFactory(new PropertyValueFactory<>(colname));
        if (editable) {

            someCol.setOnEditCommit((TableColumn.CellEditEvent<User, Double> t) -> {
                ((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCol(colname, t.getNewValue());
                updateRowByKeyUser((t.getRowValue().getId()), colname, String.valueOf(t.getNewValue()));
            });
        }
        return someCol;
    }

    private TableColumn<Customer, Long> buildTableColumnLongUCustomer(String colname, boolean editable, int width) {
        TableColumn<Customer, Long> someCol = new TableColumn<>(colname);
        someCol.setMinWidth(width);
        someCol.setCellValueFactory(new PropertyValueFactory<>(colname));
        if (editable) {

            someCol.setOnEditCommit((TableColumn.CellEditEvent<Customer, Long> t) -> {
                ((Customer) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCol(colname, t.getNewValue());
                updateRowByKeyCustomer((t.getRowValue().getId()), colname, String.valueOf(t.getNewValue()));
            });
        }
        return someCol;
    }


    public boolean showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * addNewEmployee method that will add new Employee.
     * */
    public void addNewEmployee(ActionEvent event) {
        if (tfEmployeeName.getText().isEmpty() || employeeEmail.getText().isEmpty() || employeeHireDate.getValue() == null || employeeSalary.getText().isEmpty() || roleComboBox.getSelectionModel().getSelectedItem() == null || employeePhoneNumber.getText().isEmpty() || employeePassword.getText().isEmpty()) {
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
        User user = new User(tfEmployeeName.getText(), roleComboBox.getValue(), employeeEmail.getText(), hireDate, employeePhoneNumber.getText(), hashedPassword, convertStringToDouble(employeeSalary.getText()));

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
     */
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
