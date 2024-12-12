package se.johan.projektarbete.gui;

import se.johan.projektarbete.logic.FieldInputListener;
import se.johan.projektarbete.logic.Security;


import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginGUI {

    static Connection conn = null;
    static PreparedStatement pstmt = null;
    static ResultSet rs = null;

    public static void createLoginGui() {
        // Skapa huvudfönstret
        JFrame frame = new JFrame("Jobb");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        //UpperPanel
        JPanel upperPanel = new JPanel();
        UtilGUI.createUpperPanel(upperPanel);

        //CenterPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 1, 10, 10)); // För mindre vertikalt mellanrum mellan raderna

        //LowerPanel
        JPanel lowerPanel = new JPanel();
        UtilGUI.createLowerPanel(lowerPanel);

        //Typsnitt
        Font buttonFont = new Font("SF Pro", Font.BOLD, 14); // Större font för knapparna
        Font mainFont = new Font("SF Pro", Font.PLAIN, 14);

        //HeaderLabel
        JLabel headerLabel = new JLabel("Login");
        UtilGUI.createHeaderLabel(headerLabel, upperPanel, buttonFont);


        // Skapa och lägg till etiketter och textfält

        //EmailLabel
        JLabel mailLabel = new JLabel("Email:");
        mailLabel.setFont(mainFont);
        centerPanel.add(mailLabel);
        //EmailTextField
        JTextField emailTextField = new JTextField();
        centerPanel.add(emailTextField);

        //FirstNameLabel
        JLabel passwordLabel = new JLabel("Lösenord:");
        passwordLabel.setFont(mainFont);
        centerPanel.add(passwordLabel);
        //FirstNameTextField
        JTextField passwordTextField = new JTextField();
        centerPanel.add(passwordTextField);


        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        lowerPanel.add(errorLabel);


        // Logga in
        JButton logInButton = new JButton("Logga in");
        logInButton.setFont(buttonFont);
        lowerPanel.add(logInButton);
        logInButton.addActionListener(_ -> {
            String email = emailTextField.getText();
            String password = passwordTextField.getText();

            if (!Security.checkForBlancField(email) || !Security.checkForBlancField(password)) {
                if (Security.checkForThreat(email) || Security.checkForThreat(password)) {
                    errorLabel.setText("Farliga tecken!");
                } else {
                    errorLabel.setText("");
                    if (!Security.checkThatUserExists(conn, pstmt, rs, email, /*Security.haschPassword(*/password)) {
                        errorLabel.setForeground(Color.RED);
                        errorLabel.setText("Användaren finns inte");
                    } else {
                        //errorLabel.setForeground(Color.GREEN);
                        //errorLabel.setText("Användare hittad!");
                        frame.dispose();
                    }
                }

            } else {
                errorLabel.setForeground(Color.ORANGE);
                errorLabel.setText("Ett/flera fält är tomma");
            }

        });


        // Lägg till panelen i frame
        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(lowerPanel, BorderLayout.SOUTH);

        FieldInputListener listener = new FieldInputListener(errorLabel);
        emailTextField.getDocument().addDocumentListener(listener);
        passwordTextField.getDocument().addDocumentListener(listener);


        // Centrera fönstret och gör det synligt
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }


}

