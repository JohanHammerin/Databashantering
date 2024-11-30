package se.johan.jdbclab.v48project.bös;

import java.sql.SQLException;

public interface PersonDAO {
    void insertPerson(Person person);
    void selectAllFromPersonId(int personId) throws SQLException;
    void selectAllPerson();
}
