package se.johan.projektarbete.gui;


import javax.swing.*;
import java.awt.*;

public class UtilGUI {

    public static void createFrame(JFrame frame) {
        // Skapa huvudfönstret
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
    }

    public static void createUpperPanel(JPanel panel) {
        //Skapa en upperPanel med FlowLayout
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    }

    public static void createLowerPanel(JPanel panel) {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new GridLayout(4, 1, 10, 10));
    }

    public static void createHeaderLabel(JLabel label, JPanel panel, Font font) {
        label.setFont(font);
        panel.add(label);
    }
}

