package com.webtoonscorp.spring.service;

import com.webtoonscorp.spring.exception.SqlRetrievalFailureException;

public interface SqlService {

    String getSql(String key) throws SqlRetrievalFailureException;
}
