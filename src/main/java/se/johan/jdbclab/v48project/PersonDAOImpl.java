package se.johan.jdbclab.v48project;

import se.johan.jdbclab.util.JDBCUtil;

import java.sql.*;

public class PersonDAOImpl implements PersonDAO {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;


    @Override
    public void insertPerson(Person person) {
        try {

            conn = JDBCUtil.getConnection();
            System.out.println("Successfully connected to: " + JDBCUtil.getDatabaseProductName(conn));

            String sql = "INSERT INTO Person(first_name, last_name, gender, dob, income) VALUES (?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, person.getFirstName());
            pstmt.setString(2, person.getLastName());
            pstmt.setString(3, person.getGender());
            pstmt.setDate(4, person.getDob());
            pstmt.setDouble(5, person.getIncome());

            System.out.println("Executing SQL-query");
            pstmt.executeUpdate();

            JDBCUtil.commit(conn);
            System.out.println("Data successfully inserted");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
    }

    @Override
    public void selectAllFromId(int personId) {

        try {
            conn = JDBCUtil.getConnection();
            System.out.println("Successfully connected to: " + JDBCUtil.getDatabaseProductName(conn));


            String sql = "SELECT * FROM Person WHERE person_id = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, personId);
            rs = pstmt.executeQuery();


            if(rs.next()) {
                int person_id = rs.getInt("person_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String gender = rs.getString("gender");
                Date dob = rs.getDate("dob");
                double income = rs.getDouble("income");

                System.out.println(person_id + "\n" + firstName + "\n" + lastName + "\n" + gender + "\n" + dob + "\n" + income);
                System.out.println("-".repeat(50));
            }

            JDBCUtil.commit(conn);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
    }

    @Override
    public void selectAll() {

    }
}
