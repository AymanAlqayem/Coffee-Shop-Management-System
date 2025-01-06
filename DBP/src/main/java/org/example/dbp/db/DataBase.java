package org.example.dbp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static final String dbUrl = "jdbc:mysql://127.0.0.1:3306/coffeeshopDB";
    private static final String dbUser = "root";
    private static final String dbPass = "ben@me";



    public static Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }
}





