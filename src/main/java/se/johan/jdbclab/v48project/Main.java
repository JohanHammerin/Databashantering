package se.johan.jdbclab.v48project;

import java.sql.Date;
import java.sql.SQLException;



public class Main {
    public static void main(String[] args) throws SQLException {
        PersonDAO personDAO = new PersonDAOImpl();
        HouseDAO houseDAO = new HouseDAOImpl();

        // Skapa och lägg till personen
        Person firstPerson = new Person("Johan", "Hammerin", "M", Date.valueOf("2004-01-03"), 13156);
        //personDAO.insertPerson(firstPerson);

        // Använd det genererade person_id för att skapa huset
        House firstHouse = new House("Sandalmakarbacken 9", "Hägersten", "12639", 10, 3);
        //houseDAO.insertHouse(firstHouse);
        //houseDAO.selectAllFromHouse();

        firstPerson.setPersonId(3);

        houseDAO.selectAllFromHouseAndPerson(firstPerson);
    }
}
