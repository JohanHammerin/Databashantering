package se.johan.projektarbete.gui;

import se.johan.projektarbete.util.WorkRoleAndEmployeeDAOImpl;

import java.awt.*;
import java.sql.*;
import java.util.Map;
import javax.swing.*;

public class EmployeeGUI {
    static Connection conn = null;
    static PreparedStatement pstmt = null;
    static ResultSet rs = null;

    public static void createEmployeeGUI(int employeeId) {
        WorkRoleAndEmployeeDAOImpl workRoleDAO = new WorkRoleAndEmployeeDAOImpl();
        Map<String, String> infoMap = workRoleDAO.showWorkRole(conn, pstmt, rs, employeeId);


        JFrame frame = new JFrame("Jobb");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        //UpperPanel
        JPanel upperPanel = new JPanel();
        UtilGUI.createUpperPanel(upperPanel);

        //CenterPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 1, 10, 10));

        //LowerPanel
        JPanel lowerPanel = new JPanel();
        UtilGUI.createLowerPanel(lowerPanel);

        //Typsnitt
        Font buttonFont = new Font("SF Pro", Font.BOLD, 14);
        Font mainFont = new Font("SF Pro", Font.PLAIN, 14);

        //HeaderLabel
        JLabel headerLabel = new JLabel("Info");
        UtilGUI.createHeaderLabel(headerLabel, upperPanel, buttonFont);

        //fullName
        JLabel fullNameLabel = new JLabel("Namn: " + infoMap.get("full_name"));
        fullNameLabel.setFont(mainFont);
        centerPanel.add(fullNameLabel);


        //title
        JLabel titleLabel = new JLabel("Titel: " + infoMap.get("title"));
        titleLabel.setFont(mainFont);
        centerPanel.add(titleLabel);


        //workDescription - Använd JTextArea istället för JLabel
        JTextArea workDescriptionTextArea = new JTextArea("Jobbeskrivning: " + infoMap.get("work_description"));
        workDescriptionTextArea.setFont(mainFont);
        workDescriptionTextArea.setWrapStyleWord(true); // Sätter så att ord bryts
        workDescriptionTextArea.setLineWrap(true); // Sätter radbrytning
        workDescriptionTextArea.setEditable(false); // Gör den icke-redigerbar
        centerPanel.add(workDescriptionTextArea);


        //salary
        JLabel salaryLabel = new JLabel("Lön: " + infoMap.get("salary") + "kr/mån");
        salaryLabel.setFont(mainFont);
        centerPanel.add(salaryLabel);


        //creationDate
        JLabel creationDateLabel = new JLabel(infoMap.get("creation_date"));
        creationDateLabel.setFont(mainFont);
        lowerPanel.add(creationDateLabel);

        JButton logOutButton = new JButton("Logga ut");
        logOutButton.setFont(buttonFont);

        logOutButton.addActionListener(_ -> {
            frame.dispose();
            LoginGUI.createLoginGui();
        });

        lowerPanel.add(logOutButton);


        // Lägg till panelen i frame
        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(lowerPanel, BorderLayout.SOUTH);


        // Centrera fönstret och gör det synligt
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}