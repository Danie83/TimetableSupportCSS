package unit_tests;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.sql.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.css.timetable.ConfigReader;
import com.css.timetable.JDBCConnection;
import com.css.timetable.setup.DatabaseSetup;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseSetupTest {
    @Mock
    private ConfigReader configReader = mock(ConfigReader.class);

    @Mock
    private JDBCConnection jdbcConnection = mock(JDBCConnection.class);

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    
    private List<String> logMessages;
    
    /**
     * This @BeforeEach method sets up the mock behavior for several 
     * method calls that are commonly used in the test cases. It provides 
     * the necessary mock objects and returns them when the corresponding 
     * method calls are made during the test execution.
     * @throws SQLException 
     */
    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        logMessages = new ArrayList<>();

        when(ConfigReader.getInstance()).thenReturn(configReader);
        when(JDBCConnection.getInstance()).thenReturn(jdbcConnection);
        when(jdbcConnection.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        when(statement.executeQuery(any())).thenReturn(resultSet);
    }
    
    /**
     * this test method verifies that when the setup() method of the DatabaseSetup class is called 
     * and the setup is not initiated, the expected logging message is generated. 
     * It uses a mocked Logger object to capture and verify the log message.
     */
//    @Test
//    public void testSetupNotInitiated()
//    {
//        Logger loggerMock = mock(Logger.class);
//        DatabaseSetup databaseSetup = new DatabaseSetup();
//        databaseSetup.LOG = loggerMock;
//        databaseSetup.setup();
//        verify(loggerMock).info("Database setup is not activated.");
//        verifyNoMoreInteractions(loggerMock);
//    }
      
    /**
     * This test method verifies that the setup() method of the DatabaseSetup 
     * class interacts with the configReader object correctly by calling the 
     * getProperty() method with specific arguments. It sets up the mock behavior and then 
     * verifies that the expected method calls were made.
     */
//    @Test
//    void testSetup() {
//    // Set up the mock behavior
//        when(configReader.getProperty("setup.initiate")).thenReturn("true");
//        when(configReader.getProperty("setup.groups.count")).thenReturn("10");
//        when(configReader.getProperty("setup.groups.years")).thenReturn("5");
//        when(configReader.getProperty("setup.groups.halfs")).thenReturn("2");
//        when(configReader.getProperty("setup.rooms")).thenReturn("20");
//        when(configReader.getProperty("setup.rooms.types")).thenReturn("5");
//        when(configReader.getProperty("setup.teachers")).thenReturn("15");
//        when(configReader.getProperty("setup.discipline")).thenReturn("10");
//
//        // Call the method to be tested
//        DatabaseSetup.setup();
//
//        // Verify the expected behavior
//        verify(configReader).getProperty("setup.initiate");
//        verify(configReader).getProperty("setup.groups.count");
//        verify(configReader).getProperty("setup.groups.years");
//        verify(configReader).getProperty("setup.groups.halfs");
//        verify(configReader).getProperty("setup.rooms");
//        verify(configReader).getProperty("setup.rooms.types");
//        verify(configReader).getProperty("setup.teachers");
//        verify(configReader).getProperty("setup.discipline");
//    }   
    
    /**
     *  this test method verifies that when the "setup.initiate" property 
     * is set to "false" in the Properties object obtained from the ConfigReader, 
     * the setup() method of the DatabaseSetup class behaves as expected. It uses 
     * a mock object of the configReader to capture and verify the interactions 
     * with the getProperty() method.
     */
//    @Test
//    public void testSetupNotActivated() {
//        Properties properties = ConfigReader.getInstance().getProperties();
//        properties.setProperty("setup.initiate", "false");
//        System.out.println(properties.getProperty("setup.initiate"));
//        when(configReader.getProperties()).thenReturn(properties);
//
//        DatabaseSetup.setup();
//
//        verify(configReader, times(1)).getProperty("setup.initiate");
//        verifyNoMoreInteractions(configReader);
//    }
    
    
    /**
     * This test method verifies that when the "setup.initiate" property is set to "true" in the Properties 
     * object obtained from the ConfigReader, the setup() method of the DatabaseSetup class 
     * behaves as expected. It uses a mock object of the configReader to capture and verify the interactions with the getProperty() method. 
     * Additionally, it checks that the setup() method attempts to retrieve 
     * each configuration property exactly once by verifying the number of method calls.
     */
//    @Test
//    public void testSetupConfigIncomplete() {
//        Properties properties = ConfigReader.getInstance().getProperties();
//        properties.setProperty("setup.initiate", "true");
//        System.out.println(properties.getProperty("setup.initiate"));
//        
//        when(configReader.getProperties()).thenReturn(properties);
//
//        DatabaseSetup.setup();
//
//        verify(configReader, times(8)).getProperty(any()); // One for each config property
//        verifyNoMoreInteractions(configReader);
//    }
    
      /**
       * This test method named testSetupDisciplinesAndTeachersMismatch() 
       * tests the setup() method of the DatabaseSetup class. 
       * It sets up a Properties object with various configuration properties 
       * related to setup initialization, groups, rooms, teachers, and disciplines. 
       * It then mocks the behavior of the getProperties() 
       * method of the configReader mock object to return the created properties object.
       */
//    @Test
//    public void testSetupDisciplinesAndTeachersMismatch() throws SQLException {
//        Properties properties = new Properties();
//        properties.setProperty("setup.initiate", "true");
//        properties.setProperty("setup.groups.count", "2");
//        properties.setProperty("setup.groups.years", "2");
//        properties.setProperty("setup.groups.halfs", "2");
//        properties.setProperty("setup.rooms", "3");
//        properties.setProperty("setup.rooms.types", "Seminary-40,Laboratory-40,Course-20");
//        properties.setProperty("setup.teachers", "John Smith,Jane Doe,Robert Johnson");
//        properties.setProperty("setup.discipline", "Math-1,Science-2");
//        when(configReader.getProperties()).thenReturn(properties);
//
//        DatabaseSetup.setup();
//
//        verify(configReader, times(8)).getProperty(any());
//        verifyNoMoreInteractions(configReader);
//    }
      /**
       * The test then mocks the behavior of the getProperties() method of the configReader 
       * mock object to return the created properties object. After that, it calls the 
       * setup() method of the DatabaseSetup class.
       * The purpose of this test is to verify that the setup() method correctly 
       * handles the scenario when the room types are not evenly distributed in terms of their 
       * capacities. Specifically, it ensures that the setup() method retrieves the necessary configuration 
       * properties using the getProperty() method of the configReader mock object.
       */
//    @Test
//    public void testSetupRoomTypesNotEvenlyDistributed() throws SQLException {
//        Properties properties = new Properties();
//        properties.setProperty("setup.initiate", "true");
//        properties.setProperty("setup.groups.count", "2");
//        properties.setProperty("setup.groups.years", "2");
//        properties.setProperty("setup.groups.halfs", "2");
//        properties.setProperty("setup.rooms", "3");
//        properties.setProperty("setup.rooms.types", "Seminary-40,Laboratory-30,Course-20");
//        properties.setProperty("setup.teachers", "John Smith,Jane Doe");
//        properties.setProperty("setup.discipline", "Math-1,Science-2");
//        when(configReader.getProperties()).thenReturn(properties);
//
//        DatabaseSetup.setup();
//
//        verify(configReader).getProperty(any());
//        verifyNoMoreInteractions(configReader);
//    }
    
      /**
       * The test then mocks the behavior of the getProperties() method of the 
       * configReader mock object to return the created properties object. After 
       * that, it calls the setup() method of the DatabaseSetup class.
       * The purpose of this test is to verify that the setup() method correctly 
       * handles the scenario when a discipline is assigned to a nonexistent year. It checks 
       * whether the setup() method retrieves the necessary configuration properties using 
       * the getProperty() method of the configReader mock object.
       */
//    @Test
//    public void testSetupDisciplineForNonexistentYear() throws SQLException {
//        Properties properties = new Properties();
//        properties.setProperty("setup.initiate", "true");
//        properties.setProperty("setup.groups.count", "2");
//        properties.setProperty("setup.groups.years", "2");
//        properties.setProperty("setup.groups.halfs", "2");
//        properties.setProperty("setup.rooms", "3");
//        properties.setProperty("setup.rooms.types", "Seminary-40,Laboratory-40,Course-20");
//        properties.setProperty("setup.teachers", "John Smith,Jane Doe");
//        properties.setProperty("setup.discipline", "Math-1,Science-3");
//        when(configReader.getProperties()).thenReturn(properties);
//
//        DatabaseSetup.setup();
//
//        verify(configReader).getProperty(any());
//        verifyNoMoreInteractions(configReader);
//    }
        
      /**
       * The purpose of this test is to verify that the setup() method executes successfully 
       * when all the necessary conditions are met. It checks whether the setup() method 
       * retrieves the necessary configuration properties using the getProperty() method of 
       * the configReader mock object. It also verifies that the prepareStatement() and executeUpdate() 
       * methods of the connection and preparedStatement mock objects are called during the setup process. 
       * The verify(configReader).getProperty(any()) statement verifies that the getProperty() 
       * method is called at least once with any argument during the execution of the setup() method. 
       * The verify(connection).prepareStatement(any()) statement verifies that the prepareStatement() 
       * method is called on the connection mock object. The verify(preparedStatement).executeUpdate() 
       * statement verifies that the executeUpdate() method is called on the preparedStatement mock object.
       */
//    @Test
//    public void testSetupSuccessful() throws SQLException {
//        Properties properties = new Properties();
//        properties.setProperty("setup.initiate", "true");
//        properties.setProperty("setup.groups.count", "2");
//        properties.setProperty("setup.groups.years", "2");
//        properties.setProperty("setup.groups.halfs", "2");
//        properties.setProperty("setup.rooms", "3");
//        properties.setProperty("setup.rooms.types", "Seminary-40,Laboratory-40,Course-20");
//        properties.setProperty("setup.teachers", "John Smith,Jane Doe");
//        properties.setProperty("setup.discipline", "Math-1,Science-2");
//        when(configReader.getProperties()).thenReturn(properties);
//        when(resultSet.getInt(any())).thenReturn(0);
//
//        DatabaseSetup.setup();
//
//        verify(configReader).getProperty(any());
//        verify(connection).prepareStatement(any());
//        verify(preparedStatement).executeUpdate();
//    }
}
