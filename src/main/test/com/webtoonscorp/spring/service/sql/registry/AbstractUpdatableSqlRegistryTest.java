package com.webtoonscorp.spring.service.sql.registry;

import com.webtoonscorp.spring.exception.SqlRetrievalFailureException;
import com.webtoonscorp.spring.exception.SqlUpdateFailureException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class AbstractUpdatableSqlRegistryTest {

    private String[] keys = new String[]{ "key1", "key2", "key3" };

    protected String[] getKeys() {

        return keys;
    }

    private String[] sqls = new String[]{ "sql1", "sql2", "sql3" };

    protected String[] getSqls() {

        return sqls;
    }

    private UpdatableSqlRegistry registry;

    public abstract UpdatableSqlRegistry createRegistry();

    @Before
    public void setup() {

        registry = createRegistry();
    }

    @Test
    public void find() {

        checkAndFind("sql1", "sql2", "sql3");
    }

    protected void checkAndFind(String... expected) {

        assertThat(expected.length, is(getSqls().length));

        for (int i = 0; i < 3; i++) {
            assertThat(registry.get(getKeys()[i]), is(expected[i]));
        }
    }

    @Test(expected = SqlRetrievalFailureException.class)
    public void unknownKey() {

        registry.get("key999");
    }

    @Test
    public void updateSingleSql() {

        registry.update(getKeys()[0], "sql1_modified");
        checkAndFind("sql1_modified", "sql2", "sql3");
    }

    @Test
    public void updateMultipleSqls() {

        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(getKeys()[0], "sql1_modified");
        parameters.put(getKeys()[1], "sql2_modified");

        registry.update(parameters);

        checkAndFind("sql1_modified", "sql2_modified", "sql3");
    }

    @Test(expected = SqlUpdateFailureException.class)
    public void updateWithUnknownKey() {

        registry.update("key999", "sql999_modified");
    }
}
