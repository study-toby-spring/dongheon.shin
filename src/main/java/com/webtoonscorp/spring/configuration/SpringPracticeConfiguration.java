package com.webtoonscorp.spring.configuration;

import com.mysql.jdbc.Driver;
import com.webtoonscorp.spring.service.sql.service.OxmSqlService;
import com.webtoonscorp.spring.service.sql.service.SqlService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.webtoonscorp.spring")
@Import({ SqlServiceConfiguration.class, SpringPracticeProductionConfiguration.class, SpringPracticeTestConfiguration.class })
public class SpringPracticeConfiguration {

    @Bean
    public DataSource dataSource() {

        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring");
        dataSource.setUsername("user");
        dataSource.setPassword("user_password");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());

        return transactionManager;
    }

    @Bean
    public SqlService sqlService() {
        return new OxmSqlService();
    }
}
