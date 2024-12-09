package se.johan.lektion_2;


import se.johan.util.JDBCUtil;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        //Anslutning till databas
        Connection connection = null;
        //Statement = exekverar SQL-statement
        Statement statement = null;
        //ResultSet = hålla data som returneras från databasen
        //ResultSet går att iterera (1 rad per iterering)
        ResultSet resultSet = null;

        String lastName = "Hammerin";
        //String lastName = "' OR '1'='1";


        try {
            connection = JDBCUtil.getConnection();
            statement = connection.createStatement();

            String sql = "SELECT * FROM Person WHERE last_name = '" + lastName + "'";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println("*".repeat(30));
                System.out.println("Person ID: " + resultSet.getInt("person_id"));
                System.out.println("First name: " + resultSet.getString("first_name"));
                System.out.println("Last name: " + resultSet.getString("last_name"));
                System.out.println("Gender: " + resultSet.getString("gender"));
                System.out.println("Date of birth: " + resultSet.getDate("dob"));
                System.out.println("Income: " + resultSet.getDouble("income"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResultSet(resultSet);
            JDBCUtil.closeStatement(statement);
            JDBCUtil.closeConnection(connection);
        }

    }
}
