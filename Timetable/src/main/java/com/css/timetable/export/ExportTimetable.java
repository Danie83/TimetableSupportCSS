/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.css.timetable.export;

import com.css.timetable.JDBCConnection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used to export the timetable and exam table for each group.
 */
public class ExportTimetable 
{
    
    /**
     * Method that generates all HTML pages for timetable and exam entries that 
     * belong to all existent groups. If a group has no timetable and exam entries,
     * it is logged and skipped. The timetable entries are sorted by the day of the
     * entry. Finally, the HTML page is saved in the resources folder and it's
     * name is represented by the group name.
     */
    public static void createHTML()
    {
        String[] headers = {"From-To", "Group", "Discipline", "Type", "Teacher", "Day", "Room"};
        try 
        {
            Connection conn = JDBCConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM groups;");
            List<String> groupNames = new ArrayList<>();
            while (rs.next())
            {
                groupNames.add(rs.getString("name"));
            }
            
            assert groupNames != null : "Group names list is null";
            for (String group : groupNames)
            {
                PreparedStatement ptmt = conn.prepareStatement("SELECT *, "
                        + "CASE day "
                        + "WHEN 'Monday' THEN 1 "
                        + "WHEN 'Tuesday' THEN 2 "
                        + "WHEN 'Wednesday' THEN 3 "
                        + "WHEN 'Thursday' THEN 4 "
                        + "WHEN 'Friday' THEN 5 "
                        + "END AS day_number "
                        + "FROM timetable "
                        + "WHERE group_name = ? ORDER BY day_number ASC, start_hour ASC;");
                ptmt.setString(1, group);
                ResultSet prs = ptmt.executeQuery();
                List<String[]> timetableRows = new ArrayList<>();
                List<String[]> examTableRows = new ArrayList<>();
                
                assert headers.length == 7 : "Invalid number of headers";
                while (prs.next())
                {
                    String[] row = new String[headers.length];
                    row[0] = new StringBuilder().append(prs.getInt("start_hour")).append(" - ").append(prs.getInt("end_hour")).toString();
                    row[1] = prs.getString("group_name");
                    row[2] = prs.getString("course");
                    row[3] = prs.getString("course_type");
                    row[4] = prs.getString("teacher");
                    row[5] = prs.getString("day");
                    row[6] = prs.getString("room");
                    
                    if(row[3].split(" ").length > 1 && row[3].split(" ")[1].equals("Exam"))
                    {
                        examTableRows.add(row);
                    }
                    else{
                        timetableRows.add(row);
                    }
                }
                
                if (timetableRows.isEmpty() && examTableRows.isEmpty())
                {
                    Logger.getLogger(ExportTimetable.class.getName()).log(Level.SEVERE, new StringBuilder().append("No timetable available for group ").append(group).toString());
                    continue;
                }
                
                assert !timetableRows.isEmpty() || !examTableRows.isEmpty() : "Timetable or exam data available for group " + group;
                
                StringBuilder html = new StringBuilder();
                
                if(!timetableRows.isEmpty())
                {
                    html.append("<table style=\"border-collapse: collapse; width: 100%;\">");
                    html.append("<thead style=\"background-color: #9d9af5;\">");
                    html.append("<tr>");
                    for (String header : headers)
                    {
                        html.append("<th style=\"border: 1px solid #ddd; padding: 8px; text-align: center;\">").append(header).append("</th>");
                    }
                    html.append("</tr></thead>");

                    html.append("<tbody id=\"timetable\">");
                    for (String[] row : timetableRows) 
                    {
                        html.append("<tr>");
                        for (String cell : row) 
                        {
                            html.append("<td style=\"border: 1px solid #ddd; padding: 8px; text-align: center;\">").append(cell).append("</td>");
                        }
                        html.append("</tr>");
                    }
                    html.append("</tbody>");
                    html.append("</table>");

                    html.append("<br>");
                }
                
                if(!examTableRows.isEmpty())
                {
                    html.append("<table id=\"examtable\" style=\"border-collapse: collapse; width: 100%;\">");
                    html.append("<thead style=\"background-color: #fafa6e;\">");
                    html.append("<tr>");
                    for (String header : headers)
                    {
                        html.append("<th style=\"border: 1px solid #ddd; padding: 8px; text-align: center;\">").append(header).append("</th>");
                    }
                    html.append("</tr></thead>");

                    html.append("<tbody>");
                    for (String[] row : examTableRows) 
                    {
                        // start and end hour are 2 separate columns, and the id => row.length - 2
                        assert row.length - 2 == headers.length : "Invalid number of columns in examTableRows";
                        html.append("<tr>");
                        for (String cell : row) 
                        {
                            html.append("<td style=\"border: 1px solid #ddd; padding: 8px; text-align: center;\">").append(cell).append("</td>");
                        }
                        html.append("</tr>");
                    }
                    html.append("</tbody>");
                    html.append("</table>");
                }
                
                
                URL url = ClassLoader.getSystemResource("resources/" + group + ".html");
                if (url == null)
                {
                    File file = new File("src/main/resources/" + group + ".html");
                    try 
                    {
                        file.createNewFile();
                        url = file.toURI().toURL();
                    } 
                    catch (IOException ex) 
                    {
                        Logger.getLogger(ExportTimetable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                try 
                {
                    Path path = Paths.get(url.toURI());
                    FileWriter fileWriter = new FileWriter(path.toFile());
                    fileWriter.write(html.toString());
                    fileWriter.close();
                } 
                catch (URISyntaxException | IOException ex) 
                {
                    Logger.getLogger(ExportTimetable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ExportTimetable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
