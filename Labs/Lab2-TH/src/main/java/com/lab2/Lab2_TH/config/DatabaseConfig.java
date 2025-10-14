package com.lab2.Lab2_TH.config;

import com.lab2.Lab2_TH.properties.DatabaseProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Profile("dev")
    @ConditionalOnExpression("'${spring.datasource.database}'.equals('dev_db')")
    public DataSource devDataSource(DatabaseProperties props) {
        System.out.println("Creating DEV DataSource");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:devdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.setUsername(props.getUsername());
        dataSource.setPassword(props.getPassword());
        return dataSource;
    }

    @Bean
    @Profile("prod")
//    @ConditionalOnExpression("'${spring.datasource.database}'.equals('prod_db')")
    public DataSource prodDataSource(DatabaseProperties props) {
        System.out.println("Creating PROD DataSource");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:proddb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.setUsername(props.getUsername());
        dataSource.setPassword(props.getPassword());
        return dataSource;
    }

//    @Bean
//    @Profile("!dev & !prod")
//    public DataSource defaultDataSource(DatabaseProperties props) {
//        System.out.println("Creating DEFAULT DataSource");
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(props.getDriverClassName() != null ? props.getDriverClassName() : "org.h2.Driver");
//        dataSource.setUrl(props.getUrl() != null ? props.getUrl() : "jdbc:h2:mem:defaultdb");
//        dataSource.setUsername(props.getUsername());
//        dataSource.setPassword(props.getPassword());
//        return dataSource;
//    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}