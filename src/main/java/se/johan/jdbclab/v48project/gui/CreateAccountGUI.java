package se.johan.jdbclab.v48project.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class CreateAccountGUI {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;


    public void createCreateAccountGUI() {
        JFrame frame = new JFrame("Fastigheter");
        UtilGui.createFrame(frame);


        //UpperPanel
        JPanel upperPanel = new JPanel();
        UtilGui.createUpperPanel(upperPanel);
        //CenterPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(8, 1, 10, 10));
        //LowerPanel
        JPanel lowerPanel = new JPanel();
        UtilGui.createLowerPanel(lowerPanel);

        Font buttonFont = new Font("SF Pro", Font.BOLD, 14);
        Font mainFont = new Font("SF Pro", Font.PLAIN, 14);


        JLabel headerLabel = new JLabel("Skapa konto");
        UtilGui.createHeaderLabel(headerLabel, upperPanel, buttonFont);

        //Förnamn
        JLabel firstNameLabel = new JLabel("Förnamn");
        firstNameLabel.setFont(mainFont);
        centerPanel.add(firstNameLabel);

        JTextField firstNameTextField = new JTextField();
        centerPanel.add(firstNameTextField);
        //Efternamn
        JLabel lastNameLabel = new JLabel("Efternamn");
        lastNameLabel.setFont(mainFont);
        centerPanel.add(lastNameLabel);

        JTextField lastNameTextField = new JTextField();
        centerPanel.add(lastNameTextField);

        //Mail
        JLabel mailLabel = new JLabel("Mail");
        mailLabel.setFont(mainFont);
        centerPanel.add(mailLabel);

        JTextField mailTextField = new JTextField();
        centerPanel.add(mailTextField);



        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Centrera fönstret och gör det synligt
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }


}
