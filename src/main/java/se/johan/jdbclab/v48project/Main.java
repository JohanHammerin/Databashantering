package se.johan.jdbclab.v48project;

import se.johan.jdbclab.v48project.gui.LoginGUI;

import java.sql.Date;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        PersonDAO personDAO = new PersonDAOImpl();
        HouseDAO houseDAO = new HouseDAOImpl();

        new LoginGUI().createLoginGui();

        // Skapa och lägg till personen
        //Person firstPerson = new Person("Johan", "Hammerin", "Johan04hammerin@gmail.com", "M", Date.valueOf("2004-01-03"), 13156);
        //personDAO.insertPerson(firstPerson);
        //personDAO.selectAllPerson();

        // Använd det genererade person_id för att skapa huset
        //House firstHouse = new House("Sandalmakarbacken 9", "Hägersten", "12639", 10, 1);
        //houseDAO.insertHouse(firstHouse);
        //houseDAO.selectAllFromHouseAndPerson(1);


    }
}
