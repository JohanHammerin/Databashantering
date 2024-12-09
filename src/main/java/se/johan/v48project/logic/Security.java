package se.johan.v48project.logic;

import se.johan.util.JDBCUtil;
import se.johan.v48project.gui.*;
import se.johan.projektarbete.gui.*;

import se.johan.v48project.gui.MainGUI;

import java.sql.*;


public class Security {


    public static boolean checkForThreat(String input) {
        String[] threatCharacters = {"*", "'", "\"", ";", "--", "#", "=", "(", ")", "<", ">", "NULL", "UNION", "SELECT", "INSERT", "DROP", "EXEC", "0x"};
        for (String threat : threatCharacters) {
            if (input.toUpperCase().contains(threat)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkThatUserExists(Connection conn, PreparedStatement pstmt, ResultSet rs, String email, String firstName, String lastName) {
        boolean userExists = false;

        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT person_id FROM Person WHERE email = ? AND first_name = ? AND last_name = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, email);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int personId = rs.getInt("person_id");
                userExists = true;
                new MainGUI(personId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
        return userExists;
    }

    public static boolean checkForBlancField(String input) {
        return input.isBlank();
    }
}
