/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.css.timetable.setup;

import com.css.timetable.ConfigReader;
import com.css.timetable.JDBCConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used to initialize the tables in the database with initial data.
 * It makes use of the configuration file properties to generate the necessary data.
 */
public class DatabaseSetup 
{
    /** Logger of the current class. */
    public static Logger LOG = Logger.getLogger(DatabaseSetup.class.getName());
    
    /** An instance of the ConfigReader class used to read the configuration properties of the project. */
    public static ConfigReader config = ConfigReader.getInstance();
    
    /**
     * Method that does an initial setup of the database based on the 
     * properties belonging to the current project properties configuration.
     * The setup can be toggled from the configuration property: setup.initiate.
     * The method populates the database with groups based on the number of 
     * years, number of half years and the available group count for each half year.
     * The method also populates the rooms, room types, and disciplines available.
     * NOTE: Teacher have to be added manually in the database in order to allow multiple 
     * teachers to hold the same course.
     */
    public static void setup()
    {
        String check = config.getProperty("setup.initiate");
        if (!Boolean.parseBoolean(check))
        {
            LOG.info("Database setup is not activated.");
            return;
        }
        else
        {
            LOG.info("Database setup is activated.");
        }
        
        String groupCount = config.getProperty("setup.groups.count");
        String groupYears = config.getProperty("setup.groups.years");
        String groupHalfs = config.getProperty("setup.groups.halfs");
        String rooms = config.getProperty("setup.rooms");
        String roomTypes = config.getProperty("setup.rooms.types");
        String teachers = config.getProperty("setup.teachers");
        String disciplines = config.getProperty("setup.discipline");
        if (groupCount == null || 
            groupYears == null || 
            groupHalfs == null || 
            rooms == null      || 
            roomTypes == null  || 
            teachers == null   || 
            disciplines == null)
        {
            LOG.severe("Database setup config is not complete!");
            return;
        }
        
        assert groupCount != null : "Database setup config is not complete!";
        assert groupYears != null : "Database setup config is not complete!";
        assert groupHalfs != null : "Database setup config is not complete!";
        assert rooms != null : "Database setup config is not complete!";
        assert roomTypes != null : "Database setup config is not complete!";
        assert teachers != null : "Database setup config is not complete!";
        assert disciplines != null : "Database setup config is not complete!";
        
        if (teachers.split(",").length != disciplines.split(",").length)
        {
            LOG.severe("Setup teachers and disciplines config do not match!");
            return;
        }
        
        int numTeachers = teachers.split(",").length;
        int numDisciplines = disciplines.split(",").length;
        assert numTeachers == numDisciplines : "Setup teachers and disciplines config do not match!";

        
        String[] separateRoomTypes = roomTypes.split(",");
        int sumRoomTypes = Arrays.stream(separateRoomTypes)
                                 .mapToInt(value -> Integer.parseInt(value.split("-")[1]))
                                 .sum();
        if (sumRoomTypes != 100)
        {
            LOG.severe("Setup room types are not evenly distributed");
            return;
        }
        
        assert sumRoomTypes == 100 : "Setup room types are not evenly distributed";
        
        String[] separateDisciplines = disciplines.split(",");
        boolean isInvalidDiscipline = Arrays.stream(separateDisciplines)
                                          .map(string -> string.split("-"))
                                          .allMatch(value -> Integer.parseInt(value[1]) > Integer.parseInt(groupCount));
        if (isInvalidDiscipline)
        {
            LOG.severe("Setup discipline for year that doesn't exist!");
            return;
        }
        
        assert !isInvalidDiscipline : "Setup discipline for year that doesn't exist!";
            
        try 
        {
            Connection conn = JDBCConnection.getInstance().getConnection();
            
            Statement stmt = conn.createStatement();
            // Delete all records
            stmt.executeUpdate("DELETE FROM discipline;");
            stmt.executeUpdate("DELETE FROM groups;");
            stmt.executeUpdate("DELETE FROM rooms;");
            stmt.executeUpdate("DELETE FROM teachers;");
            stmt.executeUpdate("DELETE FROM timetable;");
            
            // Reset identity sequences
            String disciplineSequenceQuery = "SELECT setval(pg_get_serial_sequence('discipline', 'id'), coalesce(MAX(id), 1)) from discipline;";
            String teachersSequenceQuery = "SELECT setval(pg_get_serial_sequence('teachers', 'id'), coalesce(MAX(id), 1)) from teachers;";
            stmt.executeQuery(disciplineSequenceQuery);
            stmt.executeQuery(teachersSequenceQuery);
            
            // Populate groups table
            int numberOfGroups = Integer.parseInt(groupCount);
            int numberOfYears = Integer.parseInt(groupYears);
            int numberOfHalfs  = Integer.parseInt(groupHalfs);
            char initial = (char) 64;
            for (int i = 1; i <= numberOfHalfs; i++)
            {
                for (int j = 1; j <= numberOfYears; j++)
                {
                    for (int k = 1; k <= numberOfGroups; k++)
                    {   
                        char half = (char) (initial + 1);
                        String groupName = new StringBuilder().append(j).append(half).append(k).toString();
                        String groupSql = "INSERT INTO groups VALUES(?);";
                        try (PreparedStatement ptmt = conn.prepareStatement(groupSql)) {
                            ptmt.setString(1, groupName);
                            ptmt.executeUpdate();
                        }
                    }
                }
                initial++;
            }
            
            // Populate rooms table
            int totalRoomCount = Integer.parseInt(rooms);
            int selectedRooms = 0;
            for (String rType : separateRoomTypes)
            {
                String[] vals = rType.split("-");
                String currentRoomType = vals[0];
                int percentageAllocated = Integer.parseInt(vals[1]);
                
                int roomsAllocated = (totalRoomCount * percentageAllocated) / 100;
                for (int i = 1; i <= roomsAllocated; i++)
                {
                    String roomName = new StringBuilder().append(currentRoomType.toCharArray()[0]).append(i).toString();
                    String roomSql = "INSERT INTO rooms VALUES(?, ?);";
                    try (PreparedStatement ptmt = conn.prepareStatement(roomSql)) 
                    {
                        ptmt.setString(1, roomName);
                        ptmt.setString(2, currentRoomType);
                        ptmt.executeUpdate();
                    }
                }
                selectedRooms += roomsAllocated;
            }
            
            while (selectedRooms != totalRoomCount)
            {
                LOG.info("Rooms were not distributed evenly, adjusting...");
                String[] vals = separateRoomTypes[0].split("-");
                String currentRoomType = vals[0];
                int currentPosition;
                
                String checkRoomQuery = "SELECT COUNT(*) FROM rooms WHERE type = ?;";
                try(PreparedStatement ptmt = conn.prepareStatement(checkRoomQuery))
                {
                    ptmt.setString(1, currentRoomType);
                    ResultSet rs = ptmt.executeQuery();
                    rs.next();
                    currentPosition = rs.getInt(1);
                }
                
                String roomName = new StringBuilder().append(currentRoomType.toCharArray()[0]).append(++currentPosition).toString();
                String roomSql = "INSERT INTO rooms VALUES(?, ?);";
                try (PreparedStatement ptmt = conn.prepareStatement(roomSql)) 
                {
                    ptmt.setString(1, roomName);
                    ptmt.setString(2, currentRoomType);
                    ptmt.executeUpdate();
                }
                selectedRooms++;
            }
            
            // Populate discipline table
            for (String discipline : separateDisciplines)
            {
                // 0 is the name, 1 is the year
                String[] vals = discipline.split("-");
                String disciplineSql = "INSERT INTO discipline (name, year) VALUES(?, ?);";
                try (PreparedStatement ptmt = conn.prepareStatement(disciplineSql)) 
                {
                    ptmt.setString(1, vals[0]);
                    ptmt.setInt(2, Integer.parseInt(vals[1]));
                    ptmt.executeUpdate();
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}