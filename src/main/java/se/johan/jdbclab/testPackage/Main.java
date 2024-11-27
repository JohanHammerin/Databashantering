package se.johan.jdbclab.testPackage;

import se.johan.jdbclab.util.JDBCUtil;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = JDBCUtil.getConnection()) {
            System.out.println("Connection successful: " + JDBCUtil.getDatabaseProductName(connection));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
