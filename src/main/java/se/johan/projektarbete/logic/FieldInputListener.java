package se.johan.projektarbete.logic;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FieldInputListener implements DocumentListener {
    private final JLabel errorLabel;

    public FieldInputListener(JLabel errorLabel) {
        this.errorLabel = errorLabel;
    }

    private void clearError() {
        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        clearError();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        clearError();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        clearError();
    }


}

