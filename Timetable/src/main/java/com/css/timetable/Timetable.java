/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.css.timetable;

import com.css.timetable.setup.DatabaseSetup;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 * Main class that calls the Database setup and displays
 * the timetable application window.
 */
public class Timetable 
{
    /**
     * Entry point of the timetable application. It executed
     * the database setup and displays the timetable window.
     * 
     * @param   args
     *          Command line arguments. 
     *          The args array is not used by the program.
     * 
     * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException 
    {
        DatabaseSetup.setup();
        JFrame frame = new TimetableUI();
        frame.setVisible(true);
    }
}
