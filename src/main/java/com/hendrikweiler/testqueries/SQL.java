package com.hendrikweiler.testqueries;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL {

    public static Connection connection = null;

    public static Connection init() {
        //String url = "jdbc:mysql://localhost:3306/sales?useSSL=false";

        System.out.println("Connecting database ...");

        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/sales");
            connection = ds.getConnection();
            System.out.println("Database connected!");
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet querySelect(String query) {
        try {
            return connection.prepareStatement(query).executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void queryInsert(String query) {
        try {
            connection.prepareStatement(query).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void queryUpdate(String query) {
        try {
            connection.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
