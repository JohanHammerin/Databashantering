package se.johan.projektarbete.util;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.util.List;
import java.util.Map;

public interface WorkRoleAndEmployeeDAO {

    //workRole
    void createNewWorkRole(Connection conn, PreparedStatement stmt, String title, String workDescription, double salary, java.sql.Date creationDate);

    void deleteWorkRole(Connection conn, PreparedStatement pstmt, String title);

    List<Map<String, Object>> showAllWorkRoles(Connection conn, PreparedStatement pstmt, ResultSet rs);

    void updateWorkRole(Connection conn, PreparedStatement pstmt, String workDescription, double salary, java.sql.Date creationDate, String selectedRole);


    //Employee
    void createNewEmployee(Connection conn, PreparedStatement pstmt, String fullName, String email, String employeePassword, String title);

    void deleteEmployee(Connection conn, PreparedStatement pstmt, String fullName);

    List<Map<String, Object>> showAllEmployees(Connection conn, PreparedStatement pstmt, ResultSet rs);

    void updateEmployee(Connection conn, PreparedStatement pstmt, ResultSet rs, String email, String employeePassword, String selectedEmployee, String selectedRole);

    Map<String, String> showWorkRole(Connection conn, PreparedStatement pstmt, ResultSet rs, int employeeId);

    Map<String, String> showWorkRole(Connection conn, PreparedStatement pstmt, ResultSet rs, String selectedRole);


}
