package org.example.demodatabricks.config;

import com.databricks.client.jdbc.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Properties;

@Configuration
public class DatabricksSqlConfig {

//    @Bean
//    public DriverManagerDataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        // Set the JDBC URL for the Databricks SQL warehouse
//        dataSource.setUrl("jdbc:databricks://adb-6315235189843787.7.azuredatabricks.net:443;HttpPath=/sql/1.0/warehouses/3adbcf750da12829");
//        // Use Properties to set your DataSource credentials
//        Properties properties = new Properties();
//        properties.put("PWD", "dapi4d6bea393c0ae2aecff8421283a7405f-2");
//        dataSource.setConnectionProperties(properties);
//
//        // It's a good practice to set the driver class name, but it might be optional depending on the driver
//        dataSource.setDriverClassName("com.databricks.client.jdbc.Driver");
//
//        return dataSource;
//    }

    @Bean
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource) {
        // Create a JdbcTemplate, using the configured DataSource
        return new JdbcTemplate(dataSource);
    }
}
