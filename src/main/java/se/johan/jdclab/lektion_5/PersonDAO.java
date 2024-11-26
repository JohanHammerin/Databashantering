package se.johan.jdclab.lektion_5;

import java.sql.*;

public interface PersonDAO {
    void insertPerson(Person person) throws SQLException;
    void selectAllFromId(int personId) throws SQLException;
    void selectAll() throws SQLException;


}
