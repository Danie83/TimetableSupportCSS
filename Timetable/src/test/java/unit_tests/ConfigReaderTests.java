package unit_tests;


import com.css.timetable.ConfigReader;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

/**
 * This is the class that contains all tests for {@link com.css.timetable.ConfigReader ConfigReader} class.
 */
public class ConfigReaderTests {
    /**
     * Tests the functionality of {@link com.css.timetable.ConfigReader#getInstance()  getInstance()}.
     * It verifies if the we get a JDBCConnection and if JDBCConnection 
     * respects the concept of singleton.
     */
    @Test
    public void testGetInstanceJDBCConnection() {
        ConfigReader myFirstInstance = ConfigReader.getInstance();
        ConfigReader mySecondInstance = ConfigReader.getInstance();
        assertNotNull(myFirstInstance);
        assertNotNull(mySecondInstance);
        assertSame(myFirstInstance, mySecondInstance );
    }
    
    /**
     * Tests the functionality of {@link com.css.timetable.ConfigReader#getProperties() getProperties()}.
     * Checks that the properties were loaded correctly from the configuration file.
     */
    @Test
    public void testGetPropertiesFromFile() {
        ConfigReader instance = ConfigReader.getInstance();
        Properties props = instance.getProperties();
        assertNotNull(props);
        assertEquals("jdbc:postgresql://localhost:5432/timetablecss", props.getProperty("database.url"));
        assertEquals("postgres", props.getProperty("database.user"));
        assertEquals("postgres", props.getProperty("database.password"));
        assertEquals("5", props.getProperty("setup.groups.count"));
    }
    
    /**
     * Tests the functionality of {@link com.css.timetable.ConfigReader#getProperty() getProperty()}.
     * Checks that getProperty() function correctly retrieves property values.
     */
    @Test 
    public void testGetProperty(){
        ConfigReader instance = ConfigReader.getInstance();
        assertEquals("jdbc:postgresql://localhost:5432/timetablecss", instance.getProperty("database.url"));
        assertEquals("postgres", instance.getProperty("database.user"));
        assertEquals("postgres", instance.getProperty("database.password"));
        assertEquals("5", instance.getProperty("setup.groups.count"));
    }  
}
