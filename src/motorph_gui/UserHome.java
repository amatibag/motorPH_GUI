/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph_gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author amatibag
 */
public class UserHome extends javax.swing.JFrame {
    private String loginName;
    private String employeeNumber;
    private String username;  // Add this line to store the username
    
    /**
     * Creates new form UserHome
     */
    public UserHome(String username, String employeeNumber) {
        
        this.username = username;
        this.employeeNumber = employeeNumber;
        initComponents();
        setIconImage();
        
        startClock();
        
        // Set the login name based on username
        setLoginName(username);
        
        // Populate employee details based on employee number
        populateEmployeeDetails();
        
        populateLeaveTable();
    }
    
    

    // Method to set the login name label
    private void setLoginName(String username) {
        loginName = UserName.getLoginName(username);
        if (loginName != null) {
            jLabelLoginName.setText(loginName);
        } else {
            JOptionPane.showMessageDialog(this, "Username not found in database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
    // Method to populate employee details from motorPHEmployeeList.csv
    private void populateEmployeeDetails() {
        try (BufferedReader br = new BufferedReader(new FileReader("motorPHEmployeeList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String csvEmployeeNumber = data[0]; // Assuming employee number is in the first column
                
                if (employeeNumber.equals(csvEmployeeNumber)) {
                    // Populate fields with corresponding data
                    jTEmployeeNo.setText(csvEmployeeNumber);
                    jTlastN.setText(data[1]);    // Last name
                    jTfirstN.setText(data[2]);   // First name
                    jTbday.setText(data[3]);     // Birthday
                    jTextAreaAdd.setLineWrap(true); // Enable line wrapping
                    jTextAreaAdd.setWrapStyleWord(true); // Wrap at word boundaries
                    jTextAreaAdd.setText(data[4]); // Address (wrapped text)
                    jTphoneNo.setText(data[5]);  // Phone number
                    jTsss.setText(data[6]);      // SSS number
                    jTphilhealth.setText(data[7]);// Philhealth number
                    jTtin.setText(data[8]);      // TIN number
                    jTpagibig.setText(data[9]);  // Pag-ibig number
                    jTstat.setText(data[10]);    // Status
                    jTpost.setText(data[11]);    // Position
                    jTsup.setText(data[12]);     // Supervisor
                    jTbasic.setText(data[13]);   // Basic salary
                    jTRice.setText(data[14]);    // Rice subsidy
                    jTPhoneA.setText(data[15]);  // Phone allowance
                    jTClothing.setText(data[16]);// Clothing allowance
                    jTSemi.setText(data[17]);    // Semi-monthly rate
                    jThourly.setText(data[18]);  // Hourly rate
                    
                    return; // Exit loop after finding the correct employee
                }
            }
            JOptionPane.showMessageDialog(this, "Employee data not found.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void startClock() {
        // Set up a timer to update the time every second
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCurrentTime();
            }
        });
        timer.start();
    }

    private void updateCurrentTime() {
       // Get current time in the Philippines
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Manila"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm:ss");
        String formattedNow = now.format(formatter);

        // Update the label
        currentTime.setText(formattedNow);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelWelcome = new javax.swing.JLabel();
        jLabelLoginName = new javax.swing.JLabel();
        logoutBTN = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        btnClockIn = new javax.swing.JButton();
        currentTime = new javax.swing.JLabel();
        clockIn = new javax.swing.JLabel();
        btnClockOut = new javax.swing.JButton();
        clockOut = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanelempDetailsTab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableLeave = new javax.swing.JTable();
        jLMonth = new javax.swing.JLabel();
        jComboBoxMonth = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jTyear = new javax.swing.JTextField();
        daySelectionPanel = new javax.swing.JPanel();
        jLTimeOffType = new javax.swing.JLabel();
        jComboBoxTimeOffType = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnSubmitLeave = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jBTableDelete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLEmployeeNo = new javax.swing.JLabel();
        jLlastname = new javax.swing.JLabel();
        jLfirstName = new javax.swing.JLabel();
        jLBirthday = new javax.swing.JLabel();
        jLAddress = new javax.swing.JLabel();
        jLPhoneNo = new javax.swing.JLabel();
        jLSSSNo = new javax.swing.JLabel();
        jLPhilhealthNo = new javax.swing.JLabel();
        jLTINno = new javax.swing.JLabel();
        jLPagibigNo = new javax.swing.JLabel();
        jLStatus = new javax.swing.JLabel();
        jLPosition = new javax.swing.JLabel();
        jLSupervisor = new javax.swing.JLabel();
        jLBasic = new javax.swing.JLabel();
        jLRice = new javax.swing.JLabel();
        jLPhoneAllowance = new javax.swing.JLabel();
        jLClothingAllowance = new javax.swing.JLabel();
        jLSemi = new javax.swing.JLabel();
        jLHourly = new javax.swing.JLabel();
        jTEmployeeNo = new javax.swing.JTextField();
        jTlastN = new javax.swing.JTextField();
        jTfirstN = new javax.swing.JTextField();
        jTbday = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaAdd = new javax.swing.JTextArea();
        jTphoneNo = new javax.swing.JTextField();
        jTsss = new javax.swing.JTextField();
        jTphilhealth = new javax.swing.JTextField();
        jTtin = new javax.swing.JTextField();
        jTpagibig = new javax.swing.JTextField();
        jTstat = new javax.swing.JTextField();
        jTpost = new javax.swing.JTextField();
        jTsup = new javax.swing.JTextField();
        jTbasic = new javax.swing.JTextField();
        jTRice = new javax.swing.JTextField();
        jTPhoneA = new javax.swing.JTextField();
        jTClothing = new javax.swing.JTextField();
        jTSemi = new javax.swing.JTextField();
        jThourly = new javax.swing.JTextField();
        userHomeBG = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Employee Home Page");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelWelcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelWelcome.setForeground(new java.awt.Color(255, 255, 255));
        jLabelWelcome.setText("Welcome,");
        getContentPane().add(jLabelWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 115, 60, 20));

        jLabelLoginName.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabelLoginName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLoginName.setText("User Name Here");
        getContentPane().add(jLabelLoginName, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 115, 280, -1));

        logoutBTN.setBackground(new java.awt.Color(153, 39, 0));
        logoutBTN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logoutBTN.setForeground(new java.awt.Color(255, 255, 255));
        logoutBTN.setText("Log-out");
        logoutBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(60, 126, 114), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(210, 89, 51)));
        logoutBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBTNActionPerformed(evt);
            }
        });
        getContentPane().add(logoutBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 20, 130, 30));

        jTabbedPane1.setBackground(new java.awt.Color(60, 126, 114));

        jPanel3.setBackground(new java.awt.Color(60, 126, 114));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnClockIn.setBackground(new java.awt.Color(153, 39, 0));
        btnClockIn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClockIn.setForeground(new java.awt.Color(255, 255, 255));
        btnClockIn.setText("CLOCK IN");
        btnClockIn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0), null, null));
        jPanel3.add(btnClockIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 100, 30));

        currentTime.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        currentTime.setForeground(new java.awt.Color(255, 255, 255));
        currentTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentTime.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 245, 206), new java.awt.Color(255, 245, 206)), "Current Date and Time", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.add(currentTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 300, 50));

        clockIn.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        clockIn.setForeground(new java.awt.Color(255, 255, 255));
        clockIn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clockIn.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 39, 0), new java.awt.Color(153, 39, 0)), "Clock In Time", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.add(clockIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 300, 75));

        btnClockOut.setBackground(new java.awt.Color(153, 39, 0));
        btnClockOut.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClockOut.setForeground(new java.awt.Color(255, 255, 255));
        btnClockOut.setText("CLOCK IN");
        btnClockOut.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0), null, null));
        jPanel3.add(btnClockOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 240, 100, 30));

        clockOut.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        clockOut.setForeground(new java.awt.Color(255, 255, 255));
        clockOut.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clockOut.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 39, 0), new java.awt.Color(153, 39, 0)), "Clock Out Time", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.add(clockOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 180, 300, 75));

        jTabbedPane1.addTab("Clock In and Clock Out", jPanel3);

        jPanel4.setBackground(new java.awt.Color(60, 126, 114));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("Time Clock Report", jPanel4);

        jPanelempDetailsTab.setBackground(new java.awt.Color(60, 126, 114));
        jPanelempDetailsTab.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelempDetailsTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableLeave.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Month", "Day", "Year", "Time Off Type", "Status", "Payable Status"
            }
        ));
        jScrollPane2.setViewportView(jTableLeave);

        jPanelempDetailsTab.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 670, 200));

        jLMonth.setForeground(new java.awt.Color(255, 255, 255));
        jLMonth.setText("Month:");
        jPanelempDetailsTab.add(jLMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jComboBoxMonth.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBoxMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jComboBoxMonth.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBoxMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMonthActionPerformed(evt);
            }
        });
        jPanelempDetailsTab.add(jComboBoxMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 18, 100, 25));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Year:");
        jPanelempDetailsTab.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        jTyear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTyear.setToolTipText("Press Enter to Load Days");
        jTyear.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTyear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTyearActionPerformed(evt);
            }
        });
        jPanelempDetailsTab.add(jTyear, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 18, 50, 25));

        daySelectionPanel.setBackground(new java.awt.Color(255, 255, 255));
        daySelectionPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelempDetailsTab.add(daySelectionPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 80, 670, 70));

        jLTimeOffType.setForeground(new java.awt.Color(255, 255, 255));
        jLTimeOffType.setText("Time-Off Type:");
        jPanelempDetailsTab.add(jLTimeOffType, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, -1));

        jComboBoxTimeOffType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vacation", "Sick", "Leave of Absence" }));
        jComboBoxTimeOffType.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelempDetailsTab.add(jComboBoxTimeOffType, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 18, 150, 25));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Enter 4 -digit year then press ENTER then mark specific day/s of the month below:");
        jPanelempDetailsTab.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        btnSubmitLeave.setText("Submit");
        btnSubmitLeave.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSubmitLeave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitLeaveActionPerformed(evt);
            }
        });
        jPanelempDetailsTab.add(btnSubmitLeave, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 155, 100, 25));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 39, 0));
        jLabel3.setText("*");
        jPanelempDetailsTab.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 39, 0));
        jLabel4.setText("*");
        jPanelempDetailsTab.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 39, 0));
        jLabel5.setText("*");
        jPanelempDetailsTab.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 39, 0));
        jLabel6.setText("*");
        jPanelempDetailsTab.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jBTableDelete.setText("Delete");
        jBTableDelete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBTableDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTableDeleteActionPerformed(evt);
            }
        });
        jPanelempDetailsTab.add(jBTableDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 395, 100, 25));

        jTabbedPane1.addTab("Leave Request", jPanelempDetailsTab);

        jPanel2.setBackground(new java.awt.Color(60, 126, 114));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.addTab("Payroll Summary", jPanel2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 180, 700, 460));
        jTabbedPane1.getAccessibleContext().setAccessibleName("Tabs");

        jPanel1.setBackground(new java.awt.Color(60, 126, 114));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLEmployeeNo.setBackground(new java.awt.Color(255, 255, 255));
        jLEmployeeNo.setForeground(new java.awt.Color(255, 255, 255));
        jLEmployeeNo.setText("Employee Number");
        jPanel1.add(jLEmployeeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLlastname.setForeground(new java.awt.Color(255, 255, 255));
        jLlastname.setText("Last Name");
        jPanel1.add(jLlastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 150, 25));

        jLfirstName.setForeground(new java.awt.Color(255, 255, 255));
        jLfirstName.setText("First Name");
        jPanel1.add(jLfirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLBirthday.setForeground(new java.awt.Color(255, 255, 255));
        jLBirthday.setText("Birthday");
        jPanel1.add(jLBirthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 145, -1, -1));

        jLAddress.setForeground(new java.awt.Color(255, 255, 255));
        jLAddress.setText("Address");
        jPanel1.add(jLAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jLPhoneNo.setForeground(new java.awt.Color(255, 255, 255));
        jLPhoneNo.setText("Phone Number");
        jPanel1.add(jLPhoneNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        jLSSSNo.setForeground(new java.awt.Color(255, 255, 255));
        jLSSSNo.setText("SSS Number");
        jPanel1.add(jLSSSNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 325, -1, -1));

        jLPhilhealthNo.setForeground(new java.awt.Color(255, 255, 255));
        jLPhilhealthNo.setText("Philhealth Number");
        jPanel1.add(jLPhilhealthNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLTINno.setForeground(new java.awt.Color(255, 255, 255));
        jLTINno.setText("TIN Number");
        jPanel1.add(jLTINno, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 415, -1, -1));

        jLPagibigNo.setForeground(new java.awt.Color(255, 255, 255));
        jLPagibigNo.setText("Pag-ibig Number");
        jPanel1.add(jLPagibigNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        jLStatus.setForeground(new java.awt.Color(255, 255, 255));
        jLStatus.setText("Status");
        jPanel1.add(jLStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 55, -1, -1));

        jLPosition.setForeground(new java.awt.Color(255, 255, 255));
        jLPosition.setText("Position");
        jPanel1.add(jLPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, -1, -1));

        jLSupervisor.setForeground(new java.awt.Color(255, 255, 255));
        jLSupervisor.setText("Immediate Supervisor");
        jPanel1.add(jLSupervisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 145, -1, -1));

        jLBasic.setForeground(new java.awt.Color(255, 255, 255));
        jLBasic.setText("Basic Salary");
        jPanel1.add(jLBasic, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, -1, -1));

        jLRice.setForeground(new java.awt.Color(255, 255, 255));
        jLRice.setText("Rice Subsidy");
        jPanel1.add(jLRice, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 235, -1, -1));

        jLPhoneAllowance.setForeground(new java.awt.Color(255, 255, 255));
        jLPhoneAllowance.setText("Phone Allowance");
        jPanel1.add(jLPhoneAllowance, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 280, -1, -1));

        jLClothingAllowance.setForeground(new java.awt.Color(255, 255, 255));
        jLClothingAllowance.setText("Clothing Allowance");
        jPanel1.add(jLClothingAllowance, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 325, -1, -1));

        jLSemi.setForeground(new java.awt.Color(255, 255, 255));
        jLSemi.setText("Semi-monthly Rate");
        jPanel1.add(jLSemi, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 370, -1, -1));

        jLHourly.setForeground(new java.awt.Color(255, 255, 255));
        jLHourly.setText("Hourly Rate");
        jPanel1.add(jLHourly, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 415, -1, -1));

        jTEmployeeNo.setEditable(false);
        jTEmployeeNo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTEmployeeNo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTEmployeeNo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTEmployeeNo.setEnabled(false);
        jPanel1.add(jTEmployeeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 175, 20));

        jTlastN.setEditable(false);
        jTlastN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTlastN.setText(" ");
        jTlastN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTlastN.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTlastN.setEnabled(false);
        jPanel1.add(jTlastN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, 175, 20));

        jTfirstN.setEditable(false);
        jTfirstN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTfirstN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTfirstN.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTfirstN.setEnabled(false);
        jPanel1.add(jTfirstN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 175, 20));

        jTbday.setEditable(false);
        jTbday.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTbday.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTbday.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTbday.setEnabled(false);
        jPanel1.add(jTbday, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 165, 175, 20));

        jTextAreaAdd.setEditable(false);
        jTextAreaAdd.setColumns(20);
        jTextAreaAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextAreaAdd.setRows(5);
        jTextAreaAdd.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextAreaAdd.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextAreaAdd.setEnabled(false);
        jScrollPane1.setViewportView(jTextAreaAdd);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 195, 60));

        jTphoneNo.setEditable(false);
        jTphoneNo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTphoneNo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTphoneNo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTphoneNo.setEnabled(false);
        jPanel1.add(jTphoneNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 175, 20));

        jTsss.setEditable(false);
        jTsss.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTsss.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTsss.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTsss.setEnabled(false);
        jPanel1.add(jTsss, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 345, 175, 20));

        jTphilhealth.setEditable(false);
        jTphilhealth.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTphilhealth.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTphilhealth.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTphilhealth.setEnabled(false);
        jPanel1.add(jTphilhealth, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 175, 20));

        jTtin.setEditable(false);
        jTtin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTtin.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTtin.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTtin.setEnabled(false);
        jPanel1.add(jTtin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 435, 175, 20));

        jTpagibig.setEditable(false);
        jTpagibig.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTpagibig.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTpagibig.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTpagibig.setEnabled(false);
        jPanel1.add(jTpagibig, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 175, 20));

        jTstat.setEditable(false);
        jTstat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTstat.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTstat.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTstat.setEnabled(false);
        jPanel1.add(jTstat, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 75, 175, 20));

        jTpost.setEditable(false);
        jTpost.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTpost.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTpost.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTpost.setEnabled(false);
        jPanel1.add(jTpost, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 175, 20));

        jTsup.setEditable(false);
        jTsup.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTsup.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTsup.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTsup.setEnabled(false);
        jPanel1.add(jTsup, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 165, 175, 20));

        jTbasic.setEditable(false);
        jTbasic.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTbasic.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTbasic.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTbasic.setEnabled(false);
        jPanel1.add(jTbasic, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 175, 20));

        jTRice.setEditable(false);
        jTRice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTRice.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTRice.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTRice.setEnabled(false);
        jPanel1.add(jTRice, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 255, 175, 20));

        jTPhoneA.setEditable(false);
        jTPhoneA.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTPhoneA.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTPhoneA.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTPhoneA.setEnabled(false);
        jPanel1.add(jTPhoneA, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 175, 20));

        jTClothing.setEditable(false);
        jTClothing.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTClothing.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTClothing.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTClothing.setEnabled(false);
        jPanel1.add(jTClothing, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 345, 175, 20));

        jTSemi.setEditable(false);
        jTSemi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTSemi.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTSemi.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTSemi.setEnabled(false);
        jPanel1.add(jTSemi, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, 175, 20));

        jThourly.setEditable(false);
        jThourly.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jThourly.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jThourly.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jThourly.setEnabled(false);
        jPanel1.add(jThourly, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 435, 175, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 178, 410, 465));

        userHomeBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/motorph_gui/EmployeeHome.jpg"))); // NOI18N
        getContentPane().add(userHomeBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBTNActionPerformed
        // Close the current frame (AdminHome or UserHome)
    dispose();

    // Open the LoginPage
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new LoginPage().setVisible(true);
        }
    });
    }//GEN-LAST:event_logoutBTNActionPerformed

    private void jComboBoxMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMonthActionPerformed
        updateDaySelection();
    }//GEN-LAST:event_jComboBoxMonthActionPerformed

    private void jTyearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTyearActionPerformed
        updateDaySelection();
    }//GEN-LAST:event_jTyearActionPerformed

    private void btnSubmitLeaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitLeaveActionPerformed
       // Get input values
    String employeeNumber = jTEmployeeNo.getText().trim();
    String lastName = jTlastN.getText().trim();
    String firstName = jTfirstN.getText().trim();
    String selectedMonth = (String) jComboBoxMonth.getSelectedItem();
    String selectedYear = jTyear.getText().trim();
    String timeOffType = (String) jComboBoxTimeOffType.getSelectedItem();
    String status = "Pending";
    String payableStatus = "Pending";

    // Validate input: Year must be entered
    if (selectedYear.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a valid year.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Get selected checkboxes (days)
    Component[] components = daySelectionPanel.getComponents();
    List<String> selectedDays = new ArrayList<>();
    for (Component component : components) {
        if (component instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) component;
            if (checkBox.isSelected()) {
                selectedDays.add(checkBox.getText());
            }
        }
    }

    // Validate input: At least one day must be selected
    if (selectedDays.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please select at least one day.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Read existing TimeOff.csv data
    List<String> lines = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader("TimeOff.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading from TimeOff.csv: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    boolean existingEntryFound = false;

    // Check if any selected days are already approved or rejected
    for (String day : selectedDays) {
        for (String line : lines) {
            String[] data = line.split(",");
            if (data.length >= 9 && data[0].equals(employeeNumber) && data[3].equals(selectedMonth) && data[4].equals(day) && data[5].equals(selectedYear)) {
                // Found existing entry for the same employee, month, day, and year
                if (data[7].equals("Approved") || data[7].equals("Rejected")) {
                    JOptionPane.showMessageDialog(this, "Leave request cannot be modified. Please contact your Payroll Administrator.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    // Update the existing line
                    lines.set(lines.indexOf(line), String.join(",", employeeNumber, lastName, firstName, selectedMonth, day, selectedYear, timeOffType, status, payableStatus));
                    existingEntryFound = true;
                    break;
                }
            }
        }
    }

    // If no existing entry found, add new entries based on selected days
    if (!existingEntryFound) {
        for (String day : selectedDays) {
            lines.add(String.join(",", employeeNumber, lastName, firstName, selectedMonth, day, selectedYear, timeOffType, status, payableStatus));
        }
    }

    // Write updated data back to TimeOff.csv
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("TimeOff.csv"))) {
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
        JOptionPane.showMessageDialog(this, "Leave requests submitted successfully.");
        
        // Refresh jTableLeave
        populateLeaveTable();
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error writing to CSV file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnSubmitLeaveActionPerformed

    private void jBTableDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTableDeleteActionPerformed
     // Get the selected row index
    int selectedRow = jTableLeave.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Get data from the selected row in jTableLeave
    DefaultTableModel model = (DefaultTableModel) jTableLeave.getModel();
    String selectedMonth = (String) model.getValueAt(selectedRow, 0);
    String selectedDay = (String) model.getValueAt(selectedRow, 1);
    String selectedYear = (String) model.getValueAt(selectedRow, 2);
    String timeOffType = (String) model.getValueAt(selectedRow, 3);
    String status = (String) model.getValueAt(selectedRow, 4);
    String payableStatus = (String) model.getValueAt(selectedRow, 5);

    // Check if the status is "Approved" or "Rejected"
    if (status.equals("Approved") || status.equals("Rejected")) {
        JOptionPane.showMessageDialog(this, "Leave request cannot be modified. Please contact your Payroll Administrator.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Confirmation dialog to delete the selected row
    int confirmDelete = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this leave request?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (confirmDelete != JOptionPane.YES_OPTION) {
        return;
    }

    // Read and modify TimeOff.csv
    List<String> lines = new ArrayList<>();
    String loggedInEmployeeNumber = jTEmployeeNo.getText(); // Get logged-in employee number

    try (BufferedReader br = new BufferedReader(new FileReader("TimeOff.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

            // Check if the row matches the selected data and the logged-in employee number
            String csvEmployeeNumber = data[0];
            String csvMonth = data[3];
            String csvDay = data[4];
            String csvYear = data[5];
            String csvStatus = data[7];

            if (loggedInEmployeeNumber.equals(csvEmployeeNumber) &&
                selectedMonth.equals(csvMonth) &&
                selectedDay.equals(csvDay) &&
                selectedYear.equals(csvYear) &&
                !csvStatus.equals("Approved") && !csvStatus.equals("Rejected")) {
                lines.add(line); // Keep the line if it matches deletion criteria
            } else {
                lines.add(line); // Keep the line if it doesn't match deletion criteria
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error reading from TimeOff.csv: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Write updated data back to TimeOff.csv
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("TimeOff.csv"))) {
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        JOptionPane.showMessageDialog(this, "Leave request deleted successfully.");

        // Remove row from jTableLeave model
        model.removeRow(selectedRow);

    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error writing to TimeOff.csv: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jBTableDeleteActionPerformed

    
    // Method to populate jTableLeave with data from TimeOff.csv
        private void populateLeaveTable() {
            // Clear existing table data
            DefaultTableModel model = (DefaultTableModel) jTableLeave.getModel();
            model.setRowCount(0); // Clear table

            String loggedInEmployeeNumber = jTEmployeeNo.getText(); // Get logged-in employee number

            // Read and populate data from TimeOff.csv
            try (BufferedReader br = new BufferedReader(new FileReader("TimeOff.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");

                    // Check if the employee number matches logged-in user
                    String csvEmployeeNumber = data[0];
                    if (!loggedInEmployeeNumber.equals(csvEmployeeNumber)) {
                        continue; // Skip if not the logged-in user
                    }

                    // Extract data for jTableLeave: Month, Day, Year, Time Off Type, Status, Payable Status
                    String month = data[3];
                    String day = data[4];
                    String year = data[5];
                    String timeOffType = data[6];
                    String status = data[7];
                    String payableStatus = data[8];

                    // Add row to jTableLeave
                    model.addRow(new Object[]{month, day, year, timeOffType, status, payableStatus});
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading from TimeOff.csv: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Enable sorting for jTableLeave
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            jTableLeave.setRowSorter(sorter);

        }


        private void updateDaySelection() {
            try {
                String selectedMonth = (String) jComboBoxMonth.getSelectedItem();
                int selectedYear = Integer.parseInt(jTyear.getText());

                // Validate year input (optional)
                if (selectedYear < 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid year.");
                    return;
                }

                // Calculate number of days in the selected month and year
                YearMonth yearMonthObject = YearMonth.of(selectedYear, jComboBoxMonth.getSelectedIndex() + 1);
                int daysInMonth = yearMonthObject.lengthOfMonth();

                // Clear existing checkboxes
                daySelectionPanel.removeAll();

                // Calculate number of rows needed for the GridLayout
                int numRows = (int) Math.ceil((double) daysInMonth / 12); // 12 columns

                // Use GridLayout with specified rows and 12 columns
                daySelectionPanel.setLayout(new GridLayout(numRows, 12));

                // Add checkboxes for each day
                for (int day = 1; day <= daysInMonth; day++) {
                    JCheckBox checkBox = new JCheckBox(String.valueOf(day));
                    daySelectionPanel.add(checkBox);
                }

                // Repaint the panel to reflect changes
                daySelectionPanel.revalidate();
                daySelectionPanel.repaint();
            } catch (NumberFormatException | DateTimeException ex) {
                // Handle exceptions such as invalid year format or month selection
                JOptionPane.showMessageDialog(this, "Invalid input. Please check your month and year.");
            }
        }
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
            java.util.logging.Logger.getLogger(UserHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new UserHome("username", "employeeNumber").setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClockIn;
    private javax.swing.JButton btnClockOut;
    private javax.swing.JButton btnSubmitLeave;
    private javax.swing.JLabel clockIn;
    private javax.swing.JLabel clockOut;
    private javax.swing.JLabel currentTime;
    private javax.swing.JPanel daySelectionPanel;
    private javax.swing.JButton jBTableDelete;
    private javax.swing.JComboBox<String> jComboBoxMonth;
    private javax.swing.JComboBox<String> jComboBoxTimeOffType;
    private javax.swing.JLabel jLAddress;
    private javax.swing.JLabel jLBasic;
    private javax.swing.JLabel jLBirthday;
    private javax.swing.JLabel jLClothingAllowance;
    private javax.swing.JLabel jLEmployeeNo;
    private javax.swing.JLabel jLHourly;
    private javax.swing.JLabel jLMonth;
    private javax.swing.JLabel jLPagibigNo;
    private javax.swing.JLabel jLPhilhealthNo;
    private javax.swing.JLabel jLPhoneAllowance;
    private javax.swing.JLabel jLPhoneNo;
    private javax.swing.JLabel jLPosition;
    private javax.swing.JLabel jLRice;
    private javax.swing.JLabel jLSSSNo;
    private javax.swing.JLabel jLSemi;
    private javax.swing.JLabel jLStatus;
    private javax.swing.JLabel jLSupervisor;
    private javax.swing.JLabel jLTINno;
    private javax.swing.JLabel jLTimeOffType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelLoginName;
    private javax.swing.JLabel jLabelWelcome;
    private javax.swing.JLabel jLfirstName;
    private javax.swing.JLabel jLlastname;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelempDetailsTab;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTClothing;
    private javax.swing.JTextField jTEmployeeNo;
    private javax.swing.JTextField jTPhoneA;
    private javax.swing.JTextField jTRice;
    private javax.swing.JTextField jTSemi;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableLeave;
    private javax.swing.JTextField jTbasic;
    private javax.swing.JTextField jTbday;
    private javax.swing.JTextArea jTextAreaAdd;
    private javax.swing.JTextField jTfirstN;
    private javax.swing.JTextField jThourly;
    private javax.swing.JTextField jTlastN;
    private javax.swing.JTextField jTpagibig;
    private javax.swing.JTextField jTphilhealth;
    private javax.swing.JTextField jTphoneNo;
    private javax.swing.JTextField jTpost;
    private javax.swing.JTextField jTsss;
    private javax.swing.JTextField jTstat;
    private javax.swing.JTextField jTsup;
    private javax.swing.JTextField jTtin;
    private javax.swing.JTextField jTyear;
    private javax.swing.JButton logoutBTN;
    private javax.swing.JLabel userHomeBG;
    // End of variables declaration//GEN-END:variables

    private void setIconImage() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Logo.jpg")));
    }
}
