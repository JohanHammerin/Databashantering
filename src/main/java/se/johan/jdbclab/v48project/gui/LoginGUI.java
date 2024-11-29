package se.johan.jdbclab.v48project.gui;

import javax.swing.*;
import java.awt.*;

public class LoginGUI {

    public void createLoginGui() {
        // Skapa huvudfönstret
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Skapa en upperPanel med en GridLayout
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(6, 1, 10, 10)); // För mindre vertikalt mellanrum mellan raderna
        Font buttonFont = new Font("Arial", Font.BOLD, 16); // Större font för knapparna


        // Skapa och lägg till etiketter och textfält
        upperPanel.add(new JLabel("Email:"));
        JTextField emailTextField = new JTextField();
        emailTextField.setBorder(new RoundedBorder(15));
        upperPanel.add(emailTextField);

        upperPanel.add(new JLabel("First Name:"));
        JTextField firstNameTextField = new JTextField();

        firstNameTextField.setBorder(new RoundedBorder(15));

        upperPanel.add(firstNameTextField);

        upperPanel.add(new JLabel("Last Name:"));
        JTextField lastNameTextField = new JTextField();
        lastNameTextField.setBorder(new RoundedBorder(15));

        upperPanel.add(lastNameTextField);


        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new GridLayout(4,1,10,10));

        //Logga in
        JButton logInButton = new JButton("Logga in");
        logInButton.setBorder(new RoundedBorder(15));
        logInButton.setFont(buttonFont);
        lowerPanel.add(logInButton);

        //Skapa konto
        JButton createAccountButton = new JButton("Skapa konto");
        createAccountButton.setBorder(new RoundedBorder(15));
        createAccountButton.setFont(buttonFont);
        lowerPanel.add(createAccountButton);



        // Lägg till panelen i frame
        frame.add(upperPanel, BorderLayout.CENTER);
        frame.add(lowerPanel, BorderLayout.SOUTH);

        // Centrera fönstret och gör det synligt
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
