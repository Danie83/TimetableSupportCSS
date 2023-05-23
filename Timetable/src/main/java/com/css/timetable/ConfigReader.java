/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.css.timetable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Singleton class that reads properties for the database connection
 * and setup.
 */
public class ConfigReader 
{
    /** The name of the configuration file. */
    private static final String RESOURCE_FILE = "config.properties";
    
    /** Reference to the only instance of this class. */
    private static ConfigReader instance = null;
    
    /** The properties that are read from the configuration properties. */
    private Properties properties;
    
    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the properties object and loads the configuration file.
     */
    private ConfigReader()
    {
        this.properties = new Properties();
        
        try 
        {
            InputStream propsInput = ConfigReader.class.getClassLoader().getResourceAsStream(RESOURCE_FILE);
            assert propsInput != null : "Failed to load resource file: " + RESOURCE_FILE;
            this.properties.load(propsInput);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(ConfigReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Retrieves the singleton instance of the ConfigReader class.
     * if the instance does not exists, it is created.
     * 
     * @return  The singleton instance of ConfigReader.
     */
    public static ConfigReader getInstance()
    {
        if (instance == null) 
        {
            instance = new ConfigReader();
        } 
        assert instance != null : "ConfigReader instance is null.";
        return instance;
    }
    
    /**
     * Get the properties obtained from the configuration file.
     * 
     * @return  The properties from the configuration file.
     */
    public Properties getProperties()
    {
        return properties;
    }
    
    /**
     * Method that retrieves a specific property using a given string.
     * 
     * @param   key
     *          The property that will be retrieved.
     * 
     * @return  The value of the property requested.
     */
    public String getProperty(String key)
    {
        assert key != null : "Key cannot be null";
        return properties.getProperty(key);
    }
    
    /**
     * Method that sets a specific property.
     * 
     * @param   key
     *          The property that is set.
     * @param   value 
     *          The new value of the property.
     */
    public void setProperty(String key, String value) 
    {
        assert key != null : "Key cannot be null";
        assert value != null : "Value cannot be null";
        properties.setProperty(key, value);
    }
}
