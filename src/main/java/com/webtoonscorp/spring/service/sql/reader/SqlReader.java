package com.webtoonscorp.spring.service.sql.reader;

import com.webtoonscorp.spring.service.sql.registry.SqlRegistry;

public interface SqlReader {

    void load(SqlRegistry registry);
}
