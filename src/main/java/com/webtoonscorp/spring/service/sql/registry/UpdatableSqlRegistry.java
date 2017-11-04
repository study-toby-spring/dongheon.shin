package com.webtoonscorp.spring.service.sql.registry;

import com.webtoonscorp.spring.exception.SqlUpdateFailureException;

import java.util.Map;

public interface UpdatableSqlRegistry extends SqlRegistry {

    void update(String key, String sql) throws SqlUpdateFailureException;
    void update(Map<String, String> sqlMap) throws SqlUpdateFailureException;
}
