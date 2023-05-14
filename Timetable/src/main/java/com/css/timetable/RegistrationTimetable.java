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
    
   public RegistrationTimetable(){
       
   }
    
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
