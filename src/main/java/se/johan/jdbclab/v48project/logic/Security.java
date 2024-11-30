package se.johan.jdbclab.v48project.logic;

import se.johan.jdbclab.util.JDBCUtil;

import java.sql.*;

public class Security {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public static boolean checkForThreat(String input) {
        return (input.contains("*") || input.contains("'") || input.contains("=") || input.contains("!"));
    }

    public boolean checkThatUserExists(String email, String firstName, String lastName) {

        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT person_id WHERE email = ? && first_name = ? && last_name ?";


        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
        return false;
    }
}
