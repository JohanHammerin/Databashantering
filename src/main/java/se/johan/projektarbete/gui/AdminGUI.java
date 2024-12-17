package se.johan.projektarbete.gui;

import se.johan.projektarbete.util.WorkRoleAndEmployeeDAO;
import se.johan.projektarbete.util.WorkRoleAndEmployeeDAOImpl;
import se.johan.projektarbete.logic.Security;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;
import java.util.Map;

import java.time.LocalDate;

import static se.johan.projektarbete.util.WorkRoleAndEmployeeDAOImpl.checkForDuplicateTitle;


public class AdminGUI {
    static Connection conn = null;
    static PreparedStatement pstmt = null;
    static ResultSet rs = null;
    static WorkRoleAndEmployeeDAO workRoleAndEmployeeDAO = new WorkRoleAndEmployeeDAOImpl();
    static CardLayout cardLayout = new CardLayout();
    static JPanel mainPanel = new JPanel(cardLayout); // Container för alla paneler
    static JFrame mainFrame = new JFrame("Jobb");
    static LocalDate currentDate = LocalDate.now();
    static JTable workRoleTable = new JTable();
    static JTable employeeTable = new JTable();
    //WorkRole
    static JComboBox<String> roleComboBoxCreateNewEmployee = new JComboBox<>();
    static JComboBox<String> roleComboBoxDeleteWorkRole = new JComboBox<>();
    static JComboBox<String> roleComboBoxUpdateWorkRole = new JComboBox<>();
    static JComboBox<String> roleComboBoxUpdateEmployee = new JComboBox<>();
    //Employee
    static JComboBox<String> employeeComboBoxUpdateEmployee = new JComboBox<>();
    static JComboBox<String> employeeComboBoxDeleteEmployee = new JComboBox<>();

    static JLabel errorLabel = new JLabel(" "); // Tomt mellanrum reserverar utrymme

    // Typsnitt
    static Font buttonFont = new Font("SF Pro", Font.BOLD, 14);
    static Font mainFont = new Font("SF Pro", Font.PLAIN, 14);

    public static void createAdminGUI() {

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 400);

        // UpperPanel
        JPanel upperPanel = new JPanel();
        UtilGUI.createUpperPanel(upperPanel);

        // LowerPanel
        JPanel lowerPanel = new JPanel(new BorderLayout());
        errorLabel.setFont(mainFont);
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lowerPanel.add(errorLabel, BorderLayout.CENTER);

        // WorkRoleHeaderButton
        JButton workRoleHeaderButton = new JButton("Roller");
        workRoleHeaderButton.addActionListener(_ -> cardLayout.show(mainPanel, "workRolePanel"));
        UtilGUI.createHeaderButton(workRoleHeaderButton, upperPanel, buttonFont);

        // Spacing
        JLabel spacingHeaderLabel = new JLabel("|               |");
        UtilGUI.createHeaderLabel(spacingHeaderLabel, upperPanel, buttonFont);

        // EmployeeHeaderButton
        JButton employeeHeaderButton = new JButton("Anställda");
        employeeHeaderButton.addActionListener(_ -> cardLayout.show(mainPanel, "employeePanel"));
        UtilGUI.createHeaderButton(employeeHeaderButton, upperPanel, buttonFont);

        // Log Out Button
        JButton logOutButton = new JButton("Logga ut");
        logOutButton.addActionListener(_ -> LoginGUI.createLoginGui());
        logOutButton.setFont(buttonFont);
        lowerPanel.add(logOutButton, BorderLayout.SOUTH);

        // Lägg till paneler i mainPanel och hantera byten med CardLayout
        mainPanel.add(showWorkRolePanel(), "workRolePanel");
        mainPanel.add(showEmployeePanel(), "employeePanel");
        mainPanel.add(createNewWorkRolePanel(), "createWorkRolePanel");
        mainPanel.add(showAllWorkRolesPanel(), "showAllWorkRolesPanel");
        mainPanel.add(createNewEmployeePanel(), "createNewEmployeePanel");
        mainPanel.add(deleteWorkRolePanel(), "deleteWorkRolePanel");
        mainPanel.add(showAllEmployeesPanel(), "showAllEmployeesPanel");
        mainPanel.add(deleteEmployee(), "deleteEmployeePanel");
        mainPanel.add(updateWorkRole(), "updateWorkRolePanel");
        mainPanel.add(updateEmployee(), "updateEmployeePanel");

        // Lägg till alla paneler i mainFrame
        mainFrame.add(upperPanel, BorderLayout.NORTH);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.add(lowerPanel, BorderLayout.SOUTH);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    /**
     * Skapar panelen för Work Role
     */
    private static JPanel showWorkRolePanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 1, 10, 10));

        // Buttons
        JButton createNewWorkRoleButton = new JButton("Ny roll");
        createNewWorkRoleButton.addActionListener(_ -> {
            updateMethods();
            cardLayout.show(mainPanel, "createWorkRolePanel");
        });
        centerPanel.add(createNewWorkRoleButton);

        JButton deleteWorkRoleButton = new JButton("Ta bort roll");
        deleteWorkRoleButton.addActionListener(_ -> {
            updateMethods();
            cardLayout.show(mainPanel, "deleteWorkRolePanel");
        });
        centerPanel.add(deleteWorkRoleButton);

        JButton updateWorkRoleButton = new JButton("Uppdatera roll");
        updateWorkRoleButton.addActionListener(_ -> {
            updateMethods();
            cardLayout.show(mainPanel, "updateWorkRolePanel");
        });
        centerPanel.add(updateWorkRoleButton);

        JButton showAllWorkRolesButton = new JButton("Visa alla roller");
        updateMethods();
        showAllWorkRolesButton.addActionListener(_ -> {
            updateMethods();
            cardLayout.show(mainPanel, "showAllWorkRolesPanel");
        });
        centerPanel.add(showAllWorkRolesButton);
        return centerPanel;
    }

    /**
     * Skapar panelen för Employees
     */
    private static JPanel showEmployeePanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 1, 10, 10));


        // Buttons
        JButton createNewEmployeeButton = new JButton("Ny kollega");
        createNewEmployeeButton.addActionListener(_ -> {
            updateMethods();
            cardLayout.show(mainPanel, "createNewEmployeePanel");
        });
        centerPanel.add(createNewEmployeeButton);

        JButton deleteEmployeeButton = new JButton("Ta bort kollega");
        deleteEmployeeButton.addActionListener(_ -> {
            updateMethods();
            cardLayout.show(mainPanel, "deleteEmployeePanel");
        });
        centerPanel.add(deleteEmployeeButton);

        JButton updateEmployeeButton = new JButton("Uppdatera kollega");
        updateEmployeeButton.addActionListener(_ -> {
            updateMethods();
            cardLayout.show(mainPanel, "updateEmployeePanel");
        });
        centerPanel.add(updateEmployeeButton);

        JButton showAllEmployeesButton = new JButton("Visa alla kollegor");
        showAllEmployeesButton.addActionListener(_ -> {
            updateMethods();
            cardLayout.show(mainPanel, "showAllEmployeesPanel");
        });
        centerPanel.add(showAllEmployeesButton);

        return centerPanel;
    }


    private static JPanel createNewWorkRolePanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 1, 10, 10));

        // Jobbtitel
        JLabel titleLabel = new JLabel("Jobbtitel");
        titleLabel.setFont(mainFont);
        centerPanel.add(titleLabel);

        JTextField titleTextField = new JTextField();
        titleTextField.setFont(mainFont);
        centerPanel.add(titleTextField);

        // Arbetsbeskrivning
        JLabel workDescriptionLabel = new JLabel("Arbetsbeskrivning");
        workDescriptionLabel.setFont(mainFont);
        centerPanel.add(workDescriptionLabel);

        JTextField workDescriptionTextField = new JTextField();
        workDescriptionTextField.setFont(mainFont);
        centerPanel.add(workDescriptionTextField);

        // Lön
        JLabel salaryLabel = new JLabel("Lön");
        salaryLabel.setFont(mainFont);
        centerPanel.add(salaryLabel);

        JTextField salaryTextField = new JTextField();
        salaryTextField.setFont(mainFont);
        centerPanel.add(salaryTextField);

        // Skapa en tillbaka knapp
        JButton returnButton = new JButton("Tillbaka");
        returnButton.setFont(buttonFont);
        returnButton.addActionListener(_ -> {
            errorLabel.setText("");
            titleTextField.setText("");
            workDescriptionTextField.setText("");
            salaryTextField.setText("");
            cardLayout.show(mainPanel, "workRolePanel");
        });
        centerPanel.add(returnButton);

        // Skapa Spara-knapp
        JButton saveButton = new JButton("Spara");
        saveButton.setFont(buttonFont);
        saveButton.addActionListener(_ -> {
            String title = titleTextField.getText();
            String workDescription = workDescriptionTextField.getText();
            String salary = salaryTextField.getText();
            Date creationDate = Date.valueOf(currentDate);


            if (Security.checkForBlancField(title) || Security.checkForBlancField(workDescription) || Security.checkForBlancField(salary)) {
                errorLabel.setForeground(Color.ORANGE);
                errorLabel.setText("Ett/flera fält är tomma!");
            } else if (Security.checkForThreat(title) || Security.checkForThreat(workDescription) || Security.checkForThreat(salary)) {
                errorLabel.setForeground(Color.RED);
                errorLabel.setText("Inga luriga tecken");
            } else if (checkForDuplicateTitle(conn, pstmt, rs, title)) {
                errorLabel.setForeground(Color.RED);
                errorLabel.setText("Denna jobbtitel finns redan!");
            } else {
                try {
                    double salaryValue = Double.parseDouble(salary);
                    if (salaryValue < 0) {
                        errorLabel.setForeground(Color.ORANGE);
                        errorLabel.setText("Lönen får inte vara mindre än 0");
                    } else {
                        // Skapa ny arbetsroll
                        workRoleAndEmployeeDAO.createNewWorkRole(conn, pstmt, title, workDescription, salaryValue, creationDate);
                        JOptionPane.showMessageDialog(null, "Ny arbetsroll tillagd");
                        titleTextField.setText("");
                        workDescriptionTextField.setText("");
                        salaryTextField.setText("");


                        // Uppdatera alla arbetsroller efter skapandet
                        updateWorkRolesTable();

                        cardLayout.show(mainPanel, "workRolePanel");
                        errorLabel.setText(" "); // Återställ felmeddelandet
                        updateRoleComboBox(roleComboBoxCreateNewEmployee);
                    }
                } catch (NumberFormatException ex) {
                    errorLabel.setForeground(Color.ORANGE);
                    errorLabel.setText("Lönen måste vara ett nummer!");
                }
            }
            updateMainFrame();
        });

        centerPanel.add(saveButton);
        return centerPanel;
    }

    private static JPanel showAllWorkRolesPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(workRoleTable); // Lägg tabellen i en scrollpanel
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Tillbaka");
        backButton.setFont(buttonFont);
        backButton.addActionListener(_ -> cardLayout.show(mainPanel, "workRolePanel"));
        centerPanel.add(backButton, BorderLayout.SOUTH);

        // Hämta data och fyll tabellen
        try {
            updateWorkRolesTable();  // Uppdatera tabellen med senaste data
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kunde inte hämta arbetsroller.");
        }

        return centerPanel;
    }

    private static void updateWorkRolesTable() {
        workRoleTable.setModel(new DefaultTableModel());
        List<Map<String, Object>> roles = workRoleAndEmployeeDAO.showAllWorkRoles(conn, pstmt, rs);
        String[] columns = {"Titel", "Lön", "Skapad"};
        String[][] tableData = new String[roles.size()][columns.length];

        for (int i = 0; i < roles.size(); i++) {
            Map<String, Object> role = roles.get(i);

            // Säkerställ att vi inte försöker anropa toString på null
            tableData[i][0] = role.get("title") != null ? (String) role.get("title") : "Titel saknas";
            tableData[i][1] = role.get("salary") != null ? role.get("salary").toString() : "Lön saknas";
            tableData[i][2] = role.get("creation_date") != null ? role.get("creation_date").toString() : "Datum saknas";
        }

        // Skapa en TableModel och gör alla celler skrivskyddade
        DefaultTableModel model = new DefaultTableModel(tableData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Sätter alla celler som icke-redigerbara
            }
        };

        workRoleTable.setModel(model); // Uppdaterar tabellen med den nya modellen
    }


    private static JPanel showAllEmployeesPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(employeeTable); // Lägg tabellen i en scrollpanel
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Tillbaka");
        backButton.setFont(buttonFont);
        backButton.addActionListener(_ -> cardLayout.show(mainPanel, "employeePanel")
        );
        centerPanel.add(backButton, BorderLayout.SOUTH);

        // Hämta data och fyll tabellen
        try {
            updateEmployeesTable();  // Uppdatera tabellen med senaste data
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kunde inte hämta anställda.");
        }

        return centerPanel;
    }

    private static JPanel createNewEmployeePanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(7, 1, 10, 10)); // Anpassad layout för fler komponenter

        // Namn
        JLabel fullNameLabel = new JLabel("Namn");
        fullNameLabel.setFont(mainFont);
        centerPanel.add(fullNameLabel);

        JTextField fullNameTextField = new JTextField();
        fullNameTextField.setFont(mainFont);
        centerPanel.add(fullNameTextField);

        // Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(mainFont);
        centerPanel.add(emailLabel);

        JTextField emailTextField = new JTextField();
        emailTextField.setFont(mainFont);
        centerPanel.add(emailTextField);

        // Lösenord
        JLabel employeePasswordLabel = new JLabel("Lösenord");
        employeePasswordLabel.setFont(mainFont);
        centerPanel.add(employeePasswordLabel);

        JTextField employeePasswordTextField = new JTextField();
        employeePasswordTextField.setFont(mainFont);
        centerPanel.add(employeePasswordTextField);

        // Roll
        JLabel roleLabel = new JLabel("Arbetsroll");
        roleLabel.setFont(mainFont);
        centerPanel.add(roleLabel);

        centerPanel.add(roleComboBoxCreateNewEmployee);

        // Tillbaka-knapp
        JButton returnButton = new JButton("Tillbaka");
        returnButton.setFont(buttonFont);
        returnButton.addActionListener(_ -> {
            errorLabel.setText("");
            fullNameTextField.setText("");
            emailTextField.setText("");
            employeePasswordTextField.setText("");
            cardLayout.show(mainPanel, "employeePanel");
        });
        centerPanel.add(returnButton);

        // Spara-knapp
        JButton saveButton = new JButton("Spara");
        saveButton.setFont(buttonFont);
        saveButton.addActionListener(_ -> {
            String fullName = fullNameTextField.getText();
            String email = emailTextField.getText();
            String employeePassword = employeePasswordTextField.getText();
            String selectedRole = (String) roleComboBoxCreateNewEmployee.getSelectedItem();


            if (Security.checkForBlancField(fullName) || Security.checkForBlancField(email) || Security.checkForBlancField(employeePassword)) {
                errorLabel.setForeground(Color.ORANGE);
                errorLabel.setText("Ett/flera fält är tomma!");
            } else if (Security.checkForThreat(fullName) || Security.checkForThreat(email) || Security.checkForThreat(employeePassword)) {
                errorLabel.setForeground(Color.RED);
                errorLabel.setText("Inga luriga tecken");
            } else {

                try {
                    // Skapa ny anställd
                    assert selectedRole != null;
                    workRoleAndEmployeeDAO.createNewEmployee(conn, pstmt, fullName, email, employeePassword, selectedRole);
                    JOptionPane.showMessageDialog(null, "Ny anställd tillagd");
                    fullNameTextField.setText("");
                    emailTextField.setText("");
                    employeePasswordTextField.setText("");
                    errorLabel.setText(" "); // Återställ felmeddelandet


                    // Uppdatera tabellen med anställda
                    updateEmployeesTable();

                    cardLayout.show(mainPanel, "employeePanel");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    errorLabel.setForeground(Color.RED);
                    errorLabel.setText("Kunde inte lägga till anställd.");
                }
            }
            updateMainFrame();
        });
        centerPanel.add(saveButton);

        return centerPanel;
    }


    private static void updateEmployeesTable() {
        employeeTable.setModel(new DefaultTableModel());

        // Hämta de senaste anställda
        List<Map<String, Object>> employees = workRoleAndEmployeeDAO.showAllEmployees(conn, pstmt, rs);
        String[] columns = {"Namn", "Email", "Roll"};
        String[][] tableData = new String[employees.size()][columns.length];

        for (int i = 0; i < employees.size(); i++) {
            Map<String, Object> employee = employees.get(i);
            tableData[i][0] = employee.get("full_name") != null ? (String) employee.get("full_name") : "Namn saknas";
            tableData[i][1] = employee.get("email") != null ? (String) employee.get("email") : "Email saknas";
            tableData[i][2] = employee.get("role_title") != null ? (String) employee.get("role_title") : "Roll saknas";
        }

        // Skapa en TableModel och gör alla celler skrivskyddade
        DefaultTableModel model = new DefaultTableModel(tableData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Sätter alla celler som icke-redigerbara
            }
        };

        employeeTable.setModel(model); // Uppdaterar tabellen med den nya modellen
    }




    private static void updateRoleComboBox(JComboBox<String> roleComboBox) {
        try {
            roleComboBox.removeAllItems();
            List<Map<String, Object>> roles = workRoleAndEmployeeDAO.showAllWorkRoles(conn, pstmt, rs);
            for (Map<String, Object> role : roles) {
                String roleTitle = role.get("title") != null ? (String) role.get("title") : "Okänd roll";
                roleComboBox.addItem(roleTitle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kunde inte hämta arbetsroller.");
        }
    }

    private static void updateEmployeeComboBox(JComboBox<String> employeeComboBox) {
        try {
            employeeComboBox.removeAllItems();
            List<Map<String, Object>> roles = workRoleAndEmployeeDAO.showAllEmployees(conn, pstmt, rs);
            for (Map<String, Object> role : roles) {
                String employeeName = role.get("full_name") != null ? (String) role.get("full_name") : "Okänd roll";
                employeeComboBox.addItem(employeeName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kunde inte hämta anställda.");
        }
    }


    private static JPanel deleteWorkRolePanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 1, 10, 10));

        //roll som ska tas bort
        JLabel workRoleLabel = new JLabel("Roller:");
        workRoleLabel.setFont(mainFont);
        centerPanel.add(workRoleLabel);

        centerPanel.add(roleComboBoxDeleteWorkRole);


        JButton returnButton = new JButton("Tillbaka");
        returnButton.setFont(buttonFont);
        returnButton.addActionListener(_ -> cardLayout.show(mainPanel, "workRolePanel"));
        centerPanel.add(returnButton);

        JButton deleteButton = new JButton("Radera");
        deleteButton.setFont(buttonFont);
        deleteButton.addActionListener(_ -> {

            String selectedRole = (String) roleComboBoxDeleteWorkRole.getSelectedItem();
            assert selectedRole != null;
            if (selectedRole.equals("Admin")) {
                errorLabel.setForeground(Color.RED);
                errorLabel.setText("Admin får inte raderas!");
            } else {
                try {

                    workRoleAndEmployeeDAO.deleteWorkRole(conn, pstmt, selectedRole);
                    errorLabel.setText("");
                    JOptionPane.showMessageDialog(null, selectedRole + " har tagits bort!");
                    updateMethods();
                    cardLayout.show(mainPanel, "workRolePanel");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        centerPanel.add(deleteButton);

        centerPanel.add(new JLabel());
        centerPanel.add(new JLabel());
        centerPanel.add(new JLabel());
        centerPanel.add(new JLabel());
        return centerPanel;

    }


    private static JPanel deleteEmployee() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel employeeNamesLabel = new JLabel("Anställda");
        employeeNamesLabel.setFont(mainFont);
        centerPanel.add(employeeNamesLabel);

        //ComboBox
        centerPanel.add(employeeComboBoxDeleteEmployee);

        JButton returnButton = new JButton("Tillbaka");
        returnButton.setFont(buttonFont);
        returnButton.addActionListener(_ -> cardLayout.show(mainPanel, "employeePanel"));
        centerPanel.add(returnButton);

        JButton deleteButton = new JButton("Ta bort");
        deleteButton.setFont(buttonFont);
        deleteButton.addActionListener(_ -> {
            String selectedEmployee = (String) employeeComboBoxDeleteEmployee.getSelectedItem();
            workRoleAndEmployeeDAO.deleteEmployee(conn, pstmt, selectedEmployee);
            JOptionPane.showMessageDialog(null, selectedEmployee + " har tagits bort!");
            cardLayout.show(mainPanel, "employeePanel");
        });
        centerPanel.add(deleteButton);


        centerPanel.add(new JLabel());
        centerPanel.add(new JLabel());
        centerPanel.add(new JLabel());
        centerPanel.add(new JLabel());


        return centerPanel;
    }


    private static JPanel updateWorkRole() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 1, 10, 10));


        JLabel workRoleLabel = new JLabel("Roller");
        workRoleLabel.setFont(mainFont);
        centerPanel.add(workRoleLabel);

        centerPanel.add(roleComboBoxUpdateWorkRole);


        // Arbetsbeskrivning
        JLabel workDescriptionLabel = new JLabel("Arbetsbeskrivning");
        workDescriptionLabel.setFont(mainFont);

        centerPanel.add(workDescriptionLabel);

        JTextArea workDescriptionTextArea = new JTextArea();
        workDescriptionTextArea.setFont(mainFont);
        centerPanel.add(workDescriptionTextArea);

        // Lön
        JLabel salaryLabel = new JLabel("Lön");
        salaryLabel.setFont(mainFont);
        centerPanel.add(salaryLabel);

        JTextField salaryTextField = new JTextField();
        salaryTextField.setFont(mainFont);
        centerPanel.add(salaryTextField);

        // Skapa en tillbaka knapp
        JButton returnButton = new JButton("Tillbaka");
        returnButton.setFont(buttonFont);
        returnButton.addActionListener(_ -> {
            errorLabel.setText("");
            workDescriptionTextArea.setText("");
            salaryTextField.setText("");
            cardLayout.show(mainPanel, "workRolePanel");
        });
        centerPanel.add(returnButton);

        // Skapa Spara-knapp
        JButton saveButton = new JButton("Spara");
        saveButton.setFont(buttonFont);
        saveButton.addActionListener(_ -> {
            String workDescription = workDescriptionTextArea.getText();
            String salary = salaryTextField.getText();
            Date creationDate = Date.valueOf(currentDate);


            if (Security.checkForBlancField(workDescription) || Security.checkForBlancField(salary)) {
                errorLabel.setForeground(Color.ORANGE);
                errorLabel.setText("Ett/flera fält är tomma!");
            } else if (Security.checkForThreat(workDescription) || Security.checkForThreat(salary)) {
                errorLabel.setForeground(Color.RED);
                errorLabel.setText("Inga luriga tecken");
            } else {
                try {
                    double salaryValue = Double.parseDouble(salary);
                    if (salaryValue < 0) {
                        errorLabel.setForeground(Color.ORANGE);
                        errorLabel.setText("Lönen får inte vara mindre än 0");
                    } else {
                        // Uppdatera ny arbetsroll
                        String selectedRole = (String) roleComboBoxUpdateWorkRole.getSelectedItem();
                        workRoleAndEmployeeDAO.updateWorkRole(conn, pstmt, workDescription, salaryValue, creationDate, selectedRole);
                        updateMethods();
                        JOptionPane.showMessageDialog(null, "Arbetsroll uppdaterad");
                        workDescriptionTextArea.setText("");
                        salaryTextField.setText("");


                        cardLayout.show(mainPanel, "workRolePanel");
                        errorLabel.setText(" "); // Återställ felmeddelandet
                    }
                } catch (NumberFormatException ex) {
                    errorLabel.setForeground(Color.ORANGE);
                    errorLabel.setText("Lönen måste vara ett nummer!");
                }
            }
            updateMainFrame();
        });

        centerPanel.add(saveButton);


        return centerPanel;
    }

    private static JPanel updateEmployee() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(7, 1, 10, 10));

        JLabel employeeNameLabel = new JLabel("Anställda");
        employeeNameLabel.setFont(mainFont);
        centerPanel.add(employeeNameLabel);

        centerPanel.add(employeeComboBoxUpdateEmployee);

        // Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(mainFont);
        centerPanel.add(emailLabel);

        JTextField emailTextField = new JTextField();
        emailTextField.setFont(mainFont);
        centerPanel.add(emailTextField);

        // Lösenord
        JLabel employeePasswordLabel = new JLabel("Lösenord");
        employeePasswordLabel.setFont(mainFont);
        centerPanel.add(employeePasswordLabel);

        JTextField employeePasswordTextField = new JTextField();
        employeePasswordTextField.setFont(mainFont);
        centerPanel.add(employeePasswordTextField);

        JLabel workRoleLabel = new JLabel("Roller");
        workRoleLabel.setFont(mainFont);
        centerPanel.add(workRoleLabel);

        centerPanel.add(roleComboBoxUpdateEmployee);


        // Tillbaka-knapp
        JButton returnButton = new JButton("Tillbaka");
        returnButton.setFont(buttonFont);
        returnButton.addActionListener(_ -> {
            errorLabel.setText("");
            emailTextField.setText("");
            employeePasswordTextField.setText("");
            cardLayout.show(mainPanel, "employeePanel");
        });
        centerPanel.add(returnButton);

        // Spara-knapp
        JButton saveButton = new JButton("Spara");
        saveButton.setFont(buttonFont);
        saveButton.addActionListener(_ -> {
            String email = emailTextField.getText();
            String employeePassword = employeePasswordTextField.getText();
            String selectedEmployee = (String) employeeComboBoxUpdateEmployee.getSelectedItem();
            String selectedRole = (String) roleComboBoxUpdateWorkRole.getSelectedItem();


            if (Security.checkForBlancField(email) || Security.checkForBlancField(employeePassword)) {
                errorLabel.setForeground(Color.ORANGE);
                errorLabel.setText("Ett/flera fält är tomma!");
            } else if (Security.checkForThreat(email) || Security.checkForThreat(employeePassword)) {
                errorLabel.setForeground(Color.RED);
                errorLabel.setText("Inga luriga tecken");
            } else {

                try {
                    // Skapa ny anställd
                    assert selectedEmployee != null;
                    workRoleAndEmployeeDAO.updateEmployee(conn, pstmt, rs, email, employeePassword, selectedEmployee, selectedRole);
                    JOptionPane.showMessageDialog(null, "Anställd uppdaterad.");
                    emailTextField.setText("");
                    employeePasswordTextField.setText("");
                    errorLabel.setText(" "); // Återställ felmeddelandet


                    // Uppdatera tabellen med anställda
                    updateMethods();

                    cardLayout.show(mainPanel, "employeePanel");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    errorLabel.setForeground(Color.RED);
                    errorLabel.setText("Kunde inte lägga till anställd.");
                }
            }
            updateMainFrame();
        });
        centerPanel.add(saveButton);
        return centerPanel;
    }


    private static void updateMainFrame() {
        mainFrame.validate();
        mainFrame.repaint();
    }

    private static void updateMethods() {
        updateRoleComboBox(roleComboBoxCreateNewEmployee);
        updateRoleComboBox(roleComboBoxDeleteWorkRole);
        updateEmployeeComboBox(employeeComboBoxDeleteEmployee);
        updateRoleComboBox(roleComboBoxUpdateWorkRole);
        updateEmployeeComboBox(employeeComboBoxUpdateEmployee);
        updateRoleComboBox(roleComboBoxUpdateEmployee);
        updateEmployeesTable();
        updateWorkRolesTable();
    }
}