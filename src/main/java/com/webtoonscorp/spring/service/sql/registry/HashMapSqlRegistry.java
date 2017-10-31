package com.webtoonscorp.spring.service.sql.registry;

import com.webtoonscorp.spring.exception.SqlRetrievalFailureException;

import java.util.HashMap;
import java.util.Map;

public class HashMapSqlRegistry implements SqlRegistry {

    private Map<String, String> map = new HashMap<String, String>();

    public void register(String key, String sql) {

        map.put(key, sql);
    }

    public String get(String key) throws SqlRetrievalFailureException {

        String sql = map.get(key);

        if (sql == null) {
            throw new SqlRetrievalFailureException(key + "에 대한 SQL을 찾을 수 없습니다");
        }

        return sql;
    }
}
