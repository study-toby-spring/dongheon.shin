package com.webtoonscorp.spring.service.sql.registry;

import com.webtoonscorp.spring.exception.SqlRetrievalFailureException;

public interface SqlRegistry {

    void register(String key, String sql);
    String get(String key) throws SqlRetrievalFailureException;
}
