package org.example.dbp.repository;

import org.example.dbp.db.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecialRepo {

    public static List<String> getTotalSalesPerCashier() {
        List<String> resultList = new ArrayList<>();
        String query = "SELECT u.name AS cashier_name, SUM(i.amount) AS total_sales "
                + "FROM User u "
                + "JOIN Invoice i ON u.id = i.cashier_id "
                + "GROUP BY u.name "
                + "ORDER BY total_sales DESC";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String cashierName = resultSet.getString("cashier_name");
                double totalSales = resultSet.getDouble("total_sales");
                resultList.add(cashierName + ": " + totalSales);
            }

        } catch (SQLException e) {
        }

        return resultList;
    }  //Used

    public static List<String> getMostPopularMenuItems() {
        List<String> resultList = new ArrayList<>();
        String query = "SELECT m.item_name, SUM(ol.ordered_Quantity) AS total_quantity_sold "
                + "FROM Order_Line ol "
                + "JOIN menu_item m ON ol.menu_item_id = m.id "
                + "GROUP BY m.item_name "
                + "ORDER BY total_quantity_sold DESC";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String itemName = resultSet.getString("item_name");
                int totalQuantitySold = resultSet.getInt("total_quantity_sold");
                resultList.add(itemName + ": " + totalQuantitySold);
            }

        } catch (SQLException e) {
        }

        return resultList;
    } //Used


    public static List<String> getTotalRevenuePerCategoryIn2024() {
        List<String> resultList = new ArrayList<>();
        String query = "SELECT c.name AS category_name, SUM(ol.ordered_Quantity * m.price) AS total_revenue "
                + "FROM Invoice i "
                + "JOIN Order_table o ON i.order_id = o.id "
                + "JOIN Order_Line ol ON o.id = ol.order_id "
                + "JOIN menu_item m ON ol.menu_item_id = m.id "
                + "JOIN category c ON m.category_id = c.id "
                + "WHERE YEAR(i.created_date_time) = 2024 "
                + "GROUP BY c.name "
                + "ORDER BY total_revenue DESC";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String categoryName = resultSet.getString("category_name");
                double totalRevenue = resultSet.getDouble("total_revenue");
                resultList.add(categoryName + ": " + totalRevenue);
            }

        } catch (SQLException e) {
        }

        return resultList;
    } // Used


    public static List<String> getTotalOrdersAndRevenuePerCustomer() {
        List<String> resultList = new ArrayList<>();
        String query = "SELECT c.customer_name, COUNT(o.id) AS total_orders, "
                + "SUM(ol.ordered_Quantity * m.price) AS total_spent "
                + "FROM Customer c "
                + "JOIN Order_table o ON c.id = o.customer_id "
                + "JOIN Order_Line ol ON o.id = ol.order_id "
                + "JOIN menu_item m ON ol.menu_item_id = m.id "
                + "GROUP BY c.customer_name "
                + "ORDER BY total_spent DESC";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String customerName = resultSet.getString("customer_name");
                int totalOrders = resultSet.getInt("total_orders");
                double totalSpent = resultSet.getDouble("total_spent");
                resultList.add(customerName + ": " + totalOrders + " orders, Total Spent: " + totalSpent);
            }

        } catch (SQLException e) {
        }

        return resultList;
    } //Used


    public static List<String> getCashierWithHighestAverageInvoiceAmount() {
        List<String> resultList = new ArrayList<>();
        String query = "SELECT u.name AS cashier_name, AVG(i.amount) AS average_invoice_amount "
                + "FROM User u "
                + "JOIN Invoice i ON u.id = i.cashier_id "
                + "GROUP BY u.name "
                + "ORDER BY average_invoice_amount DESC "
                + "LIMIT 1";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                String cashierName = resultSet.getString("cashier_name");
                double averageInvoiceAmount = resultSet.getDouble("average_invoice_amount");
                resultList.add(cashierName + ": " + averageInvoiceAmount);
            }

        } catch (SQLException e) {
        }

        return resultList;
    } //Used


    public static List<String> getOrdersAndRevenuePerDay() {
        List<String> resultList = new ArrayList<>();
        String query = "SELECT DATE(o.created_date_time) AS order_date, "
                + "COUNT(o.id) AS total_orders, "
                + "SUM(ol.ordered_Quantity * m.price) AS total_revenue "
                + "FROM Order_table o "
                + "JOIN Order_Line ol ON o.id = ol.order_id "
                + "JOIN menu_item m ON ol.menu_item_id = m.id "
                + "GROUP BY order_date "
                + "ORDER BY order_date DESC";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String orderDate = resultSet.getString("order_date");
                int totalOrders = resultSet.getInt("total_orders");
                double totalRevenue = resultSet.getDouble("total_revenue");
                resultList.add(orderDate + ": " + totalOrders + " orders, Total Revenue: " + totalRevenue);
            }

        } catch (SQLException e) {
        }

        return resultList;
    }//Used


    public static List<String> getCustomersWithNoPurchases() {
        List<String> resultList = new ArrayList<>();
        String query = "SELECT c.customer_name "
                + "FROM Customer c "
                + "LEFT JOIN Order_table o ON c.id = o.customer_id "
                + "WHERE o.id IS NULL";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String customerName = resultSet.getString("customer_name");
                resultList.add(customerName);
            }

        } catch (SQLException e) {
        }

        return resultList;
    } //Used


    public static List<String> getTopSellingMenuItemsByRevenue() {
        List<String> resultList = new ArrayList<>();
        String query = "SELECT m.item_name, SUM(ol.ordered_Quantity * m.price) AS total_revenue "
                + "FROM Order_Line ol "
                + "JOIN menu_item m ON ol.menu_item_id = m.id "
                + "JOIN Order_table o ON ol.order_id = o.id "
                + "JOIN Invoice i ON o.id = i.order_id "
                + "WHERE YEAR(i.created_date_time) = 2024 "
                + "GROUP BY m.item_name "
                + "ORDER BY total_revenue DESC "
                + "LIMIT 5";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String itemName = resultSet.getString("item_name");
                double totalRevenue = resultSet.getDouble("total_revenue");
                resultList.add(itemName + ": " + totalRevenue);
            }

        } catch (SQLException e) {
        }

        return resultList;
    } //Used




    public static List<String> getMostActiveCashierByDate() {
        List<String> resultList = new ArrayList<>();
        String query = "SELECT DATE(i.created_date_time) AS order_date, u.name AS cashier_name, "
                + "COUNT(o.id) AS total_orders "
                + "FROM Invoice i "
                + "JOIN User u ON i.cashier_id = u.id "
                + "JOIN Order_table o ON i.order_id = o.id "
                + "GROUP BY order_date, u.name "
                + "ORDER BY order_date DESC, total_orders DESC";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String orderDate = resultSet.getString("order_date");
                String cashierName = resultSet.getString("cashier_name");
                int totalOrders = resultSet.getInt("total_orders");
                resultList.add(orderDate + " - " + cashierName + ": " + totalOrders + " orders");
            }

        } catch (SQLException e) {
        }

        return resultList;
    }

    public static void printMostActiveCashierByDate(List<String> resultList) {
        for (String result : resultList) {
            System.out.println(result);
        }
    }


    public static List<String> getCustomerWhoSpentMostIn2024() {
        List<String> resultList = new ArrayList<>();
        String query = "SELECT c.customer_name, SUM(ol.ordered_Quantity * m.price) AS total_spent "
                + "FROM Customer c "
                + "JOIN Order_table o ON c.id = o.customer_id "
                + "JOIN Order_Line ol ON o.id = ol.order_id "
                + "JOIN menu_item m ON ol.menu_item_id = m.id "
                + "JOIN Invoice i ON o.id = i.order_id "
                + "WHERE YEAR(i.created_date_time) = 2024 "
                + "GROUP BY c.customer_name "
                + "ORDER BY total_spent DESC "
                + "LIMIT 1";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                String customerName = resultSet.getString("customer_name");
                double totalSpent = resultSet.getDouble("total_spent");
                resultList.add(customerName + ": " + totalSpent);
            }

        } catch (SQLException e) {
        }

        return resultList;
    }

    public static void printCustomerWhoSpentMostIn2024(List<String> resultList) {
        for (String result : resultList) {
            System.out.println(result);
        }
    }

    public static List<String> getOrdersAndRevenuePerMonthIn2024() {
        List<String> resultList = new ArrayList<>();
        String query = "SELECT MONTH(i.created_date_time) AS month, "
                + "COUNT(o.id) AS total_orders, "
                + "SUM(ol.ordered_Quantity * m.price) AS total_revenue "
                + "FROM Invoice i "
                + "JOIN Order_table o ON i.order_id = o.id "
                + "JOIN Order_Line ol ON o.id = ol.order_id "
                + "JOIN menu_item m ON ol.menu_item_id = m.id "
                + "WHERE YEAR(i.created_date_time) = 2024 "
                + "GROUP BY month "
                + "ORDER BY month";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int month = resultSet.getInt("month");
                int totalOrders = resultSet.getInt("total_orders");
                double totalRevenue = resultSet.getDouble("total_revenue");
                resultList.add("Month " + month + ": " + totalOrders + " orders, Total Revenue: " + totalRevenue);
            }

        } catch (SQLException e) {
        }

        return resultList;
    }

    public static void printOrdersAndRevenuePerMonthIn2024(List<String> resultList) {
        for (String result : resultList) {
            System.out.println(result);
        }
    }
}
