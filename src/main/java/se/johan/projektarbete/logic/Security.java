package se.johan.projektarbete.logic;

import se.johan.projektarbete.gui.AdminGUI;
import se.johan.projektarbete.gui.EmployeeGUI;
import se.johan.projektarbete.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

   /* public static String haschPassword(String password) {
        return String.valueOf(password.hashCode());
    }

    */


    public static boolean checkThatUserExists(Connection conn, PreparedStatement pstmt, ResultSet rs, String email, String password) {
        boolean userExists = false;

        try {
            conn = JDBCUtil.getConnection();
            String sql = """
                    SELECT employee_id
                    FROM employee
                    WHERE email = ? AND employee_password = ?
                    """;
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int employeeId = rs.getInt("employee_id");
                userExists = true;
                if (employeeId == 1) {
                    AdminGUI.createAdminGUI();
                } else {
                    EmployeeGUI.createEmployeeGUI(employeeId);
                }
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
        return !input.isBlank();
    }
}

