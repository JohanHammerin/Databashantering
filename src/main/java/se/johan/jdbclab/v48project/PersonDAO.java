package se.johan.jdbclab.v48project;

import java.sql.SQLException;

public interface PersonDAO {
    void insertPerson(Person person);
    void selectAllFromId(int personId) throws SQLException;
    void selectAll();
}
