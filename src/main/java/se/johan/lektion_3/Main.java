package se.johan.lektion_3;

import se.johan.util.*;
import se.johan.util.JDBCUtil;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        //Anslutning till databas
        Connection connection = null;
        //PreparedStatement = exekverar sql query
        PreparedStatement preparedStatement = null;
        //Håller data som returneras från databasen
        ResultSet resultSet = null;


        try {
            connection = JDBCUtil.getConnection();
            System.out.println("Connected to: " + JDBCUtil.getDatabaseProductName(connection));


            String sql = "INSERT INTO Person (first_name, last_name, gender, dob, income) VALUES (?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "John");
            preparedStatement.setString(2, "Pork");
            preparedStatement.setString(3, "M");
            preparedStatement.setDate(4, java.sql.Date.valueOf("2001-01-01"));
            preparedStatement.setDouble(5,5000);

            System.out.println("Executing SQL: " + preparedStatement);
            preparedStatement.executeUpdate();

            //Anropar commit för att spara ändringar
            JDBCUtil.commit(connection);
            System.out.println("Data successfully inserted.");


        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(connection);
        } finally {

            JDBCUtil.closeResultSet(resultSet);
            JDBCUtil.closeStatement(preparedStatement);
            JDBCUtil.closeConnection(connection);
        }


    }
}
