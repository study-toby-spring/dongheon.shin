package com.webtoonscorp.spring.service.sql.registry;

import com.webtoonscorp.spring.exception.SqlUpdateFailureException;
import org.junit.After;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.fail;

public class EmbeddedDatabaseSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {

    EmbeddedDatabase dataSource;
    EmbeddedDatabaseSqlRegistry registry;

    public UpdatableSqlRegistry createRegistry() {

        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("sql/hsqldb/ddl.sql")
                .addScript("sql/hsqldb/dml.sql")
                .build();

        registry = new EmbeddedDatabaseSqlRegistry();
        registry.setDataSource(dataSource);

        return registry;
    }

    @Test
    public void transaction() {

        checkAndFind(getSqls());

        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(getKeys()[0], "sql1_modified");
        parameters.put("key999", "sql999_modified");

        try {

            registry.update(parameters);
            fail();
        }
        catch (SqlUpdateFailureException e) {

            checkAndFind(getSqls());
        }
    }

    @After
    public void teardown() {

        dataSource.shutdown();
    }
}
