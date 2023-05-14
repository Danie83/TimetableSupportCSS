/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unit_tests;

import org.junit.jupiter.api.Test;
import com.css.timetable.RegistrationTimetable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.mockito.MockitoAnnotations;
/**
 *
 * @author baciu
 */
public class RegistrationTimetableTests {
    
    @Test
    public void testSetDay() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
        
        rt.setDay("Monday");
        assertTrue("Monday".equals(rt.getDay()));
    }
    
    @Test
    public void testSetCourse() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
       
        rt.setCourse("Matematica");
        assertTrue("Matematica".equals(rt.getCourse()));
    }
    
    @Test
    public void testSetCourseType() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setCourseType("seminar");
        assertTrue("seminar".equals(rt.getCourseType()));
    }
    
    @Test
    public void testSetGroupName() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setGroupName("A1");
        assertTrue("A1".equals(rt.getGroupName()));
    }
    
    @Test
    public void testSetTeacher() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setTeacher("Dragos");
        assertTrue("Dragos".equals(rt.getTeacher()));
    }
    
    @Test
    public void testSetRoom() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setRoom("101");
        assertTrue("101".equals(rt.getRoom()));
    }
    
    @Test
    public void testSetStartHour() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setStartHour(10);
        assertTrue(10 == rt.getStartHour());
    }
    
    @Test
    public void testSetEndHour() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setEndHour(12);
        assertTrue(12 == rt.getEndHour());
    }
    
    @Test
    public void testToString() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setDay("Monday");
        rt.setCourse("Matematica");
        rt.setCourseType("seminar");
        rt.setTeacher("Dragos");
        rt.setGroupName("A1");
        rt.setRoom("101");
        rt.setStartHour(10);
        rt.setEndHour(12);
    
        String expectedString = "RegistrationTimetable{" +
                "startHour=" + rt.getStartHour() +
                ", endHour=" + rt.getEndHour() +
                ", room='" + rt.getRoom() + '\'' +
                ", day='" + rt.getDay() + '\'' +
                ", course='" + rt.getCourse() + '\'' +
                ", courseType='" + rt.getCourseType() + '\'' +
                ", groupName='" + rt.getGroupName() + '\'' +
                ", teacher='" + rt.getTeacher() + '\'' +
                '}';
        
        assertTrue(expectedString.equals(rt.toString()));
    }
    
}
