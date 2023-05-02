/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.css.timetable;

import java.sql.SQLException;
import javax.swing.JFrame;
public class Timetable {

    public static void main(String[] args) throws SQLException {
        
        JDBCConnection conn = JDBCConnection.getInstance();
        ConfigReader.getProperties();
        
        JFrame frame = new TimetableUI();
        frame.setVisible(true);
    }
}
