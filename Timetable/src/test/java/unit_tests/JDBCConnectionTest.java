package unit_tests;

import com.css.timetable.JDBCConnection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the class that contains all tests for {@link com.css.timetable.JDBCConnection JDBCConnection} class.
 */
public class JDBCConnectionTest {
    /**
     * Tests the functionality of {@link com.css.timetable.JDBCConnection#getInstance() getInstance()}.
     * It verifies if we can get an instance from JDBCConnection class and if JDBCConnection 
     * respects the concept of singleton.
     * @throws SQLException if there exists an error in establishing a JDBC connection.
     */
    @Test
    public void testGetInstanceJDBCConnection() throws SQLException {
        JDBCConnection myFirstInstance = JDBCConnection.getInstance();
        JDBCConnection mySecondInstance = JDBCConnection.getInstance();
        assertNotNull(myFirstInstance);
        assertNotNull(mySecondInstance );
        assertSame(myFirstInstance, mySecondInstance );
    }
    
     /**
     * Tests the functionality of {@link com.css.timetable.JDBCConnection#getConnection() getConnection()}.
     * Checks if the connection got from JDBCConnection instance is not null.
     * @throws SQLException if there exists an error in establishing a JDBC connection.
     */
    @Test
    public void testConnectionJDBCConnectionNotNull() throws SQLException {
        JDBCConnection myInstance = JDBCConnection.getInstance();
        assertNotNull(myInstance.getConnection());
    }
}
