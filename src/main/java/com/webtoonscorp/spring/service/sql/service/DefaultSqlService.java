package com.webtoonscorp.spring.service.sql.service;

import com.webtoonscorp.spring.service.sql.reader.JaxbXmlSqlReader;
import com.webtoonscorp.spring.service.sql.registry.HashMapSqlRegistry;

public class DefaultSqlService extends BaseSqlService {

    public DefaultSqlService() {

        setSqlRegistry(new HashMapSqlRegistry());
        setSqlReader(new JaxbXmlSqlReader());
    }
}
