package org.example.dbp;


import java.sql.*;

public class MyJDBC {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dpproject", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from usert");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("user_name"));
                System.out.println(resultSet.getString("pass"));
                System.out.println("--------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
