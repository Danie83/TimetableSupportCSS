/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.css.timetable;

/**
 *
 * @author 1
 */
public class RegistrationTimetable {
    private int startHour, endHour;
    private String room, day, course, courseType, groupName, teacher;
    
    public RegistrationTimetable(){}
    
    public RegistrationTimetable(int startH, int endH, String room, String day, String course, String courseType, String groupName, String teacher){
        this.startHour = startH;
        this.endHour = endH;
        this.room = room;
        this.day = day;
        this.course = course;
        this.courseType = courseType;
        this.groupName = groupName;
        this.teacher = teacher;
    }
    

    /**
     * @return the day
     */
    public String getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(String day) {
        assert day != null : "Day must not be null";
        this.day = day;
    }

    /**
     * @return the course
     */
    public String getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(String course) {
        assert course != null : "Course must not be null";
        this.course = course;
    }

    /**
     * @return the courseType
     */
    public String getCourseType() {
        return courseType;
    }

    /**
     * @param courseType the courseType to set
     */
    public void setCourseType(String courseType) {
        assert courseType != null : "Course type must not be null";
        this.courseType = courseType;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        assert groupName != null : "Group name must not be null";
        this.groupName = groupName;
    }

    /**
     * @return the teacher
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * @param teacher the teacher to set
     */
    public void setTeacher(String teacher) {
        assert teacher != null : "Teacher must not be null";
        this.teacher = teacher;
    }

    /**
     * @return the room
     */
    public String getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(String room) {
        assert room != null : "Room must not be null";
        this.room = room;
    }

    /**
     * @return the starHour
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     * @param starHour the starHour to set
     */
    public void setStartHour(int starHour) {
        assert starHour < 8 && starHour > 18 : "Start hour must have the value between 8 and 16";
        this.startHour = starHour;
    }

    /**
     * @return the endHour
     */
    public int getEndHour() {
        return endHour;
    }

    /**
     * @param endHour the endHour to set
     */
    public void setEndHour(int endHour) {
        assert endHour < 9 && endHour > 20: "End hour must have the value between 9 and 20";
        this.endHour = endHour;
    }

    @Override
    public String toString() {
        return "RegistrationTimetable{" +
                "startHour=" + startHour +
                ", endHour=" + endHour +
                ", room='" + room + '\'' +
                ", day='" + day + '\'' +
                ", course='" + course + '\'' +
                ", courseType='" + courseType + '\'' +
                ", groupName='" + groupName + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
