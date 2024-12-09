package se.johan.projektarbete.util;

import java.sql.*;
import java.util.Map;

public interface WorkRoleAndEmployeeDAO {

    //workRole
    void createNewWorkRole(Connection conn, PreparedStatement pstmt, ResultSet rs, String title, String workDescription, double salary, Date creationDate);
    void deleteWorkRole(Connection conn, PreparedStatement pstmt, ResultSet rs, String title);
    void showAllWorkRoles(Connection conn, PreparedStatement pstmt, ResultSet rs);
    void updateWorkRole(Connection conn, PreparedStatement pstmt, ResultSet rs, String title, String workDescription, double salary, Date creationDate);

    //Employee
    void createNewEmployee(Connection conn, PreparedStatement pstmt, ResultSet rs, String fullName, String email, String employeePassword, int roleId);
    void deleteEmployee(Connection conn, PreparedStatement pstmt, ResultSet rs, String fullName);
    void showAllEmployees(Connection conn, PreparedStatement pstmt, ResultSet rs);
    void updateEmployee(Connection conn, PreparedStatement pstmt, ResultSet rs, String fullName, String email, String employeePassword, int roleId);

    Map<String, String> showWorkRole(Connection conn, PreparedStatement pstmt, ResultSet rs, int employeeId);


}
