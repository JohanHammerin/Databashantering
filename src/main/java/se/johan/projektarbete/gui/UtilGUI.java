package se.johan.projektarbete.gui;


import javax.swing.*;
import java.awt.*;

public class UtilGUI {

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

    public static void createHeaderButton(JButton button, JPanel panel, Font font) {
        button.setFont(font);
        panel.add(button);
    }
}

