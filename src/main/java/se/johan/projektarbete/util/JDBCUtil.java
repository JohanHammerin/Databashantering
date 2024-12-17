package se.johan.projektarbete.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {

    private static Properties properties = new Properties();


    static {
        try (InputStream input = JDBCUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IOException("Unable to find application.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to load database properties");
        }
    }


    public static Connection getConnection() throws SQLException {
        // Registrera MySQL JDBC-drivrutinen
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        // Skapa en URL till MySQL-databasen
        String dbURL = properties.getProperty("db.url");
        String userId = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

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

    public static void close(Connection conn, PreparedStatement pstmt) {
        JDBCUtil.closeStatement(pstmt);
        JDBCUtil.closeConnection(conn);
    }

    public static void close(Connection conn) {
        JDBCUtil.closeConnection(conn);
    }

    public static void close(PreparedStatement pstmt) {
        JDBCUtil.closeStatement(pstmt);
    }
}
