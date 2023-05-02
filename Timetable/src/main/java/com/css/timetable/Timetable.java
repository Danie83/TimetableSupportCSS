/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.css.timetable;

import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JFrame;
public class Timetable {

    public static void main(String[] args) throws SQLException {
        
        JDBCConnection conn = JDBCConnection.getInstance();
        ConfigReader cr = ConfigReader.getInstance();
        Properties p = cr.getProperties();
        System.out.println(p.getProperty("database.url"));
        System.out.println(cr.getProperty("database.url"));
        
        JFrame frame = new TimetableUI();
        frame.setVisible(true);
    }
}
