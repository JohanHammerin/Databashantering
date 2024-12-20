package se.johan.v48project.bös;

import se.johan.util.JDBCUtil;

import java.sql.*;

public class PersonDAOImpl implements PersonDAO {
    //Sätter alla värden till null
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;


    @Override
    public void insertPerson(Person person) {
        try {
            //Skapar en koppling till databasen
            conn = JDBCUtil.getConnection();


            String sql = "INSERT INTO Person(first_name, last_name, email, gender, dob, income) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, person.getFirstName());
            pstmt.setString(2, person.getLastName());
            pstmt.setString(3, person.getEmail());
            pstmt.setString(4, person.getGender());
            pstmt.setDate(5, person.getDob());
            pstmt.setDouble(6, person.getIncome());

            pstmt.executeUpdate();
            JDBCUtil.commit(conn);


        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
    }

    @Override
    public void selectAllFromPersonId(int personId) {

        try {
            conn = JDBCUtil.getConnection();


            String sql = "SELECT * FROM Person WHERE person_id = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, personId);
            rs = pstmt.executeQuery();


            if (rs.next()) {
                int person_id = rs.getInt("person_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                Date dob = rs.getDate("dob");
                double income = rs.getDouble("income");

                System.out.println(person_id + "\n" + firstName + "\n" + lastName + "\n" + email + "\n" + gender + "\n" + dob + "\n" + income);
                System.out.println("-".repeat(50));
            } else {
                System.out.println("Finns ingen data där person_id = " + personId);
            }
            //Commit changes
            JDBCUtil.commit(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
    }

    @Override
    public void selectAllPerson() {
        try {
            //Connect to db
            conn = JDBCUtil.getConnection();
            //SQL query
            String sql = "SELECT * FROM Person";
            //preparedStatement
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();


            while (rs.next()) {
                int person_id = rs.getInt("person_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                Date dob = rs.getDate("dob");
                double income = rs.getDouble("income");

                System.out.println(person_id + "\n" + firstName + "\n" + lastName + "\n" + email + "\n" + gender + "\n" + dob + "\n" + income);
                System.out.println("-".repeat(50));
            }

            JDBCUtil.commit(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
    }
}
