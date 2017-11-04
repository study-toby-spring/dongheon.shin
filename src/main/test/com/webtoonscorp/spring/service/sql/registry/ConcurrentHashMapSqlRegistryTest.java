package com.webtoonscorp.spring.service.sql.registry;

import com.webtoonscorp.spring.exception.SqlRetrievalFailureException;
import com.webtoonscorp.spring.exception.SqlUpdateFailureException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConcurrentHashMapSqlRegistryTest {

    private String[] keys = new String[]{ "key1", "key2", "key3" };
    private String[] sqls = new String[]{ "sql1", "sql2", "sql3" };

    UpdatableSqlRegistry registry;

    @Before
    public void setup() {

        registry = new ConcurrentHashMapSqlRegistry();

        for (int i = 0; i < 3; i++) {
            registry.register(keys[i], sqls[i]);
        }
    }

    @Test
    public void find() {

        checkAndFind("sql1", "sql2", "sql3");
    }

    public void checkAndFind(String... expected) {

        assertThat(expected.length, is(sqls.length));

        for (int i = 0; i < 3; i++) {
            assertThat(registry.get(keys[i]), is(expected[i]));
        }
    }

    @Test(expected = SqlRetrievalFailureException.class)
    public void unknownKey() {

        registry.get("key999");
    }

    @Test
    public void updateSingleSql() {

        registry.update(keys[0], "sql1_modified");
        checkAndFind("sql1_modified", "sql2", "sql3");
    }

    @Test
    public void updateMultipleSqls() {

        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(keys[0], "sql1_modified");
        parameters.put(keys[1], "sql2_modified");

        registry.update(parameters);

        checkAndFind("sql1_modified", "sql2_modified", "sql3");
    }

    @Test(expected = SqlUpdateFailureException.class)
    public void updateWithUnknownKey() {

        registry.update("key999", "sql999_modified");
    }
}
