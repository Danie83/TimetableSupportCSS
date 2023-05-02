/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.css.timetable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigReader {
    
    private static final String FILE_PATH = "../resources/config.properties";
    
    public static void getProperties()
    {
        Properties props = new Properties();
        InputStream input;
        
        try
        {
            input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
            props.load(input);
            
            String url = props.getProperty("database.url");
            System.out.println(url);
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(ConfigReader.class.getName()).log(Level.SEVERE, "File not found", ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ConfigReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
