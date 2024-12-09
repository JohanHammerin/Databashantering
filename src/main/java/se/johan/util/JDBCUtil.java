package se.johan.util;

import java.sql.*;

public class JDBCUtil {

    public static String getDatabaseProductName(Connection connection) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        return metadata.getDatabaseProductName();
    }

    public static Connection getConnection() throws SQLException {
        // Registrera MySQL JDBC-drivrutinen
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        // Skapa en URL till MySQL-databasen
        String dbURL = "jdbc:mysql://localhost:3306/jdbclab";
        // Ange användarnamn och lösenord
        String userId = "root"; // Använd ditt användarnamn för MySQL
        String password = "password";  // Använd ditt lösenord för MySQL

        // Använd metoden getConnection i DriverManager för att få en anslutning till databasen
        Connection conn = DriverManager.getConnection(dbURL, userId, password);

        // Sätt autoCommit till false för explicit transaktionshantering
        conn.setAutoCommit(false);

        // Returnera anslutningen
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void commit(Connection conn) {
        try {
            if (conn != null) {
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        JDBCUtil.closeResultSet(rs);
        JDBCUtil.closeStatement(pstmt);
        JDBCUtil.closeConnection(conn);
    }
}
