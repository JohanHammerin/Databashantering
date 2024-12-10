package se.johan.projektarbete.util;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class WorkRoleDAOImpl implements WorkRoleAndEmployeeDAO {

    //Klar
    @Override
    public void createNewWorkRole(Connection conn, PreparedStatement pstmt, ResultSet rs, String title, String workDescription, double salary, Date creationDate) {
        try {
            conn = JDBCUtil.getConnection();
            String sql = """
                            INSERT INTO work_role (title, work_description, salary, creation_date)
                            VALUES (?, ?, ?, ?);
                    """;

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, workDescription);
            pstmt.setDouble(3, salary);
            pstmt.setDate(4, creationDate);
            rs = pstmt.executeQuery();

            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
    }

    //Klar
    @Override
    public void deleteWorkRole(Connection conn, PreparedStatement pstmt, ResultSet rs, String title) {

        try {
            conn = JDBCUtil.getConnection();


            String sql = """
                    SET SQL_SAFE_UPDATES = 0;
                    DELETE FROM work_role
                    WHERE title LIKE ?
                    SET SQL_SAFE_UPDATES = 1;
                    """;

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt);
        }


    }

    //Inte klar!!!!!
    @Override
    public void showAllWorkRoles(Connection conn, PreparedStatement pstmt, ResultSet rs) {
    }

    //Klar
    @Override
    public void updateWorkRole(Connection conn, PreparedStatement pstmt, String title, String workDescription, double salary, Date creationDate, int workId) {
        try {
            conn = JDBCUtil.getConnection();

            String sql = """
                    UPDATE work_role
                        SET title = ?,
                        SET work_description = ?,
                        SET salary = ?,
                        SET creation_date = ?,
                    WHERE work_id = ?;
                    """;

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, title);
            pstmt.setString(2, workDescription);
            pstmt.setDouble(3, salary);
            pstmt.setDate(4, creationDate);
            pstmt.setInt(5, workId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt);
        }

    }

    //Inte klar
    @Override
    public void createNewEmployee(Connection conn, PreparedStatement pstmt, String fullName, String email, String employeePassword, int roleId) {

        try {
            conn = JDBCUtil.getConnection();

            String sql = """
                    INSERT INTO employee (full_name, email, employee_password, role_id)
                    VALUES (?, ?, ?, ?);
                    """;

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, employeePassword);
            pstmt.setInt(4, roleId);


        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt);
        }

    }


    @Override
    public void deleteEmployee(Connection conn, PreparedStatement pstmt, ResultSet rs, String fullName) {

    }

    @Override
    public void showAllEmployees(Connection conn, PreparedStatement pstmt, ResultSet rs) {

    }

    @Override
    public void updateEmployee(Connection conn, PreparedStatement pstmt, ResultSet rs, String fullName, String email, String employeePassword, int roleId) {

    }


    @Override
    public Map<String, String> showWorkRole(Connection conn, PreparedStatement pstmt, ResultSet rs, int employeeId) {
        try {
            conn = JDBCUtil.getConnection();

            String sql = """
                    SELECT *
                    FROM employee
                    INNER JOIN work_role ON work_role.role_id = employee.role_id
                    WHERE employee_id LIKE ?
                    """;

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            rs = pstmt.executeQuery();

            Map<String, String> infoMap = new HashMap<>();

            while (rs.next()) {
                infoMap.put("title", rs.getString("title"));
                infoMap.put("work_description", rs.getString("work_description"));
                infoMap.put("salary", String.valueOf(rs.getDouble("salary")));
                infoMap.put("full_name", rs.getString("full_name"));
                infoMap.put("creation_date", String.valueOf(rs.getDate("creation_date")));
            }
            return infoMap;

        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);

        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
        return Map.of();
    }

}
