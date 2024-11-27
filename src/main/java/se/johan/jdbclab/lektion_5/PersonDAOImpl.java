package se.johan.jdbclab.lektion_5;

import se.johan.jdbclab.util.JDBCUtil;

import java.sql.*;


public class PersonDAOImpl implements PersonDAO {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    @Override
    public void insertPerson(Person person) throws SQLException {
        try {
            connect();
            String sql = "INSERT INTO Person(first_name, last_name, gender, dob, income) VALUES (?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getGender());
            preparedStatement.setDate(4, person.getDob());
            preparedStatement.setDouble(5, person.getIncome());

            System.out.println("Executing SQL query: " + preparedStatement);
            preparedStatement.executeUpdate();

            //Anropar commit för att spara ändringarna
            JDBCUtil.commit(connection);
            System.out.println("Data successfully inserted");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void selectAllFromId(int personId) throws SQLException {
        try {
            connect();

            String sql = "SELECT * FROM Person WHERE person_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, personId);
            resultSet = preparedStatement.executeQuery();
            printInfoAboutPerson(resultSet);
            JDBCUtil.commit(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }


    @Override
    public void selectAll() {
        try {
            connect();
            String sql = "SELECT * FROM Person";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            printInfoAboutPerson(resultSet);
            JDBCUtil.commit(connection);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        JDBCUtil.closeResultSet(resultSet);
        JDBCUtil.closeStatement(preparedStatement);
        JDBCUtil.closeConnection(connection);
    }

    private void connect() throws SQLException {
        connection = JDBCUtil.getConnection();
        System.out.println("Successfully connected to :" + JDBCUtil.getDatabaseProductName(connection));
    }

    private void printInfoAboutPerson(ResultSet resultSet) throws SQLException {

        if (!resultSet.next()) {
            System.out.println("No data found");
            return;
        }


        while (resultSet.next()) {
            int id = resultSet.getInt("person_id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String gender = resultSet.getString("gender");
            Date dob = resultSet.getDate("dob");
            double income = resultSet.getDouble("income");

            // Skriv ut resultaten
            System.out.println("ID: " + id + "\nFirst Name: " + firstName + "\nLast Name: " + lastName +
                    "\nGender: " + gender + "\nDOB: " + dob + "\nIncome: " + income);

            System.out.println("-".repeat(50));
        }
    }
}
