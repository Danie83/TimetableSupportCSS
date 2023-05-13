package unit_tests;

import com.css.timetable.ConfigReader;
import com.css.timetable.JDBCConnection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class JDBCConnectionTest {
    
    @Test
    public void testGetInstanceJDBCConnection() throws SQLException {
        JDBCConnection myFirstInstance = JDBCConnection.getInstance();
        JDBCConnection mySecondInstance = JDBCConnection.getInstance();
        assertNotNull(myFirstInstance);
        assertNotNull(mySecondInstance );
        assertSame(myFirstInstance, mySecondInstance );
    }
    
    @Test
    public void testConnectionJDBCConnectionNotNull() throws SQLException {
        JDBCConnection myInstance = JDBCConnection.getInstance();
        assertNotNull(myInstance .getConnection());
    }
}
