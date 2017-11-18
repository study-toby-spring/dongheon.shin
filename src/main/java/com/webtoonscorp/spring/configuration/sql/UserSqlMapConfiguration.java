package com.webtoonscorp.spring.configuration.sql;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class UserSqlMapConfiguration implements SqlMapConfiguration {

    public Resource getSqlMapResource() {
        return new ClassPathResource("/sql/sqlmap.xml");
    }
}
