package se.johan.projektarbete.util;

import org.junit.jupiter.api.*;
import java.sql.*;


import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WorkRoleAndEmployeeDAOTest {

    private Connection conn;
    private WorkRoleAndEmployeeDAOImpl dao;

    @BeforeAll
    public void setup() throws SQLException {
        // Skapa DAO och sätt upp en in-memory H2-databas
        dao = new WorkRoleAndEmployeeDAOImpl();
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "root", "");

        // Skapa den tabell som krävs
        Statement stmt = conn.createStatement();
        stmt.execute("""
            CREATE TABLE work_role (
                role_id INT AUTO_INCREMENT PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                work_description TEXT NOT NULL,
                salary DOUBLE NOT NULL,
                creation_date DATE NOT NULL
            );
        """);
    }

    @BeforeEach
    public void cleanDatabase() throws SQLException {
        conn.createStatement().execute("DELETE FROM work_role;");
    }

    @Test
    void createNewWorkRole() throws SQLException {
        // Dynamisk testdata
        String title = "Software Engineer " + System.currentTimeMillis();
        String workDescription = "Develop and maintain software applications.";
        double salary = 60000.0;
        Date creationDate = new Date(System.currentTimeMillis());

        // Anropa metoden som ska testas
        dao.createNewWorkRole(conn, title, workDescription, salary, creationDate);


        // Verifiera att datan har lagts till
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM work_role WHERE title = ?");
        pstmt.setString(1, title);
        ResultSet rs = pstmt.executeQuery();

        assertTrue(rs.next(), "Arbetsrollen kunde inte hittas i databasen.");
        assertEquals(title, rs.getString("title"), "Titeln på arbetsrollen stämmer inte.");
        assertEquals(workDescription, rs.getString("work_description"), "Arbetsbeskrivningen stämmer inte.");
        assertEquals(salary, rs.getDouble("salary"), "Lönen stämmer inte.");
        assertEquals(creationDate.toString(), rs.getDate("creation_date").toString(), "Skapandedatumet stämmer inte.");
    }

    @AfterAll
    public void cleanup() throws SQLException {
        JDBCUtil.close(conn);
    }
}
