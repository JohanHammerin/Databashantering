package se.johan.jdbclab.v48project.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import se.johan.jdbclab.v48project.logic.FieldInputListener;
import se.johan.jdbclab.v48project.logic.Security;

public class LoginGUI {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public void createLoginGui() {
        // Skapa huvudfönstret
        JFrame frame = new JFrame("Fastighetskollen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        //Skapa en upperPanel med FlowLayout
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));


        // Skapa en centerPanel med en GridLayout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(7, 1, 10, 10)); // För mindre vertikalt mellanrum mellan raderna

        //Typsnitt
        Font buttonFont = new Font("SF Pro", Font.BOLD, 14); // Större font för knapparna
        Font headerFont = new Font("SF Pro", Font.BOLD, 14);
        Font mainFont = new Font("SF Pro", Font.PLAIN, 14);

        //HeaderLabel
        JLabel headerLabel = new JLabel("Login");
        headerLabel.setFont(headerFont);
        upperPanel.add(headerLabel);


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


        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        lowerPanel.add(errorLabel);


        // Logga in
        JButton logInButton = new JButton("Logga in");
        logInButton.setFont(buttonFont);
        logInButton.addActionListener(_ -> {
            String email = emailTextField.getText();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();

            if (!Security.checkForBlancField(email) && !Security.checkForBlancField(firstName) && !Security.checkForBlancField(lastName)) {
                if (Security.checkForThreat(email) || Security.checkForThreat(firstName) || Security.checkForThreat(lastName)) {
                    errorLabel.setText("Fälten får inte innehålla luriga tecken😳");
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


        lowerPanel.add(logInButton);

        // Skapa konto
        JButton createAccountButton = new JButton("Skapa konto");
        createAccountButton.setFont(buttonFont);

        createAccountButton.addActionListener(_ -> new CreateAccountGUI());

        lowerPanel.add(createAccountButton);


        // Lägg till panelen i frame
        frame.add(upperPanel, BorderLayout. NORTH);
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
