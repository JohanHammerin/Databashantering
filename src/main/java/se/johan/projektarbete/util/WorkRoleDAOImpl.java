package se.johan.projektarbete.util;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class WorkRoleDAOImpl implements WorkRoleAndEmployeeDAO {


    @Override
    public void createNewWorkRole(Connection conn, PreparedStatement pstmt, ResultSet rs, String title, String workDescription, double salary, Date creationDate) {
        try {
            conn = JDBCUtil.getConnection();
            String sql =
                            """
                            INSERT INTO work_role (title, work_description, salary, )
                            """;



        } catch(SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
    }

    @Override
    public void deleteWorkRole(Connection conn, PreparedStatement pstmt, ResultSet rs, String title) {

    }

    @Override
    public void showAllWorkRoles(Connection conn, PreparedStatement pstmt, ResultSet rs) {

    }

    @Override
    public void updateWorkRole(Connection conn, PreparedStatement pstmt, ResultSet rs, String title, String workDescription, double salary, Date creationDate) {

    }

    @Override
    public void createNewEmployee(Connection conn, PreparedStatement pstmt, ResultSet rs, String fullName, String email, String employeePassword, int roleId) {

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

            String sql =
                    """
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
