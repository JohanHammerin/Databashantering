package se.johan.jdclab.lektion_5;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        PersonDAOImpl dao = new PersonDAOImpl();
        //dao.insertPerson(new Person("Håkan", "Glazeman", "M", Date.valueOf("2000-01-01"), 2000));
        dao.selectAll();
        dao.selectAllFromId(1);
        int hello = 10;
        System.out.println(hello);

    }
}