/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.css.timetable;

/**
 * Representation class for an entry in the timetable.
 */
public class RegistrationTimetable 
{
    /** Start hour of the course in the timetable entry. */
    private int startHour;
    
    /** End hour of course in the timetable entry. */
    private int endHour;
    
    /** Room where the course takes place. */
    private String room;
    
    /** Day when the course takes place. */
    private String day;
    
    /** Course of the timetable entry. */
    private String course;
    
    /** Type of the course. */
    private String courseType;
    
    /** Name of the group that attends the course. */
    private String groupName;
    
    /** Teacher that holds the course. */
    private String teacher;
    
    /**
     * Empty constructor.
     */
    public RegistrationTimetable()
    {
    }
    
    /**
     * Constructor that instantiates a timetable entry.
     * 
     * @param   startHour
     *          The start hour of the course.
     * @param   endHour
     *          The end hour of the course.
     * @param   room
     *          Room where the course takes place.
     * @param   day
     *          Day when the course takes place.
     * @param   course
     *          The course that takes place.
     * @param   courseType
     *          The type of the course.
     * @param   groupName
     *          The name of the group that attends the course.
     * @param   teacher 
     *          The name of the teacher that holds the course.
     */
    public RegistrationTimetable(int startHour,
                                 int endHour,
                                 String room,
                                 String day,
                                 String course,
                                 String courseType,
                                 String groupName,
                                 String teacher)
    {
        this.startHour = startHour;
        this.endHour = endHour;
        this.room = room;
        this.day = day;
        this.course = course;
        this.courseType = courseType;
        this.groupName = groupName;
        this.teacher = teacher;
    }

    /**
     * Getter for the day property.
     * 
     * @return  The day.
     */
    public String getDay() 
    {
        return day;
    }

    /**
     * Setter for the day property.
     * 
     * @param   day 
     *          The day to set.
     */
    public void setDay(String day) 
    {
        assert day != null : "Day must not be null";
        this.day = day;
    }

    /**
     * Getter for the course property.
     * 
     * @return  The course.
     */
    public String getCourse()
    {
        return course;
    }

    /**
     * Setter for the course property.
     * 
     * @param   course
     *          The course to set.
     */
    public void setCourse(String course) 
    {
        assert course != null : "Course must not be null";
        this.course = course;
    }

    /**
     * Getter for the courseType property.
     * 
     * @return  The courseType.
     */
    public String getCourseType() 
    {
        return courseType;
    }

    /**
     * Setter for the courseType property.
     * 
     * @param   courseType 
     *          The courseType to set.
     */
    public void setCourseType(String courseType)
    {
        assert courseType != null : "Course type must not be null";
        this.courseType = courseType;
    }

    /**
     * Getter for the groupName property.
     * 
     * @return  The groupName.
     */
    public String getGroupName() 
    {
        return groupName;
    }

    /**
     * Setter for the groupName property.
     * 
     * @param   groupName 
     *          The groupName to set.
     */
    public void setGroupName(String groupName) 
    {
        assert groupName != null : "Group name must not be null";
        this.groupName = groupName;
    }

    /**
     * Getter for the teacher property.
     * 
     * @return  The teacher
     */
    public String getTeacher() 
    {
        return teacher;
    }

    /**
     * Setter for the teacher property.
     * 
     * @param   teacher 
     *          The teacher to set.
     */
    public void setTeacher(String teacher) 
    {
        assert teacher != null : "Teacher must not be null";
        this.teacher = teacher;
    }

    /**
     * Getter for the room property.
     * 
     * @return  The room.
     */
    public String getRoom() 
    {
        return room;
    }

    /**
     * Setter for the room property.
     * 
     * @param   room 
     *          The room to set.
     */
    public void setRoom(String room) {
        assert room != null : "Room must not be null";
        this.room = room;
    }

    /**
     * Getter for the starHour property.
     * 
     * @return  The starHour.
     */
    public int getStartHour() 
    {
        return startHour;
    }

    /**
     * Setter for the startHour property.
     * 
     * @param`  starHour 
     *          The starHour to set.
     */
    public void setStartHour(int starHour) 
    {
        assert starHour < 8 && starHour > 18 : "Start hour must have the value between 8 and 16";
        this.startHour = starHour;
    }

    /**
     * The getter for the endHour property.
     * 
     * @return  The endHour.
     */
    public int getEndHour() 
    {
        return endHour;
    }

    /**
     * The setter for the endHour property.
     * 
     * @param   endHour 
     *          The endHour to set.
     */
    public void setEndHour(int endHour) 
    {
        assert endHour < 9 && endHour > 20: "End hour must have the value between 9 and 20";
        this.endHour = endHour;
    }

    /**
     * Returns a string representation containing all
     * the properties of the timetable entry.
     * 
     * @return String representation of the instance.
     */
    @Override
    public String toString()
    {
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
