package com.webtoonscorp.spring.service.sql;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmbeddedDatabaseTest {

    EmbeddedDatabase database;
    JdbcTemplate template;

    String[] keys = new String[] { "key1", "key2" };
    String[] sqls = new String[] { "sql1", "sql2" };

    @Before
    public void setup() {

        database = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("sql/hsqldb/ddl.sql")
                .addScript("sql/hsqldb/dml.sql")
                .build();

        template = new JdbcTemplate(database);
    }

    @Test
    public void initialize() {

        List<Map<String, Object>> list = template.queryForList("SELECT * FROM SQLMAP ORDER BY KEY_");

        assertThat(list.size(), is(keys.length));

        for (int i = 0; i < list.size(); i ++) {

            Map<String, Object> item = list.get(i);
            assertSql(item, keys[i], sqls[i]);
        }
    }

    public void assertSql(Map<String, Object> item, String key, String sql) {

        assertThat((String) item.get("KEY_"), is(key));
        assertThat((String) item.get("SQL_"), is(sql));
    }

    @Test
    public void insert() {

        String key = "key3";
        String sql = "sql3";

        template.update("INSERT INTO SQLMAP(KEY_, SQL_) VALUES (?, ?)", key, sql);

        assertThat(template.queryForObject("SELECT COUNT(*) FROM SQLMAP", Integer.class), is(3));
    }

}
