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
    
    @Test
    public void testSetupNotInitiated()
    {
        Logger loggerMock = mock(Logger.class);
        DatabaseSetup databaseSetup = new DatabaseSetup();
        databaseSetup.LOG = loggerMock;
        databaseSetup.setup();
        verify(loggerMock).info("Database setup is not activated.");
        verifyNoMoreInteractions(loggerMock);
    }
    
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

    /*@Test
    public void testSetupNotActivated() {
        Properties properties = ConfigReader.getInstance().getProperties();
        properties.setProperty("setup.initiate", "false");
        System.out.println(properties.getProperty("setup.initiate"));
        when(configReader.getProperties()).thenReturn(properties);

        DatabaseSetup.setup();

        verify(configReader, times(1)).getProperty("setup.initiate");
        verifyNoMoreInteractions(configReader);
    }

    @Test
    public void testSetupConfigIncomplete() {
        Properties properties = ConfigReader.getInstance().getProperties();
        properties.setProperty("setup.initiate", "true");
        System.out.println(properties.getProperty("setup.initiate"));
        
        when(configReader.getProperties()).thenReturn(properties);

        DatabaseSetup.setup();

        verify(configReader, times(8)).getProperty(any()); // One for each config property
        verifyNoMoreInteractions(configReader);
    }
    
    
    @Test
    public void testSetupDisciplinesAndTeachersMismatch() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("setup.initiate", "true");
        properties.setProperty("setup.groups.count", "2");
        properties.setProperty("setup.groups.years", "2");
        properties.setProperty("setup.groups.halfs", "2");
        properties.setProperty("setup.rooms", "3");
        properties.setProperty("setup.rooms.types", "Seminary-40,Laboratory-40,Course-20");
        properties.setProperty("setup.teachers", "John Smith,Jane Doe,Robert Johnson");
        properties.setProperty("setup.discipline", "Math-1,Science-2");
        when(configReader.getProperties()).thenReturn(properties);

        DatabaseSetup.setup();

        verify(configReader, times(8)).getProperty(any());
        verifyNoMoreInteractions(configReader);
    }

    @Test
    public void testSetupRoomTypesNotEvenlyDistributed() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("setup.initiate", "true");
        properties.setProperty("setup.groups.count", "2");
        properties.setProperty("setup.groups.years", "2");
        properties.setProperty("setup.groups.halfs", "2");
        properties.setProperty("setup.rooms", "3");
        properties.setProperty("setup.rooms.types", "Seminary-40,Laboratory-30,Course-20");
        properties.setProperty("setup.teachers", "John Smith,Jane Doe");
        properties.setProperty("setup.discipline", "Math-1,Science-2");
        when(configReader.getProperties()).thenReturn(properties);

        DatabaseSetup.setup();

        verify(configReader).getProperty(any());
        verifyNoMoreInteractions(configReader);
    }

    @Test
    public void testSetupDisciplineForNonexistentYear() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("setup.initiate", "true");
        properties.setProperty("setup.groups.count", "2");
        properties.setProperty("setup.groups.years", "2");
        properties.setProperty("setup.groups.halfs", "2");
        properties.setProperty("setup.rooms", "3");
        properties.setProperty("setup.rooms.types", "Seminary-40,Laboratory-40,Course-20");
        properties.setProperty("setup.teachers", "John Smith,Jane Doe");
        properties.setProperty("setup.discipline", "Math-1,Science-3");
        when(configReader.getProperties()).thenReturn(properties);

        DatabaseSetup.setup();

        verify(configReader).getProperty(any());
        verifyNoMoreInteractions(configReader);
    }

    @Test
    public void testSetupSuccessful() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("setup.initiate", "true");
        properties.setProperty("setup.groups.count", "2");
        properties.setProperty("setup.groups.years", "2");
        properties.setProperty("setup.groups.halfs", "2");
        properties.setProperty("setup.rooms", "3");
        properties.setProperty("setup.rooms.types", "Seminary-40,Laboratory-40,Course-20");
        properties.setProperty("setup.teachers", "John Smith,Jane Doe");
        properties.setProperty("setup.discipline", "Math-1,Science-2");
        when(configReader.getProperties()).thenReturn(properties);
        when(resultSet.getInt(any())).thenReturn(0);

        DatabaseSetup.setup();

        verify(configReader).getProperty(any());
        verify(connection).prepareStatement(any());
        verify(preparedStatement).executeUpdate();
    }*/

}
