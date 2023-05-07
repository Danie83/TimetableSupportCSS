/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.css.timetable;

import com.css.timetable.export.ExportTimetable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class TimetableUI extends javax.swing.JFrame {

    /**
     * Creates new form TimetableUI
     */
    public TimetableUI() {
        super("Timetable UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        populateGroupComboBox();
        populateTeacherComboBox();
        populateClassComboBox();
        populateRoomComboBox();
        populateTimeSlotStartComboBox();
        populateTimeSlotEndComboBox();
        
        updateDisciplineComboBox();
        updateTeacherComboBox();
        updateYearComboBox();
        updateRoomComboBox();
        
        populateTable();
        
        ConfigReader config = ConfigReader.getInstance();
        
        int years = Integer.parseInt(config.getProperty("setup.groups.years"));
        String[] yearItems = new String[years];
        for (int i = 0; i < years; i++) {
            yearItems[i] = Integer.toString(i+1);
        }
        
        populateYearComboBox(yearItems);
        String[] dayItems = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        populateDayComboBox(dayItems);
    }
    
    public void updateUI()
    {
        String[] items = {"Group 1", "Group 2", "Group 3"};
        // modify existent method to receive a parameter
        // populateGroupComboBox(items);
        // updateUI should be called when required (button is pressed, combobox item is selected)
    }
    
    public void populateTable()
    {
        String columns[] = {"From - To", "Group", "Discipline", "Type", "Teacher", "Day", "Room"};
        String group = (String) groupComboBox.getSelectedItem();
        String sql = "SELECT * FROM timetable WHERE group_name = ?;";
        List<String[]> timetable = new ArrayList<>();
        try 
        {
            Connection conn = JDBCConnection.getInstance().getConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, group);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next())
            {
                int i = 0;
                String[] item = new String[columns.length];
                item[i++] = new StringBuilder().append(rs.getInt("start_hour")).append(" - ").append(rs.getInt("end_hour")).toString();
                item[i++] = rs.getString("group_name");
                item[i++] = rs.getString("course");
                item[i++] = rs.getString("course_type");
                item[i++] = rs.getString("teacher");
                item[i++] = rs.getString("day");
                item[i++] = rs.getString("room");
                timetable.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String[][] data = new String[timetable.size()][];
        for (int j = 0; j < timetable.size(); j++)
        {
            data[j] = timetable.get(j);
        }
        
        DefaultTableModel model = new DefaultTableModel(data, columns);
        jTable1.setModel(model);
    }
    
    public void populateGroupComboBox()
    {
        int groupNumber = 0;
        int i = 0;
        try
        {
          Connection conn = JDBCConnection.getInstance().getConnection();
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM groups;");
          while(rs.next()){
            groupNumber = rs.getInt("total");
          }
          
          String[] groupItems = new String[groupNumber];
          stmt = conn.createStatement();
          rs = stmt.executeQuery("SELECT name FROM groups;");
          while(rs.next()){
             groupItems[i] = rs.getString("name");
             i++;
          }
          
          DefaultComboBoxModel model = new DefaultComboBoxModel(groupItems);
          groupComboBox.setModel(model);
        } 
        catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void populateTeacherComboBox()
    {
        int teachersNumber = 0;
        String items[];
        try
        {
          Connection conn = JDBCConnection.getInstance().getConnection();
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM teachers;");
          while(rs.next()){
            teachersNumber = rs.getInt("total");
          }
          
          items = new String[teachersNumber];
          int i = 0;
          stmt = conn.createStatement();
          rs = stmt.executeQuery("SELECT last_name, first_name FROM teachers;");
          while(rs.next()){
            String last_name = rs.getString("last_name");
            String first_name = rs.getString("first_name");
            items[i] = new StringBuilder().append(last_name).append(" ").append(first_name).toString();
            i++;
          }
          
          DefaultComboBoxModel model = new DefaultComboBoxModel(items);
          teacherComboBox.setModel(model);
        } 
        catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateTeacherComboBox()
    {
        String selectedDiscipline = (String) disciplineComboBox.getSelectedItem();
        try
        {
            Connection conn = JDBCConnection.getInstance().getConnection();
            
            String sql = "SELECT first_name, last_name FROM teachers JOIN discipline ON teachers.course_id = discipline.id WHERE discipline.name = ?;";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, selectedDiscipline);
            ResultSet rs = ptmt.executeQuery();
            List<String> items = new ArrayList<>();
            while (rs.next())
            {
                String fName = rs.getString("first_name");
                String lName = rs.getString("last_name");
                
                String name = new StringBuilder().append(fName).append(" ").append(lName).toString();
                items.add(name);
            }
            
            String[] finalItems = items.toArray(new String[items.size()]);
            DefaultComboBoxModel model = new DefaultComboBoxModel(finalItems);
            teacherComboBox.setModel(model);
        }
        catch(SQLException ex)
        {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void populateDisciplineComboBox()
    {
        int disciplineNumber = 0;
        String items[];
        try
        {
          Connection conn = JDBCConnection.getInstance().getConnection();
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM discipline;");
          while(rs.next()){
            disciplineNumber = rs.getInt("total");
          }
          
          items = new String[disciplineNumber];
          int i = 0;
          stmt = conn.createStatement();
          rs = stmt.executeQuery("SELECT name FROM discipline;");
          while(rs.next()){
            String name = rs.getString("name");
            items[i] = new StringBuilder().append(name).toString();
            i++;
          }
          
          DefaultComboBoxModel model = new DefaultComboBoxModel(items);
          disciplineComboBox.setModel(model);
        } 
        catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateDisciplineComboBox()
    {
        String selectedGroup = (String) groupComboBox.getSelectedItem();
        int year = selectedGroup.toCharArray()[0] - '0';
        String checkDisciplinesQuery = "SELECT name FROM discipline WHERE year = ?;";
        try
        {
            Connection conn = JDBCConnection.getInstance().getConnection();
            
            PreparedStatement ptmt1 = conn.prepareStatement("SELECT COUNT(*) AS total FROM discipline WHERE year = ?;");
            ptmt1.setInt(1, year);
            ResultSet rs1 = ptmt1.executeQuery();
            rs1.next();
            String[] items = new String[rs1.getInt("total")];
            
            PreparedStatement ptmt = conn.prepareStatement(checkDisciplinesQuery);
            ptmt.setInt(1, year);
            ResultSet rs = ptmt.executeQuery();
            int i = 0;
            while (rs.next())
            {
                rs.getString("name");
                items[i++] = rs.getString("name");
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(items);
            disciplineComboBox.setModel(model);
        }
        catch(SQLException ex)
        {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void populateClassComboBox()
    {
        String[] items = {"Seminary", "Laboratory", "Course", "Course Exam", "Lab Exam"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        classComboBox.setModel(model);
    }
    
    public void populateRoomComboBox()
    {
        int roomsNumber = 0;
        String items[];
        try
        {
          Connection conn = JDBCConnection.getInstance().getConnection();
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM rooms;");
          while(rs.next()){
            roomsNumber = rs.getInt("total");
          }
          
          items = new String[roomsNumber];
          int i = 0;
          stmt = conn.createStatement();
          rs = stmt.executeQuery("SELECT name FROM rooms;");
          while(rs.next()){
            String name = rs.getString("name");
            items[i] = new StringBuilder().append(name).toString();
            i++;
          }
          
          DefaultComboBoxModel model = new DefaultComboBoxModel(items);
          roomComboBox.setModel(model);
        } 
        catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateRoomComboBox()
    {
        String selectedClass = (String) classComboBox.getSelectedItem();
        selectedClass = selectedClass.startsWith("C") ? "Course" : selectedClass;
        selectedClass = selectedClass.startsWith("L") ? "Laboratory" : selectedClass;
         
        try
        {
            Connection conn = JDBCConnection.getInstance().getConnection();
            PreparedStatement ptmt = conn.prepareStatement("SELECT name FROM rooms WHERE type = ?;");
            ptmt.setString(1, selectedClass);
            ResultSet rs = ptmt.executeQuery();
            List<String> itemsList = new ArrayList<>();
            while(rs.next())
            {
                itemsList.add(rs.getString("name"));
            }
            String[] items = itemsList.toArray(new String[itemsList.size()]);
            DefaultComboBoxModel model = new DefaultComboBoxModel(items);
            roomComboBox.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void populateTimeSlotStartComboBox()
    {
        String[] items = {"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        timeSlotStartComboBox.setModel(model);
    }
    
        public void updateTimeSlotStartComboBox(int endHour)
    {
        int numberOfHours = endHour - 8;
        String[] items = new String[numberOfHours];
        
        for (int i = 8, j = 0; i < endHour; i++, j++)
        {
            items[j] = String.valueOf(i);
        }
        
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        timeSlotStartComboBox.setModel(model);
    }
    
    public void populateTimeSlotEndComboBox()
    {
        String[] items = {"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        timeSlotEndComboBox.setModel(model);
    }
    
    public void updateTimeSlotEndComboBox(int startHour)
    {
        int numberOfHours = 20 - startHour;
        String[] items = new String[numberOfHours];
        
        for (int i = startHour + 1, j = 0; i <= 20; i++, j++)
        {
            items[j] = String.valueOf(i);
        }
        
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        timeSlotEndComboBox.setModel(model);
    }
    
    public void populateYearComboBox(String[] items)
    {
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        yearComboBox.setModel(model);
    }
    
    public void updateYearComboBox()
    {
        String selectedGroup = (String) groupComboBox.getSelectedItem();
        String[] items = new String[1];
        
        items[0] = Character.toString(selectedGroup.toCharArray()[0]);
        
        yearComboBox.setSelectedItem(items[0]);
    }
    
    public void populateDayComboBox(String[] items)
    {
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        dayComboBox.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        submitButon = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        groupComboBox = new javax.swing.JComboBox<>();
        teacherComboBox = new javax.swing.JComboBox<>();
        disciplineComboBox = new javax.swing.JComboBox<>();
        classComboBox = new javax.swing.JComboBox<>();
        roomComboBox = new javax.swing.JComboBox<>();
        timeSlotStartComboBox = new javax.swing.JComboBox<>();
        timeSlotEndComboBox = new javax.swing.JComboBox<>();
        yearComboBox = new javax.swing.JComboBox<>();
        dayComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Add to timetable");

        jLabel9.setText("Actions:");

        submitButon.setText("Submit");
        submitButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButonActionPerformed(evt);
            }
        });

        jButton2.setText("Export All");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel10.setText("Log");

        jLabel11.setText("Timetable");

        jLabel2.setText("Group:");

        jLabel3.setText("Teacher:");

        jLabel4.setText("Discipline:");

        jLabel5.setText("Class Type:");

        jLabel6.setText("Room:");

        jLabel7.setText("Time Slots:");

        jLabel8.setText("Year:");

        jLabel12.setText("Day:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12))
                        .addGap(0, 12, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        groupComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        groupComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupComboBoxActionPerformed(evt);
            }
        });

        teacherComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        disciplineComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        classComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        classComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classComboBoxActionPerformed(evt);
            }
        });

        roomComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        timeSlotStartComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        timeSlotStartComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeSlotStartComboBoxActionPerformed(evt);
            }
        });

        timeSlotEndComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        timeSlotEndComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeSlotEndComboBoxActionPerformed(evt);
            }
        });

        yearComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        dayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(teacherComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(disciplineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(timeSlotStartComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timeSlotEndComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(groupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teacherComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(disciplineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeSlotStartComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeSlotEndComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(submitButon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(326, 326, 326)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(submitButon)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 16, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void groupComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupComboBoxActionPerformed
        updateDisciplineComboBox();
        updateTeacherComboBox();
        updateYearComboBox();
        populateTable();
    }//GEN-LAST:event_groupComboBoxActionPerformed

    private void submitButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButonActionPerformed
        int startHour = Integer.parseInt((String) timeSlotStartComboBox.getSelectedItem());
        int endHour = Integer.parseInt((String) timeSlotEndComboBox.getSelectedItem());
        String group = (String) groupComboBox.getSelectedItem();
        String discipline = (String) disciplineComboBox.getSelectedItem();
        String type = (String) classComboBox.getSelectedItem();
        String teacher = (String) teacherComboBox.getSelectedItem();
        String room = (String) roomComboBox.getSelectedItem();
        String day = (String) dayComboBox.getSelectedItem();
        
        try
        {
            Connection conn = JDBCConnection.getInstance().getConnection();
            try (PreparedStatement ptmt = conn.prepareStatement("INSERT INTO timetable (room, start_hour, end_hour, day, course, course_type, group_name, teacher) VALUES(?,?,?,?,?,?,?,?);")) {
                ptmt.setString(1, room);
                ptmt.setInt(2, startHour);
                ptmt.setInt(3, endHour);
                ptmt.setString(4, day);
                ptmt.setString(5, discipline);
                ptmt.setString(6, type);
                ptmt.setString(7, group);
                ptmt.setString(8, teacher);
                ptmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        populateTable();
    }//GEN-LAST:event_submitButonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ExportTimetable.createHTML();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void timeSlotStartComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeSlotStartComboBoxActionPerformed
        JComboBox<String> comboBox = (JComboBox<String>) evt.getSource();
        int selectedHour = Integer.parseInt((String)comboBox.getSelectedItem());
        updateTimeSlotEndComboBox(selectedHour);
    }//GEN-LAST:event_timeSlotStartComboBoxActionPerformed

    private void timeSlotEndComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeSlotEndComboBoxActionPerformed
        JComboBox<String> comboBox = (JComboBox<String>) evt.getSource();
        int selectedHour = Integer.parseInt((String)comboBox.getSelectedItem());
        updateTimeSlotStartComboBox(selectedHour);
    }//GEN-LAST:event_timeSlotEndComboBoxActionPerformed

    private void classComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classComboBoxActionPerformed
        updateRoomComboBox();
    }//GEN-LAST:event_classComboBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TimetableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TimetableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TimetableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TimetableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TimetableUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> classComboBox;
    private javax.swing.JComboBox<String> dayComboBox;
    private javax.swing.JComboBox<String> disciplineComboBox;
    private javax.swing.JComboBox<String> groupComboBox;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JComboBox<String> roomComboBox;
    private javax.swing.JButton submitButon;
    private javax.swing.JComboBox<String> teacherComboBox;
    private javax.swing.JComboBox<String> timeSlotEndComboBox;
    private javax.swing.JComboBox<String> timeSlotStartComboBox;
    private javax.swing.JComboBox<String> yearComboBox;
    // End of variables declaration//GEN-END:variables
}
