package se.johan.jdclab.util;

import java.sql.*;

public class JDBCUtil {

    public static String getDatabaseProductName(Connection connection) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        return metadata.getDatabaseProductName();
    }

    public static Connection getConnection() throws SQLException {
//skapa upp en instans av hsql:s jdbcDriver-klass
        Driver hsqlDriver = new org.hsqldb.jdbcDriver();
//registrera drivern hos klassen DriverManager
        DriverManager.registerDriver(hsqlDriver);
//Skapa en URL till databasen
        String dbURL = "jdbc:hsqldb:hsql://localhost/jdbclab";
//Default användarnamn
        String userId = "sa";
//Default password
        String password = "";
//Använd metoden getConnection i DriverManager för att få en anslutning till databasen
        Connection conn = DriverManager.getConnection(dbURL, userId, password);
//Sätt autoCommit till false
        conn.setAutoCommit(false);
//returnera anslutningen
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




}
