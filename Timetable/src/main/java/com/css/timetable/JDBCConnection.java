package com.css.timetable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

    private static JDBCConnection instance;
    private Connection connection;
    private String url = "jdbc:postgresql://localhost:5432/timetablecss";
    private String username = "postgres";
    private String password = "postgres";

    JDBCConnection() throws SQLException 
    {
        try 
        {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database Connection Created");
        } 
        catch (ClassNotFoundException ex) 
        {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection()
    {
        return connection;
    }

    public static JDBCConnection getInstance() throws SQLException 
    {
        if (instance == null) 
        {
            instance = new JDBCConnection();
        } 
        else if (instance.getConnection().isClosed()) 
        {
            instance = new JDBCConnection();
        }

        return instance;
    }
}