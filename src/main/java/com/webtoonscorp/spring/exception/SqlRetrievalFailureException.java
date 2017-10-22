package com.webtoonscorp.spring.exception;

public class SqlRetrievalFailureException extends RuntimeException {

    public SqlRetrievalFailureException(String message) {

        super(message);
    }
}
