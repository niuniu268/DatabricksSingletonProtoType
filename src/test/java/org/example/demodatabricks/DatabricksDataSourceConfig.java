package org.example.demodatabricks;

import com.databricks.client.jdbc.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Configuration
public class DatabricksDataSourceConfig {

    // Define your DataSource beans here, each configured for different Databricks SQL warehouses

    @Bean
    @Scope("prototype")
    public JdbcTemplate jdbcTemplate(ObjectProvider<DataSource> dataSourceProvider) {
        // Since this is prototype scoped, a new JdbcTemplate will be created each time it's injected,
        // using the currently available DataSource from the provider.
        return new JdbcTemplate(dataSourceProvider.getObject());
    }

    @Bean
    public MultiDatabricksJdbcTemplate multiDatabricksJdbcTemplate(ObjectProvider<DataSource> dataSourceProvider) {
        // This custom template allows for dynamic selection of the DataSource.
        return new MultiDatabricksJdbcTemplate(dataSourceProvider);
    }

    public static class MultiDatabricksJdbcTemplate extends NamedParameterJdbcTemplate {

        private final ObjectProvider <DataSource> dataSourceObjectProvider;

        public MultiDatabricksJdbcTemplate(ObjectProvider<DataSource> dataSourceObjectProvider) {
            super(dataSourceObjectProvider.getObject());
            this.dataSourceObjectProvider = dataSourceObjectProvider;
        }

        public NamedParameterJdbcOperations getNamedParameterJdbcOperations() {
            // Dynamically obtains a DataSource and creates a new NamedParameterJdbcTemplate per operation
            DataSource dataSource = dataSourceObjectProvider.getObject();
            System.out.println("Creating a new NamedParameterJdbcTemplate bean.");
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }
}
