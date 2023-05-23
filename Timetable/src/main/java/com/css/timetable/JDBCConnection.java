/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.css.timetable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A Singleton class that handles a connection to the database.
 */
public class JDBCConnection 
{
    /** Reference to the only instance of this class. */
    private static JDBCConnection instance = null;
    
    /** A database connection associated with the instance. */
    private Connection connection;
    
    /** Database connection URL. */
    private String url = "jdbc:postgresql://localhost:5432/timetablecss";
    
    /** Database connection Username. */
    private String username = "postgres";
    
    /** Database connection Password. */
    private String password = "postgres";

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the database connection.
     */
    private JDBCConnection() throws SQLException
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
        assert this.connection != null : "Connection failed to initialize";
    }

    /**
     * Getter method that returns the database connection.
     * 
     * @return  The database connection.
     */
    public Connection getConnection()
    {
        assert this.connection != null : "Connection is null";
        return connection;
    }

    /**
     * Retrieves the singleton instance of the JDBCConnection class.if the instance does not exists, it is created.
     * 
     * @return  The singleton instance of JDBCConnection.
     * 
     * @throws java.sql.SQLException
     */
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
        assert instance != null : "Instance is null";
        assert !instance.getConnection().isClosed() : "Connection is closed";

        return instance;
    }
}