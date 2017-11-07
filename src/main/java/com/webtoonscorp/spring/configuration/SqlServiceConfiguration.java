package com.webtoonscorp.spring.configuration;

import com.webtoonscorp.spring.service.sql.registry.EmbeddedDatabaseSqlRegistry;
import com.webtoonscorp.spring.service.sql.registry.SqlRegistry;
import com.webtoonscorp.spring.service.sql.service.OxmSqlService;
import com.webtoonscorp.spring.service.sql.service.SqlService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SqlServiceConfiguration {

    @Bean
    public SqlService sqlService() {

        OxmSqlService sqlService = new OxmSqlService();

        sqlService.setUnmarshaller(unmarshaller());
        sqlService.setSqlRegistry(sqlRegistry());

        return sqlService;
    }

    @Bean
    public Unmarshaller unmarshaller() {

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.webtoonscorp.spring.jaxb");

        return marshaller;
    }

    @Bean
    public SqlRegistry sqlRegistry() {

        EmbeddedDatabaseSqlRegistry registry = new EmbeddedDatabaseSqlRegistry();
        registry.setDataSource(embeddedDatabase());

        return registry;
    }

    @Bean
    public EmbeddedDatabase embeddedDatabase() {

        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("sql/hsqldb/ddl.sql")
                .addScript("sql/hsqldb/dml.sql")
                .build();
    }
}
