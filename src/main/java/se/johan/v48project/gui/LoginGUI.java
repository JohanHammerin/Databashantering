package se.johan.v48project.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import se.johan.v48project.logic.FieldInputListener;
import se.johan.v48project.logic.Security;

public class LoginGUI {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public void createLoginGui() {
        // Skapa huvudfönstret
        JFrame frame = new JFrame("Fastigheter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        //UpperPanel
        JPanel upperPanel = new JPanel();
        UtilGui.createUpperPanel(upperPanel);

        //CenterPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(8, 1, 10, 10)); // För mindre vertikalt mellanrum mellan raderna

        //LowerPanel
        JPanel lowerPanel = new JPanel();
        UtilGui.createLowerPanel(lowerPanel);

        //Typsnitt
        Font buttonFont = new Font("SF Pro", Font.BOLD, 14); // Större font för knapparna
        Font mainFont = new Font("SF Pro", Font.PLAIN, 14);

        //HeaderLabel
        JLabel headerLabel = new JLabel("Login");
        UtilGui.createHeaderLabel(headerLabel, upperPanel, buttonFont);


        // Skapa och lägg till etiketter och textfält

        //EmailLabel
        JLabel mailLabel = new JLabel("Email:");
        mailLabel.setFont(mainFont);
        centerPanel.add(mailLabel);
        //EmailTextField
        JTextField emailTextField = new JTextField();
        centerPanel.add(emailTextField);

        //FirstNameLabel
        JLabel firstNameLabel = new JLabel("Förnamn:");
        firstNameLabel.setFont(mainFont);
        centerPanel.add(firstNameLabel);
        //FirstNameTextField
        JTextField firstNameTextField = new JTextField();
        centerPanel.add(firstNameTextField);

        //LastNameLabel
        JLabel lastNameLabel = new JLabel("Efternamn:");
        lastNameLabel.setFont(mainFont);
        centerPanel.add(lastNameLabel);

        //LastNameTextField
        JTextField lastNameTextField = new JTextField();
        centerPanel.add(lastNameTextField);

        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        lowerPanel.add(errorLabel);


        // Skapa konto
        JButton createAccountButton = new JButton("Skapa konto");
        createAccountButton.setFont(buttonFont);
        lowerPanel.add(createAccountButton);
        createAccountButton.addActionListener(_ -> {
            new CreateAccountGUI().createCreateAccountGUI(); // Öppna nytt GUI
            frame.dispose(); // Stäng nuvarande fönster helt
        });


        // Logga in
        JButton logInButton = new JButton("Logga in");
        logInButton.setFont(buttonFont);
        lowerPanel.add(logInButton);
        logInButton.addActionListener(_ -> {
            String email = emailTextField.getText();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();

            if (!Security.checkForBlancField(email) && !Security.checkForBlancField(firstName) && !Security.checkForBlancField(lastName)) {
                if (Security.checkForThreat(email) || Security.checkForThreat(firstName) || Security.checkForThreat(lastName)) {
                    errorLabel.setText("Farliga tecken!");
                } else {
                    errorLabel.setText("");
                    if (!Security.checkThatUserExists(conn, pstmt, rs, email, firstName, lastName)) {
                        errorLabel.setForeground(Color.RED);
                        errorLabel.setText("Användaren finns inte");
                    } else {
                        errorLabel.setForeground(Color.GREEN);
                        errorLabel.setText("Användare hittad!");
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
        firstNameTextField.getDocument().addDocumentListener(listener);
        lastNameTextField.getDocument().addDocumentListener(listener);


        // Centrera fönstret och gör det synligt
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }



}

