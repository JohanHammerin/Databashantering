package se.johan.projektarbete.util;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class WorkRoleAndEmployeeDAOImpl implements WorkRoleAndEmployeeDAO {

    //Klar
    @Override
    public void createNewWorkRole(Connection conn, PreparedStatement pstmt, String title, String workDescription, double salary, Date creationDate) {
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

            pstmt.executeUpdate();

            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt);
        }
    }

    //Klar
    @Override
    public void deleteWorkRole(Connection conn, PreparedStatement pstmt, String title) {

        try {
            conn = JDBCUtil.getConnection();

            Statement stmt = conn.createStatement();
            stmt.execute(" SET SQL_SAFE_UPDATES = 0");


            String sql = """
                    DELETE FROM work_role
                    WHERE title LIKE ?
                    """;

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.executeUpdate();


            stmt.execute(" SET SQL_SAFE_UPDATES = 1");


            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt);
        }


    }

    // klar!!!!!
    @Override
    public List<Map<String, Object>> showAllWorkRoles(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        List<Map<String, Object>> workRoles = new ArrayList<>();
        try {
            conn = JDBCUtil.getConnection();

            String sql = """
                    SELECT *
                    FROM work_role
                    """;

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> role = new HashMap<>();
                role.put("title", rs.getString("title"));
                role.put("salary", rs.getDouble("salary"));
                role.put("creation_date", rs.getDate("creation_date"));
                workRoles.add(role);
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
        return workRoles;
    }

    //Klar
    @Override
    public void updateWorkRole(Connection conn, PreparedStatement pstmt, String title, String workDescription, double salary, Date creationDate, int roleId) {
        try {
            conn = JDBCUtil.getConnection();

            String sql = """
                    UPDATE work_role
                        SET title = ?,
                            work_description = ?,
                            salary = ?,
                            creation_date = ?
                    WHERE role_id = ?;
                    """;

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, title);
            pstmt.setString(2, workDescription);
            pstmt.setDouble(3, salary);
            pstmt.setDate(4, creationDate);
            pstmt.setInt(5, roleId);

            pstmt.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt);
        }

    }

    //Klar
    @Override
    public void createNewEmployee(Connection conn, PreparedStatement pstmt, String fullName, String email, String employeePassword, String title) {
        try {
            // Få anslutning till databasen
            conn = JDBCUtil.getConnection();

            // Hämta role_id baserat på title
            String roleIdQuery = "SELECT role_id FROM work_role WHERE title = ?";
            pstmt = conn.prepareStatement(roleIdQuery);
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            // Kontrollera om den valda titeln finns i work_role-tabellen
            int roleId = -1;
            if (rs.next()) {
                roleId = rs.getInt("role_id");
            } else {
                throw new SQLException("Rolltitel hittades inte.");
            }

            // SQL-fråga för att skapa en ny anställd
            String sql = """
                    INSERT INTO employee (full_name, email, employee_password, role_id)
                    VALUES (?, ?, ?, ?);
                    """;

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, employeePassword);
            pstmt.setInt(4, roleId); // Använd role_id från work_role-tabellen

            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn); // Ångra om det uppstår ett fel
        } finally {
            JDBCUtil.close(conn, pstmt);
        }
    }


    //Klar
    @Override
    public void deleteEmployee(Connection conn, PreparedStatement pstmt, String fullName) {

        try {
            conn = JDBCUtil.getConnection();

            Statement stmt = conn.createStatement();
            stmt.execute(" SET SQL_SAFE_UPDATES = 0");

            String sql = """
                    DELETE FROM employee
                    WHERE full_name LIKE ?
                    """;

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fullName);
            pstmt.executeUpdate();


            stmt.execute(" SET SQL_SAFE_UPDATES = 1");

            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt);
        }


    }

    // klar!!!
    @Override
    public List<Map<String, Object>> showAllEmployees(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        List<Map<String, Object>> employees = new ArrayList<>();
        try {
            conn = JDBCUtil.getConnection();

            // Uppdaterad SQL-fråga som gör en JOIN mellan employee och work_role
            String sql = """
                    SELECT employee.full_name, employee.email, work_role.title
                    FROM employee
                    INNER JOIN work_role ON employee.role_id = work_role.role_id
                    """;

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> employee = new HashMap<>();
                employee.put("full_name", rs.getString("full_name"));
                employee.put("email", rs.getString("email"));
                employee.put("role_title", rs.getString("title")); // Här hämtas titeln på arbetsrollen
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
        return employees;
    }


    //Klar
    @Override
    public void updateEmployee(Connection conn, PreparedStatement pstmt, String fullName, String email, String employeePassword, int roleId, int employeeId) {

        try {
            conn = JDBCUtil.getConnection();

            String sql = """
                    UPDATE employee
                        SET full_name = ?,
                        email = ?,
                        employee_password = ?,
                        role_id = ?
                    WHERE employee_id = ?
                    """;

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, employeePassword);
            pstmt.setInt(4, roleId);
            pstmt.setInt(5, employeeId);

            pstmt.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt);
        }

    }

    //Klar
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