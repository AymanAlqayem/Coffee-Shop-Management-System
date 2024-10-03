package org.example.dbp;


import java.sql.*;

public class MyJDBC {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/regproject", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user_table");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("first_name"));
                System.out.println(resultSet.getString("last_name"));
                System.out.println(resultSet.getString("user_name"));
                System.out.println(resultSet.getString("pass"));
                System.out.println("--------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
