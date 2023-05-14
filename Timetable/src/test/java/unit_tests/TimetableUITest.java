/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unit_tests;

import com.css.timetable.JDBCConnection;
import com.css.timetable.RegistrationTimetable;
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
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockitoAnnotations;

public class TimetableUITest {
    @Test
    public void testPopulateExamTable() throws SQLException {
        JDBCConnection jdbcConnectionMock = Mockito.mock(JDBCConnection.class);
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        
        String group = "Group1";
        String[] columns = {"id", "From - To", "Group", "Discipline", "Type", "Teacher", "Date", "Room"};
        String[][] timetableData = {{"1", "10 - 12", "Group1", "Math", "Exam", "John Doe", "2023-05-20", "Room1"}};
        
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
        
        TimetableUI timetableUI = new TimetableUI();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
        
        timetableUI.populateExamTable();
        
        Assertions.assertEquals(0, timetableUI.jTable2.getRowCount());
    }
    
    /*CONSTRAINTS TESTS*/
    @Test
    public void testSuitableCourseRoom() {
        TimetableUI timetableUI = new TimetableUI();
        MockitoAnnotations.openMocks(this); 
        String type = "Course";
        String room = "C1";
        boolean result = timetableUI.isRoomSuitable(type, room);
        assertTrue(result);   
    }
    
    @Test
    public void testUnsuitableCourseRoom() {
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
    
    @Test
    public void testIsCourseNotTotallyOverlapped() {
        RegistrationTimetable oldOne = new RegistrationTimetable();
        oldOne.setStartHour(9);
        oldOne.setEndHour(11);
        oldOne.setRoom("L1");
        oldOne.setDay("Monday");
        oldOne.setCourse("Matematica");
        oldOne.setCourseType("Laboratory");
        oldOne.setGroupName("1A2");
        oldOne.setTeacher("Teacher A");

        RegistrationTimetable newOne = new RegistrationTimetable();
        oldOne.setStartHour(11);
        oldOne.setEndHour(13);
        oldOne.setRoom("L1");
        oldOne.setDay("Monday");
        oldOne.setCourse("Matematica");
        oldOne.setCourseType("Course");
        oldOne.setGroupName("1A3");
        oldOne.setTeacher("Teacher B");
        
        TimetableUI timetableUI = new TimetableUI();
        MockitoAnnotations.openMocks(this);
        boolean expected = true;
        boolean actual = timetableUI.isCourseNotTotallyOverlapped(oldOne, newOne);
        assertEquals(expected, actual);
    }

    @Test
    public void testIsCourseNotPartiallyOverlapped() {
        RegistrationTimetable oldOne = new RegistrationTimetable();
        oldOne.setStartHour(9);
        oldOne.setEndHour(12);
        oldOne.setRoom("L1");
        oldOne.setDay("Monday");
        oldOne.setCourse("Matematica");
        oldOne.setCourseType("Laboratory");
        oldOne.setGroupName("1A2");
        oldOne.setTeacher("Teacher A");

        RegistrationTimetable newOne = new RegistrationTimetable();
        newOne.setStartHour(11);
        newOne.setEndHour(13);
        newOne.setRoom("L1");
        newOne.setDay("Monday");
        newOne.setCourse("Matematica");
        newOne.setCourseType("Course");
        newOne.setGroupName("1A3");
        newOne.setTeacher("Teacher B");

        TimetableUI timetableUI = new TimetableUI();
        boolean expected = false;
        boolean actual = timetableUI.isCourseNotPartiallyOverlapped(oldOne, newOne);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testHasValidDate() {
        TimetableUI timetableUI = new TimetableUI();
        RegistrationTimetable reg = new RegistrationTimetable();
        reg.setDay("Monday");
        boolean expected = true;
        boolean actual = timetableUI.hasValidDate(reg);
        assertEquals(expected, actual);

        reg.setDay("32-01-2022");
        expected = false;
        actual = timetableUI.hasValidDate(reg);
        assertEquals(expected, actual);

        reg.setDay("14-05-2023");
        expected = false;
        actual = timetableUI.hasValidDate(reg);
        assertEquals(expected, actual);

        reg.setDay("16-05-2023");
        expected = true;
        actual = timetableUI.hasValidDate(reg);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSameCourseOnceAWeek() {
        RegistrationTimetable oldOne = new RegistrationTimetable();
        oldOne.setStartHour(9);
        oldOne.setEndHour(11);
        oldOne.setRoom("C1");
        oldOne.setDay("Monday");
        oldOne.setCourse("Matematica");
        oldOne.setCourseType("Course");
        oldOne.setGroupName("1A2");
        oldOne.setTeacher("Teacher A");

        RegistrationTimetable newOne = new RegistrationTimetable();
        newOne.setStartHour(11);
        newOne.setEndHour(13);
        newOne.setRoom("C1");
        newOne.setDay("Monday");
        newOne.setCourse("Matematica");
        newOne.setCourseType("Course");
        newOne.setGroupName("1B1");
        newOne.setTeacher("Teacher B");

        TimetableUI timetableUI = new TimetableUI();
        boolean expected = false;
        boolean actual = timetableUI.sameCourseOnceAWeek(oldOne, newOne);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIsViableForInsert() {
        // Create a registration timetable for testing
        RegistrationTimetable newReg = new RegistrationTimetable(10, 12, "RoomA", "Monday", "CourseA", "Lecture", "GroupA", "TeacherA");

        // Set up mock database
        RegistrationTimetable[] registrations = new RegistrationTimetable[] {
            new RegistrationTimetable(9, 10, "RoomB", "Monday", "CourseB", "Lecture", "GroupB", "TeacherB"),
            new RegistrationTimetable(11, 13, "RoomC", "Monday", "CourseC", "Lecture", "GroupA", "TeacherC")
        };
        
        TimetableUI timetableUI = new TimetableUI();
        // Test case where everything is valid
        assertTrue(timetableUI.isViableForInsert(newReg));

        // Test case where date format is invalid
        newReg.setDay("33-13-2022");
        assertFalse(timetableUI.isViableForInsert(newReg));

        // Test case where room is not suitable
        newReg.setRoom("L1");
        assertFalse(timetableUI.isViableForInsert(newReg));

        // must insert the course
        newReg.setDay("Tuesday");
        newReg.setStartHour(9);
        newReg.setEndHour(11);
        assertTrue(timetableUI.isViableForInsert(newReg));
    }
}
