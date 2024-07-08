/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph_gui;

import java.awt.Toolkit;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author amatibag
 */
public class AdminLeaveApproval extends javax.swing.JFrame {
    private String loginName;
    private String username;
    private DefaultTableModel leaveTableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    /**
     * Creates new form AdminLeaveApproval
     */
    public AdminLeaveApproval(String username) {
        initComponents();
        setIconImage();
        // Set the login name based on username
        setLoginName(username);
        
        this.username = username;
        
        initLeaveTable(); // Initialize and populate the leave table
        
        // Add selection listener to jTLeave
        jTLeave.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = jTLeave.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                        populateFieldsFromSelectedRow(selectedRow);
                    }
                }
            }
        });
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
    
    
    
    private void initLeaveTable() {
        leaveTableModel = (DefaultTableModel) jTLeave.getModel();
            leaveTableModel.setRowCount(0); // Clear existing rows

            // Read TimeOff.csv and populate the table, skipping the header
            try (BufferedReader br = new BufferedReader(new FileReader("TimeOff.csv"))) {
                String line;
                boolean headerSkipped = false;
                while ((line = br.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue; // Skip the header line
                    }
                    String[] data = line.split(",");
                    leaveTableModel.addRow(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading TimeOff.csv: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Enable sorting for jTableLeave if not already set
            if (sorter == null) {
                sorter = new TableRowSorter<>(leaveTableModel);
                jTLeave.setRowSorter(sorter);
            }
        }

        private void applyFilters() {
            List<RowFilter<Object, Object>> filters = new ArrayList<>();

            // Employee filter
            if (jCBemployee.isSelected()) {
                String selectedEmployee = (String) jComboEmployee.getSelectedItem();
                if (selectedEmployee != null && !selectedEmployee.isEmpty()) {
                    String[] parts = selectedEmployee.split(" - ");
                    if (parts.length >= 1) {
                        String empNo = parts[0].trim();
                        RowFilter<Object, Object> employeeFilter = RowFilter.regexFilter(empNo, 0);
                        filters.add(employeeFilter);
                    }
                }
            }

            // Month filter
            if (jCBmonth.isSelected()) {
                String selectedMonth = (String) jComboMonth.getSelectedItem();
                if (selectedMonth != null && !selectedMonth.isEmpty()) {
                    RowFilter<Object, Object> monthFilter = RowFilter.regexFilter(selectedMonth, 3);
                    filters.add(monthFilter);
                }
            }

            // Year filter
            if (jCByear.isSelected()) {
                String selectedYear = (String) jComboyear.getSelectedItem();
                if (selectedYear != null && !selectedYear.isEmpty()) {
                    RowFilter<Object, Object> yearFilter = RowFilter.regexFilter(selectedYear, 5);
                    filters.add(yearFilter);
                }
            }

            // Type filter
            if (jCBtype.isSelected()) {
                String selectedType = (String) jComboType.getSelectedItem();
                if (selectedType != null && !selectedType.isEmpty()) {
                    RowFilter<Object, Object> typeFilter = RowFilter.regexFilter(selectedType, 6);
                    filters.add(typeFilter);
                }
            }
            
            // Status filter
            if (jCBstatus.isSelected()) {
                String selectedStatus = (String) jComboStatus.getSelectedItem();
                if (selectedStatus != null && !selectedStatus.isEmpty()) {
                    RowFilter<Object, Object> typeFilter = RowFilter.regexFilter(selectedStatus, 7);
                    filters.add(typeFilter);
                }
            }
            
            if (jCBFpayable.isSelected()) {
                String selectedPayable = (String) jComboPayable.getSelectedItem();
                if (selectedPayable != null && !selectedPayable.isEmpty()) {
                    RowFilter<Object, Object> typeFilter = RowFilter.regexFilter(selectedPayable, 8);
                    filters.add(typeFilter);
                }
            }             

            // Apply compound filter if any filters are present
            if (!filters.isEmpty()) {
                RowFilter<Object, Object> compoundFilter = RowFilter.andFilter(filters);
                sorter.setRowFilter(compoundFilter);
            } else {
                // If no filters are selected, show all rows
                sorter.setRowFilter(null);
            }
}

    // Method to populate UI fields from selected row
    private void populateFieldsFromSelectedRow(int rowIndex) {
        // Assuming column indices based on your CSV structure
        String empNo = (String) leaveTableModel.getValueAt(rowIndex, 0); // First column
        String lastName = (String) leaveTableModel.getValueAt(rowIndex, 1); // Second column
        String firstName = (String) leaveTableModel.getValueAt(rowIndex, 2); // Third column
        String date = (String) leaveTableModel.getValueAt(rowIndex, 4); // Fourth column
        String month = (String) leaveTableModel.getValueAt(rowIndex, 3); // Fifth column
        String year = (String) leaveTableModel.getValueAt(rowIndex, 5); // Sixth column
        String type = (String) leaveTableModel.getValueAt(rowIndex, 6); // Seventh column
        String status = (String) leaveTableModel.getValueAt(rowIndex, 7); // Eighth column
        String payable = (String) leaveTableModel.getValueAt(rowIndex, 8); // Ninth column

        // Update JTextFields and JComboBoxes
        jTempNo.setText(empNo);
        jTName.setText(lastName + ", " + firstName);
        jTdate.setText(month + " " + date + ", " + year);
        jTtype.setText(type);
        jCBStatus.setSelectedItem(status);
        jCBpayable.setSelectedItem(payable);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTLeave = new javax.swing.JTable();
        logoutBTN = new javax.swing.JButton();
        btnHome = new javax.swing.JButton();
        btnPayroll = new javax.swing.JButton();
        btnSummary = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCBStatus = new javax.swing.JComboBox<>();
        jCBpayable = new javax.swing.JComboBox<>();
        jTempNo = new javax.swing.JTextField();
        jTName = new javax.swing.JTextField();
        jTdate = new javax.swing.JTextField();
        jTtype = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        jCBemployee = new javax.swing.JCheckBox();
        jComboEmployee = new javax.swing.JComboBox<>();
        jCBmonth = new javax.swing.JCheckBox();
        jComboMonth = new javax.swing.JComboBox<>();
        jCByear = new javax.swing.JCheckBox();
        jComboyear = new javax.swing.JComboBox<>();
        jCBtype = new javax.swing.JCheckBox();
        jComboType = new javax.swing.JComboBox<>();
        jCBstatus = new javax.swing.JCheckBox();
        jComboStatus = new javax.swing.JComboBox<>();
        jCBFpayable = new javax.swing.JCheckBox();
        jComboPayable = new javax.swing.JComboBox<>();
        jLBGLeave = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Leave Approval Page");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelWelcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelWelcome.setForeground(new java.awt.Color(255, 255, 255));
        jLabelWelcome.setText("Welcome,");
        getContentPane().add(jLabelWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 60, 20));

        jLabelLoginName.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabelLoginName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLoginName.setText("User Name Here");
        getContentPane().add(jLabelLoginName, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 180, -1));

        jTLeave.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee No.", "Last Name", "First Name", "Month", "Day", "Year", "Type", "Status", "Payable"
            }
        ));
        jTLeave.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jScrollPane1.setViewportView(jTLeave);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 710, 440));

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

        btnPayroll.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnPayroll.setToolTipText("");
        btnPayroll.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPayroll.setLabel("PROCESS PAYROLL");
        btnPayroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayrollActionPerformed(evt);
            }
        });
        getContentPane().add(btnPayroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 110, 160, 25));

        btnSummary.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnSummary.setText("PAYROLL SUMMARY");
        btnSummary.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSummaryActionPerformed(evt);
            }
        });
        getContentPane().add(btnSummary, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 110, 160, 25));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Employee Number");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Name");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Date");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Time Off Type");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, -1));

        jCBStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Approved", "Rejected", " " }));
        jCBStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jCBStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBStatusActionPerformed(evt);
            }
        });
        getContentPane().add(jCBStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 230, 25));

        jCBpayable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Paid", "Unpaid" }));
        jCBpayable.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jCBpayable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBpayableActionPerformed(evt);
            }
        });
        getContentPane().add(jCBpayable, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 230, 25));

        jTempNo.setEditable(false);
        jTempNo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTempNo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTempNo.setEnabled(false);
        getContentPane().add(jTempNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 230, 25));

        jTName.setEditable(false);
        jTName.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTName.setEnabled(false);
        getContentPane().add(jTName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 230, 25));

        jTdate.setEditable(false);
        jTdate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTdate.setEnabled(false);
        getContentPane().add(jTdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 230, 25));

        jTtype.setEditable(false);
        jTtype.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTtype.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTtype.setEnabled(false);
        getContentPane().add(jTtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 230, 25));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Status");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Payable Status");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, -1, -1));

        btnSubmit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        getContentPane().add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 600, 200, 25));

        jCBemployee.setBackground(new java.awt.Color(60, 126, 114));
        jCBemployee.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCBemployee.setForeground(new java.awt.Color(255, 255, 255));
        jCBemployee.setText("Filter by Employee");
        jCBemployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBemployeeActionPerformed(evt);
            }
        });
        getContentPane().add(jCBemployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 220, 150, 20));

        jComboEmployee.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "..." }));
        jComboEmployee.setEnabled(false);
        jComboEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboEmployeeActionPerformed(evt);
            }
        });
        getContentPane().add(jComboEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 240, 150, 20));

        jCBmonth.setBackground(new java.awt.Color(60, 126, 114));
        jCBmonth.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCBmonth.setForeground(new java.awt.Color(255, 255, 255));
        jCBmonth.setText("Filter by Month");
        jCBmonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBmonthActionPerformed(evt);
            }
        });
        getContentPane().add(jCBmonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 280, 150, 20));

        jComboMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "..." }));
        jComboMonth.setEnabled(false);
        jComboMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboMonthActionPerformed(evt);
            }
        });
        getContentPane().add(jComboMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 300, 150, 20));

        jCByear.setBackground(new java.awt.Color(60, 126, 114));
        jCByear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCByear.setForeground(new java.awt.Color(255, 255, 255));
        jCByear.setText("Filter by Year");
        jCByear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCByearActionPerformed(evt);
            }
        });
        getContentPane().add(jCByear, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 340, 150, 20));

        jComboyear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "..." }));
        jComboyear.setEnabled(false);
        jComboyear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboyearActionPerformed(evt);
            }
        });
        getContentPane().add(jComboyear, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 360, 150, 20));

        jCBtype.setBackground(new java.awt.Color(60, 126, 114));
        jCBtype.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCBtype.setForeground(new java.awt.Color(255, 255, 255));
        jCBtype.setText("Filter by Type");
        jCBtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBtypeActionPerformed(evt);
            }
        });
        getContentPane().add(jCBtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 400, 150, 20));

        jComboType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "...", "Vacation", "Sick", "Leave of Absence" }));
        jComboType.setEnabled(false);
        jComboType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboTypeActionPerformed(evt);
            }
        });
        getContentPane().add(jComboType, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 420, 150, 20));

        jCBstatus.setBackground(new java.awt.Color(60, 126, 114));
        jCBstatus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCBstatus.setForeground(new java.awt.Color(255, 255, 255));
        jCBstatus.setText("Filter by Status");
        jCBstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBstatusActionPerformed(evt);
            }
        });
        getContentPane().add(jCBstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 460, 150, 20));

        jComboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "...", "Pending", "Approved", "Rejected" }));
        jComboStatus.setEnabled(false);
        jComboStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboStatusActionPerformed(evt);
            }
        });
        getContentPane().add(jComboStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 480, 150, 20));

        jCBFpayable.setBackground(new java.awt.Color(60, 126, 114));
        jCBFpayable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCBFpayable.setForeground(new java.awt.Color(255, 255, 255));
        jCBFpayable.setText("Filter by Payable");
        jCBFpayable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBFpayableActionPerformed(evt);
            }
        });
        getContentPane().add(jCBFpayable, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 520, 150, 20));

        jComboPayable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "...", "Pending", "Paid", "Unpaid" }));
        jComboPayable.setEnabled(false);
        jComboPayable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPayableActionPerformed(evt);
            }
        });
        getContentPane().add(jComboPayable, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 540, 150, 20));

        jLBGLeave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/motorph_gui/AdminLeave.jpg"))); // NOI18N
        getContentPane().add(jLBGLeave, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 670));

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

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        this.dispose();

        // Launch the new AdminLeaveApproval frame
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminHome(username).setVisible(true);
            }
        });
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnPayrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayrollActionPerformed
        this.dispose();

        // Launch the new AdminLeaveApproval frame
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPayroll(username).setVisible(true);
            }
        });
    }//GEN-LAST:event_btnPayrollActionPerformed

    private void btnSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSummaryActionPerformed
        this.dispose();

        // Launch the new AdminLeaveApproval frame
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPaySummary().setVisible(true);
            }
        });
    }//GEN-LAST:event_btnSummaryActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // Get the selected row index
        int selectedRow = jTLeave.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to submit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get data from UI components
        String empNo = jTempNo.getText().trim();
        String[] name = jTName.getText().split(",\\s*");
        String lastName = name[0].trim();
        String firstName = name[1].trim();
        String[] dateParts = jTdate.getText().split("\\s+");
        String month = dateParts[0].trim();
        String day = dateParts[1].trim().replace(",", ""); // Remove comma
        String year = dateParts[2].trim();
        String type = jTtype.getText().trim();
        String status = jCBStatus.getSelectedItem().toString().trim();
        String payable = jCBpayable.getSelectedItem().toString().trim();

        // Validate fields
        if (status.equals("Approved") && (!payable.equals("Paid") && !payable.equals("Unpaid"))) {
            JOptionPane.showMessageDialog(this, "Please select 'Paid' or 'Unpaid' for payable status when the status is 'Approved'.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmation dialog
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to submit this entry?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Update table model
            DefaultTableModel model = (DefaultTableModel) jTLeave.getModel();
            model.setValueAt(empNo, selectedRow, 0);
            model.setValueAt(lastName, selectedRow, 1);
            model.setValueAt(firstName, selectedRow, 2);
            model.setValueAt(month, selectedRow, 3);
            model.setValueAt(day, selectedRow, 4);
            model.setValueAt(year, selectedRow, 5);
            model.setValueAt(type, selectedRow, 6);
            model.setValueAt(status, selectedRow, 7);
            model.setValueAt(payable, selectedRow, 8);

            // Update TimeOff.csv file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("TimeOff.csv"))) {
                // Write header
                writer.write("Employee No.,Last Name,First Name,Month,Day,Year,Type,Status,Payable");
                writer.newLine();

                // Write updated data
                for (int i = 0; i < model.getRowCount(); i++) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        sb.append(model.getValueAt(i, j));
                        if (j < model.getColumnCount() - 1) {
                            sb.append(",");
                        }
                    }
                    writer.write(sb.toString());
                    writer.newLine();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error writing to TimeOff.csv: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void jCBpayableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBpayableActionPerformed
       String selectedStatus = jCBStatus.getSelectedItem().toString().trim();
        String selectedPayable = jCBpayable.getSelectedItem().toString().trim();

        if (selectedStatus.equals("Approved")) {
            if (!selectedPayable.equals("Paid") && !selectedPayable.equals("Unpaid")) {
                JOptionPane.showMessageDialog(this, "Please select 'Paid' or 'Unpaid' when the status is 'Approved'.", "Error", JOptionPane.ERROR_MESSAGE);
                jCBpayable.requestFocus();
            }
        }
    }//GEN-LAST:event_jCBpayableActionPerformed

    private void jCBStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBStatusActionPerformed
        String selectedStatus = jCBStatus.getSelectedItem().toString().trim();
    
        if (selectedStatus.equals("Rejected")) {
            jCBpayable.setSelectedItem("Unpaid");
            jCBpayable.setEnabled(false);
        } else if (selectedStatus.equals("Approved")) {
            jCBpayable.setEnabled(true);
        } else {
            jCBpayable.setEnabled(true);
        }
    }//GEN-LAST:event_jCBStatusActionPerformed
    
    
    private void jCBemployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBemployeeActionPerformed
        // Enable or disable jComboEmployee based on jCBemployee state
        jComboEmployee.setEnabled(jCBemployee.isSelected());

        if (jCBemployee.isSelected()) {
            populateEmployeeComboBox(); // Populate combo box with employee data
        } else {
            jComboEmployee.setModel(new DefaultComboBoxModel<>()); // Clear combo box when checkbox is unchecked
            applyFilters(); // Apply remaining filters
        }
    }//GEN-LAST:event_jCBemployeeActionPerformed

    private void jCBmonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBmonthActionPerformed
        // Enable or disable jComboMonth based on jCBmonth state
        jComboMonth.setEnabled(jCBmonth.isSelected());

        if (jCBmonth.isSelected()) {
            populateMonthComboBox(); // Populate combo box with month data
        } else {
            jComboMonth.setModel(new DefaultComboBoxModel<>()); // Clear combo box when checkbox is unchecked
            applyFilters(); // Apply remaining filters
        }
    }//GEN-LAST:event_jCBmonthActionPerformed

    private void jCByearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCByearActionPerformed
        // Enable or disable jComboYear based on jCByear state
        jComboyear.setEnabled(jCByear.isSelected());

        if (jCByear.isSelected()) {
            populateYearComboBox(); // Populate combo box with year data
        } else {
            jComboyear.setModel(new DefaultComboBoxModel<>()); // Clear combo box when checkbox is unchecked
            applyFilters(); // Apply remaining filters
        }
    }//GEN-LAST:event_jCByearActionPerformed

    private void jCBtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBtypeActionPerformed
        // Enable or disable jComboType based on jCBtype state
        jComboType.setEnabled(jCBtype.isSelected());

        // Apply filters whenever the checkbox state changes
        applyFilters();
    }//GEN-LAST:event_jCBtypeActionPerformed

    private void jCBstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBstatusActionPerformed
        // Enable or disable jComboStatus based on jCBstatus state
        jComboStatus.setEnabled(jCBstatus.isSelected());
        // Apply filters whenever the checkbox state changes
        applyFilters();
    }//GEN-LAST:event_jCBstatusActionPerformed

    private void jCBFpayableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBFpayableActionPerformed
       // Enable or disable jCBpayable based on jCBFpayable state
        jComboPayable.setEnabled(jCBFpayable.isSelected());
        // Apply filters whenever the checkbox state changes
        applyFilters();
    }//GEN-LAST:event_jCBFpayableActionPerformed

    private void jComboEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboEmployeeActionPerformed
        applyFilters(); 
    
    }//GEN-LAST:event_jComboEmployeeActionPerformed

    private void jComboMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboMonthActionPerformed
       applyFilters();
    }//GEN-LAST:event_jComboMonthActionPerformed

    private void jComboyearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboyearActionPerformed
        applyFilters();
    }//GEN-LAST:event_jComboyearActionPerformed

    private void jComboTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboTypeActionPerformed
        applyFilters();
    }//GEN-LAST:event_jComboTypeActionPerformed

    private void jComboStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboStatusActionPerformed
        applyFilters();
    }//GEN-LAST:event_jComboStatusActionPerformed

    private void jComboPayableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPayableActionPerformed
        applyFilters();
    }//GEN-LAST:event_jComboPayableActionPerformed

    // Method to populate jComboEmployee with data from TimeOff.csv
    private void populateEmployeeComboBox() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
    Set<String> employees = new TreeSet<>(); // Use TreeSet for sorting and uniqueness

    try (BufferedReader reader = new BufferedReader(new FileReader("TimeOff.csv"))) {
        String line;
        boolean isFirstLine = true;

        while ((line = reader.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false; // Skip the header row
                continue;
            }

            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String empNo = parts[0].trim();
                String lastName = parts[1].trim();
                String firstName = parts[2].trim();

                String employee = empNo + " - " + lastName + ", " + firstName;
                employees.add(employee); // Add formatted employee string to set
            }
        }

        // Add sorted and unique employee names to the combo box model
        for (String employee : employees) {
            model.addElement(employee);
        }

        jComboEmployee.setModel(model); // Set the model to combo box
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error reading TimeOff.csv: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void populateMonthComboBox() {
            Set<String> months = new TreeSet<>((month1, month2) -> {
                String[] monthOrder = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                int index1 = -1, index2 = -1;
                for (int i = 0; i < monthOrder.length; i++) {
                    if (monthOrder[i].equalsIgnoreCase(month1)) index1 = i;
                    if (monthOrder[i].equalsIgnoreCase(month2)) index2 = i;
                }
                return Integer.compare(index1, index2);
            });

            try (BufferedReader reader = new BufferedReader(new FileReader("TimeOff.csv"))) {
                String line;
                boolean isFirstLine = true;

                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false; // Skip the header row
                        continue;
                    }

                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String month = parts[3].trim();
                        months.add(month);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Update jComboMonth with the sorted, distinct values
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(months.toArray(new String[0]));
            jComboMonth.setModel(model);
        }

    private void populateYearComboBox() {
        Set<String> years = new TreeSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("TimeOff.csv"))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip the header row
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String year = parts[5].trim();
                    years.add(year);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update jComboYear with the sorted, distinct values
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(years.toArray(new String[0]));
        jComboyear.setModel(model);
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
            java.util.logging.Logger.getLogger(AdminLeaveApproval.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminLeaveApproval.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminLeaveApproval.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminLeaveApproval.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminLeaveApproval("loginName").setVisible(true);
            }
        });
    }

    private void setIconImage() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Logo.jpg")));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnPayroll;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnSummary;
    private javax.swing.JCheckBox jCBFpayable;
    private javax.swing.JComboBox<String> jCBStatus;
    private javax.swing.JCheckBox jCBemployee;
    private javax.swing.JCheckBox jCBmonth;
    private javax.swing.JComboBox<String> jCBpayable;
    private javax.swing.JCheckBox jCBstatus;
    private javax.swing.JCheckBox jCBtype;
    private javax.swing.JCheckBox jCByear;
    private javax.swing.JComboBox<String> jComboEmployee;
    private javax.swing.JComboBox<String> jComboMonth;
    private javax.swing.JComboBox<String> jComboPayable;
    private javax.swing.JComboBox<String> jComboStatus;
    private javax.swing.JComboBox<String> jComboType;
    private javax.swing.JComboBox<String> jComboyear;
    private javax.swing.JLabel jLBGLeave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelLoginName;
    private javax.swing.JLabel jLabelWelcome;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTLeave;
    private javax.swing.JTextField jTName;
    private javax.swing.JTextField jTdate;
    private javax.swing.JTextField jTempNo;
    private javax.swing.JTextField jTtype;
    private javax.swing.JButton logoutBTN;
    // End of variables declaration//GEN-END:variables
}
