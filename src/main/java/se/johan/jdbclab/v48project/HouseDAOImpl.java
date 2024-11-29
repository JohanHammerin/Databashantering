package se.johan.jdbclab.v48project;

import se.johan.jdbclab.util.JDBCUtil;

import java.sql.*;

public class HouseDAOImpl implements HouseDAO {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    @Override
    public void insertHouse(House house) {

        try {
            conn = JDBCUtil.getConnection();
            System.out.println("Successfully connected to: " + JDBCUtil.getDatabaseProductName(conn));

            String sql = "INSERT INTO HOUSE(address, city, postal_code, price, person_id) VALUES(?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, house.getAddress());
            pstmt.setString(2, house.getCity());
            pstmt.setString(3, house.getPostalCode());
            pstmt.setDouble(4, house.getPrice());
            pstmt.setInt(5, house.getPersonId());

            pstmt.executeUpdate();

            JDBCUtil.commit(conn);


        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }

    }

    @Override
    public void selectAllHouseFromCity(String city) {

        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM House WHERE city = ?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, city);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int houseId = rs.getInt("house_id");
                String address = rs.getString("address");
                String cityForPrint = rs.getString("city");
                String postalCode = rs.getString("postal_code");
                double price = rs.getDouble("price");
                String personId = rs.getString("person_id");

                System.out.println("house_id: " + houseId + "\n" + "address: " + address + "\n" + "city: " + cityForPrint + "\n" + "postal_code: " + postalCode + "\n" + "price: " + price + "\n" + "person_id: " + personId);
                System.out.println("-".repeat(50));
            }
            JDBCUtil.commit(conn);


        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }

    }

    @Override
    public void selectAllFromHouse() {
        try {
            conn = JDBCUtil.getConnection();

            String sql = "SELECT * FROM House";
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int houseId = rs.getInt("house_id");
                String address = rs.getString("address");
                String cityForPrint = rs.getString("city");
                String postalCode = rs.getString("postal_code");
                double price = rs.getDouble("price");
                int personId = rs.getInt("person_id");

                System.out.println("house_id: " + houseId + "\n" + "address: " + address + "\n" + "city: " + cityForPrint + "\n" + "postal_code: " + postalCode + "\n" + "price: " + price + "\n" + "person_id: " + personId);
                System.out.println("-".repeat(50));

            }

            JDBCUtil.commit(conn);


        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        }
    }


    @Override
    public void selectAllFromHouseAndPerson(int personId) {
        try {
            conn = JDBCUtil.getConnection();

            String sql = "SELECT * FROM House INNER JOIN Person ON House.person_id = Person.person_id WHERE Person.person_id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, personId);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String address = rs.getString("address");
                String city = rs.getString("city");

                System.out.println("Name: " + firstName + " " + lastName + "\n" + "address: " + address + "\ncity: " + city);
                System.out.println("_".repeat(50));
            }


            JDBCUtil.commit(conn);

        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.close(conn, pstmt, rs);
        }
    }
}
