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
        // Test case 1: The courses are not totally overlapsed
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
        newOne.setStartHour(11);
        newOne.setEndHour(13);
        newOne.setRoom("L1");
        newOne.setDay("Monday");
        newOne.setCourse("Matematica");
        newOne.setCourseType("Course");
        newOne.setGroupName("1A3");
        newOne.setTeacher("Teacher B");

        TimetableUI timetableUI = new TimetableUI();
        boolean expected = true;
        boolean actual = timetableUI.isCourseNotTotallyOverlapped(oldOne, newOne);
        assertEquals(expected, actual);

        // Test case 2: courses are totally overlapping
        oldOne = new RegistrationTimetable();
        oldOne.setStartHour(14);
        oldOne.setEndHour(16);
        oldOne.setRoom("L1");
        oldOne.setDay("Monday");
        oldOne.setCourse("Matematica");
        oldOne.setCourseType("Laboratory");
        oldOne.setGroupName("1A2");
        oldOne.setTeacher("Teacher A");

        newOne = new RegistrationTimetable();
        newOne.setStartHour(11);
        newOne.setEndHour(17);
        newOne.setRoom("L1");
        newOne.setDay("Monday");
        newOne.setCourse("Matematica");
        newOne.setCourseType("Laboratory");
        newOne.setGroupName("1A3");
        newOne.setTeacher("Teacher B");

        expected = false;
        actual = timetableUI.isCourseNotTotallyOverlapped(oldOne, newOne);
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
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks

        boolean expected = false;
        boolean actual = timetableUI.isCourseNotPartiallyOverlapped(oldOne, newOne);
        assertEquals(expected, actual);

        // Test overlapping start times
        newOne.setStartHour(10);
        expected = false;
        actual = timetableUI.isCourseNotPartiallyOverlapped(oldOne, newOne);
        assertEquals(expected, actual);

        // Test overlapping end times
        newOne.setStartHour(8);
        newOne.setEndHour(11);
        expected = false;
        actual = timetableUI.isCourseNotPartiallyOverlapped(oldOne, newOne);
        assertEquals(expected, actual);

        // Test fully contained within oldOne
        newOne.setStartHour(10);
        newOne.setEndHour(11);
        expected = true;
        actual = timetableUI.isCourseNotPartiallyOverlapped(oldOne, newOne);
        assertEquals(expected, actual);

        // Test partially overlapping start times
        newOne.setStartHour(8);
        newOne.setEndHour(10);
        expected = false;
        actual = timetableUI.isCourseNotPartiallyOverlapped(oldOne, newOne);
        assertEquals(expected, actual);

        // Test partially overlapping end times
        newOne.setStartHour(11);
        newOne.setEndHour(14);
        expected = false;
        actual = timetableUI.isCourseNotPartiallyOverlapped(oldOne, newOne);
        assertEquals(expected, actual);

        // Test non-overlapping courses
        newOne.setStartHour(14);
        newOne.setEndHour(16);
        expected = true;
        actual = timetableUI.isCourseNotPartiallyOverlapped(oldOne, newOne);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testHasValidDate() {
        TimetableUI timetableUI = new TimetableUI();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks

        RegistrationTimetable reg = new RegistrationTimetable();
        reg.setDay("Monday");
        boolean expected = true;
        boolean actual = timetableUI.hasValidDate(reg);
        assertEquals(expected, actual);

        reg.setDay("32-01-2022");
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
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks

        boolean expected = false;
        boolean actual = timetableUI.sameCourseOnceAWeek(oldOne, newOne);
        assertEquals(expected, actual);
    }

    @Test
    public void testIsViableForInsert() {
        TimetableUI timetableUI = new TimetableUI();
        RegistrationTimetable[] registrations = new RegistrationTimetable[3];

        RegistrationTimetable reg1 = new RegistrationTimetable();
        reg1.setStartHour(9);
        reg1.setEndHour(12);
        reg1.setRoom("L1");
        reg1.setDay("Monday");
        reg1.setCourse("Math");
        reg1.setCourseType("Laboratory");
        reg1.setGroupName("1A2");
        reg1.setTeacher("Teacher A");

        RegistrationTimetable reg2 = new RegistrationTimetable();
        reg2.setStartHour(13);
        reg2.setEndHour(15);
        reg2.setRoom("C1");
        reg2.setDay("Monday");
        reg2.setCourse("CSS");
        reg2.setCourseType("Course");
        reg2.setGroupName("1A2");
        reg2.setTeacher("Teacher B");

        RegistrationTimetable reg3 = new RegistrationTimetable();
        reg3.setStartHour(15);
        reg3.setEndHour(17);
        reg3.setRoom("C2");
        reg3.setDay("Tuesday");
        reg3.setCourse("Matematica");
        reg3.setCourseType("Course");
        reg3.setGroupName("1A3");
        reg3.setTeacher("Teacher C");

        registrations[0] = reg1;
        registrations[1] = reg2;
        registrations[2] = reg3;


        // Create a new valid registration
        RegistrationTimetable newReg1 = new RegistrationTimetable();
        newReg1.setStartHour(9);
        newReg1.setEndHour(12);
        newReg1.setRoom("L2");
        newReg1.setDay("Monday");
        newReg1.setCourse("Matematica");
        newReg1.setCourseType("Laboratory");
        newReg1.setGroupName("1A2");
        newReg1.setTeacher("Teacher D");

        // Test with a new valid registration
        assertTrue(timetableUI.isViableForInsert(newReg1));

        // Test with an invalid date format
        RegistrationTimetable newReg2 = new RegistrationTimetable();
        newReg2.setStartHour(10);
        newReg2.setEndHour(12);
        newReg2.setRoom("L2");
        newReg2.setDay("13-05-2023"); // invalid date format
        newReg2.setCourse("Matematica");
        newReg2.setCourseType("Laboratory");
        newReg2.setGroupName("1A2");
        newReg2.setTeacher("Teacher D");
        assertFalse(timetableUI.isViableForInsert(newReg2));

        // Test with an invalid room
        RegistrationTimetable newReg3 = new RegistrationTimetable();
        newReg3.setStartHour(10);
        newReg3.setEndHour(12);
        newReg3.setRoom("C2"); // invalid room for the chosen course type
        newReg3.setDay("Monday");
        newReg3.setCourse("Matematica");
        newReg3.setCourseType("Course");
        newReg3.setGroupName("1A2");
        newReg3.setTeacher("Teacher D");
        assertTrue(timetableUI.isViableForInsert(newReg3));
    }
    private Connection conn;
    
//    @Test
//    public void testPopulateTeacherComboBox() throws SQLException {
//        TimetableUI TUI = new TimetableUI();
//        conn = JDBCConnection.getInstance().getConnection();
//        Statement stmt = conn.createStatement();
//        int teachersNumber = 0;
//        
//        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM teachers;");
//        while (rs.next()) {
//            teachersNumber = rs.getInt("total");
//        }
//        
//        stmt.execute("INSERT INTO teachers (course_id, first_name, last_name) VALUES ('2', 'Dragos', 'Baciu')");
//        
//        TUI.populateTeacherComboBox();
//        javax.swing.JComboBox<String> teacherComboBox = TUI.getTeacherComboBox();
//        
//        assertTrue(teacherComboBox.getItemCount() == teachersNumber + 1);
//        assertTrue(teacherComboBox.getItemAt(0).equals("Baciu Dragos"));
//        
//        stmt.execute("DELETE FROM teachers");
//        conn.close();
//    }
//    
    @Test
    public void testPopulateGroupComboBox() throws SQLException {
        TimetableUI TUI = new TimetableUI();
        conn = JDBCConnection.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        int groupNumber = 0;
        
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM groups;");
        while (rs.next()) {
            groupNumber = rs.getInt("total");
        }
        
        TUI.populateGroupComboBox();
        javax.swing.JComboBox<String> groupComboBox = TUI.getGroupComboBox();
        
        assertTrue(groupComboBox.getItemCount() == groupNumber);
        conn.close();
    }
    
    @Test
    public void testPopulateDisciplineComboBox() throws SQLException {
        TimetableUI TUI = new TimetableUI();
        conn = JDBCConnection.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        int disciplineNumber = 0;
        
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM discipline;");
        while (rs.next()) {
            disciplineNumber = rs.getInt("total");
        }
        
        TUI.populateDisciplineComboBox();
        javax.swing.JComboBox<String> disciplineComboBox = TUI.getDisciplineComboBox();
        
        assertTrue(disciplineComboBox.getItemCount() == disciplineNumber);
        conn.close();
    }
    
    @Test
    public void testPopulateClassComboBox() throws SQLException {
        TimetableUI TUI = new TimetableUI();
        conn = JDBCConnection.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        
        TUI.populateClassComboBox();
        javax.swing.JComboBox<String> classComboBox = TUI.getClassComboBox();
        
        assertTrue(classComboBox.getItemAt(0).equals("Seminary"));
        assertTrue(classComboBox.getItemAt(1).equals("Laboratory"));
        assertTrue(classComboBox.getItemAt(2).equals("Course"));
        assertTrue(classComboBox.getItemAt(3).equals("Course Exam"));
        assertTrue(classComboBox.getItemAt(4).equals("Lab Exam"));
        conn.close();
    }
    
    @Test
    public void testPopulateRoomComboBox() throws SQLException {
        TimetableUI TUI = new TimetableUI();
        conn = JDBCConnection.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        int roomNumber = 0;
        
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM rooms;");
        while (rs.next()) {
            roomNumber = rs.getInt("total");
        }
        
        TUI.populateRoomComboBox();
        javax.swing.JComboBox<String> roomComboBox = TUI.getRoomComboBox();
        
        assertTrue(roomComboBox.getItemCount() == roomNumber);
        assertTrue(roomComboBox.getItemAt(0).equals("S1"));    
        assertTrue(roomComboBox.getItemAt(1).equals("S2"));    
        assertTrue(roomComboBox.getItemAt(2).equals("S3"));    
        assertTrue(roomComboBox.getItemAt(3).equals("S4"));    
        assertTrue(roomComboBox.getItemAt(4).equals("S5"));    
        assertTrue(roomComboBox.getItemAt(5).equals("S6"));    
        assertTrue(roomComboBox.getItemAt(6).equals("L1"));    
        assertTrue(roomComboBox.getItemAt(7).equals("L2"));    
        assertTrue(roomComboBox.getItemAt(8).equals("L3"));    
        assertTrue(roomComboBox.getItemAt(9).equals("L4"));    
        assertTrue(roomComboBox.getItemAt(10).equals("L5"));    
        assertTrue(roomComboBox.getItemAt(11).equals("L6"));    
        assertTrue(roomComboBox.getItemAt(12).equals("C1"));    
        assertTrue(roomComboBox.getItemAt(13).equals("C2"));
        assertTrue(roomComboBox.getItemAt(15).equals("S7"));    
        conn.close();
    }
    
    @Test
    public void testPopulateTimeSlotStartComboBox() throws SQLException {
        TimetableUI TUI = new TimetableUI();
                        
        TUI.populateTimeSlotStartComboBox();
        javax.swing.JComboBox<String> timeSlotStartComboBox = TUI.getTimeSlotStartComboBox();
        
        assertTrue(timeSlotStartComboBox.getItemAt(0).equals("8"));    
        assertTrue(timeSlotStartComboBox.getItemAt(1).equals("9"));    
        assertTrue(timeSlotStartComboBox.getItemAt(2).equals("10"));    
        assertTrue(timeSlotStartComboBox.getItemAt(3).equals("11"));    
        assertTrue(timeSlotStartComboBox.getItemAt(4).equals("12"));    
        assertTrue(timeSlotStartComboBox.getItemAt(5).equals("13"));    
        assertTrue(timeSlotStartComboBox.getItemAt(6).equals("14"));    
        assertTrue(timeSlotStartComboBox.getItemAt(7).equals("15"));    
        assertTrue(timeSlotStartComboBox.getItemAt(8).equals("16"));    
        assertTrue(timeSlotStartComboBox.getItemAt(9).equals("17"));    
        assertTrue(timeSlotStartComboBox.getItemAt(10).equals("18"));    
        assertTrue(timeSlotStartComboBox.getItemAt(11).equals("19"));    
    }
    
    @Test
    public void testPopulateTimeSlotEndComboBox() throws SQLException {
        TimetableUI TUI = new TimetableUI();
                        
        TUI.populateTimeSlotEndComboBox();
        javax.swing.JComboBox<String> timeSlotEndComboBox = TUI.getTimeSlotEndComboBox();
        
        assertTrue(timeSlotEndComboBox.getItemAt(0).equals("9"));    
        assertTrue(timeSlotEndComboBox.getItemAt(1).equals("10"));    
        assertTrue(timeSlotEndComboBox.getItemAt(2).equals("11"));    
        assertTrue(timeSlotEndComboBox.getItemAt(3).equals("12"));    
        assertTrue(timeSlotEndComboBox.getItemAt(4).equals("13"));    
        assertTrue(timeSlotEndComboBox.getItemAt(5).equals("14"));    
        assertTrue(timeSlotEndComboBox.getItemAt(6).equals("15"));    
        assertTrue(timeSlotEndComboBox.getItemAt(7).equals("16"));    
        assertTrue(timeSlotEndComboBox.getItemAt(8).equals("17"));    
        assertTrue(timeSlotEndComboBox.getItemAt(9).equals("18"));    
        assertTrue(timeSlotEndComboBox.getItemAt(10).equals("19"));    
        assertTrue(timeSlotEndComboBox.getItemAt(11).equals("20"));    
        
        
    }
    
    @Test
    public void testPopulateYearComboBox() throws SQLException {
        TimetableUI TUI = new TimetableUI();
                        
        String[] arrayOfYears = {"1", "2", "3"};
        TUI.populateYearComboBox(arrayOfYears);
        javax.swing.JComboBox<String> yearComboBox = TUI.getYearComboBox();
        
        assertTrue(yearComboBox.getItemCount() == arrayOfYears.length);
        assertTrue(yearComboBox.getItemAt(0).equals("1"));
        assertTrue(yearComboBox.getItemAt(1).equals("2"));
        assertTrue(yearComboBox.getItemAt(2).equals("3"));
        
        
    }
    
    @Test
    public void testPopulateDayComboBox() throws SQLException {
        TimetableUI TUI = new TimetableUI();
        
        String[] arrayOfYears = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        TUI.populateDayComboBox(arrayOfYears);
        javax.swing.JComboBox<String> dayComboBox = TUI.getDayComboBox();
        
        assertTrue(dayComboBox.getItemCount() == arrayOfYears.length);
        assertTrue(dayComboBox.getItemAt(0).equals("Monday"));
        assertTrue(dayComboBox.getItemAt(1).equals("Tuesday"));
        assertTrue(dayComboBox.getItemAt(2).equals("Wednesday"));
        assertTrue(dayComboBox.getItemAt(3).equals("Thursday"));
        assertTrue(dayComboBox.getItemAt(4).equals("Friday"));
    }
    
    @Test
    public void testUpdateTeacherComboBox() throws SQLException {
        TimetableUI TUI = new TimetableUI();
        conn = JDBCConnection.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        
        TUI.populateTeacherComboBox();
        TUI.populateDisciplineComboBox();
        javax.swing.JComboBox<String> beforeTeacherComboBox = TUI.getTeacherComboBox();
        
        System.out.println("Before" + beforeTeacherComboBox);
        for(int i = 0; i < beforeTeacherComboBox.getItemCount(); ++i) {
            
        }
        
        TUI.updateTeacherComboBox();
        javax.swing.JComboBox<String> afterTeacherComboBox = TUI.getTeacherComboBox();
        
        System.out.println("After" + afterTeacherComboBox);
        conn.close();
    }
}
