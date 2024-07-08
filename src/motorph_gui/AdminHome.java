/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package motorph_gui;

import com.opencsv.CSVReader;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.PlainDocument;

/**
 *
 * @author amatibag
 */
public class AdminHome extends javax.swing.JFrame {
    private String loginName;
    private List<String[]> csvData;
    private String username;  // Add this line to store the username
    

    /**
     * Creates new form AdminHome
     */
    public AdminHome(String username) {
        // Initialize the username
        this.username = username;  
        
        initComponents();
        
        setIconImage();
        
        // Set the login name based on username
        setLoginName(username);
        
        // Load the CSV data into the table
        loadCSVData();
        
        // Add table selection listener
        tableSelectionListener();
        
        // Apply the text filters
        applyDocumentFilters(); 
        
        
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
    
    private void loadCSVData() {
    DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
    csvData = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader("motorPHEmployeeList.csv"))) {
        String line;
        boolean isFirstRow = true;
        while ((line = br.readLine()) != null) {
            if (isFirstRow) {
                isFirstRow = false; // Skip the header row
                continue;
            }
            String[] data = line.split(",");
            if (data.length >= 19) { // Ensure it has enough columns
                model.addRow(new Object[]{data[0], data[1], data[2], data[6], data[7], data[8], data[9]});
                csvData.add(data); // Store data for later use
            }
        }
        // Set header font after loading data
        employeeTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 10));

    } catch (IOException e) {
        e.printStackTrace();
    }
    // Enable sorting for jTableLeave
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            employeeTable.setRowSorter(sorter);
}
    
    private void tableSelectionListener() {
    employeeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) { // If a row is selected
                    if (jTlastName.isEditable()) {
                        int choice = JOptionPane.showConfirmDialog(null, "All changes made will not be saved. Do you wish to continue selecting a new employee?", "Confirm Selection", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            disableTextFields();
                            refreshTable();
                            populateTextFields(selectedRow);
                        } else {
                            // Do nothing, close the popup
                        }
                    } else {
                        populateTextFields(selectedRow);
                    }
                }
            }
        }
    });
}

        private void populateTextFields(int selectedRow) {
    if (selectedRow >= 0 && selectedRow < csvData.size()) {
        String[] rowData = csvData.get(selectedRow);
        // Ensure rowData has enough elements based on your CSV structure
        if (rowData.length >= 19) { 
            jTemployeeNo.setText(rowData[0]);    
            jTlastName.setText(rowData[1]);
            jTfirstName.setText(rowData[2]);
            jTbirthday.setText(rowData[3]);
            String address = rowData[4];
            jTAddress.setText(address);
            jTAddress.setLineWrap(true);
            jTAddress.setWrapStyleWord(true);
            jTAddress.setText(address);
            jTAddress.setEditable(false);
            jTphoneNo.setText(rowData[5]);
            jTsssNo.setText(rowData[6]);
            jTphilhealth.setText(rowData[7]);
            jTtin.setText(rowData[8]);
            jTpagibig.setText(rowData[9]);
            jTstatus.setText(rowData[10]);
            jTposition.setText(rowData[11]);
            jsupervisor.setText(rowData[12]);
            jTbasic.setText(rowData[13]);
            jTrice.setText(rowData[14]);
            jTphoneallow.setText(rowData[15]);
            jTclothing.setText(rowData[16]);
            jTsemi.setText(rowData[17]);
            jThourly.setText(rowData[18]);
        } else {
            // Handle case where rowData doesn't have enough elements
            System.err.println("CSV data does not have enough columns for selected row.");
        }
    }
}

        private List<String[]> readDataFromCSV() {
            // Implement method to read data from motorHEmployeeList.csv and return as List<String[]>
            // Example code to read CSV file and store data in a List<String[]>
            List<String[]> data = new ArrayList<>();
            try (CSVReader reader = new CSVReader(new FileReader("motorHEmployeeList.csv"))) {
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    data.add(nextLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }
        
        private void disableTextFields() {
            jTlastName.setEditable(false);
            jTfirstName.setEditable(false);
            jTbirthday.setEditable(false);
            jTAddress.setEditable(false);
            jTphoneNo.setEditable(false);
            jTsssNo.setEditable(false);
            jTphilhealth.setEditable(false);
            jTtin.setEditable(false);
            jTpagibig.setEditable(false);
            jTstatus.setEditable(false);
            jTposition.setEditable(false);
            jsupervisor.setEditable(false);
            jTbasic.setEditable(false);
            jTrice.setEditable(false);
            jTphoneallow.setEditable(false);
            jTclothing.setEditable(false);
            jTsemi.setEditable(false);
            jThourly.setEditable(false);
            // Set text of jTemployeeNo to blank
            jTemployeeNo.setText("");
            
            // Ensuring components are disabled
            jTAddress.setEnabled(false);
            jTbasic.setEnabled(false);
            jTbirthday.setEnabled(false);
            jTclothing.setEnabled(false);
            jTfirstName.setEnabled(false);
            jThourly.setEnabled(false);
            jTlastName.setEnabled(false);
            jTpagibig.setEnabled(false);
            jTphilhealth.setEnabled(false);
            jTphoneNo.setEnabled(false);
            jTphoneallow.setEnabled(false);
            jTposition.setEnabled(false);
            jTrice.setEnabled(false);
            jTsemi.setEnabled(false);
            jTsssNo.setEnabled(false);
            jTstatus.setEnabled(false);
            jTtin.setEnabled(false);
            jsupervisor.setEnabled(false);
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
        jScrollPaneTable = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        jLeeNo = new javax.swing.JLabel();
        jLlastName = new javax.swing.JLabel();
        jLfirstName = new javax.swing.JLabel();
        jLbDay = new javax.swing.JLabel();
        jLaddress = new javax.swing.JLabel();
        jLphoneNo = new javax.swing.JLabel();
        jLSSS = new javax.swing.JLabel();
        jLPhilhealth = new javax.swing.JLabel();
        jLTIN = new javax.swing.JLabel();
        jLPagibig = new javax.swing.JLabel();
        jLStatus = new javax.swing.JLabel();
        jLPosition = new javax.swing.JLabel();
        jLSup = new javax.swing.JLabel();
        jLBasic = new javax.swing.JLabel();
        jLRice = new javax.swing.JLabel();
        jLphoneAllow = new javax.swing.JLabel();
        jLClothing = new javax.swing.JLabel();
        jLSemi = new javax.swing.JLabel();
        jLHourly = new javax.swing.JLabel();
        jTemployeeNo = new javax.swing.JTextField();
        jTlastName = new javax.swing.JTextField();
        jTfirstName = new javax.swing.JTextField();
        jTbirthday = new javax.swing.JTextField();
        jTphoneNo = new javax.swing.JTextField();
        jTsssNo = new javax.swing.JTextField();
        jTphilhealth = new javax.swing.JTextField();
        jTtin = new javax.swing.JTextField();
        jTpagibig = new javax.swing.JTextField();
        jTstatus = new javax.swing.JTextField();
        jTposition = new javax.swing.JTextField();
        jsupervisor = new javax.swing.JTextField();
        jTbasic = new javax.swing.JTextField();
        jTrice = new javax.swing.JTextField();
        jTphoneallow = new javax.swing.JTextField();
        jTclothing = new javax.swing.JTextField();
        jTsemi = new javax.swing.JTextField();
        jThourly = new javax.swing.JTextField();
        jScrollAddress = new javax.swing.JScrollPane();
        jTAddress = new javax.swing.JTextArea();
        jLeeNo1 = new javax.swing.JLabel();
        jLlastName1 = new javax.swing.JLabel();
        jLfirstName1 = new javax.swing.JLabel();
        jLbDay1 = new javax.swing.JLabel();
        jLaddress1 = new javax.swing.JLabel();
        jLphoneNo1 = new javax.swing.JLabel();
        jLSSS1 = new javax.swing.JLabel();
        jLPhilhealth1 = new javax.swing.JLabel();
        jLTIN1 = new javax.swing.JLabel();
        jLPagibig1 = new javax.swing.JLabel();
        jLStatus1 = new javax.swing.JLabel();
        jLPosition1 = new javax.swing.JLabel();
        jLSup1 = new javax.swing.JLabel();
        jLBasic1 = new javax.swing.JLabel();
        jLRice1 = new javax.swing.JLabel();
        jLphoneAllow1 = new javax.swing.JLabel();
        jLClothing1 = new javax.swing.JLabel();
        jLSemi1 = new javax.swing.JLabel();
        jLHourly1 = new javax.swing.JLabel();
        jLbdayformat = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        btnLeave = new javax.swing.JButton();
        btnPayroll = new javax.swing.JButton();
        btnSummary = new javax.swing.JButton();
        adminHomeBG = new javax.swing.JLabel();
        jLabelWelcome1 = new javax.swing.JLabel();
        jLabelLoginName1 = new javax.swing.JLabel();
        logoutBTN1 = new javax.swing.JButton();
        logoutBTN2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Home Page");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelWelcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelWelcome.setForeground(new java.awt.Color(255, 255, 255));
        jLabelWelcome.setText("Welcome,");
        getContentPane().add(jLabelWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 60, 20));

        jLabelLoginName.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabelLoginName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLoginName.setText("User Name Here");
        getContentPane().add(jLabelLoginName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 180, -1));

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

        employeeTable.setFont(new java.awt.Font("Segoe UI", 0, 9)); // NOI18N
        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee No.", "Last Name", "First Name", "SSS No.", "Philhealth No.", "TIN No.", "Pag-ibig No."
            }
        ));
        employeeTable.setGridColor(new java.awt.Color(60, 126, 114));
        employeeTable.setSelectionBackground(new java.awt.Color(210, 89, 51));
        jScrollPaneTable.setViewportView(employeeTable);

        getContentPane().add(jScrollPaneTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 120, 600, 510));

        jLeeNo.setText("Employee Number");
        getContentPane().add(jLeeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 125, -1, -1));

        jLlastName.setText("Last Name");
        getContentPane().add(jLlastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 150, -1, -1));

        jLfirstName.setText("First Name");
        getContentPane().add(jLfirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 175, -1, -1));

        jLbDay.setText("Birthday");
        getContentPane().add(jLbDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 200, -1, -1));

        jLaddress.setText("Address");
        getContentPane().add(jLaddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 225, -1, -1));

        jLphoneNo.setText("Phone Number");
        getContentPane().add(jLphoneNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 285, -1, -1));

        jLSSS.setText("SSS Number");
        getContentPane().add(jLSSS, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 310, -1, -1));

        jLPhilhealth.setText("Philhealth Number");
        getContentPane().add(jLPhilhealth, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 335, -1, -1));

        jLTIN.setText("TIN Number");
        getContentPane().add(jLTIN, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 360, -1, -1));

        jLPagibig.setText("Pag-ibig Number");
        getContentPane().add(jLPagibig, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 385, -1, -1));

        jLStatus.setText("Status");
        getContentPane().add(jLStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 410, -1, -1));

        jLPosition.setText("Position");
        getContentPane().add(jLPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 435, -1, -1));

        jLSup.setText("Immediate Supervisor");
        getContentPane().add(jLSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 460, -1, -1));

        jLBasic.setText("Basic Salary");
        getContentPane().add(jLBasic, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 485, -1, -1));

        jLRice.setText("Rice Subsidy");
        getContentPane().add(jLRice, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 510, -1, -1));

        jLphoneAllow.setText("Phone Allowance");
        getContentPane().add(jLphoneAllow, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 535, -1, -1));

        jLClothing.setText("Clothing Allowance");
        getContentPane().add(jLClothing, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 560, -1, -1));

        jLSemi.setText("Gross Semi-monthly Rate");
        getContentPane().add(jLSemi, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 585, -1, -1));

        jLHourly.setText("Hourly Rate");
        getContentPane().add(jLHourly, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 610, -1, -1));

        jTemployeeNo.setBackground(new java.awt.Color(255, 245, 206));
        jTemployeeNo.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTemployeeNo.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTemployeeNo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTemployeeNo.setEnabled(false);
        getContentPane().add(jTemployeeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 122, 145, 22));

        jTlastName.setEditable(false);
        jTlastName.setBackground(new java.awt.Color(255, 245, 206));
        jTlastName.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTlastName.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTlastName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTlastName.setEnabled(false);
        getContentPane().add(jTlastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 147, 145, 22));

        jTfirstName.setEditable(false);
        jTfirstName.setBackground(new java.awt.Color(255, 245, 206));
        jTfirstName.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTfirstName.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTfirstName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTfirstName.setEnabled(false);
        getContentPane().add(jTfirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 172, 145, 22));

        jTbirthday.setEditable(false);
        jTbirthday.setBackground(new java.awt.Color(255, 245, 206));
        jTbirthday.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTbirthday.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTbirthday.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTbirthday.setEnabled(false);
        getContentPane().add(jTbirthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 197, 145, 22));

        jTphoneNo.setEditable(false);
        jTphoneNo.setBackground(new java.awt.Color(255, 245, 206));
        jTphoneNo.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTphoneNo.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTphoneNo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTphoneNo.setEnabled(false);
        getContentPane().add(jTphoneNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 282, 145, 22));

        jTsssNo.setEditable(false);
        jTsssNo.setBackground(new java.awt.Color(255, 245, 206));
        jTsssNo.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTsssNo.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTsssNo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTsssNo.setEnabled(false);
        getContentPane().add(jTsssNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 307, 145, 22));

        jTphilhealth.setEditable(false);
        jTphilhealth.setBackground(new java.awt.Color(255, 245, 206));
        jTphilhealth.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTphilhealth.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTphilhealth.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTphilhealth.setEnabled(false);
        getContentPane().add(jTphilhealth, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 332, 145, 22));

        jTtin.setEditable(false);
        jTtin.setBackground(new java.awt.Color(255, 245, 206));
        jTtin.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTtin.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTtin.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTtin.setEnabled(false);
        getContentPane().add(jTtin, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 357, 145, 22));

        jTpagibig.setEditable(false);
        jTpagibig.setBackground(new java.awt.Color(255, 245, 206));
        jTpagibig.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTpagibig.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTpagibig.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTpagibig.setEnabled(false);
        getContentPane().add(jTpagibig, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 382, 145, 22));

        jTstatus.setEditable(false);
        jTstatus.setBackground(new java.awt.Color(255, 245, 206));
        jTstatus.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTstatus.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTstatus.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTstatus.setEnabled(false);
        getContentPane().add(jTstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 407, 145, 22));

        jTposition.setEditable(false);
        jTposition.setBackground(new java.awt.Color(255, 245, 206));
        jTposition.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTposition.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTposition.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTposition.setEnabled(false);
        getContentPane().add(jTposition, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 432, 145, 22));

        jsupervisor.setEditable(false);
        jsupervisor.setBackground(new java.awt.Color(255, 245, 206));
        jsupervisor.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jsupervisor.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jsupervisor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jsupervisor.setEnabled(false);
        getContentPane().add(jsupervisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 457, 145, 22));

        jTbasic.setEditable(false);
        jTbasic.setBackground(new java.awt.Color(255, 245, 206));
        jTbasic.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTbasic.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTbasic.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTbasic.setEnabled(false);
        getContentPane().add(jTbasic, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 482, 115, 22));

        jTrice.setEditable(false);
        jTrice.setBackground(new java.awt.Color(255, 245, 206));
        jTrice.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTrice.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTrice.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTrice.setEnabled(false);
        getContentPane().add(jTrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 507, 115, 22));

        jTphoneallow.setEditable(false);
        jTphoneallow.setBackground(new java.awt.Color(255, 245, 206));
        jTphoneallow.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTphoneallow.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTphoneallow.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTphoneallow.setEnabled(false);
        getContentPane().add(jTphoneallow, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 532, 115, 22));

        jTclothing.setEditable(false);
        jTclothing.setBackground(new java.awt.Color(255, 245, 206));
        jTclothing.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTclothing.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTclothing.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTclothing.setEnabled(false);
        getContentPane().add(jTclothing, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 557, 115, 22));

        jTsemi.setEditable(false);
        jTsemi.setBackground(new java.awt.Color(255, 245, 206));
        jTsemi.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTsemi.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTsemi.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTsemi.setEnabled(false);
        getContentPane().add(jTsemi, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 582, 115, 22));

        jThourly.setEditable(false);
        jThourly.setBackground(new java.awt.Color(255, 245, 206));
        jThourly.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jThourly.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jThourly.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jThourly.setEnabled(false);
        getContentPane().add(jThourly, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 607, 115, 22));

        jScrollAddress.setEnabled(false);
        jScrollAddress.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N

        jTAddress.setEditable(false);
        jTAddress.setBackground(new java.awt.Color(255, 245, 206));
        jTAddress.setColumns(20);
        jTAddress.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jTAddress.setRows(5);
        jTAddress.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 175, 0), new java.awt.Color(255, 175, 0)));
        jTAddress.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTAddress.setEnabled(false);
        jScrollAddress.setViewportView(jTAddress);

        getContentPane().add(jScrollAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 220, 220, 60));

        jLeeNo1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLeeNo1.setForeground(new java.awt.Color(153, 39, 0));
        jLeeNo1.setText("*");
        getContentPane().add(jLeeNo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 125, -1, -1));

        jLlastName1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLlastName1.setForeground(new java.awt.Color(153, 39, 0));
        jLlastName1.setText("*");
        getContentPane().add(jLlastName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 150, -1, -1));

        jLfirstName1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLfirstName1.setForeground(new java.awt.Color(153, 39, 0));
        jLfirstName1.setText("*");
        getContentPane().add(jLfirstName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 175, -1, -1));

        jLbDay1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLbDay1.setForeground(new java.awt.Color(153, 39, 0));
        jLbDay1.setText("*");
        getContentPane().add(jLbDay1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 200, -1, -1));

        jLaddress1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLaddress1.setForeground(new java.awt.Color(153, 39, 0));
        jLaddress1.setText("*");
        getContentPane().add(jLaddress1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 225, -1, -1));

        jLphoneNo1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLphoneNo1.setForeground(new java.awt.Color(153, 39, 0));
        jLphoneNo1.setText("*");
        getContentPane().add(jLphoneNo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 285, -1, -1));

        jLSSS1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLSSS1.setForeground(new java.awt.Color(153, 39, 0));
        jLSSS1.setText("*");
        getContentPane().add(jLSSS1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 310, -1, -1));

        jLPhilhealth1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLPhilhealth1.setForeground(new java.awt.Color(153, 39, 0));
        jLPhilhealth1.setText("*");
        getContentPane().add(jLPhilhealth1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 335, -1, -1));

        jLTIN1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLTIN1.setForeground(new java.awt.Color(153, 39, 0));
        jLTIN1.setText("*");
        getContentPane().add(jLTIN1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 360, -1, -1));

        jLPagibig1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLPagibig1.setForeground(new java.awt.Color(153, 39, 0));
        jLPagibig1.setText("*");
        jLPagibig1.setToolTipText("");
        getContentPane().add(jLPagibig1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 385, -1, -1));

        jLStatus1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLStatus1.setForeground(new java.awt.Color(153, 39, 0));
        jLStatus1.setText("*");
        getContentPane().add(jLStatus1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 410, -1, -1));

        jLPosition1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLPosition1.setForeground(new java.awt.Color(153, 39, 0));
        jLPosition1.setText("*");
        getContentPane().add(jLPosition1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 435, -1, -1));

        jLSup1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLSup1.setForeground(new java.awt.Color(153, 39, 0));
        jLSup1.setText("*");
        getContentPane().add(jLSup1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 460, -1, -1));

        jLBasic1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLBasic1.setForeground(new java.awt.Color(153, 39, 0));
        jLBasic1.setText("*");
        getContentPane().add(jLBasic1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 485, -1, -1));

        jLRice1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLRice1.setForeground(new java.awt.Color(153, 39, 0));
        jLRice1.setText("*");
        getContentPane().add(jLRice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 510, -1, -1));

        jLphoneAllow1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLphoneAllow1.setForeground(new java.awt.Color(153, 39, 0));
        jLphoneAllow1.setText("*");
        getContentPane().add(jLphoneAllow1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 535, -1, -1));

        jLClothing1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLClothing1.setForeground(new java.awt.Color(153, 39, 0));
        jLClothing1.setText("*");
        getContentPane().add(jLClothing1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 560, -1, -1));

        jLSemi1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLSemi1.setForeground(new java.awt.Color(153, 39, 0));
        jLSemi1.setText("*");
        getContentPane().add(jLSemi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 585, -1, -1));

        jLHourly1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLHourly1.setForeground(new java.awt.Color(153, 39, 0));
        jLHourly1.setText("*");
        getContentPane().add(jLHourly1, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 610, -1, -1));

        jLbdayformat.setFont(new java.awt.Font("Calibri", 0, 10)); // NOI18N
        jLbdayformat.setText("(MM/DD/YYYY)");
        getContentPane().add(jLbdayformat, new org.netbeans.lib.awtextra.AbsoluteConstraints(301, 203, -1, -1));

        btnEdit.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEdit.setText("UPDATE");
        btnEdit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        getContentPane().add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 140, 25));

        btnAdd.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnAdd.setText("ADD");
        btnAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 325, 140, 25));

        btnSave.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnSave.setText("SAVE");
        btnSave.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, 140, 25));

        btnDelete1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnDelete1.setText("DELETE");
        btnDelete1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnDelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, 140, 25));

        btnLeave.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnLeave.setText("LEAVE APPROVAL");
        btnLeave.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLeave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnLeave, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, 160, 25));

        btnPayroll.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnPayroll.setToolTipText("");
        btnPayroll.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPayroll.setLabel("PROCESS PAYROLL");
        btnPayroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayrollActionPerformed(evt);
            }
        });
        getContentPane().add(btnPayroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 535, 160, 25));

        btnSummary.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnSummary.setText("PAYROLL SUMMARY");
        btnSummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSummaryActionPerformed(evt);
            }
        });
        getContentPane().add(btnSummary, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 570, 160, 25));

        adminHomeBG.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        adminHomeBG.setForeground(new java.awt.Color(153, 39, 0));
        adminHomeBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/motorph_gui/AdminHome.jpg"))); // NOI18N
        getContentPane().add(adminHomeBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabelWelcome1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelWelcome1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelWelcome1.setText("Welcome,");
        getContentPane().add(jLabelWelcome1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 60, 20));

        jLabelLoginName1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabelLoginName1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLoginName1.setText("User Name Here");
        getContentPane().add(jLabelLoginName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 180, -1));

        logoutBTN1.setBackground(new java.awt.Color(153, 39, 0));
        logoutBTN1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logoutBTN1.setForeground(new java.awt.Color(255, 255, 255));
        logoutBTN1.setText("Log-out");
        logoutBTN1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(60, 126, 114), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(210, 89, 51)));
        logoutBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBTN1ActionPerformed(evt);
            }
        });
        getContentPane().add(logoutBTN1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 20, 130, 30));

        logoutBTN2.setBackground(new java.awt.Color(153, 39, 0));
        logoutBTN2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logoutBTN2.setForeground(new java.awt.Color(255, 255, 255));
        logoutBTN2.setText("Log-out");
        logoutBTN2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(60, 126, 114), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(210, 89, 51)));
        logoutBTN2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBTN2ActionPerformed(evt);
            }
        });
        getContentPane().add(logoutBTN2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 20, 130, 30));

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

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        jTAddress.setEditable(true);
        jTbasic.setEditable(true);
        jTbirthday.setEditable(true);
        jTclothing.setEditable(true);
        jTfirstName.setEditable(true);
        //jThourly.setEditable(true);
        jTlastName.setEditable(true);
        jTpagibig.setEditable(true);
        jTphilhealth.setEditable(true);
        jTphoneNo.setEditable(true);
        jTphoneallow.setEditable(true);
        jTposition.setEditable(true);
        jTrice.setEditable(true);
        //jTsemi.setEditable(true);
        jTsssNo.setEditable(true);
        jTstatus.setEditable(true);
        jTtin.setEditable(true);
        jsupervisor.setEditable(true);

        // Ensuring components are enabled
        jTAddress.setEnabled(true);
        jTbasic.setEnabled(true);
        jTbirthday.setEnabled(true);
        jTclothing.setEnabled(true);
        jTfirstName.setEnabled(true);
        //jThourly.setEnabled(true);
        jTlastName.setEnabled(true);
        jTpagibig.setEnabled(true);
        jTphilhealth.setEnabled(true);
        jTphoneNo.setEnabled(true);
        jTphoneallow.setEnabled(true);
        jTposition.setEnabled(true);
        jTrice.setEnabled(true);
        //jTsemi.setEnabled(true);
        jTsssNo.setEnabled(true);
        jTstatus.setEnabled(true);
        jTtin.setEnabled(true);
        jsupervisor.setEnabled(true);

        // Debugging print statement to confirm components are set to editable
        System.out.println("All components set to editable and enabled");

        // Refresh the UI
        revalidate();
        repaint();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        
            btnEditActionPerformed(evt);

            // Calculate the next employee number
            int nextEmployeeNumber = getNextEmployeeNumber();

            // Clear all text fields and text area
            clearAllFields();

            // Set the calculated employee number
            jTemployeeNo.setText(String.valueOf(nextEmployeeNumber));    
        }

        private int getNextEmployeeNumber() {
            
            int nextNumber = 1;
            int maxEmployeeNumber = 0;

            try (BufferedReader br = new BufferedReader(new FileReader("motorPHEmployeeList.csv"))) {
                String line;
                boolean isFirstLine = true;
                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Skip header line
                    }
                    String[] data = line.split(",");
                    if (data.length > 0) {
                        // Assuming employee number is in the first column (data[0])
                        try {
                            int employeeNumber = Integer.parseInt(data[0].trim());
                            if (employeeNumber > maxEmployeeNumber) {
                                maxEmployeeNumber = employeeNumber;
                            }
                        } catch (NumberFormatException e) {
                            // Handle cases where data[0] is not a valid integer
                            System.err.println("Invalid employee number: " + data[0]);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            nextNumber = maxEmployeeNumber + 1;
            return nextNumber;
        }

        private void clearAllFields() {
            jTlastName.setText("");
            jTfirstName.setText("");
            jTbirthday.setText("");
            jTAddress.setText("");
            jTphoneNo.setText("");
            jTsssNo.setText("");
            jTphilhealth.setText("");
            jTtin.setText("");
            jTpagibig.setText("");
            jTstatus.setText("");
            jTposition.setText("");
            jsupervisor.setText("");
            jTbasic.setText("");
            jTrice.setText("");
            jTphoneallow.setText("");
            jTclothing.setText("");
            jTsemi.setText("");
            jThourly.setText("");

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
             // Check if any field is blank
                if (isAnyFieldBlank()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Replace commas with semicolons in each field as necessary
                replaceCommasWithSemicolons();

                // Retrieve data from text fields
                String employeeNo = jTemployeeNo.getText().trim();
                String lastName = jTlastName.getText().trim();
                String firstName = jTfirstName.getText().trim();
                String birthday = jTbirthday.getText().trim();
                String address = jTAddress.getText().trim();
                String phoneNo = jTphoneNo.getText().trim();
                String sssNo = jTsssNo.getText().trim();
                String philhealth = jTphilhealth.getText().trim();
                String tin = jTtin.getText().trim();
                String pagibig = jTpagibig.getText().trim();
                String status = jTstatus.getText().trim();
                String position = jTposition.getText().trim();
                String supervisor = jsupervisor.getText().trim();
                String basic = jTbasic.getText().trim();
                String rice = jTrice.getText().trim();
                String phoneAllowance = jTphoneallow.getText().trim();
                String clothing = jTclothing.getText().trim();

                // Calculate semiMonthly and hourlyRate
                double basicValue = Double.parseDouble(basic);
                double semiMonthlyValue = basicValue / 2;
                double hourlyRateValue = basicValue / 21 / 8;

                // Format semiMonthly and hourlyRate to 2 decimal places
                String semiMonthly = String.format("%.2f", semiMonthlyValue);
                String hourlyRate = String.format("%.2f", hourlyRateValue);

                // Update the text fields (if needed)
                jTsemi.setText(semiMonthly);
                jThourly.setText(hourlyRate);

                // Check if employee number already exists
                boolean employeeExists = false;
                int existingRowIndex = -1;

                for (int i = 0; i < csvData.size(); i++) {
                    String[] rowData = csvData.get(i);
                    if (rowData[0].equals(employeeNo)) {
                        employeeExists = true;
                        existingRowIndex = i;
                        break;
                    }
                }

                // Prompt user to confirm saving
                int option;
                if (employeeExists) {
                    option = JOptionPane.showConfirmDialog(this, "Employee number already exists. Do you want to update the record?", "Confirmation", JOptionPane.YES_NO_OPTION);
                } else {
                    option = JOptionPane.showConfirmDialog(this, "Are you sure you want to save?", "Confirmation", JOptionPane.YES_NO_OPTION);
                }

                if (option == JOptionPane.YES_OPTION) {
                    if (employeeExists) {
                        // Update existing row
                        String[] existingRow = csvData.get(existingRowIndex);
                        existingRow[1] = lastName;
                        existingRow[2] = firstName;
                        existingRow[3] = birthday;
                        existingRow[4] = address;
                        existingRow[5] = phoneNo;
                        existingRow[6] = sssNo;
                        existingRow[7] = philhealth;
                        existingRow[8] = tin;
                        existingRow[9] = pagibig;
                        existingRow[10] = status;
                        existingRow[11] = position;
                        existingRow[12] = supervisor;
                        existingRow[13] = basic;
                        existingRow[14] = rice;
                        existingRow[15] = phoneAllowance;
                        existingRow[16] = clothing;
                        existingRow[17] = semiMonthly;
                        existingRow[18] = hourlyRate;
                    } else {
                        // Add new entry
                        String[] newEntry = {employeeNo, lastName, firstName, birthday, address, phoneNo, sssNo, philhealth, tin, pagibig,
                                status, position, supervisor, basic, rice, phoneAllowance, clothing, semiMonthly, hourlyRate};
                        csvData.add(newEntry);
                    }

                    // Update CSV file
                    updateCSVFile();
                    updatePasswordCSVFile(employeeNo, lastName, firstName);

                    // Clear fields and notify user
                    clearAllFields();
                    JOptionPane.showMessageDialog(this, "Data saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Refresh the JTable
                    refreshTable();

                    // Disable text fields
                    disableTextFields();
                }
            }

            private boolean isAnyFieldBlank() {
                return (jTlastName.getText().isEmpty() ||
                        jTfirstName.getText().isEmpty() ||
                        jTbirthday.getText().isEmpty() ||
                        jTAddress.getText().isEmpty() ||
                        jTphoneNo.getText().isEmpty() ||
                        jTsssNo.getText().isEmpty() ||
                        jTphilhealth.getText().isEmpty() ||
                        jTtin.getText().isEmpty() ||
                        jTpagibig.getText().isEmpty() ||
                        jTstatus.getText().isEmpty() ||
                        jTposition.getText().isEmpty() ||
                        jsupervisor.getText().isEmpty() ||
                        jTbasic.getText().isEmpty() ||
                        jTrice.getText().isEmpty() ||
                        jTphoneallow.getText().isEmpty() ||
                        jTclothing.getText().isEmpty()); //||
                        //jTsemi.getText().isEmpty(); ||
                        //jThourly.getText().isEmpty());
            }

            private void replaceCommasWithSemicolons() {
                jTlastName.setText(jTlastName.getText().replace(",", ";"));
                jTfirstName.setText(jTfirstName.getText().replace(",", ";"));
                jTbirthday.setText(jTbirthday.getText().replace(",", ";"));
                jTAddress.setText(jTAddress.getText().replace(",", ";"));
                jTphoneNo.setText(jTphoneNo.getText().replace(",", ";"));
                jTsssNo.setText(jTsssNo.getText().replace(",", ";"));
                jTphilhealth.setText(jTphilhealth.getText().replace(",", ";"));
                jTtin.setText(jTtin.getText().replace(",", ";"));
                jTpagibig.setText(jTpagibig.getText().replace(",", ";"));
                jTstatus.setText(jTstatus.getText().replace(",", ";"));
                jTposition.setText(jTposition.getText().replace(",", ";"));
                jsupervisor.setText(jsupervisor.getText().replace(",", ";"));
                jTbasic.setText(jTbasic.getText().replace(",", ";"));
                jTrice.setText(jTrice.getText().replace(",", ";"));
                jTphoneallow.setText(jTphoneallow.getText().replace(",", ";"));
                jTclothing.setText(jTclothing.getText().replace(",", ";"));
                jTsemi.setText(jTsemi.getText().replace(",", ";"));
                jThourly.setText(jThourly.getText().replace(",", ";"));
            }

            private void updateCSVFile() {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("motorPHEmployeeList.csv"))) {
                    // Write header
                    bw.write("Employee Number,Last Name,First Name,Birthday,Address,Phone Number,SSS Number,Philhealth Number,TIN Number,Pag-ibig Number," +
                            "Status,Position,Supervisor,Basic,Rice,Phone Allowance,Clothing,Semi-Monthly,Hourly Rate");
                    bw.newLine();

                    // Write data
                    for (String[] rowData : csvData) {
                        bw.write(String.join(",", rowData));
                        bw.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error saving data to file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void refreshTable() {
                
            DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
            model.setRowCount(0); // Clear existing rows

            // Reload data from csvData into the table with the correct column order
            for (String[] rowData : csvData) {
                // Ensure rowData matches the order of columns in your original addRow method
                Object[] row = new Object[]{
                    rowData[0], rowData[1], rowData[2], rowData[6], rowData[7], rowData[8], rowData[9]
                };
                model.addRow(row);
            }


    }//GEN-LAST:event_btnSaveActionPerformed

     private void updatePasswordCSVFile(String employeeNo, String lastName, String firstName) {
        String password = "motorPH" + employeeNo;
        String role = "Employee";
        String lowercaseLastName = lastName.toLowerCase();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("password.csv", true))) {
            bw.write(employeeNo + "," + lowercaseLastName + "," + password + "," + role + "," + firstName + "," + lastName);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        int selectedRow = employeeTable.getSelectedRow();
    if (selectedRow == -1) {
        // No row selected, show error message or return
        JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Get employee details from selected row
    DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
    String employeeNumber = model.getValueAt(selectedRow, 0).toString(); // Assuming employee number is in column 0
    String lastName = model.getValueAt(selectedRow, 1).toString(); // Assuming last name is in column 1
    String firstName = model.getValueAt(selectedRow, 2).toString(); // Assuming first name is in column 2

    // Confirmation dialog before deletion
    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete:\nEmployee Number: " + employeeNumber + "\nLast Name: " + lastName + "\nFirst Name: " + firstName, "Confirm Deletion", JOptionPane.YES_NO_OPTION);
    if (choice == JOptionPane.YES_OPTION) {
        // Delete the selected row from the table model
        model.removeRow(selectedRow);

        // Update csvData to reflect the deletion
        csvData.remove(selectedRow); // Assuming csvData is an ArrayList<String[]> holding your CSV rows

        // Save updated csvData back to the CSV file, preserving the header
        saveToCSV("motorPHEmployeeList.csv", true);

        // Delete corresponding row in password.csv
        deleteFromPasswordCSV(employeeNumber);

        // Call refreshTable() to update the table view
        refreshTable();

        // Optionally, you can update your csvData or any other data source here
        JOptionPane.showMessageDialog(null, "Employee deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

private void saveToCSV(String fileName, boolean preserveHeader) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        // Write header if needed
        if (preserveHeader) {
            writer.write("Employee Number,Last Name,First Name,Birthday,Address,Phone Number,SSS Number,Philhealth Number,TIN Number,Pag-ibig Number,Status,Position,Supervisor,Basic,Rice,Phone Allowance,Clothing,Semi-Monthly,Hourly Rate");
            writer.newLine();
        }

        // Write each row in csvData to the file
        for (String[] rowData : csvData) {
            writer.write(String.join(",", rowData));
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to save changes to CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void deleteFromPasswordCSV(String employeeNumber) {
    List<String[]> passwordData = new ArrayList<>();

    // Read the password.csv file and load data into passwordData
    try (BufferedReader br = new BufferedReader(new FileReader("password.csv"))) {
        String line;
        boolean isFirstLine = true;
        while ((line = br.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                passwordData.add(line.split(",")); // Add the header row
                continue;
            }
            String[] rowData = line.split(",");
            if (!rowData[0].equals(employeeNumber)) {
                passwordData.add(rowData); // Add rows that don't match the employee number
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Write the updated passwordData back to the password.csv file
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("password.csv"))) {
        for (String[] rowData : passwordData) {
            bw.write(String.join(",", rowData));
            bw.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

}

private void saveToCSV(String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        // Write header if needed
        // writer.write("Employee Number,Last Name,First Name,...");

        // Write each row in csvData to the file
        for (String[] rowData : csvData) {
            writer.write(String.join(",", rowData));
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to save changes to CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btnLeaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaveActionPerformed
               System.out.println("Redirecting to AdminLeaveApproval with username: " + username);
                new AdminLeaveApproval(username).setVisible(true);
                this.dispose();
    }//GEN-LAST:event_btnLeaveActionPerformed

    private void btnSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSummaryActionPerformed
                this.dispose();

                // Launch the new AdminLeaveApproval frame
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new AdminPaySummary().setVisible(true);
                    }
                });
    }//GEN-LAST:event_btnSummaryActionPerformed

    private void btnPayrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayrollActionPerformed
                this.dispose();

                // Launch the new AdminLeaveApproval frame
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new AdminPayroll(username).setVisible(true);
                    }
                });
    }//GEN-LAST:event_btnPayrollActionPerformed

    private void logoutBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBTN1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutBTN1ActionPerformed

    private void logoutBTN2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBTN2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutBTN2ActionPerformed

    private void applyDocumentFilters() {
    // Allow digits and /
    jTbirthday.setDocument(new PlainDocument());
    ((PlainDocument) jTbirthday.getDocument()).setDocumentFilter(new CustomDocumentFilter("0123456789/"));

    // Allow digits and -
    String digitsAndDash = "0123456789-";
    jTphoneNo.setDocument(new PlainDocument());
    ((PlainDocument) jTphoneNo.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDash));
    jTsssNo.setDocument(new PlainDocument());
    ((PlainDocument) jTsssNo.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDash));
    jTphilhealth.setDocument(new PlainDocument());
    ((PlainDocument) jTphilhealth.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDash));
    jTtin.setDocument(new PlainDocument());
    ((PlainDocument) jTtin.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDash));
    jTpagibig.setDocument(new PlainDocument());
    ((PlainDocument) jTpagibig.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDash));

    // Allow digits and .
    String digitsAndDot = "0123456789.";
    jTbasic.setDocument(new PlainDocument());
    ((PlainDocument) jTbasic.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
    jTrice.setDocument(new PlainDocument());
    ((PlainDocument) jTrice.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
    jTphoneallow.setDocument(new PlainDocument());
    ((PlainDocument) jTphoneallow.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
    jTclothing.setDocument(new PlainDocument());
    ((PlainDocument) jTclothing.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
    jTsemi.setDocument(new PlainDocument());
    ((PlainDocument) jTsemi.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
    jThourly.setDocument(new PlainDocument());
    ((PlainDocument) jThourly.getDocument()).setDocumentFilter(new CustomDocumentFilter(digitsAndDot));
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
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AdminHome("username").setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adminHomeBG;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnLeave;
    private javax.swing.JButton btnPayroll;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSummary;
    private javax.swing.JTable employeeTable;
    private javax.swing.JLabel jLBasic;
    private javax.swing.JLabel jLBasic1;
    private javax.swing.JLabel jLClothing;
    private javax.swing.JLabel jLClothing1;
    private javax.swing.JLabel jLHourly;
    private javax.swing.JLabel jLHourly1;
    private javax.swing.JLabel jLPagibig;
    private javax.swing.JLabel jLPagibig1;
    private javax.swing.JLabel jLPhilhealth;
    private javax.swing.JLabel jLPhilhealth1;
    private javax.swing.JLabel jLPosition;
    private javax.swing.JLabel jLPosition1;
    private javax.swing.JLabel jLRice;
    private javax.swing.JLabel jLRice1;
    private javax.swing.JLabel jLSSS;
    private javax.swing.JLabel jLSSS1;
    private javax.swing.JLabel jLSemi;
    private javax.swing.JLabel jLSemi1;
    private javax.swing.JLabel jLStatus;
    private javax.swing.JLabel jLStatus1;
    private javax.swing.JLabel jLSup;
    private javax.swing.JLabel jLSup1;
    private javax.swing.JLabel jLTIN;
    private javax.swing.JLabel jLTIN1;
    private javax.swing.JLabel jLabelLoginName;
    private javax.swing.JLabel jLabelLoginName1;
    private javax.swing.JLabel jLabelWelcome;
    private javax.swing.JLabel jLabelWelcome1;
    private javax.swing.JLabel jLaddress;
    private javax.swing.JLabel jLaddress1;
    private javax.swing.JLabel jLbDay;
    private javax.swing.JLabel jLbDay1;
    private javax.swing.JLabel jLbdayformat;
    private javax.swing.JLabel jLeeNo;
    private javax.swing.JLabel jLeeNo1;
    private javax.swing.JLabel jLfirstName;
    private javax.swing.JLabel jLfirstName1;
    private javax.swing.JLabel jLlastName;
    private javax.swing.JLabel jLlastName1;
    private javax.swing.JLabel jLphoneAllow;
    private javax.swing.JLabel jLphoneAllow1;
    private javax.swing.JLabel jLphoneNo;
    private javax.swing.JLabel jLphoneNo1;
    private javax.swing.JScrollPane jScrollAddress;
    private javax.swing.JScrollPane jScrollPaneTable;
    private javax.swing.JTextArea jTAddress;
    private javax.swing.JTextField jTbasic;
    private javax.swing.JTextField jTbirthday;
    private javax.swing.JTextField jTclothing;
    private javax.swing.JTextField jTemployeeNo;
    private javax.swing.JTextField jTfirstName;
    private javax.swing.JTextField jThourly;
    private javax.swing.JTextField jTlastName;
    private javax.swing.JTextField jTpagibig;
    private javax.swing.JTextField jTphilhealth;
    private javax.swing.JTextField jTphoneNo;
    private javax.swing.JTextField jTphoneallow;
    private javax.swing.JTextField jTposition;
    private javax.swing.JTextField jTrice;
    private javax.swing.JTextField jTsemi;
    private javax.swing.JTextField jTsssNo;
    private javax.swing.JTextField jTstatus;
    private javax.swing.JTextField jTtin;
    private javax.swing.JTextField jsupervisor;
    private javax.swing.JButton logoutBTN;
    private javax.swing.JButton logoutBTN1;
    private javax.swing.JButton logoutBTN2;
    // End of variables declaration//GEN-END:variables

    private void setIconImage() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Logo.jpg")));
    }
}
