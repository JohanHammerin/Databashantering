package se.johan.jdbclab.v48project.logic;

import se.johan.jdbclab.v48project.gui.LoginGUI;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        new LoginGUI().createLoginGui();
    }
}