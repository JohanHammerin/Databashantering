package se.johan.jdbclab.v48project;

import java.sql.Date;
import java.sql.SQLException;



public class Main {
    public static void main(String[] args) throws SQLException {
        PersonDAO personDAO = new PersonDAOImpl();

        Person firstPerson = new Person("Johan", "Hammerin", "M", Date.valueOf("2004-01-03"), 13156);
        personDAO.insertPerson(firstPerson);
        personDAO.selectAllFromId(1);
    }
}
