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

public class ConfigReader {
    
    private static String RESOURCE_FILE = "config.properties";
    private static ConfigReader instance = null;
    private Properties properties;
    
    private ConfigReader()
    {
        this.properties = new Properties();
        
        try 
        {
            InputStream propsInput = ConfigReader.class.getClassLoader().getResourceAsStream(RESOURCE_FILE);
            this.properties.load(propsInput);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(ConfigReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ConfigReader getInstance()
    {
        if (instance == null) 
        {
            instance = new ConfigReader();
        } 
        return instance;
    }
    
    public Properties getProperties()
    {
        return properties;
    }
    
    public String getProperty(String key)
    {
        return properties.getProperty(key);
    }
    
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
