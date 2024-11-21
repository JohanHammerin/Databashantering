package se.johan.jdclab;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        System.out.println(JDBCUtil.getDatabaseProductName(JDBCUtil.getConnection()));

        Connection conn = null;
        PreparedStatement pstmt = null;

        System.out.println(conn);
    }
}
