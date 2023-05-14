/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unit_tests;

import org.junit.jupiter.api.Test;
import com.css.timetable.JDBCConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import com.css.timetable.export.ExportTimetable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author baciu
 */
public class ExportTimetableTests {
    
    private Connection conn;
    
    @AfterEach
    public void cleanUp() throws SQLException {
        conn = JDBCConnection.getInstance().getConnection();
        Statement stmt = conn.createStatement();

        stmt.execute("DELETE FROM groups");
        stmt.execute("DELETE FROM timetable ");

        conn.close();
    }   
    
    @Test
    public void testTableNoInformation() throws SQLException {
        ExportTimetable.createHTML();
        
        String filePath = "C:\\Users\\baciu\\Documents\\GitHub\\TimetableSupportCSS\\Timetable\\src\\main\\resources\\1A2.html";
        File file = new File(filePath);
        assertFalse(file.exists());
    }
    
    @Test
    public void testTableWithInformation() throws SQLException {
        conn = JDBCConnection.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        
        stmt.execute("INSERT INTO groups (name) VALUES ('1A2')");
        stmt.execute("INSERT INTO timetable (start_hour, end_hour, group_name, course, course_type, teacher, day, room) "
                + "VALUES (8, 10, '1A2', 'Matematica', 'seminary', 'Dragos', 'Monday', '101')");
        stmt.execute("INSERT INTO timetable (start_hour, end_hour, group_name, course, course_type, teacher, day, room) "
                + "VALUES (10, 12, '1A2', 'Css', 'seminary', 'Cezar', 'Monday', '202')");
        
        ExportTimetable.createHTML();
        
        String filePath = "C:\\Users\\baciu\\Documents\\GitHub\\TimetableSupportCSS\\Timetable\\src\\main\\resources\\1A2.html";
        try {
            File file = new File(filePath);
            try (Scanner scanner = new Scanner(file)) {
                Document doc = Jsoup.parse(scanner.nextLine());
                Elements timeTableRows = doc.select("tbody tr");
                ArrayList<String> contents1 = new ArrayList<>();
                ArrayList<String> contents2 = new ArrayList<>();
                int rowIndex = 1;
                
                assertTrue(timeTableRows.size() == 2);
                
                for (Element rowElement : timeTableRows) {
                    Elements row = rowElement.select("td");
                    
                    for(Element cell : row) {
                        String value = cell.text();
                        
                        if (rowIndex == 1) contents1.add(value);
                        else contents2.add(value);
                    }
                    rowIndex = 2;
                }
                
                assertTrue(checkIntegrityRows(contents1, "8 - 10", "1A2", "Matematica", "seminary", "Dragos", "Monday", "101"));
                assertTrue(checkIntegrityRows(contents2, "10 - 12", "1A2", "Css", "seminary", "Cezar", "Monday", "202"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        stmt.execute("DELETE FROM groups WHERE name = '1A2'");
        stmt.execute("DELETE FROM timetable WHERE course = 'Matematica'");
        stmt.execute("DELETE FROM timetable WHERE course = 'Css'");
        conn.close();
    }
    
    
    
    public static boolean checkIntegrityRows(ArrayList<String> toBeChecked, String hours, String group_name, String course, String course_type, String teacher, String day, String room) {
        return toBeChecked.get(0).equals(hours) &&
                toBeChecked.get(1).equals(group_name) &&
                toBeChecked.get(2).equals(course) &&
                toBeChecked.get(3).equals(course_type) &&
                toBeChecked.get(4).equals(teacher) &&
                toBeChecked.get(5).equals(day) &&
                toBeChecked.get(6).equals(room);
    }
}
