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
    
    /**
     * In summary, this test method ensures that the setDay() method of the RegistrationTimetable class 
     * correctly sets the value and that the value can be retrieved using the 
     * getDay() method. If the value returned by rt.getDay() is equal to "Monday", 
     * the assertion passes; otherwise, the assertion fails.
     */
    @Test
    public void testSetDay() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
        
        rt.setDay("Monday");
        assertTrue("Monday".equals(rt.getDay()));
    }
    
    /**
     * In summary, this test method ensures that the setCourse() method of the RegistrationTimetable class 
     * correctly sets the value and that the value can be retrieved using the 
     * getCourse() method. If the value returned by rt.getCourse() is equal to "Matematica", 
     * the assertion passes; otherwise, the assertion fails.
     */
    @Test
    public void testSetCourse() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
       
        rt.setCourse("Matematica");
        assertTrue("Matematica".equals(rt.getCourse()));
    }
    
    /**
     * In summary, this test method ensures that the setCourseType() method of the RegistrationTimetable class 
     * correctly sets the value and that the value can be retrieved using the 
     * getCourseType() method. If the value returned by rt.getCourseType() is equal to "seminar", 
     * the assertion passes; otherwise, the assertion fails.
     */
    @Test
    public void testSetCourseType() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setCourseType("seminar");
        assertTrue("seminar".equals(rt.getCourseType()));
    }
    
    /**
     * In summary, this test method ensures that the setGroupName() method of the RegistrationTimetable class 
     * correctly sets the value and that the value can be retrieved using the 
     * getGroupName() method. If the value returned by rt.getGroupName() is equal to "A1", 
     * the assertion passes; otherwise, the assertion fails.
     */
    @Test
    public void testSetGroupName() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setGroupName("A1");
        assertTrue("A1".equals(rt.getGroupName()));
    }
    
    /**
     * In summary, this test method ensures that the setTeacher() method of the RegistrationTimetable class 
     * correctly sets the value and that the value can be retrieved using the 
     * getTeacher() method. If the value returned by rt.getTeacher() is equal to "Dragos", 
     * the assertion passes; otherwise, the assertion fails.
     */
    @Test
    public void testSetTeacher() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setTeacher("Dragos");
        assertTrue("Dragos".equals(rt.getTeacher()));
    }
    
    /**
     * In summary, this test method ensures that the setRoom() method of the RegistrationTimetable class 
     * correctly sets the value and that the value can be retrieved using the 
     * getRoom() method. If the value returned by rt.getRoom() is equal to "101", 
     * the assertion passes; otherwise, the assertion fails.
     */
    @Test
    public void testSetRoom() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setRoom("101");
        assertTrue("101".equals(rt.getRoom()));
    }
    
    /**
     * In summary, this test method ensures that the setStartHour() method of the RegistrationTimetable class 
     * correctly sets the value and that the value can be retrieved using the 
     * getStartHour() method. If the value returned by rt.getStartHour() is equal to "10", 
     * the assertion passes; otherwise, the assertion fails.
     */
    @Test
    public void testSetStartHour() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setStartHour(10);
        assertTrue(10 == rt.getStartHour());
    }
    
    /**
     * In summary, this test method ensures that the setEndHour() method of the RegistrationTimetable class 
     * correctly sets the value and that the value can be retrieved using the 
     * getEndHour() method. If the value returned by rt.getEndHour() is equal to "12", 
     * the assertion passes; otherwise, the assertion fails.
     */
    @Test
    public void testSetEndHour() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setEndHour(12);
        assertTrue(12 == rt.getEndHour());
    }
    
    /**
     * In summary, this test method verifies that the toString() 
     * method of the RegistrationTimetable class produces the expected 
     * string representation of the object. It compares the expected 
     * string with the actual string returned by rt.toString(), and the assertion 
     * passes if they are equal; otherwise, the assertion fails.
     */
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
    
    /**
     * In summary, this test method ensures that the getDay() method 
     * of the RegistrationTimetable class correctly retrieves the 
     * value. If the value returned by 
     * rt.getDay() is equal to "Monday", the assertion passes; 
     * otherwise, the assertion fails.
     */
    @Test
    public void testGetDay() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setDay("Monday");
        assertTrue(rt.getDay().equals("Monday"));
    }
    
    /**
     * In summary, this test method ensures that the getCourse() method 
     * of the RegistrationTimetable class correctly retrieves the 
     * value. If the value returned by 
     * rt.getCourse() is equal to "Matematica", the assertion passes; 
     * otherwise, the assertion fails.
     */
    @Test
    public void testGetCourse() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setCourse("Matematica");
        assertTrue(rt.getCourse().equals("Matematica"));
    }
    
    /**
     * In summary, this test method ensures that the getCourseType() method 
     * of the RegistrationTimetable class correctly retrieves the 
     * value. If the value returned by 
     * rt.getCourseType() is equal to "seminary", the assertion passes; 
     * otherwise, the assertion fails.
     */
    @Test
    public void testGetCourseType() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setCourseType("seminary");
        assertTrue(rt.getCourseType().equals("seminary"));
    }
    
    /**
     * In summary, this test method ensures that the getGroupName() method 
     * of the RegistrationTimetable class correctly retrieves the 
     * value. If the value returned by 
     * rt.getGroupName() is equal to "A1", the assertion passes; 
     * otherwise, the assertion fails.
     */
    @Test
    public void testGetGroupName() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setGroupName("A1");
        assertTrue(rt.getGroupName().equals("A1"));
    }
    
    /**
     * In summary, this test method ensures that the getTeacher() method 
     * of the RegistrationTimetable class correctly retrieves the 
     * value. If the value returned by 
     * rt.getTeacher() is equal to "Dragos", the assertion passes; 
     * otherwise, the assertion fails.
     */
    @Test
    public void testGetTeacher() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setTeacher("Dragos");
        assertTrue(rt.getTeacher().equals("Dragos"));
    }
    
    /**
     * In summary, this test method ensures that the getRoom() method 
     * of the RegistrationTimetable class correctly retrieves the 
     * value. If the value returned by 
     * rt.getRoom() is equal to "101", the assertion passes; 
     * otherwise, the assertion fails.
     */
    @Test
    public void testGetRoom() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setRoom("101");
        assertTrue(rt.getRoom().equals("101"));
    }
    
    /**
     * In summary, this test method ensures that the getStartHour() method 
     * of the RegistrationTimetable class correctly retrieves the 
     * value. If the value returned by 
     * rt.getStartHour() is equal to "8", the assertion passes; 
     * otherwise, the assertion fails.
     */
    @Test
    public void testGetStartHour() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setStartHour(8);
        assertTrue(rt.getStartHour() == 8);
    }
    
    /**
     * In summary, this test method ensures that the getEndHour() method 
     * of the RegistrationTimetable class correctly retrieves the 
     * value. If the value returned by 
     * rt.getEndHour() is equal to "9", the assertion passes; 
     * otherwise, the assertion fails.
     */
    @Test
    public void testGetEndHour() {
        RegistrationTimetable rt = new RegistrationTimetable();
        MockitoAnnotations.openMocks(this); // Initialize the annotated mocks
    
        rt.setEndHour(9);
        assertTrue(rt.getEndHour() == 9);
    }
    
}
