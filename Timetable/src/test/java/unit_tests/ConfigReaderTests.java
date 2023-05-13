package unit_tests;


import com.css.timetable.ConfigReader;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ConfigReaderTests {
    @Test
    public void testGetInstanceJDBCConnection() {
        ConfigReader myFirstInstance = ConfigReader.getInstance();
        ConfigReader mySecondInstance = ConfigReader.getInstance();
        assertNotNull(myFirstInstance);
        assertNotNull(mySecondInstance);
        assertSame(myFirstInstance, mySecondInstance );
    }
    
    @Test
    public void testGetPropertiesFromFile() {
        /***
        * Checks that the properties were loaded correctly from the configuration file.
        ***/
        ConfigReader instance = ConfigReader.getInstance();
        Properties props = instance.getProperties();
        assertNotNull(props);
        assertEquals("jdbc:postgresql://localhost:5432/timetablecss", props.getProperty("database.url"));
        assertEquals("postgres", props.getProperty("database.user"));
        assertEquals("postgres", props.getProperty("database.password"));
        assertEquals("5", props.getProperty("setup.groups.count"));
    }
    
    @Test 
    public void testGetProperty(){
        /***
         * Checks that getProperty() function correctly retrieves property values
         */
        ConfigReader instance = ConfigReader.getInstance();
        assertEquals("jdbc:postgresql://localhost:5432/timetablecss", instance.getProperty("database.url"));
        assertEquals("postgres", instance.getProperty("database.user"));
        assertEquals("postgres", instance.getProperty("database.password"));
        assertEquals("5", instance.getProperty("setup.groups.count"));
    }
    
}
