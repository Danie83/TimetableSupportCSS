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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
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
        textAreaExamDate.setVisible(false);
        jLabel13.setVisible(false);

        ConfigReader config = ConfigReader.getInstance();

        int years = Integer.parseInt(config.getProperty("setup.groups.years"));
        String[] yearItems = new String[years];
        for (int i = 0; i < years; i++) {
            yearItems[i] = Integer.toString(i + 1);
        }

        populateYearComboBox(yearItems);
        String[] dayItems = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        populateDayComboBox(dayItems);
        
    }

    public void updateUI() {
        String[] items = {"Group 1", "Group 2", "Group 3"};
        // modify existent method to receive a parameter
        // populateGroupComboBox(items);
        // updateUI should be called when required (button is pressed, combobox item is selected)
    }
    
    public void populateExamTable(){
        String columns[] = {"id", "From - To", "Group", "Discipline", "Type", "Teacher", "Date", "Room"};
        String group = (String) groupComboBox.getSelectedItem();
        String sql = "SELECT * FROM examtable WHERE group_name = ?;";
        List<String[]> timetable = new ArrayList<>();
        try {
            Connection conn = JDBCConnection.getInstance().getConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, group);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                int i = 0;
                String[] item = new String[columns.length];
                item[i++] = String.valueOf(rs.getInt("id"));
                item[i++] = new StringBuilder().append(rs.getInt("start_hour")).append(" - ").append(rs.getInt("end_hour")).toString();
                item[i++] = rs.getString("group_name");
                item[i++] = rs.getString("course");
                item[i++] = rs.getString("course_type");
                item[i++] = rs.getString("teacher");
                item[i++] = rs.getString("date");
                item[i++] = rs.getString("room");
                timetable.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[][] data = new String[timetable.size()][];
        for (int j = 0; j < timetable.size(); j++) {
            data[j] = timetable.get(j);
        }

        DefaultTableModel model = new DefaultTableModel(data, columns);
        jTable2.setModel(model);
        jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable2.getColumnModel().getColumn(0).setMinWidth(0);
        jTable2.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(0).setResizable(false);
    }

    public void populateTable() {
        String columns[] = {"id", "From - To", "Group", "Discipline", "Type", "Teacher", "Day", "Room"};
        String columns1[] = {"id", "From - To", "Group", "Discipline", "Type", "Teacher", "Date", "Room"};
        String group = (String) groupComboBox.getSelectedItem();
        String sql = "SELECT * FROM timetable WHERE group_name = ?;";
        List<String[]> timetable = new ArrayList<>();
        int totalExams = 0;
        int totalDisciplines = 0;
        
        try {
            Connection conn = JDBCConnection.getInstance().getConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, group);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                int i = 0;
                String[] item = new String[columns.length];
                item[i++] = String.valueOf(rs.getInt("id"));
                item[i++] = new StringBuilder().append(rs.getInt("start_hour")).append(" - ").append(rs.getInt("end_hour")).toString();
                item[i++] = rs.getString("group_name");
                item[i++] = rs.getString("course");
                item[i++] = rs.getString("course_type");
                if(item[i - 1].split(" ").length > 1 && item[i - 1].split(" ")[1].equals("Exam")){
                    totalExams++;
                }
                else{
                    totalDisciplines++;
                }
                item[i++] = rs.getString("teacher");
                item[i++] = rs.getString("day");
                item[i++] = rs.getString("room");
                timetable.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[][] dataTimetable = new String[totalDisciplines][];
        String[][] dataExamtable = new String[totalExams][];
        
        int i = 0;
        int ii = 0;
        for(int j = 0; j<timetable.size(); j++){
            String[] item = timetable.get(j);
            if(item[4].split(" ").length > 1 && item[4].split(" ")[1].equals("Exam")){
                dataExamtable[i] = item;
                i++;
            }
            else{
                dataTimetable[ii] = item;
                ii++;
            }
        }
        
        //for (int j = 0; j < timetable.size(); j++) {
        //    data[j] = timetable.get(j);
        //}

        DefaultTableModel model = new DefaultTableModel(dataTimetable, columns);
        DefaultTableModel model1 = new DefaultTableModel(dataExamtable, columns1);
        
        jTable1.setModel(model);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setResizable(false);
        
        jTable2.setModel(model1);
        jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable2.getColumnModel().getColumn(0).setMinWidth(0);
        jTable2.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(0).setResizable(false);
    }

    public void populateGroupComboBox() {
        int groupNumber = 0;
        int i = 0;
        try {
            Connection conn = JDBCConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM groups;");
            while (rs.next()) {
                groupNumber = rs.getInt("total");
            }

            String[] groupItems = new String[groupNumber];
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT name FROM groups;");
            while (rs.next()) {
                groupItems[i] = rs.getString("name");
                i++;
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(groupItems);
            groupComboBox.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void populateTeacherComboBox() {
        int teachersNumber = 0;
        String items[];
        try {
            Connection conn = JDBCConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM teachers;");
            while (rs.next()) {
                teachersNumber = rs.getInt("total");
            }

            items = new String[teachersNumber];
            int i = 0;
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT last_name, first_name FROM teachers;");
            while (rs.next()) {
                String last_name = rs.getString("last_name");
                String first_name = rs.getString("first_name");
                items[i] = new StringBuilder().append(last_name).append(" ").append(first_name).toString();
                i++;
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(items);
            teacherComboBox.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateTeacherComboBox() {
        String selectedDiscipline = (String) disciplineComboBox.getSelectedItem();
        try {
            Connection conn = JDBCConnection.getInstance().getConnection();
            String sql = "SELECT first_name, last_name FROM teachers JOIN discipline ON teachers.course_id = discipline.id WHERE discipline.name = ?;";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, selectedDiscipline);
            ResultSet rs = ptmt.executeQuery();
            List<String> items = new ArrayList<>();
            while (rs.next()) {
                String fName = rs.getString("first_name");
                String lName = rs.getString("last_name");

                String name = new StringBuilder().append(fName).append(" ").append(lName).toString();
                items.add(name);
                System.out.println(name);
            }

            String[] finalItems = items.toArray(new String[items.size()]);
            DefaultComboBoxModel model = new DefaultComboBoxModel(finalItems);
            teacherComboBox.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void populateDisciplineComboBox() {
        int disciplineNumber = 0;
        String items[];
        try {
            Connection conn = JDBCConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM discipline;");
            while (rs.next()) {
                disciplineNumber = rs.getInt("total");
            }

            items = new String[disciplineNumber];
            int i = 0;
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT name FROM discipline;");
            while (rs.next()) {
                String name = rs.getString("name");
                items[i] = new StringBuilder().append(name).toString();
                i++;
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(items);
            disciplineComboBox.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateDisciplineComboBox() {
        String selectedGroup = (String) groupComboBox.getSelectedItem();
        int year = selectedGroup.toCharArray()[0] - '0';
        String checkDisciplinesQuery = "SELECT name FROM discipline WHERE year = ?;";
        try {
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
            while (rs.next()) {
                rs.getString("name");
                items[i++] = rs.getString("name");
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(items);
            disciplineComboBox.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateTeacherComboBox();
    }

    public void populateClassComboBox() {
        String[] items = {"Seminary", "Laboratory", "Course", "Course Exam", "Lab Exam"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        classComboBox.setModel(model);
    }

    public void populateRoomComboBox() {
        int roomsNumber = 0;
        String items[];
        try {
            Connection conn = JDBCConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM rooms;");
            while (rs.next()) {
                roomsNumber = rs.getInt("total");
            }

            items = new String[roomsNumber];
            int i = 0;
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT name FROM rooms;");
            while (rs.next()) {
                String name = rs.getString("name");
                items[i] = new StringBuilder().append(name).toString();
                i++;
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(items);
            roomComboBox.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateRoomComboBox() {
        String selectedClass = (String) classComboBox.getSelectedItem();
        selectedClass = selectedClass.startsWith("C") ? "Course" : selectedClass;
        selectedClass = selectedClass.startsWith("L") ? "Laboratory" : selectedClass;

        try {
            Connection conn = JDBCConnection.getInstance().getConnection();
            PreparedStatement ptmt = conn.prepareStatement("SELECT name FROM rooms WHERE type = ?;");
            ptmt.setString(1, selectedClass);
            ResultSet rs = ptmt.executeQuery();
            List<String> itemsList = new ArrayList<>();
            while (rs.next()) {
                itemsList.add(rs.getString("name"));
            }
            String[] items = itemsList.toArray(new String[itemsList.size()]);
            DefaultComboBoxModel model = new DefaultComboBoxModel(items);
            roomComboBox.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void populateTimeSlotStartComboBox() {
        String[] items = {"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        timeSlotStartComboBox.setModel(model);
    }

    public void updateTimeSlotStartComboBox(int endHour) {
        Object selectedItem = timeSlotStartComboBox.getSelectedItem();
        int numberOfHours = endHour - 8;
        String[] items = new String[numberOfHours];

        for (int i = 8, j = 0; i < endHour; i++, j++) {
            items[j] = String.valueOf(i);
        }

        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        timeSlotStartComboBox.setModel(model);
        for (int i = 0; i < timeSlotStartComboBox.getItemCount(); i++) {
            Object item = timeSlotStartComboBox.getItemAt(i);
            if (item != null && item.toString().equals(selectedItem.toString())) {
                timeSlotStartComboBox.setSelectedItem(selectedItem.toString());
                break;
            }
        }
        updateTimeSlotEndComboBox(endHour - 1);
    }

    public void populateTimeSlotEndComboBox() {
        String[] items = {"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        timeSlotEndComboBox.setModel(model);
    }

    public void updateTimeSlotEndComboBox(int startHour) {
        int numberOfHours = 20 - startHour;
        String[] items = new String[numberOfHours];

        for (int i = startHour + 1, j = 0; i <= 20; i++, j++) {
            items[j] = String.valueOf(i);
        }

        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        timeSlotEndComboBox.setModel(model);
    }

    public void populateYearComboBox(String[] items) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        yearComboBox.setModel(model);
    }

    public void updateYearComboBox() {
        String selectedGroup = (String) groupComboBox.getSelectedItem();
        String[] items = new String[1];

        items[0] = Character.toString(selectedGroup.toCharArray()[0]);

        yearComboBox.setSelectedItem(items[0]);
    }

    public void populateDayComboBox(String[] items) {
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
        jLabel13 = new javax.swing.JLabel();
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
        textAreaExamDate = new javax.swing.JTextField();
        removeItem = new javax.swing.JButton();
        examTimetableLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

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
        jTextArea1.setMaximumSize(new java.awt.Dimension(16, 27));
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

        jLabel13.setText("Exam Date:");

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
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        groupComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        groupComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupComboBoxActionPerformed(evt);
            }
        });

        teacherComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        disciplineComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        disciplineComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disciplineComboBoxActionPerformed(evt);
            }
        });

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

        textAreaExamDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textAreaExamDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(teacherComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(disciplineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(timeSlotStartComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timeSlotEndComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textAreaExamDate, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(groupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teacherComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(disciplineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(roomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeSlotStartComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeSlotEndComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textAreaExamDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        removeItem.setText("Remove Item");
        removeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeItemActionPerformed(evt);
            }
        });

        examTimetableLabel.setText("Exam Timetable");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(submitButon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))
                            .addComponent(jLabel10)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel11)
                                .addComponent(removeItem)))
                        .addGap(32, 32, 32))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane4)))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(332, 332, 332)
                .addComponent(examTimetableLabel)
                .addGap(0, 0, Short.MAX_VALUE))
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
                        .addComponent(removeItem)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel11))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(examTimetableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void groupComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupComboBoxActionPerformed
        updateDisciplineComboBox();
        updateYearComboBox();
        populateTable();
    }//GEN-LAST:event_groupComboBoxActionPerformed

    //function for seeing if class and the room are suitable
    private boolean isRoomSuitable(String newType, String newRoom) {
        boolean ok = true;
        if ((newType == "Course" && newRoom.charAt(0) != 'C')
                || (newType == "Laboratory" && newRoom.charAt(0) != 'L')) {
            ok = false;
        }
        return ok;
    }

    //functie prin care iau numarul de inregistrari anterioare
    private Integer getRegistrationsNumber() {
        //vector folosit pentru vechile inregistrari
        int registrationsNumber = 0;
        RegistrationTimetable registrations[] = null;

        //Conectare la BD
        try {
            Connection conn = JDBCConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total_regs FROM timetable;");
            while (rs.next()) {
                registrationsNumber = rs.getInt("total_regs");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registrationsNumber;
    }

    //functie pentru a lua inregistrarile anterioare din tabel
    private RegistrationTimetable[] getRegistrationsFromDatabase() {

        //vector folosit pentru vechile inregistrari
        int registrationsNumber = 0;
        RegistrationTimetable registrations[] = null;

        //Connection to database
        try {
            Connection conn = JDBCConnection.getInstance().getConnection();
            registrationsNumber = getRegistrationsNumber();

            registrations = new RegistrationTimetable[registrationsNumber];
            int iter = 0;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM timetable");
            while (rs.next()) {
                RegistrationTimetable regi = new RegistrationTimetable();

                regi.setStartHour(rs.getInt("start_hour"));
                regi.setEndHour(rs.getInt("end_hour"));

                String group = rs.getString("group_name");
                regi.setGroupName(group.trim());

                String course = rs.getString("course");
                regi.setCourse(course.trim());

                String course_type = rs.getString("course_type");
                regi.setCourseType(course_type.trim());

                ////DE REVENIT CAND SE ADAUGA PROFII, PUNE TRIM()
                String teacher = rs.getString("teacher");
                regi.setTeacher(new StringBuilder().append(teacher).toString());

                String room = rs.getString("room").trim();
                regi.setRoom(room);

                String day = rs.getString("day");
                regi.setDay(day.trim());

                registrations[iter++] = regi;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return registrations;

    }

    //functie pentru constrangerea: un curs este inclus total temporal in celalalt
    private boolean isCourseNotTotallyOverlapped(RegistrationTimetable oldOne, RegistrationTimetable newOne) {

        boolean ok = true;

        if ( // un curs existent este inclus total temporal cursul nou
                oldOne.getStartHour() >= newOne.getStartHour()
                && oldOne.getEndHour() <= newOne.getEndHour()
                && oldOne.getDay().equals(newOne.getDay())
                && (oldOne.getRoom().equals(newOne.getRoom()) || /*oldOne.getTeacher().equals(newOne.getTeacher())||*/ oldOne.getGroupName().equals(newOne.getGroupName()))) {
            ok = false;
        }

        if ( //un curs existent include total temporal cursul nou
                oldOne.getStartHour() <= newOne.getStartHour()
                && oldOne.getEndHour() >= newOne.getEndHour()
                && oldOne.getDay().equals(newOne.getDay())
                && (oldOne.getRoom().equals(newOne.getRoom()) || /*oldOne.getTeacher().equals(newOne.getTeacher())||*/ oldOne.getGroupName().equals(newOne.getGroupName()))) {
            ok = false;
        }

        return ok;
    }

    //functie pentru constrangerea://cursul nou incepe/ se termina in timpul altui curs 
    private boolean isCourseNotPartiallyOverlapped(RegistrationTimetable oldOne, RegistrationTimetable newOne) {
        boolean ok = true;

        if ( //cursul nou incepe in timpul altui curs 
                oldOne.getEndHour() > newOne.getStartHour()
                && oldOne.getEndHour() <= newOne.getEndHour()
                && oldOne.getDay().equals(newOne.getDay())
                && (oldOne.getRoom().equals(newOne.getRoom()) || /*oldOne.getTeacher().equals(newOne.getTeacher())||*/ oldOne.getGroupName().equals(newOne.getGroupName()))) {
            ok = false;
        }

        if ( //cursul nou incepe in timpul altui curs 
                oldOne.getStartHour() >= newOne.getStartHour()
                && oldOne.getStartHour() < newOne.getEndHour()
                && oldOne.getDay().equals(newOne.getDay())
                && (oldOne.getRoom().equals(newOne.getRoom()) || /*oldOne.getTeacher().equals(newOne.getTeacher())||*/ oldOne.getGroupName().equals(newOne.getGroupName()))) {
            ok = false;
        }
        return ok;

    }
    
    private boolean hasValidDate(RegistrationTimetable reg){
        String day = reg.getDay();
        String[] dayItems = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        List<String> weekDays = new ArrayList<>();
        for(int i = 0; i<dayItems.length; i++){
            weekDays.add(dayItems[i]);
        }
        
        if(weekDays.contains(day)){
            return true;
        }
        else{
            String format = "dd-MM-yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(false);
            try {
                Date date = dateFormat.parse(day);
                LocalDate today = LocalDate.now();
                LocalDate dateToCheck = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                return dateToCheck.isAfter(today);
            } catch (ParseException e) {
                return false;
            }
        }
    }
    

    private boolean sameCourseOnceAWeek(RegistrationTimetable oldOne, RegistrationTimetable newOne) {

        boolean ok = true;

        if (newOne.getCourseType().equals("Course")) {
            if (oldOne.getCourseType().equals("Course")
                    && oldOne.getCourse().equals(newOne.getCourse())
                    && oldOne.getGroupName().charAt(0) == newOne.getGroupName().charAt(0)) {
                ok = false;
            }
        }
        return ok;
    }

    private boolean isViableForInsert(RegistrationTimetable newReg) {

        boolean ok = true;
        
        if(!hasValidDate(newReg)){
            ok = false;
            jTextArea1.setText("Invalid date format. The format is: (dd-MM-yyyy)");
        }
        
        if (isRoomSuitable(newReg.getCourseType(), newReg.getRoom()) == false) {
            ok = false;
            jTextArea1.setText("Invalid room. Choose one suitable for the type of activity that you have chosen. \nCourse rooms - name starts with C; \nLaboratory rooms - name starts with L;");
        }

        RegistrationTimetable[] registrations = getRegistrationsFromDatabase();
        int registrationsNumber = getRegistrationsNumber();

        for (int i = 0; i < registrationsNumber && ok; i++) {
            if (isCourseNotTotallyOverlapped(registrations[i], newReg) == false) {
                ok = false;
                jTextArea1.setText("The course that you have inserted overlaps the following one: " + registrations[i].toString() + "\n The activity that you have chosen is: " + newReg.toString());
                break;
            }

            if (isCourseNotPartiallyOverlapped(registrations[i], newReg) == false) {
                ok = false;
                jTextArea1.setText("The course that you have inserted partially overlaps the following one: " + registrations[i].toString() + "\n The activity that you have chosen is: " + newReg.toString());
                break;
            }

            if (sameCourseOnceAWeek(registrations[i], newReg) == false) {
                ok = false;
                jTextArea1.setText("There is already a course of " + registrations[i].getCourse() + " for this year. See table for group " + registrations[i].getGroupName() + ".");
                break;
            }
        }

        return ok;
    }

    private void submitButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButonActionPerformed
        int startHour = Integer.parseInt((String) timeSlotStartComboBox.getSelectedItem());
        int endHour = Integer.parseInt((String) timeSlotEndComboBox.getSelectedItem());
        String group = (String) groupComboBox.getSelectedItem();
        String discipline = (String) disciplineComboBox.getSelectedItem();
        String type = (String) classComboBox.getSelectedItem();
        String teacher = (String) teacherComboBox.getSelectedItem();
        String room = (String) roomComboBox.getSelectedItem();
        String day = (String) dayComboBox.getSelectedItem();
        String date = (String) textAreaExamDate.getText();
        System.out.println(date);
        
        RegistrationTimetable newReg = new RegistrationTimetable();
        
        boolean okExam = false;
        String[] splitType = type.split(" ");
        if(splitType.length > 1){
            if(splitType[1].equals("Exam")){
                okExam = true;
            }
            else{
                okExam = false;
            }
        }
        
        newReg.setStartHour(startHour);
        newReg.setEndHour(endHour);
        newReg.setGroupName(group);
        newReg.setCourse(discipline);
        newReg.setCourseType(type);
        newReg.setTeacher(teacher);
        newReg.setRoom(room);
        if(okExam){
            newReg.setDay(date);
            day = new String(date);
        }
        else{
            newReg.setDay(day);
        }
        
        
        if (isViableForInsert(newReg)) {
            if (newReg.getCourseType().equals("Course") || newReg.getCourseType().equals("Course Exam") || newReg.getCourseType().equals("Lab Exam")) {
                try {
                    int groupNumber = 0;
                    Connection conn = JDBCConnection.getInstance().getConnection();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM groups;");
                    while (rs.next()) {
                        groupNumber = rs.getInt("total");
                    }

                    String currentGroup = new String();
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT name FROM groups;");
                    while (rs.next()) {
                        currentGroup = rs.getString("name").trim();
                        if (newReg.getGroupName().charAt(0) == currentGroup.charAt(0)) {
                            PreparedStatement ptmt = conn.prepareStatement("INSERT INTO timetable (room, start_hour, end_hour, day, course, course_type, group_name, teacher) VALUES(?,?,?,?,?,?,?,?);");
                            ptmt.setString(1, room);
                            ptmt.setInt(2, startHour);
                            ptmt.setInt(3, endHour);
                            ptmt.setString(4, day);
                            ptmt.setString(5, discipline);
                            ptmt.setString(6, type);
                            ptmt.setString(7, currentGroup);
                            ptmt.setString(8, teacher);
                            ptmt.executeUpdate();
                            jTextArea1.setText("The activity was registered.");
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                try {
                    Connection conn = JDBCConnection.getInstance().getConnection();
                    PreparedStatement ptmt = conn.prepareStatement("INSERT INTO timetable (room, start_hour, end_hour, day, course, course_type, group_name, teacher) VALUES(?,?,?,?,?,?,?,?);");
                    ptmt.setString(1, room);
                    ptmt.setInt(2, startHour);
                    ptmt.setInt(3, endHour);
                    ptmt.setString(4, day);
                    ptmt.setString(5, discipline);
                    ptmt.setString(6, type);
                    ptmt.setString(7, group);
                    ptmt.setString(8, teacher);
                    ptmt.executeUpdate();
                    jTextArea1.setText("The activity was registered.");
                } catch (SQLException ex) {
                    Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        //populateExamTable();
        populateTable();
        
    }//GEN-LAST:event_submitButonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ExportTimetable.createHTML();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void timeSlotStartComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeSlotStartComboBoxActionPerformed
        JComboBox<String> comboBox = (JComboBox<String>) evt.getSource();
        int selectedHour = Integer.parseInt((String) comboBox.getSelectedItem());
        updateTimeSlotEndComboBox(selectedHour);
    }//GEN-LAST:event_timeSlotStartComboBoxActionPerformed

    private void timeSlotEndComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeSlotEndComboBoxActionPerformed
        JComboBox<String> comboBox = (JComboBox<String>) evt.getSource();
        int selectedHour = Integer.parseInt((String) comboBox.getSelectedItem());
        updateTimeSlotStartComboBox(selectedHour);
    }//GEN-LAST:event_timeSlotEndComboBoxActionPerformed

    private void classComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classComboBoxActionPerformed
        updateRoomComboBox();
        
        JComboBox<String> comboBox = (JComboBox<String>) evt.getSource();
        String selectedClassType = (String) comboBox.getSelectedItem();
        String[] splitRoomType = selectedClassType.split(" ");
        if(splitRoomType.length > 1){
            if(splitRoomType[1].equals("Exam")){
                textAreaExamDate.setVisible(true);
                jLabel13.setVisible(true);
                
                jLabel12.setVisible(false);
                dayComboBox.setVisible(false);
            }
        }
        else{
            textAreaExamDate.setVisible(false);
            jLabel13.setVisible(false);
            
            jLabel12.setVisible(true);
            dayComboBox.setVisible(true);
        }
        
    }//GEN-LAST:event_classComboBoxActionPerformed

    private void removeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeItemActionPerformed
        int selectedRowIndex = jTable1.getSelectedRow();
        int selectedRowIndex1 = jTable2.getSelectedRow();
        if (selectedRowIndex == -1 && selectedRowIndex1 == -1) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.INFO, "No items telected");
        }
        JTable refTable = jTable1;
        if(selectedRowIndex != -1){
            refTable = jTable1;
        }
        else if(selectedRowIndex1 != -1){
            selectedRowIndex = selectedRowIndex1;
            refTable = jTable2;
        }
        
        try {
            String value = refTable.getValueAt(selectedRowIndex, 0).toString();
            
            Connection conn = JDBCConnection.getInstance().getConnection();
            String sql = "DELETE FROM timetable WHERE id = ?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1, Integer.parseInt(value));
            int rowsDeleted = ptmt.executeUpdate();
            if (rowsDeleted > 0) {
                Logger.getLogger(TimetableUI.class.getName()).log(Level.INFO, "ITEM DELETED");
            } else {
                Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, "ITEM WAS NOT DELETED");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        populateTable();
    }//GEN-LAST:event_removeItemActionPerformed

    private void disciplineComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disciplineComboBoxActionPerformed
        updateTeacherComboBox();
    }//GEN-LAST:event_disciplineComboBoxActionPerformed

    private void textAreaExamDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textAreaExamDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textAreaExamDateActionPerformed

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
    private javax.swing.JLabel examTimetableLabel;
    private javax.swing.JComboBox<String> groupComboBox;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JTable jTable1;
    public javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton removeItem;
    private javax.swing.JComboBox<String> roomComboBox;
    private javax.swing.JButton submitButon;
    private javax.swing.JComboBox<String> teacherComboBox;
    private javax.swing.JTextField textAreaExamDate;
    private javax.swing.JComboBox<String> timeSlotEndComboBox;
    private javax.swing.JComboBox<String> timeSlotStartComboBox;
    private javax.swing.JComboBox<String> yearComboBox;
    // End of variables declaration//GEN-END:variables

}
