/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph_gui;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.text.PlainDocument;

/**
 *
 * @author amatibag
 */
public class AdminPayroll extends javax.swing.JFrame {
    private String loginName;
    private String username;
     
    
    // Define a list to store all CSV data for easy access
    private List<String[]> csvData = new ArrayList<>();

    // Additional variable to store the employee data for easy access
    private List<String[]> employeeData = new ArrayList<>();
    /**
     * Creates new form AdminPayroll
     */
    public AdminPayroll(String username) {
        
        initComponents();
        setIconImage();
        initializeHourlyText();
        applyDocumentFilters();
        
        setLoginName(username);
        
        this.username = username;
        
        // Populate the jComboEmployee JComboBox with data from CSV
        populateEmployeeComboBox();
        
        // Add action listener to jComboEmployee
        jComboEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboEmployeeActionPerformed(evt);
            }
        });
        // Add action listener to jComboMonth and jTFYear
        jComboMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterTimeOffData();
            }
        });

        jTFYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterTimeOffData();
            }
        });
    }
    
    // Method to populate jComboEmployee from CSV
    private void populateEmployeeComboBox() {
        String csvFile = "motorPHEmployeeList.csv";
        String line;
        String cvsSplitBy = ","; 

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            csvData = new ArrayList<>(); // Initialize the csvData list

            // Skip the header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                csvData.add(data);
            }

            // Populate jComboEmployee with a blank item and employee names
            List<String> employees = new ArrayList<>();
            employees.add(""); // Add blank item
            for (String[] data : csvData) {
                if (data.length >= 3) {
                    String employeeInfo = data[0] + " - " + data[1] + ", " + data[2];
                    employees.add(employeeInfo);
                }
            }
            jComboEmployee.setModel(new javax.swing.DefaultComboBoxModel<>(employees.toArray(new String[0])));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
}
    // Method to filter the TimeOff.csv based on selected values
private void filterTimeOffData() {
    String selectedEmployee = (String) jComboEmployee.getSelectedItem();
    String selectedMonth = (String) jComboMonth.getSelectedItem();
    String selectedYear = jTFYear.getText().trim();

    if (selectedEmployee.isEmpty() || selectedMonth.equals("...") || selectedYear.isEmpty()) {
        return; // If any of the fields are empty, do not proceed
    }

    String employeeNumber = selectedEmployee.split(" - ")[0];

    String timeOffFile = "TimeOff.csv";
    String line;
    String cvsSplitBy = ",";
    List<String> unpaidLeaveDates = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(timeOffFile))) {
        br.readLine(); // Skip header

        while ((line = br.readLine()) != null) {
            String[] data = line.split(cvsSplitBy);

            if (data.length >= 9 && data[0].equals(employeeNumber) && data[3].equals(selectedMonth) &&
                    data[5].equals(selectedYear) && data[8].equals("Unpaid")) {
                unpaidLeaveDates.add(data[3] + " " + data[4] + ", " + data[5]); // Adjust format as needed
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading TimeOff.csv file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    // Display the unpaid leave list in the JTextArea
    unpaidLeaveList1.setText(String.join("\n", unpaidLeaveDates));
    unpaidLeaveList2.setText(String.join("\n", unpaidLeaveDates));

    // Calculate the leave deduction
    calculateLeaveDeduction(employeeNumber, unpaidLeaveDates);
}

// Method to calculate and display leave deduction
private void calculateLeaveDeduction(String employeeNumber, List<String> unpaidLeaveDates) {
    String selectedDataFile = "motorPHEmployeeList.csv";
    String line;
    String cvsSplitBy = ",";
    double hourlyRate = 0.0; // Initialize hourly rate

    // Retrieve hourly rate from motorPHEmployeeList.csv based on employeeNumber
    try (BufferedReader br = new BufferedReader(new FileReader(selectedDataFile))) {
        br.readLine(); // Skip header

        while ((line = br.readLine()) != null) {
            String[] data = line.split(cvsSplitBy);

            if (data[0].equals(employeeNumber)) {
                hourlyRate = Double.parseDouble(data[18]); // Assuming hourly rate is in data[18]
                break;
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading motorPHEmployeeList.csv file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    // Count unpaid leave days and calculate leave deduction
    int unpaidLeaveDays = unpaidLeaveDates.size();
    double leaveDeduction = unpaidLeaveDays * 8 * hourlyRate;

    // Update JLabel jLeaveDed1 with the calculated deduction rounded to 2 decimals
    jLeaveDed1.setText(String.format("%.2f", leaveDeduction));
    jLeaveDed2.setText(String.format("%.2f", leaveDeduction));
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
        btnHome = new javax.swing.JButton();
        btnLeave = new javax.swing.JButton();
        btnSummary = new javax.swing.JButton();
        logoutBTN = new javax.swing.JButton();
        jLabel = new javax.swing.JLabel();
        jComboEmployee = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboMonth = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTFYear = new javax.swing.JTextField();
        jTFPayDetails = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPTypeMonthly = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        adjEarning1 = new javax.swing.JTextField();
        otDur1 = new javax.swing.JTextField();
        otRate1 = new javax.swing.JTextField();
        jCheckPayAllowance = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        rice1 = new javax.swing.JLabel();
        phone1 = new javax.swing.JLabel();
        clothing1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        adjBene1 = new javax.swing.JTextField();
        jCheckUnpaidLeave1 = new javax.swing.JCheckBox();
        jLeaveDed1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        unpaidLeaveList1 = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        btnMonthly = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTCalcNote = new javax.swing.JTextArea();
        jLabel42 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jPTypeMonthly1 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        adjEarning2 = new javax.swing.JTextField();
        otDur2 = new javax.swing.JTextField();
        otRate2 = new javax.swing.JTextField();
        jCheckPayAllowance1 = new javax.swing.JCheckBox();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        rice2 = new javax.swing.JLabel();
        phone2 = new javax.swing.JLabel();
        clothing2 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        adjBene2 = new javax.swing.JTextField();
        jCheckUnpaidLeave2 = new javax.swing.JCheckBox();
        jLeaveDed2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        unpaidLeaveList2 = new javax.swing.JTextArea();
        jLabel39 = new javax.swing.JLabel();
        btnSemi = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTCalcNote1 = new javax.swing.JTextArea();
        jComboBoxPayType = new javax.swing.JComboBox<>();
        jLabel43 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        in1 = new javax.swing.JTextField();
        in2 = new javax.swing.JTextField();
        in3 = new javax.swing.JTextField();
        in4 = new javax.swing.JTextField();
        in5 = new javax.swing.JTextField();
        in6 = new javax.swing.JTextField();
        in7 = new javax.swing.JTextField();
        in8 = new javax.swing.JTextField();
        in9 = new javax.swing.JTextField();
        in10 = new javax.swing.JTextField();
        in11 = new javax.swing.JTextField();
        in12 = new javax.swing.JTextField();
        in13 = new javax.swing.JTextField();
        in14 = new javax.swing.JTextField();
        in15 = new javax.swing.JTextField();
        in16 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        out1 = new javax.swing.JTextField();
        out2 = new javax.swing.JTextField();
        out3 = new javax.swing.JTextField();
        out4 = new javax.swing.JTextField();
        out5 = new javax.swing.JTextField();
        out6 = new javax.swing.JTextField();
        out7 = new javax.swing.JTextField();
        out8 = new javax.swing.JTextField();
        out9 = new javax.swing.JTextField();
        out10 = new javax.swing.JTextField();
        out11 = new javax.swing.JTextField();
        out12 = new javax.swing.JTextField();
        out13 = new javax.swing.JTextField();
        out14 = new javax.swing.JTextField();
        out15 = new javax.swing.JTextField();
        out16 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        in17 = new javax.swing.JTextField();
        in18 = new javax.swing.JTextField();
        in19 = new javax.swing.JTextField();
        in20 = new javax.swing.JTextField();
        in21 = new javax.swing.JTextField();
        in22 = new javax.swing.JTextField();
        in23 = new javax.swing.JTextField();
        in24 = new javax.swing.JTextField();
        in25 = new javax.swing.JTextField();
        in26 = new javax.swing.JTextField();
        in27 = new javax.swing.JTextField();
        in28 = new javax.swing.JTextField();
        in29 = new javax.swing.JTextField();
        in30 = new javax.swing.JTextField();
        in31 = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        out17 = new javax.swing.JTextField();
        out18 = new javax.swing.JTextField();
        out19 = new javax.swing.JTextField();
        out20 = new javax.swing.JTextField();
        out21 = new javax.swing.JTextField();
        out22 = new javax.swing.JTextField();
        out23 = new javax.swing.JTextField();
        out24 = new javax.swing.JTextField();
        out25 = new javax.swing.JTextField();
        out26 = new javax.swing.JTextField();
        out27 = new javax.swing.JTextField();
        out28 = new javax.swing.JTextField();
        out29 = new javax.swing.JTextField();
        out30 = new javax.swing.JTextField();
        out31 = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        btnEditTime = new javax.swing.JButton();
        jLabel80 = new javax.swing.JLabel();
        jPTypeMonthly2 = new javax.swing.JTextField();
        jComboBoxPayType1 = new javax.swing.JComboBox<>();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        lunchDed = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        adjEarning3 = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        otRate3 = new javax.swing.JTextField();
        jCheckPayAllowance2 = new javax.swing.JCheckBox();
        jLabel85 = new javax.swing.JLabel();
        adjBene3 = new javax.swing.JTextField();
        jCheckUnpaidLeave3 = new javax.swing.JCheckBox();
        paidLeave = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextAreaPaidLeave = new javax.swing.JTextArea();
        jLabel86 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTCalcNote2 = new javax.swing.JTextArea();
        btnSemi1 = new javax.swing.JButton();
        jLabel87 = new javax.swing.JLabel();
        totRegHrs = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        overTimeHrs = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        rice3 = new javax.swing.JLabel();
        phone3 = new javax.swing.JLabel();
        clothing3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        jTFPayemployeeNo = new javax.swing.JTextField();
        jTFPayLastName = new javax.swing.JTextField();
        jTFPayFirstName = new javax.swing.JTextField();
        jTFPayGross = new javax.swing.JTextField();
        jTFPayGrossAdj = new javax.swing.JTextField();
        jTFPayUnpaidLeave = new javax.swing.JTextField();
        jTFPaySSS = new javax.swing.JTextField();
        jTFPayPhilhealth = new javax.swing.JTextField();
        jTFPayPagibig = new javax.swing.JTextField();
        jTFWitholding = new javax.swing.JTextField();
        jTFBenefits = new javax.swing.JTextField();
        jTFBenefitsAdj = new javax.swing.JTextField();
        jTFNet = new javax.swing.JTextField();
        jTFPayType = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        payNote = new javax.swing.JTextArea();
        jLabel41 = new javax.swing.JLabel();
        jLBG = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Payroll Processing Page");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelWelcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelWelcome.setForeground(new java.awt.Color(255, 255, 255));
        jLabelWelcome.setText("Welcome,");
        getContentPane().add(jLabelWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 105, 60, 20));

        jLabelLoginName.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabelLoginName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLoginName.setText("User Name Here");
        getContentPane().add(jLabelLoginName, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 105, 180, -1));

        btnHome.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnHome.setText("HOME");
        btnHome.setToolTipText("");
        btnHome.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });
        getContentPane().add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, 160, 25));

        btnLeave.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnLeave.setText("LEAVE APPROVAL");
        btnLeave.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLeave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnLeave, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 110, 160, 25));

        btnSummary.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnSummary.setText("PAYROLL SUMMARY");
        btnSummary.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSummaryActionPerformed(evt);
            }
        });
        getContentPane().add(btnSummary, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 110, 160, 25));

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

        jLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel.setForeground(new java.awt.Color(255, 255, 255));
        jLabel.setText("Select Employee");
        getContentPane().add(jLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 163, -1, -1));

        jComboEmployee.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboEmployeeActionPerformed(evt);
            }
        });
        getContentPane().add(jComboEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 183, 180, 25));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Month");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(935, 163, -1, -1));

        jComboMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "...", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jComboMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboMonthActionPerformed(evt);
            }
        });
        getContentPane().add(jComboMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(935, 183, 110, 25));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Year");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 163, -1, -1));

        jTFYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFYearActionPerformed(evt);
            }
        });
        getContentPane().add(jTFYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 183, 60, 24));

        jTFPayDetails.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFPayDetails.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFPayDetails.setEnabled(false);
        jTFPayDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFPayDetailsActionPerformed(evt);
            }
        });
        getContentPane().add(jTFPayDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 183, 410, 24));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Pay Details");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 163, -1, -1));

        jPanel1.setBackground(new java.awt.Color(60, 126, 114));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Payroll Type:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 13, -1, -1));

        jPTypeMonthly.setEditable(false);
        jPTypeMonthly.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPTypeMonthly.setText("Monthly");
        jPTypeMonthly.setBorder(null);
        jPTypeMonthly.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPTypeMonthly.setEnabled(false);
        jPanel1.add(jPTypeMonthly, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, 100, 25));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 175, 0));
        jLabel5.setText("Calculate salary based on Monthly Rate:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Enter amount of adjustment on Taxable Earnings:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 63, -1, -1));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Enter overtime hours if applicable: ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 93, -1, -1));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Enter overtime rate in decimal form (sample 1.25 for 125%:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 123, -1, -1));

        adjEarning1.setText("0");
        adjEarning1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(adjEarning1, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 60, 100, 25));

        otDur1.setText("0");
        otDur1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(otDur1, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 90, 100, 25));

        otRate1.setText("0");
        otRate1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(otRate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 120, 100, 25));

        jCheckPayAllowance.setBackground(new java.awt.Color(0, 0, 0));
        jCheckPayAllowance.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jCheckPayAllowance.setForeground(new java.awt.Color(255, 255, 255));
        jCheckPayAllowance.setText("Pay non-taxable benefits:");
        jPanel1.add(jCheckPayAllowance, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Rice Subsidy:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, 20));

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Phone Allowance:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, 20));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Clothing Allowance:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, 20));

        rice1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rice1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(rice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 50, 20));

        phone1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        phone1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(phone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 50, 20));

        clothing1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clothing1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(clothing1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 50, 20));

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Enter amount of adjustment on Non-Taxable Earnings:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 273, -1, -1));

        adjBene1.setText("0");
        adjBene1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(adjBene1, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 270, 100, 25));

        jCheckUnpaidLeave1.setBackground(new java.awt.Color(0, 0, 0));
        jCheckUnpaidLeave1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jCheckUnpaidLeave1.setForeground(new java.awt.Color(255, 255, 255));
        jCheckUnpaidLeave1.setText("Apply Unpaid Leave Deductions:");
        jPanel1.add(jCheckUnpaidLeave1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 60, -1, -1));

        jLeaveDed1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLeaveDed1.setForeground(new java.awt.Color(255, 255, 255));
        jLeaveDed1.setText("0");
        jPanel1.add(jLeaveDed1, new org.netbeans.lib.awtextra.AbsoluteConstraints(755, 280, 50, -1));

        unpaidLeaveList1.setColumns(20);
        unpaidLeaveList1.setLineWrap(true);
        unpaidLeaveList1.setRows(5);
        unpaidLeaveList1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        unpaidLeaveList1.setEnabled(false);
        jScrollPane1.setViewportView(unpaidLeaveList1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, 170, 180));

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Unpaid Leave Deduction:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, -1, -1));

        btnMonthly.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMonthly.setText("Calculate");
        btnMonthly.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnMonthly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonthlyActionPerformed(evt);
            }
        });
        jPanel1.add(btnMonthly, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 330, 100, 25));

        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Add Notes:");
        jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        jTCalcNote.setColumns(20);
        jTCalcNote.setLineWrap(true);
        jTCalcNote.setRows(5);
        jScrollPane2.setViewportView(jTCalcNote);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, 360, 50));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(153, 39, 0));
        jLabel42.setText("Enter negative (-) amount for deduction adjustment. Positive numbers will be for additional earnings.");
        jPanel1.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jTabbedPane1.addTab("Monthly", jPanel1);

        jPanel2.setBackground(new java.awt.Color(60, 126, 114));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Payroll Type:");
        jPanel2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 13, -1, -1));

        jPTypeMonthly1.setEditable(false);
        jPTypeMonthly1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPTypeMonthly1.setText("Semi-Monthly");
        jPTypeMonthly1.setBorder(null);
        jPTypeMonthly1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPTypeMonthly1.setEnabled(false);
        jPTypeMonthly1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPTypeMonthly1ActionPerformed(evt);
            }
        });
        jPanel2.add(jPTypeMonthly1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 100, 25));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 175, 0));
        jLabel31.setText("Calculate salary based on Semi-Monthly Rate:");
        jPanel2.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Enter amount of adjustment on Taxable Earnings:");
        jPanel2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 63, -1, -1));

        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Enter overtime hours if applicable: ");
        jPanel2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 93, -1, -1));

        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Enter overtime rate in decimal form (sample 1.25 for 125%:");
        jPanel2.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 123, -1, -1));

        adjEarning2.setText("0");
        adjEarning2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(adjEarning2, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 60, 100, 25));

        otDur2.setText("0");
        otDur2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(otDur2, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 90, 100, 25));

        otRate2.setText("0");
        otRate2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(otRate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 120, 100, 25));

        jCheckPayAllowance1.setBackground(new java.awt.Color(0, 0, 0));
        jCheckPayAllowance1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jCheckPayAllowance1.setForeground(new java.awt.Color(255, 255, 255));
        jCheckPayAllowance1.setText("Pay non-taxable benefits:");
        jPanel2.add(jCheckPayAllowance1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Rice Subsidy:");
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, 20));

        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Phone Allowance:");
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, 20));

        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Clothing Allowance:");
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, 20));

        rice2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rice2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(rice2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 50, 20));

        phone2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        phone2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(phone2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 50, 20));

        clothing2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clothing2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(clothing2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 50, 20));

        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Enter amount of adjustment on Non-Taxable Earnings:");
        jPanel2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 273, -1, -1));

        adjBene2.setText("0");
        adjBene2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(adjBene2, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 270, 100, 25));

        jCheckUnpaidLeave2.setBackground(new java.awt.Color(0, 0, 0));
        jCheckUnpaidLeave2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jCheckUnpaidLeave2.setForeground(new java.awt.Color(255, 255, 255));
        jCheckUnpaidLeave2.setText("Apply Unpaid Leave Deductions:");
        jPanel2.add(jCheckUnpaidLeave2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 60, -1, -1));

        jLeaveDed2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLeaveDed2.setForeground(new java.awt.Color(255, 255, 255));
        jLeaveDed2.setText("0");
        jPanel2.add(jLeaveDed2, new org.netbeans.lib.awtextra.AbsoluteConstraints(755, 280, 50, -1));

        unpaidLeaveList2.setColumns(20);
        unpaidLeaveList2.setLineWrap(true);
        unpaidLeaveList2.setRows(5);
        unpaidLeaveList2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        unpaidLeaveList2.setEnabled(false);
        jScrollPane4.setViewportView(unpaidLeaveList2);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, 170, 180));

        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Unpaid Leave Deduction:");
        jPanel2.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, -1, -1));

        btnSemi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSemi.setText("Calculate");
        btnSemi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSemi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSemiActionPerformed(evt);
            }
        });
        jPanel2.add(btnSemi, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 330, 100, 25));

        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Add Notes:");
        jPanel2.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        jTCalcNote1.setColumns(20);
        jTCalcNote1.setLineWrap(true);
        jTCalcNote1.setRows(5);
        jScrollPane5.setViewportView(jTCalcNote1);

        jPanel2.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, 360, 50));

        jComboBoxPayType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "...", "1st Half", "2nd Half" }));
        jPanel2.add(jComboBoxPayType, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, 100, 25));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(153, 39, 0));
        jLabel43.setText("Enter negative (-) amount for deduction adjustment. Positive numbers will be for additional earnings.");
        jPanel2.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jTabbedPane1.addTab("Semi Monthly", jPanel2);

        jPanel3.setBackground(new java.awt.Color(60, 126, 114));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 175, 0));
        jLabel44.setText("Calculate salary based on Hourly Rate:");
        jPanel3.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Day 1 (HH:MM)");
        jPanel3.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 45, -1, -1));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Day 2 (HH:MM)");
        jPanel3.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 65, -1, -1));

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Day 3 (HH:MM)");
        jPanel3.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 85, -1, -1));

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Day 4 (HH:MM)");
        jPanel3.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, -1, -1));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Day 5 (HH:MM)");
        jPanel3.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, -1, -1));

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Day 6 (HH:MM)");
        jPanel3.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 145, -1, -1));

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Day 7 (HH:MM)");
        jPanel3.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 165, -1, -1));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Day 8 (HH:MM)");
        jPanel3.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 185, -1, -1));

        jLabel53.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Day 9 (HH:MM)");
        jPanel3.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 205, -1, -1));

        jLabel54.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Day 10 (HH:MM)");
        jPanel3.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 225, -1, -1));

        jLabel55.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Day 11 (HH:MM)");
        jPanel3.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 245, -1, -1));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Day 12 (HH:MM)");
        jPanel3.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 265, -1, -1));

        jLabel57.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Day 13 (HH:MM)");
        jPanel3.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 285, -1, -1));

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Day 14 (HH:MM)");
        jPanel3.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 305, -1, -1));

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Day 16 (HH:MM)");
        jPanel3.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 345, -1, -1));

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("Day 15 (HH:MM)");
        jPanel3.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 325, -1, -1));

        in1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in1.setText("00:00");
        in1.setBorder(null);
        in1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in1.setEnabled(false);
        jPanel3.add(in1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 45, 35, 18));

        in2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in2.setText("00:00");
        in2.setBorder(null);
        in2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in2.setEnabled(false);
        jPanel3.add(in2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 65, 35, 18));

        in3.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in3.setText("00:00");
        in3.setBorder(null);
        in3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in3.setEnabled(false);
        jPanel3.add(in3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 85, 35, 18));

        in4.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in4.setText("00:00");
        in4.setBorder(null);
        in4.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in4.setEnabled(false);
        jPanel3.add(in4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 105, 35, 18));

        in5.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in5.setText("00:00");
        in5.setBorder(null);
        in5.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in5.setEnabled(false);
        jPanel3.add(in5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 125, 35, 18));

        in6.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in6.setText("00:00");
        in6.setBorder(null);
        in6.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in6.setEnabled(false);
        jPanel3.add(in6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 145, 35, 18));

        in7.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in7.setText("00:00");
        in7.setBorder(null);
        in7.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in7.setEnabled(false);
        jPanel3.add(in7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 165, 35, 18));

        in8.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in8.setText("00:00");
        in8.setBorder(null);
        in8.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in8.setEnabled(false);
        jPanel3.add(in8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 185, 35, 18));

        in9.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in9.setText("00:00");
        in9.setBorder(null);
        in9.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in9.setEnabled(false);
        jPanel3.add(in9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 205, 35, 18));

        in10.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in10.setText("00:00");
        in10.setBorder(null);
        in10.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in10.setEnabled(false);
        jPanel3.add(in10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 225, 35, 18));

        in11.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in11.setText("00:00");
        in11.setBorder(null);
        in11.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in11.setEnabled(false);
        jPanel3.add(in11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 245, 35, 18));

        in12.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in12.setText("00:00");
        in12.setBorder(null);
        in12.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in12.setEnabled(false);
        jPanel3.add(in12, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 265, 35, 18));

        in13.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in13.setText("00:00");
        in13.setBorder(null);
        in13.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in13.setEnabled(false);
        jPanel3.add(in13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 285, 35, 18));

        in14.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in14.setText("00:00");
        in14.setBorder(null);
        in14.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in14.setEnabled(false);
        jPanel3.add(in14, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 305, 35, 18));

        in15.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in15.setText("00:00");
        in15.setBorder(null);
        in15.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in15.setEnabled(false);
        jPanel3.add(in15, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 325, 35, 18));

        in16.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in16.setText("00:00");
        in16.setBorder(null);
        in16.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in16.setEnabled(false);
        jPanel3.add(in16, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 345, 35, 18));

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("LOGIN:");
        jPanel3.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 25, -1, -1));

        out1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out1.setText("00:00");
        out1.setBorder(null);
        out1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out1.setEnabled(false);
        jPanel3.add(out1, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 45, 35, 18));

        out2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out2.setText("00:00");
        out2.setBorder(null);
        out2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out2.setEnabled(false);
        jPanel3.add(out2, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 65, 35, 18));

        out3.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out3.setText("00:00");
        out3.setBorder(null);
        out3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out3.setEnabled(false);
        jPanel3.add(out3, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 85, 35, 18));

        out4.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out4.setText("00:00");
        out4.setBorder(null);
        out4.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out4.setEnabled(false);
        jPanel3.add(out4, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 105, 35, 18));

        out5.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out5.setText("00:00");
        out5.setBorder(null);
        out5.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out5.setEnabled(false);
        jPanel3.add(out5, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 125, 35, 18));

        out6.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out6.setText("00:00");
        out6.setBorder(null);
        out6.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out6.setEnabled(false);
        jPanel3.add(out6, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 145, 35, 18));

        out7.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out7.setText("00:00");
        out7.setBorder(null);
        out7.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out7.setEnabled(false);
        jPanel3.add(out7, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 165, 35, 18));

        out8.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out8.setText("00:00");
        out8.setBorder(null);
        out8.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out8.setEnabled(false);
        jPanel3.add(out8, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 185, 35, 18));

        out9.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out9.setText("00:00");
        out9.setBorder(null);
        out9.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out9.setEnabled(false);
        jPanel3.add(out9, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 205, 35, 18));

        out10.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out10.setText("00:00");
        out10.setBorder(null);
        out10.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out10.setEnabled(false);
        jPanel3.add(out10, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 225, 35, 18));

        out11.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out11.setText("00:00");
        out11.setBorder(null);
        out11.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out11.setEnabled(false);
        jPanel3.add(out11, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 245, 35, 18));

        out12.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out12.setText("00:00");
        out12.setBorder(null);
        out12.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out12.setEnabled(false);
        jPanel3.add(out12, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 265, 35, 18));

        out13.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out13.setText("00:00");
        out13.setBorder(null);
        out13.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out13.setEnabled(false);
        jPanel3.add(out13, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 285, 35, 18));

        out14.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out14.setText("00:00");
        out14.setBorder(null);
        out14.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out14.setEnabled(false);
        jPanel3.add(out14, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 305, 35, 18));

        out15.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out15.setText("00:00");
        out15.setBorder(null);
        out15.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out15.setEnabled(false);
        jPanel3.add(out15, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 325, 35, 18));

        out16.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out16.setText("00:00");
        out16.setBorder(null);
        out16.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out16.setEnabled(false);
        jPanel3.add(out16, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 345, 35, 18));

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("LOGOUT:");
        jPanel3.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 25, -1, -1));

        jButton1.setBackground(new java.awt.Color(153, 39, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("REVIEW");
        jButton1.setBorder(null);
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 45, 50, 18));

        jButton2.setBackground(new java.awt.Color(153, 39, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("REVIEW");
        jButton2.setBorder(null);
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 65, 50, 18));

        jButton3.setBackground(new java.awt.Color(153, 39, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("REVIEW");
        jButton3.setBorder(null);
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 85, 50, 18));

        jButton4.setBackground(new java.awt.Color(153, 39, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("REVIEW");
        jButton4.setBorder(null);
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 105, 50, 18));

        jButton5.setBackground(new java.awt.Color(153, 39, 0));
        jButton5.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("REVIEW");
        jButton5.setBorder(null);
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 125, 50, 18));

        jButton6.setBackground(new java.awt.Color(153, 39, 0));
        jButton6.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("REVIEW");
        jButton6.setBorder(null);
        jPanel3.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 145, 50, 18));

        jButton7.setBackground(new java.awt.Color(153, 39, 0));
        jButton7.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("REVIEW");
        jButton7.setBorder(null);
        jPanel3.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 165, 50, 18));

        jButton8.setBackground(new java.awt.Color(153, 39, 0));
        jButton8.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("REVIEW");
        jButton8.setBorder(null);
        jPanel3.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 185, 50, 18));

        jButton9.setBackground(new java.awt.Color(153, 39, 0));
        jButton9.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("REVIEW");
        jButton9.setBorder(null);
        jPanel3.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 205, 50, 18));

        jButton10.setBackground(new java.awt.Color(153, 39, 0));
        jButton10.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("REVIEW");
        jButton10.setBorder(null);
        jPanel3.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 225, 50, 18));

        jButton11.setBackground(new java.awt.Color(153, 39, 0));
        jButton11.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("REVIEW");
        jButton11.setBorder(null);
        jPanel3.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 245, 50, 18));

        jButton12.setBackground(new java.awt.Color(153, 39, 0));
        jButton12.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("REVIEW");
        jButton12.setBorder(null);
        jPanel3.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 265, 50, 18));

        jButton13.setBackground(new java.awt.Color(153, 39, 0));
        jButton13.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("REVIEW");
        jButton13.setBorder(null);
        jPanel3.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 285, 50, 18));

        jButton14.setBackground(new java.awt.Color(153, 39, 0));
        jButton14.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("REVIEW");
        jButton14.setBorder(null);
        jPanel3.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 305, 50, 18));

        jButton15.setBackground(new java.awt.Color(153, 39, 0));
        jButton15.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("REVIEW");
        jButton15.setBorder(null);
        jPanel3.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 325, 50, 18));

        jButton16.setBackground(new java.awt.Color(153, 39, 0));
        jButton16.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("REVIEW");
        jButton16.setBorder(null);
        jPanel3.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 345, 50, 18));

        jLabel63.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Day 17 (HH:MM)");
        jPanel3.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 45, -1, -1));

        jLabel64.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("Day 18 (HH:MM)");
        jPanel3.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 65, -1, -1));

        jLabel65.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("Day 19 (HH:MM)");
        jPanel3.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 85, -1, -1));

        jLabel66.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("Day 20 (HH:MM)");
        jPanel3.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 105, -1, -1));

        jLabel67.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Day 21 (HH:MM)");
        jPanel3.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 125, -1, -1));

        jLabel68.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Day 22 (HH:MM)");
        jPanel3.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 145, -1, -1));

        jLabel69.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Day 23 (HH:MM)");
        jPanel3.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 165, -1, -1));

        jLabel70.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Day 24 (HH:MM)");
        jPanel3.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 185, -1, -1));

        jLabel71.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("Day 25 (HH:MM)");
        jPanel3.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 205, -1, -1));

        jLabel72.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Day 26 (HH:MM)");
        jPanel3.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 225, -1, -1));

        jLabel73.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Day 27 (HH:MM)");
        jPanel3.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 245, -1, -1));

        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setText("Day 28 (HH:MM)");
        jPanel3.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 265, -1, -1));

        jLabel75.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("Day 30 (HH:MM)");
        jPanel3.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 305, -1, -1));

        jLabel76.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setText("Day 29 (HH:MM)");
        jPanel3.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 285, -1, -1));

        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setText("Day 31 (HH:MM)");
        jPanel3.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 325, -1, -1));

        in17.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in17.setText("00:00");
        in17.setBorder(null);
        in17.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in17.setEnabled(false);
        jPanel3.add(in17, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 45, 35, 18));

        in18.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in18.setText("00:00");
        in18.setBorder(null);
        in18.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in18.setEnabled(false);
        jPanel3.add(in18, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 65, 35, 18));

        in19.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in19.setText("00:00");
        in19.setBorder(null);
        in19.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in19.setEnabled(false);
        jPanel3.add(in19, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 85, 35, 18));

        in20.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in20.setText("00:00");
        in20.setBorder(null);
        in20.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in20.setEnabled(false);
        jPanel3.add(in20, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 105, 35, 18));

        in21.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in21.setText("00:00");
        in21.setBorder(null);
        in21.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in21.setEnabled(false);
        jPanel3.add(in21, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 125, 35, 18));

        in22.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in22.setText("00:00");
        in22.setBorder(null);
        in22.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in22.setEnabled(false);
        jPanel3.add(in22, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 145, 35, 18));

        in23.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in23.setText("00:00");
        in23.setBorder(null);
        in23.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in23.setEnabled(false);
        jPanel3.add(in23, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 165, 35, 18));

        in24.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in24.setText("00:00");
        in24.setBorder(null);
        in24.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in24.setEnabled(false);
        jPanel3.add(in24, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 185, 35, 18));

        in25.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in25.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in25.setText("00:00");
        in25.setBorder(null);
        in25.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in25.setEnabled(false);
        jPanel3.add(in25, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 205, 35, 18));

        in26.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in26.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in26.setText("00:00");
        in26.setBorder(null);
        in26.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in26.setEnabled(false);
        jPanel3.add(in26, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 225, 35, 18));

        in27.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in27.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in27.setText("00:00");
        in27.setBorder(null);
        in27.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in27.setEnabled(false);
        jPanel3.add(in27, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 245, 35, 18));

        in28.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in28.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in28.setText("00:00");
        in28.setBorder(null);
        in28.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in28.setEnabled(false);
        jPanel3.add(in28, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 265, 35, 18));

        in29.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in29.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in29.setText("00:00");
        in29.setBorder(null);
        in29.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in29.setEnabled(false);
        jPanel3.add(in29, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 285, 35, 18));

        in30.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in30.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in30.setText("00:00");
        in30.setBorder(null);
        in30.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in30.setEnabled(false);
        jPanel3.add(in30, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 305, 35, 18));

        in31.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        in31.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        in31.setText("00:00");
        in31.setBorder(null);
        in31.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        in31.setEnabled(false);
        jPanel3.add(in31, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 325, 35, 18));

        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("LOGIN:");
        jPanel3.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 25, -1, -1));

        out17.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out17.setText("00:00");
        out17.setBorder(null);
        out17.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out17.setEnabled(false);
        jPanel3.add(out17, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 45, 35, 18));

        out18.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out18.setText("00:00");
        out18.setBorder(null);
        out18.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out18.setEnabled(false);
        jPanel3.add(out18, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 65, 35, 18));

        out19.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out19.setText("00:00");
        out19.setBorder(null);
        out19.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out19.setEnabled(false);
        jPanel3.add(out19, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 85, 35, 18));

        out20.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out20.setText("00:00");
        out20.setBorder(null);
        out20.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out20.setEnabled(false);
        jPanel3.add(out20, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 105, 35, 18));

        out21.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out21.setText("00:00");
        out21.setBorder(null);
        out21.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out21.setEnabled(false);
        jPanel3.add(out21, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 125, 35, 18));

        out22.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out22.setText("00:00");
        out22.setBorder(null);
        out22.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out22.setEnabled(false);
        jPanel3.add(out22, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 145, 35, 18));

        out23.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out23.setText("00:00");
        out23.setBorder(null);
        out23.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out23.setEnabled(false);
        jPanel3.add(out23, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 165, 35, 18));

        out24.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out24.setText("00:00");
        out24.setBorder(null);
        out24.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out24.setEnabled(false);
        jPanel3.add(out24, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 185, 35, 18));

        out25.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out25.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out25.setText("00:00");
        out25.setBorder(null);
        out25.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out25.setEnabled(false);
        jPanel3.add(out25, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 205, 35, 18));

        out26.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out26.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out26.setText("00:00");
        out26.setBorder(null);
        out26.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out26.setEnabled(false);
        jPanel3.add(out26, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 225, 35, 18));

        out27.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out27.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out27.setText("00:00");
        out27.setBorder(null);
        out27.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out27.setEnabled(false);
        jPanel3.add(out27, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 245, 35, 18));

        out28.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out28.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out28.setText("00:00");
        out28.setBorder(null);
        out28.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out28.setEnabled(false);
        jPanel3.add(out28, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 265, 35, 18));

        out29.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out29.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out29.setText("00:00");
        out29.setBorder(null);
        out29.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out29.setEnabled(false);
        jPanel3.add(out29, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 285, 35, 18));

        out30.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out30.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out30.setText("00:00");
        out30.setBorder(null);
        out30.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out30.setEnabled(false);
        jPanel3.add(out30, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 305, 35, 18));

        out31.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        out31.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        out31.setText("00:00");
        out31.setBorder(null);
        out31.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        out31.setEnabled(false);
        jPanel3.add(out31, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 325, 35, 18));

        jLabel79.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setText("LOGOUT:");
        jPanel3.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 25, -1, -1));

        jButton17.setBackground(new java.awt.Color(153, 39, 0));
        jButton17.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setText("REVIEW");
        jButton17.setBorder(null);
        jPanel3.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 45, 50, 18));

        jButton18.setBackground(new java.awt.Color(153, 39, 0));
        jButton18.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("REVIEW");
        jButton18.setBorder(null);
        jPanel3.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 65, 50, 18));

        jButton19.setBackground(new java.awt.Color(153, 39, 0));
        jButton19.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("REVIEW");
        jButton19.setBorder(null);
        jPanel3.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 85, 50, 18));

        jButton20.setBackground(new java.awt.Color(153, 39, 0));
        jButton20.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton20.setForeground(new java.awt.Color(255, 255, 255));
        jButton20.setText("REVIEW");
        jButton20.setBorder(null);
        jPanel3.add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 105, 50, 18));

        jButton21.setBackground(new java.awt.Color(153, 39, 0));
        jButton21.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton21.setForeground(new java.awt.Color(255, 255, 255));
        jButton21.setText("REVIEW");
        jButton21.setBorder(null);
        jPanel3.add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 125, 50, 18));

        jButton22.setBackground(new java.awt.Color(153, 39, 0));
        jButton22.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton22.setForeground(new java.awt.Color(255, 255, 255));
        jButton22.setText("REVIEW");
        jButton22.setBorder(null);
        jPanel3.add(jButton22, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 145, 50, 18));

        jButton23.setBackground(new java.awt.Color(153, 39, 0));
        jButton23.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton23.setForeground(new java.awt.Color(255, 255, 255));
        jButton23.setText("REVIEW");
        jButton23.setBorder(null);
        jPanel3.add(jButton23, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 165, 50, 18));

        jButton24.setBackground(new java.awt.Color(153, 39, 0));
        jButton24.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton24.setForeground(new java.awt.Color(255, 255, 255));
        jButton24.setText("REVIEW");
        jButton24.setBorder(null);
        jPanel3.add(jButton24, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 185, 50, 18));

        jButton25.setBackground(new java.awt.Color(153, 39, 0));
        jButton25.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton25.setForeground(new java.awt.Color(255, 255, 255));
        jButton25.setText("REVIEW");
        jButton25.setBorder(null);
        jPanel3.add(jButton25, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 205, 50, 18));

        jButton26.setBackground(new java.awt.Color(153, 39, 0));
        jButton26.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton26.setForeground(new java.awt.Color(255, 255, 255));
        jButton26.setText("REVIEW");
        jButton26.setBorder(null);
        jPanel3.add(jButton26, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 225, 50, 18));

        jButton27.setBackground(new java.awt.Color(153, 39, 0));
        jButton27.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton27.setForeground(new java.awt.Color(255, 255, 255));
        jButton27.setText("REVIEW");
        jButton27.setBorder(null);
        jPanel3.add(jButton27, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 245, 50, 18));

        jButton28.setBackground(new java.awt.Color(153, 39, 0));
        jButton28.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton28.setForeground(new java.awt.Color(255, 255, 255));
        jButton28.setText("REVIEW");
        jButton28.setBorder(null);
        jPanel3.add(jButton28, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 265, 50, 18));

        jButton29.setBackground(new java.awt.Color(153, 39, 0));
        jButton29.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton29.setForeground(new java.awt.Color(255, 255, 255));
        jButton29.setText("REVIEW");
        jButton29.setBorder(null);
        jPanel3.add(jButton29, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 285, 50, 18));

        jButton30.setBackground(new java.awt.Color(153, 39, 0));
        jButton30.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton30.setForeground(new java.awt.Color(255, 255, 255));
        jButton30.setText("REVIEW");
        jButton30.setBorder(null);
        jPanel3.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 305, 50, 18));

        jButton31.setBackground(new java.awt.Color(153, 39, 0));
        jButton31.setFont(new java.awt.Font("Segoe UI", 3, 8)); // NOI18N
        jButton31.setForeground(new java.awt.Color(255, 255, 255));
        jButton31.setText("REVIEW");
        jButton31.setBorder(null);
        jPanel3.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 325, 50, 18));

        btnEditTime.setBackground(new java.awt.Color(210, 89, 51));
        btnEditTime.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnEditTime.setForeground(new java.awt.Color(255, 255, 255));
        btnEditTime.setText("Edit Time Card");
        btnEditTime.setBorder(null);
        jPanel3.add(btnEditTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 355, 90, 20));

        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText("Payroll Type:");
        jPanel3.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 5, -1, -1));

        jPTypeMonthly2.setEditable(false);
        jPTypeMonthly2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jPTypeMonthly2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPTypeMonthly2.setText("Hourly");
        jPTypeMonthly2.setBorder(null);
        jPTypeMonthly2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPTypeMonthly2.setEnabled(false);
        jPTypeMonthly2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPTypeMonthly2ActionPerformed(evt);
            }
        });
        jPanel3.add(jPTypeMonthly2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 5, 100, 20));

        jComboBoxPayType1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "...", "1st Half", "2nd Half" }));
        jPanel3.add(jComboBoxPayType1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 5, 100, 20));

        jLabel81.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(153, 39, 0));
        jLabel81.setText("Enter negative (-) amount for deduction adjustment. Positive numbers will be for additional earnings.");
        jLabel81.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel3.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 25, 310, 40));

        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText("Enter Number of Days with 1 hour Lunch Deduction:");
        jLabel82.setAlignmentX(0.5F);
        jLabel82.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 65, 85, 60));

        lunchDed.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lunchDed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lunchDed.setText("0");
        lunchDed.setBorder(null);
        lunchDed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lunchDedActionPerformed(evt);
            }
        });
        jPanel3.add(lunchDed, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 125, 85, 18));

        jLabel83.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText("Enter amount of adjustment on Taxable Earnings:");
        jLabel83.setAlignmentX(0.5F);
        jLabel83.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 65, 85, 60));

        adjEarning3.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        adjEarning3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        adjEarning3.setText("0.00");
        adjEarning3.setBorder(null);
        jPanel3.add(adjEarning3, new org.netbeans.lib.awtextra.AbsoluteConstraints(605, 125, 85, 18));

        jLabel84.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("Enter overtime rate in decimal form (sample 1.25 for 125%:");
        jLabel84.setAlignmentX(0.5F);
        jLabel84.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 65, 90, 60));

        otRate3.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        otRate3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        otRate3.setText("0.00");
        otRate3.setBorder(null);
        jPanel3.add(otRate3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 125, 85, 18));

        jCheckPayAllowance2.setBackground(new java.awt.Color(0, 0, 0));
        jCheckPayAllowance2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckPayAllowance2.setForeground(new java.awt.Color(255, 255, 255));
        jCheckPayAllowance2.setText("Pay non-taxable benefits:");
        jPanel3.add(jCheckPayAllowance2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 145, 190, 20));

        jLabel85.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel85.setText("Enter amount of adjustment on Non-Taxable Earnings:");
        jLabel85.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel85.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 210, 170, 30));

        adjBene3.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        adjBene3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        adjBene3.setText("0.00");
        adjBene3.setBorder(null);
        jPanel3.add(adjBene3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 220, 85, 18));

        jCheckUnpaidLeave3.setBackground(new java.awt.Color(0, 0, 0));
        jCheckUnpaidLeave3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckUnpaidLeave3.setForeground(new java.awt.Color(255, 255, 255));
        jCheckUnpaidLeave3.setText("Apply Paid Leave Earnings:");
        jPanel3.add(jCheckUnpaidLeave3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 245, 190, 20));

        paidLeave.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        paidLeave.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        paidLeave.setText("0.00");
        paidLeave.setBorder(null);
        jPanel3.add(paidLeave, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 245, 85, 18));

        jTextAreaPaidLeave.setColumns(20);
        jTextAreaPaidLeave.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jTextAreaPaidLeave.setRows(5);
        jScrollPane6.setViewportView(jTextAreaPaidLeave);

        jPanel3.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(564, 265, 232, 60));

        jLabel86.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(255, 255, 255));
        jLabel86.setText("Add Notes:");
        jPanel3.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 325, -1, -1));

        jTCalcNote2.setColumns(20);
        jTCalcNote2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jTCalcNote2.setLineWrap(true);
        jTCalcNote2.setRows(5);
        jScrollPane7.setViewportView(jTCalcNote2);

        jPanel3.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(564, 325, 232, 40));

        btnSemi1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSemi1.setText("Calculate");
        btnSemi1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSemi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSemi1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnSemi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 365, 100, 20));

        jLabel87.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(255, 245, 206));
        jLabel87.setText("REGULAR HRS:");
        jPanel3.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 345, -1, -1));

        totRegHrs.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        totRegHrs.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totRegHrs.setText("0");
        totRegHrs.setBorder(null);
        totRegHrs.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        totRegHrs.setEnabled(false);
        jPanel3.add(totRegHrs, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 345, 35, 18));

        jLabel88.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(255, 245, 206));
        jLabel88.setText("OVERTIME HRS:");
        jPanel3.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 365, -1, -1));

        overTimeHrs.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        overTimeHrs.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        overTimeHrs.setText("0");
        overTimeHrs.setBorder(null);
        overTimeHrs.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        overTimeHrs.setEnabled(false);
        jPanel3.add(overTimeHrs, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 365, 35, 18));

        jLabel89.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Rice Subsidy:");
        jPanel3.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(605, 160, -1, 20));

        jLabel90.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("Phone Allowance:");
        jPanel3.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(605, 175, -1, 20));

        jLabel91.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("Clothing Allowance:");
        jPanel3.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(605, 190, -1, 20));

        rice3.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        rice3.setForeground(new java.awt.Color(255, 255, 255));
        rice3.setText("0.00");
        jPanel3.add(rice3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 160, 50, 20));

        phone3.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        phone3.setForeground(new java.awt.Color(255, 255, 255));
        phone3.setText("0.00");
        jPanel3.add(phone3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 175, 50, 20));

        clothing3.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        clothing3.setForeground(new java.awt.Color(255, 255, 255));
        clothing3.setText("0.00");
        jPanel3.add(clothing3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 190, 50, 20));

        jTabbedPane1.addTab("Hourly", jPanel3);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 218, 820, 418));

        jLabel14.setText("Employee Number:");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 180, -1, -1));

        jLabel15.setText("Last Name:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 200, -1, -1));

        jLabel16.setText("Fist Name:");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 220, -1, -1));

        jLabel17.setText("Gross Pay:");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 250, -1, -1));

        jLabel18.setText("Earnings Adjustment:");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 270, -1, -1));

        jLabel19.setText("Unpaid Leave:");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 290, -1, -1));

        jLabel20.setText("SSS Deduction:");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 320, -1, -1));

        jLabel21.setText("PhilHealth Deduction:");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 340, -1, -1));

        jLabel22.setText("Pagibig Deduction:");
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 360, -1, -1));

        jLabel23.setText("Witholding Tax:");
        getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 380, -1, -1));

        jLabel24.setText("Benefits Pay:");
        getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 410, -1, -1));

        jLabel25.setText("Benefits Pay Adjustment:");
        getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 430, -1, -1));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel26.setText("NetPay:");
        getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 460, -1, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel27.setText("Payroll Type: ");
        getContentPane().add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 490, -1, -1));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel28.setText("Note:");
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 510, -1, -1));

        btnSubmit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        getContentPane().add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 610, 100, 25));

        jTFPayemployeeNo.setEditable(false);
        jTFPayemployeeNo.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFPayemployeeNo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFPayemployeeNo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFPayemployeeNo.setEnabled(false);
        getContentPane().add(jTFPayemployeeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 180, 135, 18));

        jTFPayLastName.setEditable(false);
        jTFPayLastName.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFPayLastName.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFPayLastName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFPayLastName.setEnabled(false);
        getContentPane().add(jTFPayLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 200, 135, 18));

        jTFPayFirstName.setEditable(false);
        jTFPayFirstName.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFPayFirstName.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFPayFirstName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFPayFirstName.setEnabled(false);
        getContentPane().add(jTFPayFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 220, 135, 18));

        jTFPayGross.setEditable(false);
        jTFPayGross.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFPayGross.setText("0.00");
        jTFPayGross.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFPayGross.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFPayGross.setEnabled(false);
        getContentPane().add(jTFPayGross, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 250, 105, 18));

        jTFPayGrossAdj.setEditable(false);
        jTFPayGrossAdj.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFPayGrossAdj.setText("0.00");
        jTFPayGrossAdj.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFPayGrossAdj.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFPayGrossAdj.setEnabled(false);
        getContentPane().add(jTFPayGrossAdj, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 270, 105, 18));

        jTFPayUnpaidLeave.setEditable(false);
        jTFPayUnpaidLeave.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFPayUnpaidLeave.setText("0.00");
        jTFPayUnpaidLeave.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFPayUnpaidLeave.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFPayUnpaidLeave.setEnabled(false);
        getContentPane().add(jTFPayUnpaidLeave, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 290, 105, 18));

        jTFPaySSS.setEditable(false);
        jTFPaySSS.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFPaySSS.setText("0.00");
        jTFPaySSS.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFPaySSS.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFPaySSS.setEnabled(false);
        getContentPane().add(jTFPaySSS, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 320, 105, 18));

        jTFPayPhilhealth.setEditable(false);
        jTFPayPhilhealth.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFPayPhilhealth.setText("0.00");
        jTFPayPhilhealth.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFPayPhilhealth.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFPayPhilhealth.setEnabled(false);
        getContentPane().add(jTFPayPhilhealth, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 340, 105, 18));

        jTFPayPagibig.setEditable(false);
        jTFPayPagibig.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFPayPagibig.setText("0.00");
        jTFPayPagibig.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFPayPagibig.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFPayPagibig.setEnabled(false);
        getContentPane().add(jTFPayPagibig, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 360, 105, 18));

        jTFWitholding.setEditable(false);
        jTFWitholding.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFWitholding.setText("0.00");
        jTFWitholding.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFWitholding.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFWitholding.setEnabled(false);
        getContentPane().add(jTFWitholding, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 380, 105, 18));

        jTFBenefits.setEditable(false);
        jTFBenefits.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFBenefits.setText("0.00");
        jTFBenefits.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFBenefits.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFBenefits.setEnabled(false);
        getContentPane().add(jTFBenefits, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 410, 105, 18));

        jTFBenefitsAdj.setEditable(false);
        jTFBenefitsAdj.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFBenefitsAdj.setText("0.00");
        jTFBenefitsAdj.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFBenefitsAdj.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFBenefitsAdj.setEnabled(false);
        getContentPane().add(jTFBenefitsAdj, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 430, 105, 18));

        jTFNet.setEditable(false);
        jTFNet.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFNet.setText("0.00");
        jTFNet.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFNet.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFNet.setEnabled(false);
        getContentPane().add(jTFNet, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 457, 105, 25));

        jTFPayType.setEditable(false);
        jTFPayType.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTFPayType.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTFPayType.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFPayType.setEnabled(false);
        getContentPane().add(jTFPayType, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 490, 135, 18));

        payNote.setEditable(false);
        payNote.setColumns(20);
        payNote.setLineWrap(true);
        payNote.setRows(5);
        payNote.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        payNote.setEnabled(false);
        jScrollPane3.setViewportView(payNote);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 516, 210, 90));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("* Note: Press ENTER after Month selection and Year entry to load leave data.");
        getContentPane().add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 220, -1, -1));

        jLBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/motorph_gui/AdminPayroll.jpg"))); // NOI18N
        getContentPane().add(jLBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        this.dispose();

        // Launch the new AdminLeaveApproval frame
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminHome(username).setVisible(true);
            }
        });
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSummaryActionPerformed
        this.dispose();

        // Launch the new AdminLeaveApproval frame
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPaySummary().setVisible(true);
            }
        });
    }//GEN-LAST:event_btnSummaryActionPerformed

    private void btnLeaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaveActionPerformed
        System.out.println("Redirecting to AdminLeaveApproval with username: " + username);
        new AdminLeaveApproval(username).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLeaveActionPerformed

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

    private void jComboEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboEmployeeActionPerformed
        // Get selected index from jComboEmployee
        int selectedIndex = jComboEmployee.getSelectedIndex();

        if (selectedIndex > 0) { // Ensure an actual employee is selected (not the blank item)
            String[] selectedData = csvData.get(selectedIndex - 1); // Adjust index due to blank item

            // Clear previous text in jTFPayDetails
            jTFPayDetails.setText("");

            try {
                // Ensure enough columns exist in the data
                if (selectedData.length >= 19) {
                    String basicSalary = "Basic Salary: " + selectedData[13];
                    String grossSemiMonthlyRate = "Gross Semi-monthly Rate: " + selectedData[17];
                    String hourlyRate = "Hourly Rate: " + selectedData[18];

                    // Set text in jTFPayDetails
                    jTFPayDetails.setText(basicSalary + "\n" + grossSemiMonthlyRate + "\n" + hourlyRate);

                    // Set values for JLabels
                    rice1.setText(selectedData[14]);
                    phone1.setText(selectedData[15]);
                    clothing1.setText(selectedData[16]);
                } else {
                    jTFPayDetails.setText("Data not available for selected employee.");
                    rice1.setText("Rice: Data not available");
                    phone1.setText("Phone: Data not available");
                    clothing1.setText("Clothing: Data not available");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                jTFPayDetails.setText("Data not available for selected employee.");
                rice1.setText("Rice: Data not available");
                phone1.setText("Phone: Data not available");
                clothing1.setText("Clothing: Data not available");
            }
        } else {
            // Clear the fields if no valid selection is made
            jTFPayDetails.setText("");
            rice1.setText("Rice:");
            phone1.setText("Phone:");
            clothing1.setText("Clothing:");
        }
        filterTimeOffData();
    }//GEN-LAST:event_jComboEmployeeActionPerformed

    private void jTFPayDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFPayDetailsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFPayDetailsActionPerformed

    private void jComboMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboMonthActionPerformed
        filterTimeOffData();
    }//GEN-LAST:event_jComboMonthActionPerformed

    private void jTFYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFYearActionPerformed
        filterTimeOffData();
    }//GEN-LAST:event_jTFYearActionPerformed

    private void btnMonthlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonthlyActionPerformed
      // Retrieve the selected employee info from the JComboBox
    String selectedEmployeeInfo = (String) jComboEmployee.getSelectedItem();

    if (selectedEmployeeInfo == null || selectedEmployeeInfo.isEmpty()) {
        return; // If no employee is selected, do not proceed
    }

    // Extract employee number from the selected employee info
    String employeeNumber = selectedEmployeeInfo.split(" - ")[0];

    // File path for motorPHEmployeeList.csv
    String employeeDataFile = "motorPHEmployeeList.csv";
    String line;
    String cvsSplitBy = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(employeeDataFile))) {
        br.readLine(); // Skip header

        while ((line = br.readLine()) != null) {
            String[] data = line.split(cvsSplitBy);

            if (data.length < 19) {
                continue; // Skip the line if it does not have enough data
            }

            if (data[0].equals(employeeNumber)) {
                // Extract required details
                String firstName = data[2];
                String lastName = data[1];
                double hourlySalary = Double.parseDouble(data[18]); // Assuming hourly salary is in column 19

                // Retrieve input values
                try {
                    double otRate = Double.parseDouble(otRate1.getText());
                    double otHours = Double.parseDouble(otDur1.getText());
                    double adjEarnings = Double.parseDouble(adjEarning1.getText());
                    double benefitsAdjustment = Double.parseDouble(adjBene1.getText());
                    double basicSalary = Double.parseDouble(data[13]);
                    double riceSubsidyP = Double.parseDouble(data[14]);
                    double phoneAllowanceP = Double.parseDouble(data[15]);
                    double clothingAllowanceP = Double.parseDouble(data[16]);
                    double hourlyRate = Double.parseDouble(data[18]);
                    double unpaidLeave = Double.parseDouble(jLeaveDed1.getText());

                    double overtimeEarnings = PayrollCalculation.calculateOvertimeEarnings(otRate, hourlyRate, otHours);
                    double gross = overtimeEarnings + basicSalary;
                    // Calculate without leave deduction
                    double taxableEarnings = PayrollCalculation.calculateTaxableEarnings(basicSalary, adjEarnings);
                    double netGross = PayrollCalculation.calculateNetGross(overtimeEarnings, taxableEarnings);
                    double sssDeduction = PayrollCalculation.calculateSSS(netGross);
                    double philHealthDeduction = PayrollCalculation.calculatePhilHealth(netGross);
                    double pagIbigDeduction = PayrollCalculation.calculatePagibig(netGross);
                    double taxableIncome = netGross - (sssDeduction + philHealthDeduction + pagIbigDeduction);
                    double witholdingTax = PayrollCalculation.calculateWitholdingTax(taxableIncome);
                    double nettaxedEarnings = taxableIncome - witholdingTax;

                    // Calculate with leave deduction 
                    double taxableEarningsL = PayrollCalculation.calculateTaxableEarnings(basicSalary, adjEarnings) - unpaidLeave;
                    double netGrossL = PayrollCalculation.calculateNetGross(overtimeEarnings, taxableEarningsL);
                    double sssDeductionL = PayrollCalculation.calculateSSS(netGrossL);
                    double philHealthDeductionL = PayrollCalculation.calculatePhilHealth(netGrossL);
                    double pagIbigDeductionL = PayrollCalculation.calculatePagibig(netGrossL);
                    double taxableIncomeL = netGrossL - (sssDeductionL + philHealthDeductionL + pagIbigDeductionL);
                    double witholdingTaxL = PayrollCalculation.calculateWitholdingTax(taxableIncomeL);
                    double nettaxedEarningsL = taxableIncomeL - witholdingTaxL;

                    // Calculate and update UI based on unpaid leave checkbox
                    if (jCheckUnpaidLeave1.isSelected()) {
                        // Update UI components for leave deduction
                        jTFPayemployeeNo.setText(data[0]);
                        jTFPayLastName.setText(lastName);
                        jTFPayFirstName.setText(firstName);
                        jTFPayGross.setText(String.format("%.2f", gross));
                        jTFPayGrossAdj.setText(String.format("%.2f", adjEarnings));
                        jTFPayUnpaidLeave.setText(String.format("%.2f", unpaidLeave));
                        jTFPaySSS.setText(String.format("%.2f", sssDeductionL));
                        jTFPayPhilhealth.setText(String.format("%.2f", philHealthDeductionL));
                        jTFPayPagibig.setText(String.format("%.2f", pagIbigDeductionL));
                        jTFWitholding.setText(String.format("%.2f", witholdingTaxL));
                        jTFPayType.setText(jPTypeMonthly.getText());
                        payNote.setText(jTCalcNote.getText());
                    } else {
                        // Update UI components without leave deduction
                        jTFPayemployeeNo.setText(data[0]);
                        jTFPayLastName.setText(lastName);
                        jTFPayFirstName.setText(firstName);
                        jTFPayGross.setText(String.format("%.2f", gross));
                        jTFPayUnpaidLeave.setText("0.00");
                        jTFPayGrossAdj.setText(String.format("%.2f", adjEarnings));
                        jTFPaySSS.setText(String.format("%.2f", sssDeduction));
                        jTFPayPhilhealth.setText(String.format("%.2f", philHealthDeduction));
                        jTFPayPagibig.setText(String.format("%.2f", pagIbigDeduction));
                        jTFWitholding.setText(String.format("%.2f", witholdingTax));
                        jTFPayType.setText(jPTypeMonthly.getText());
                        payNote.setText(jTCalcNote.getText());
                    }

                    // Check if allowances are selected
                    if (jCheckPayAllowance.isSelected()) {
                        double totalBenefits = riceSubsidyP + phoneAllowanceP + clothingAllowanceP;
                        double netWben = jCheckUnpaidLeave1.isSelected() ? nettaxedEarningsL + totalBenefits + benefitsAdjustment : nettaxedEarnings + totalBenefits + benefitsAdjustment;
                        jTFBenefits.setText(String.format("%.2f", totalBenefits));
                        jTFBenefitsAdj.setText(String.format("%.2f", benefitsAdjustment));
                        jTFNet.setText(String.format("%.2f", netWben));
                    } else {
                        jTFNet.setText(String.format("%.2f", jCheckUnpaidLeave1.isSelected() ? nettaxedEarningsL : nettaxedEarnings));
                        jTFBenefits.setText("0.00");
                        jTFBenefitsAdj.setText("0.00");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }

                break; // Exit loop once the required employee details are found
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading motorPHEmployeeList.csv file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    }//GEN-LAST:event_btnMonthlyActionPerformed

    private void btnSemiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSemiActionPerformed
        // Retrieve the selected employee info from the JComboBox
    String selectedEmployeeInfo = (String) jComboEmployee.getSelectedItem();

    if (selectedEmployeeInfo == null || selectedEmployeeInfo.isEmpty()) {
        return; // If no employee is selected, do not proceed
    }

    // Extract employee number from the selected employee info
    String employeeNumber = selectedEmployeeInfo.split(" - ")[0];

    // File path for motorPHEmployeeList.csv
    String employeeDataFile = "motorPHEmployeeList.csv";
    String line;
    String cvsSplitBy = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(employeeDataFile))) {
        br.readLine(); // Skip header

        while ((line = br.readLine()) != null) {
            String[] data = line.split(cvsSplitBy);

            if (data.length < 19) {
                continue; // Skip the line if it does not have enough data
            }

            if (data[0].equals(employeeNumber)) {
                // Extract required details
                String firstName = data[2];
                String lastName = data[1];
                double hourlySalary = Double.parseDouble(data[18]); // Assuming hourly salary is in column 19

                // Retrieve input values
                try {
                    double otRate = Double.parseDouble(otRate2.getText());
                    double otHours = Double.parseDouble(otDur2.getText());
                    double adjEarnings = Double.parseDouble(adjEarning2.getText());
                    double benefitsAdjustment = Double.parseDouble(adjBene2.getText());
                    double semiSalary = Double.parseDouble(data[17]);
                    double riceSubsidyP = Double.parseDouble(data[14]);
                    double phoneAllowanceP = Double.parseDouble(data[15]);
                    double clothingAllowanceP = Double.parseDouble(data[16]);
                    double hourlyRate = Double.parseDouble(data[18]);
                    double unpaidLeave = Double.parseDouble(jLeaveDed2.getText());

                    double overtimeEarnings = PayrollCalculation.calculateOvertimeEarnings(otRate, hourlyRate, otHours);
                    double gross = overtimeEarnings + semiSalary;
                    // Calculate without leave deduction
                    double taxableEarnings = PayrollCalculation.calculateTaxableEarnings(semiSalary, adjEarnings);
                    double netGross = PayrollCalculation.calculateNetGross(overtimeEarnings, taxableEarnings);
                    double sssDeduction = PayrollCalculation.calculateSSS(netGross);
                    double philHealthDeduction = PayrollCalculation.calculatePhilHealth(netGross);
                    double pagIbigDeduction = PayrollCalculation.calculatePagibig(netGross);
                    double taxableIncome = netGross - (sssDeduction + philHealthDeduction + pagIbigDeduction);
                    double witholdingTax = PayrollCalculation.calculateWitholdingTax(taxableIncome);
                    double nettaxedEarnings = taxableIncome - witholdingTax;

                    // Calculate with leave deduction 
                    double taxableEarningsL = PayrollCalculation.calculateTaxableEarnings(semiSalary, adjEarnings) - unpaidLeave;
                    double netGrossL = PayrollCalculation.calculateNetGross(overtimeEarnings, taxableEarningsL);
                    double sssDeductionL = PayrollCalculation.calculateSSS(netGrossL);
                    double philHealthDeductionL = PayrollCalculation.calculatePhilHealth(netGrossL);
                    double pagIbigDeductionL = PayrollCalculation.calculatePagibig(netGrossL);
                    double taxableIncomeL = netGrossL - (sssDeductionL + philHealthDeductionL + pagIbigDeductionL);
                    double witholdingTaxL = PayrollCalculation.calculateWitholdingTax(taxableIncomeL);
                    double nettaxedEarningsL = taxableIncomeL - witholdingTaxL;

                    // Calculate and update UI based on unpaid leave checkbox
                    if (jCheckUnpaidLeave2.isSelected()) {
                        // Update UI components for leave deduction
                        jTFPayemployeeNo.setText(data[0]);
                        jTFPayLastName.setText(lastName);
                        jTFPayFirstName.setText(firstName);
                        jTFPayGross.setText(String.format("%.2f", gross));
                        jTFPayGrossAdj.setText(String.format("%.2f", adjEarnings));
                        jTFPayUnpaidLeave.setText(String.format("%.2f", unpaidLeave));
                        jTFPaySSS.setText(String.format("%.2f", sssDeductionL));
                        jTFPayPhilhealth.setText(String.format("%.2f", philHealthDeductionL));
                        jTFPayPagibig.setText(String.format("%.2f", pagIbigDeductionL));
                        jTFWitholding.setText(String.format("%.2f", witholdingTaxL));
                        jTFPayType.setText(jPTypeMonthly1.getText() + " | " + (String) jComboBoxPayType.getSelectedItem());
                        payNote.setText(jTCalcNote1.getText());
                    } else {
                        // Update UI components without leave deduction
                        jTFPayemployeeNo.setText(data[0]);
                        jTFPayLastName.setText(lastName);
                        jTFPayFirstName.setText(firstName);
                        jTFPayGross.setText(String.format("%.2f", gross));
                        jTFPayUnpaidLeave.setText("0.00");
                        jTFPayGrossAdj.setText(String.format("%.2f", adjEarnings));
                        jTFPaySSS.setText(String.format("%.2f", sssDeduction));
                        jTFPayPhilhealth.setText(String.format("%.2f", philHealthDeduction));
                        jTFPayPagibig.setText(String.format("%.2f", pagIbigDeduction));
                        jTFWitholding.setText(String.format("%.2f", witholdingTax));
                        jTFPayType.setText(jPTypeMonthly1.getText() + " | " + (String) jComboBoxPayType.getSelectedItem());
                        payNote.setText(jTCalcNote1.getText());
                    }

                    // Check if allowances are selected
                    if (jCheckPayAllowance1.isSelected()) {
                        double totalBenefits = riceSubsidyP + phoneAllowanceP + clothingAllowanceP;
                        double netWben = jCheckUnpaidLeave1.isSelected() ? nettaxedEarningsL + totalBenefits + benefitsAdjustment : nettaxedEarnings + totalBenefits + benefitsAdjustment;
                        jTFBenefits.setText(String.format("%.2f", totalBenefits));
                        jTFBenefitsAdj.setText(String.format("%.2f", benefitsAdjustment));
                        jTFNet.setText(String.format("%.2f", netWben));
                    } else {
                        jTFNet.setText(String.format("%.2f", jCheckUnpaidLeave1.isSelected() ? nettaxedEarningsL : nettaxedEarnings));
                        jTFBenefits.setText("0.00");
                        jTFBenefitsAdj.setText("0.00");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }

                break; // Exit loop once the required employee details are found
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading motorPHEmployeeList.csv file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnSemiActionPerformed

    private void jPTypeMonthly1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPTypeMonthly1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPTypeMonthly1ActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        try {
        // Ensure the year field is not blank
        if (jTFPayDetails.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Make an employee selection", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        
        } else if (jTFYear.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Year cannot be blank.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (jComboMonth.getSelectedItem().toString().trim().equals("...")) {
            JOptionPane.showMessageDialog(null, "Make a month selection.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (jTFPayType.getText().trim().equals("Semi-Monthly | ...")) {
            JOptionPane.showMessageDialog(null, "Select appropriate Payroll Type", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (jTFPayemployeeNo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete a Payroll Calculation First.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        

        // Retrieve values from the inputs and calculated fields
        String empNo = jTFPayemployeeNo.getText();
        String lName = jTFPayLastName.getText();
        String fName = jTFPayFirstName.getText();
        String selectedMonth = (String) jComboMonth.getSelectedItem();
        String year = jTFYear.getText().trim();
        String pType = jTFPayType.getText();
        String grossP = jTFPayGross.getText();
        String gAdj = jTFPayGrossAdj.getText();
        String unpLeave = jTFPayUnpaidLeave.getText();
        String sss = jTFPaySSS.getText();
        String phealth = jTFPayPhilhealth.getText();
        String pgibig = jTFPayPagibig.getText();
        String wTax = jTFWitholding.getText();
        String ben = jTFBenefits.getText();
        String benAdj = jTFBenefitsAdj.getText();
        String netP = jTFNet.getText();
        String note = payNote.getText();
        
        // Replace commas with semicolons in the note
        note = note.replace(",", ";");

        // Prepare the data to be written to the CSV
        String[] newData = {
            empNo,
            lName,
            fName,
            selectedMonth,
            year,
            pType,
            grossP,
            gAdj,
            unpLeave,
            sss,
            phealth,
            pgibig,
            wTax,
            ben,
            benAdj,
            netP,
            note
        };

        // Read existing data from the CSV file
        List<String[]> csvData = new ArrayList<>();
        String csvFile = "PaySummary.csv";
        boolean entryExists = false;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length > 5 && row[0].equals(empNo) && row[1].equals(lName)
                    && row[2].equals(fName) && row[3].equals(selectedMonth) && row[4].equals(year) && row[5].equals(pType)) {
                    // If entry exists, replace it
                    csvData.add(newData);
                    entryExists = true;
                } else {
                    csvData.add(row);
                }
            }
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null, "Error reading from file.", "File Error", JOptionPane.ERROR_MESSAGE);
            ioException.printStackTrace();
            return;
        }

        // If entry does not exist, add new entry
        if (!entryExists) {
            csvData.add(newData);
        }

        // Write updated data back to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            for (String[] row : csvData) {
                StringBuilder sb = new StringBuilder();
                for (String field : row) {
                    sb.append(field).append(",");
                }
                sb.deleteCharAt(sb.length() - 1); // Remove trailing comma
                sb.append("\n");
                writer.write(sb.toString());
            }
            JOptionPane.showMessageDialog(null, "Payroll data submitted successfully.");
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null, "Error writing to file.", "File Error", JOptionPane.ERROR_MESSAGE);
            ioException.printStackTrace();
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "An error occurred while processing the data.", "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }

    }//GEN-LAST:event_btnSubmitActionPerformed

    private void jPTypeMonthly2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPTypeMonthly2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPTypeMonthly2ActionPerformed

    private void lunchDedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lunchDedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lunchDedActionPerformed

    private void btnSemi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSemi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSemi1ActionPerformed

    private void applyDocumentFilters() {
    
    // Allow digits and .
    String digitsAndDot = "0123456789.";
    
    // Apply document filter to each text field without replacing the existing document
    ((PlainDocument) adjEarning2.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
    ((PlainDocument) otDur2.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
    ((PlainDocument) otRate2.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
    ((PlainDocument) adjBene2.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
    
    ((PlainDocument) adjEarning1.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
    ((PlainDocument) otDur1.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
    ((PlainDocument) otRate1.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
    ((PlainDocument) adjBene1.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
   
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
            java.util.logging.Logger.getLogger(AdminPayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPayroll("loginName").setVisible(true);
            }
        });
    }
    

    private void setIconImage() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Logo.jpg")));
    }
    
    private void initializeHourlyText() {
        jLabel81.setText("<html>Enter negative (-) amount for deduction adjustment. Positive numbers will be for additional earnings.</html>");
        jLabel82.setText("<html>Enter Number of Days with 1-hour Lunch Deduction:</html>");
        jLabel83.setText("<html>Enter amount of adjustment on Taxable Earnings:</html>");
        jLabel84.setText("<html>Enter overtime rate in decimal form (sample 1.25 for 125%:</html>");
        jLabel85.setText("<html>Enter amount of adjustment on Non-Taxable Earnings:</html>");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adjBene1;
    private javax.swing.JTextField adjBene2;
    private javax.swing.JTextField adjBene3;
    private javax.swing.JTextField adjEarning1;
    private javax.swing.JTextField adjEarning2;
    private javax.swing.JTextField adjEarning3;
    private javax.swing.JButton btnEditTime;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLeave;
    private javax.swing.JButton btnMonthly;
    private javax.swing.JButton btnSemi;
    private javax.swing.JButton btnSemi1;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnSummary;
    private javax.swing.JLabel clothing1;
    private javax.swing.JLabel clothing2;
    private javax.swing.JLabel clothing3;
    private javax.swing.JTextField in1;
    private javax.swing.JTextField in10;
    private javax.swing.JTextField in11;
    private javax.swing.JTextField in12;
    private javax.swing.JTextField in13;
    private javax.swing.JTextField in14;
    private javax.swing.JTextField in15;
    private javax.swing.JTextField in16;
    private javax.swing.JTextField in17;
    private javax.swing.JTextField in18;
    private javax.swing.JTextField in19;
    private javax.swing.JTextField in2;
    private javax.swing.JTextField in20;
    private javax.swing.JTextField in21;
    private javax.swing.JTextField in22;
    private javax.swing.JTextField in23;
    private javax.swing.JTextField in24;
    private javax.swing.JTextField in25;
    private javax.swing.JTextField in26;
    private javax.swing.JTextField in27;
    private javax.swing.JTextField in28;
    private javax.swing.JTextField in29;
    private javax.swing.JTextField in3;
    private javax.swing.JTextField in30;
    private javax.swing.JTextField in31;
    private javax.swing.JTextField in4;
    private javax.swing.JTextField in5;
    private javax.swing.JTextField in6;
    private javax.swing.JTextField in7;
    private javax.swing.JTextField in8;
    private javax.swing.JTextField in9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckPayAllowance;
    private javax.swing.JCheckBox jCheckPayAllowance1;
    private javax.swing.JCheckBox jCheckPayAllowance2;
    private javax.swing.JCheckBox jCheckUnpaidLeave1;
    private javax.swing.JCheckBox jCheckUnpaidLeave2;
    private javax.swing.JCheckBox jCheckUnpaidLeave3;
    private javax.swing.JComboBox<String> jComboBoxPayType;
    private javax.swing.JComboBox<String> jComboBoxPayType1;
    private javax.swing.JComboBox<String> jComboEmployee;
    private javax.swing.JComboBox<String> jComboMonth;
    private javax.swing.JLabel jLBG;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabelLoginName;
    private javax.swing.JLabel jLabelWelcome;
    private javax.swing.JLabel jLeaveDed1;
    private javax.swing.JLabel jLeaveDed2;
    private javax.swing.JTextField jPTypeMonthly;
    private javax.swing.JTextField jPTypeMonthly1;
    private javax.swing.JTextField jPTypeMonthly2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextArea jTCalcNote;
    private javax.swing.JTextArea jTCalcNote1;
    private javax.swing.JTextArea jTCalcNote2;
    private javax.swing.JTextField jTFBenefits;
    private javax.swing.JTextField jTFBenefitsAdj;
    private javax.swing.JTextField jTFNet;
    private javax.swing.JTextField jTFPayDetails;
    private javax.swing.JTextField jTFPayFirstName;
    private javax.swing.JTextField jTFPayGross;
    private javax.swing.JTextField jTFPayGrossAdj;
    private javax.swing.JTextField jTFPayLastName;
    private javax.swing.JTextField jTFPayPagibig;
    private javax.swing.JTextField jTFPayPhilhealth;
    private javax.swing.JTextField jTFPaySSS;
    private javax.swing.JTextField jTFPayType;
    private javax.swing.JTextField jTFPayUnpaidLeave;
    private javax.swing.JTextField jTFPayemployeeNo;
    private javax.swing.JTextField jTFWitholding;
    private javax.swing.JTextField jTFYear;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextAreaPaidLeave;
    private javax.swing.JButton logoutBTN;
    private javax.swing.JTextField lunchDed;
    private javax.swing.JTextField otDur1;
    private javax.swing.JTextField otDur2;
    private javax.swing.JTextField otRate1;
    private javax.swing.JTextField otRate2;
    private javax.swing.JTextField otRate3;
    private javax.swing.JTextField out1;
    private javax.swing.JTextField out10;
    private javax.swing.JTextField out11;
    private javax.swing.JTextField out12;
    private javax.swing.JTextField out13;
    private javax.swing.JTextField out14;
    private javax.swing.JTextField out15;
    private javax.swing.JTextField out16;
    private javax.swing.JTextField out17;
    private javax.swing.JTextField out18;
    private javax.swing.JTextField out19;
    private javax.swing.JTextField out2;
    private javax.swing.JTextField out20;
    private javax.swing.JTextField out21;
    private javax.swing.JTextField out22;
    private javax.swing.JTextField out23;
    private javax.swing.JTextField out24;
    private javax.swing.JTextField out25;
    private javax.swing.JTextField out26;
    private javax.swing.JTextField out27;
    private javax.swing.JTextField out28;
    private javax.swing.JTextField out29;
    private javax.swing.JTextField out3;
    private javax.swing.JTextField out30;
    private javax.swing.JTextField out31;
    private javax.swing.JTextField out4;
    private javax.swing.JTextField out5;
    private javax.swing.JTextField out6;
    private javax.swing.JTextField out7;
    private javax.swing.JTextField out8;
    private javax.swing.JTextField out9;
    private javax.swing.JTextField overTimeHrs;
    private javax.swing.JTextField paidLeave;
    private javax.swing.JTextArea payNote;
    private javax.swing.JLabel phone1;
    private javax.swing.JLabel phone2;
    private javax.swing.JLabel phone3;
    private javax.swing.JLabel rice1;
    private javax.swing.JLabel rice2;
    private javax.swing.JLabel rice3;
    private javax.swing.JTextField totRegHrs;
    private javax.swing.JTextArea unpaidLeaveList1;
    private javax.swing.JTextArea unpaidLeaveList2;
    // End of variables declaration//GEN-END:variables
}
