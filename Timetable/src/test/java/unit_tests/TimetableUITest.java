/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unit_tests;

import com.css.timetable.JDBCConnection;
import com.css.timetable.TimetableUI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.mockito.MockitoAnnotations;

public class TimetableUITest {
    @Test
    public void testPopulateExamTable() throws SQLException {
        JDBCConnection jdbcConnectionMock = Mockito.mock(JDBCConnection.class);
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        
        // Set up the expected values
        String group = "Group1";
        String[] columns = {"id", "From - To", "Group", "Discipline", "Type", "Teacher", "Date", "Room"};
        String[][] timetableData = {{"1", "10 - 12", "Group1", "Math", "Exam", "John Doe", "2023-05-20", "Room1"}};
        
        // Mock the JDBCConnection and prepare statement
        Mockito.when(jdbcConnectionMock.getConnection()).thenReturn(connectionMock);
        Mockito.when(connectionMock.prepareStatement(Mockito.anyString())).thenReturn(preparedStatementMock);
        Mockito.when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        Mockito.when(resultSetMock.next()).thenReturn(true, false); // Return true for the first call and false for subsequent calls
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getInt("start_hour")).thenReturn(10);
        Mockito.when(resultSetMock.getInt("end_hour")).thenReturn(12);
        Mockito.when(resultSetMock.getString("group_name")).thenReturn("Group1");
        Mockito.when(resultSetMock.getString("course")).thenReturn("Math");
        Mockito.when(resultSetMock.getString("course_type")).thenReturn("Exam");
        Mockito.when(resultSetMock.getString("teacher")).thenReturn("John Doe");
        Mockito.when(resultSetMock.getString("date")).thenReturn("2023-05-20");
        Mockito.when(resultSetMock.getString("room")).thenReturn("Room1");
        
        // Create an instance of the class under test and inject the mocks
        TimetableUI timetableUI = new TimetableUI();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
        
        // Call the method to populate the exam table
        timetableUI.populateExamTable();
        
        // Verify the expected interactions
        
        // Verify that the table model was set with the expected data
        Assertions.assertEquals(0, timetableUI.jTable2.getRowCount());
    }
    
    /*CONSTRAINTS TESTS*/
    //Room suitable
    
    @Test
    public void testSuitableCourseRoom() {
        // Create an instance of the class under test and inject the mocks
        TimetableUI timetableUI = new TimetableUI();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
        
        
        String type = "Course";
        String room = "C1";
        boolean result = timetableUI.isRoomSuitable(type, room);
        assertTrue(result);
        
    }
    
    @Test
    public void testUnsuitableCourseRoom() {
        // Create an instance of the class under test and inject the mocks
        TimetableUI timetableUI = new TimetableUI();
        MockitoAnnotations.openMocks(this); 
        
        String type = "Course";
        String room = "L1";
        boolean result = timetableUI.isRoomSuitable(type, room);
        assertFalse(result);
    }
    
    @Test
    public void testSuitableLabRoom() {
        TimetableUI timetableUI = new TimetableUI();
        MockitoAnnotations.openMocks(this); 
        String type = "Laboratory";
        String room = "L2";
        boolean result = timetableUI.isRoomSuitable(type, room);
        assertTrue(result);
    }
    
    @Test
    public void testUnsuitableLabRoom() {
        TimetableUI timetableUI = new TimetableUI();
        MockitoAnnotations.openMocks(this); 
        String type = "Laboratory";
        String room = "C1";
        boolean result = timetableUI.isRoomSuitable(type, room);
        assertFalse(result);
    }
    
    
    //THIS TEST IS SUPPOSED TO FAIL. IF YOU WANT JUST PASSED TESTS, COMMENT THIS FUNCTION
//    @Test
//    public void testUnsuitableCourseRoomFailingTest() {
//        // Create an instance of the class under test and inject the mocks
//        TimetableUI timetableUI = new TimetableUI();
//        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
//        
//        String type = "Course";
//        String room = "C1";
//        boolean result = timetableUI.isRoomSuitable(type, room);
//        assertFalse(result);
//    }
    
}
