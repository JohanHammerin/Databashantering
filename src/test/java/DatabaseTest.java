import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {

    private Connection conn;

    @BeforeEach
    public void setUp() throws Exception {
        // Skapa en gemensam anslutning till H2-databasen
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "root", "");
    }

    @Test
    public void testInMemoryDatabase() throws Exception {
        Statement statement = conn.createStatement();

        // Skapa schema
        statement.execute("CREATE TABLE Person (person_id INT AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(50));");

        // Infoga och validera data
        statement.execute("INSERT INTO Person (first_name) VALUES ('Johan');");
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM Person;");

        if (resultSet.next()) {
            assertEquals(1, resultSet.getInt("total"));
        }
    }

    @Test
    public void testUpdateData() throws Exception {
        Statement statement = conn.createStatement();

        // Skapa schema och infoga data
        statement.execute("CREATE TABLE Person (person_id INT AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(50));");
        statement.execute("INSERT INTO Person (first_name) VALUES ('Johan');");

        // Uppdatera data
        statement.execute("UPDATE Person SET first_name = 'Jonas' WHERE first_name = 'Johan';");

        // Verifiera uppdatering
        ResultSet resultSet = statement.executeQuery("SELECT first_name FROM Person WHERE first_name = 'Jonas';");

        if (resultSet.next()) {
            assertEquals("Jonas", resultSet.getString("first_name"));
        }
    }

    @Test
    public void testDeleteData() throws Exception {
        Statement statement = conn.createStatement();

        // Skapa schema och infoga data
        statement.execute("CREATE TABLE Person (person_id INT AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(50));");
        statement.execute("INSERT INTO Person (first_name) VALUES ('Johan');");

        // Ta bort data
        statement.execute("DELETE FROM Person WHERE first_name = 'Johan';");

        // Verifiera borttagning
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM Person;");

        if (resultSet.next()) {
            assertEquals(0, resultSet.getInt("total"));
        }
    }

    @Test
    public void testForeignKeyConstraint() throws Exception {
        Statement statement = conn.createStatement();

        // Skapa schema med en relation
        statement.execute("CREATE TABLE Person (person_id INT AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(50));");
        statement.execute("CREATE TABLE House (house_id INT AUTO_INCREMENT PRIMARY KEY, address VARCHAR(255), person_id INT, FOREIGN KEY (person_id) REFERENCES Person(person_id));");

        // Infoga data
        statement.execute("INSERT INTO Person (first_name) VALUES ('Johan');");
        statement.execute("INSERT INTO House (address, person_id) VALUES ('123 Main St', 1);");

        // Verifiera datarelation
        ResultSet resultSet = statement.executeQuery("SELECT h.address, p.first_name FROM House h JOIN Person p ON h.person_id = p.person_id;");

        if (resultSet.next()) {
            assertEquals("123 Main St", resultSet.getString("address"));
            assertEquals("Johan", resultSet.getString("first_name"));
        }
    }

    @AfterEach
    public void tearDown() {
        if (conn != null) {
            try (Statement statement = conn.createStatement()) {
                // Rensa databasen
                statement.execute("DROP ALL OBJECTS;");
                conn.close();
                System.out.println("Testdatabasen stängdes och rensades.");
            } catch (Exception e) {
                System.err.println("Fel vid stängning av testdatabasen: " + e.getMessage());
            }
        }
    }
}
