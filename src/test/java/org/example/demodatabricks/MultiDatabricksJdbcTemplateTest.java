package org.example.demodatabricks;

import com.databricks.client.jdbc.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.*;

public class MultiDatabricksJdbcTemplateTest {

    @Mock
    private ObjectProvider <DataSource> dataSourceProvider;

    @Mock
    private DataSource dataSource1;

    @Mock
    private DataSource dataSource2;

    private DatabricksDataSourceConfig.MultiDatabricksJdbcTemplate multiDatabricksJdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(dataSourceProvider.getObject()).thenReturn(dataSource1);
        multiDatabricksJdbcTemplate = new DatabricksDataSourceConfig.MultiDatabricksJdbcTemplate(dataSourceProvider);
    }

    @Test
    void testMultipleNamedParameterJdbcTemplates() {
        // Mock the behavior of dataSourceProvider to return dataSource1 first, then dataSource2
        when(dataSourceProvider.getObject()).thenReturn(dataSource1, dataSource2);

        // Obtain NamedParameterJdbcOperations for the first and second calls
        NamedParameterJdbcOperations operations1 = multiDatabricksJdbcTemplate.getNamedParameterJdbcOperations();
        NamedParameterJdbcOperations operations2 = multiDatabricksJdbcTemplate.getNamedParameterJdbcOperations();

        // Verify that dataSourceProvider.getObject() was called twice
        verify(dataSourceProvider, times(3  )).getObject();

        // Assert that two different NamedParameterJdbcOperations instances were created
        assertNotSame(operations1, operations2, "Expected two different NamedParameterJdbcOperations instances");

        // Additional assertions can be made about the operations, such as verifying SQL executions,
        // but those would typically involve more detailed mocking and interaction testing.
    }
}
